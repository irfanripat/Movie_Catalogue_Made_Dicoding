package com.irfan.moviecatalogue.detail


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.irfan.moviecatalogue.R
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.utils.Constants.IMAGE_URL
import com.irfan.moviecatalogue.databinding.ActivityDetailBinding
import com.irfan.moviecatalogue.utils.Commons.rating
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private var detailMovie: Movie? = null

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setupActionBar()
        detailMovie = intent.getParcelableExtra(EXTRA_MOVIE)
        showDetailMovie(detailMovie)
    }

    private fun setUpBinding() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        binding.appBar.bringToFront()
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun showDetailMovie(movie: Movie?) {
        movie?.let {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            binding.tvRelease.text = it.releaseDate
            binding.ratingBar.rating = it.score.rating()
            Glide.with(this@DetailActivity).apply {
                load(IMAGE_URL + it.posterPath)
                    .error(R.drawable.default_placeholder)
                    .into(binding.imagePoster)

                load(IMAGE_URL + it.backdropPath)
                    .error(R.drawable.default_placeholder)
                    .into(binding.imageBg)
            }
            var isFavorite = it.isFavorite
            setFavoriteIcon(isFavorite)
            binding.btnFavorite.setOnClickListener {
                isFavorite = !isFavorite
                viewModel.setFavoriteMovie(movie, isFavorite)
                setFavoriteIcon(isFavorite)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_filled_color)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border_color)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_share) {
            shareData()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun shareData() {
        detailMovie?.let {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT, """
                ${it.title} (${it.releaseDate})


                ${it.overview}
                """.trimIndent())
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}