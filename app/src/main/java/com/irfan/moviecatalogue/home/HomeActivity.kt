package com.irfan.moviecatalogue.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.irfan.moviecatalogue.R
import com.irfan.moviecatalogue.core.data.Resource
import com.irfan.moviecatalogue.core.domain.model.Movie
import com.irfan.moviecatalogue.core.ui.MovieAdapter
import com.irfan.moviecatalogue.databinding.ActivityHomeBinding
import com.irfan.moviecatalogue.detail.DetailActivity
import com.irfan.moviecatalogue.utils.Commons.hide
import com.irfan.moviecatalogue.utils.Commons.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        setUpToolbar()
        showMovieList()
        binding.noConnection.btnRefresh.setOnClickListener(this)
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    private fun setUpBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayShowTitleEnabled(true)
        }
    }

    private fun showMovieList() {
        val movieAdapter = MovieAdapter().apply {
            onItemClick = {
                navigateToDetailScreen(it)
            }
        }

        viewModel.movie.observe(this, { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        showLoadingIndicator()
                    }
                    is Resource.Error -> {
                        hideLoadingIndicator()
                        binding.noConnection.root.show()
                        binding.rvMovie.hide()
                    }
                    is Resource.Success -> {
                        hideLoadingIndicator()
                        binding.noConnection.root.hide()
                        binding.rvMovie.show()
                        movieAdapter.setData(movie.data)
                    }
                }
            }
        })

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun navigateToDetailScreen(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }

    private fun showLoadingIndicator() {
        with(binding) {
            shimmer.apply {
                show()
                startShimmer()
            }
            noConnection.root.hide()
            rvMovie.hide()
        }
    }

    private fun hideLoadingIndicator() {
        binding.shimmer.apply {
            hide()
            stopShimmer()
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.noConnection.btnRefresh -> {
                viewModel.loadData()
            }
        }
    }

    override fun onRefresh() {
        viewModel.loadData()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favourite) {
            val uri = Uri.parse("movieapp://favorite")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        return super.onOptionsItemSelected(item)
    }

}