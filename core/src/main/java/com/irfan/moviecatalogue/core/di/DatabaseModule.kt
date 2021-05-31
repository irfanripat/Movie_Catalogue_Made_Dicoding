package com.irfan.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.irfan.moviecatalogue.core.data.source.local.room.MovieDao
import com.irfan.moviecatalogue.core.data.source.local.room.MovieDatabase
import com.irfan.moviecatalogue.core.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration()
        .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("irfan".toCharArray())))
        .build()

    @Provides
    fun provideTourismDao(database: MovieDatabase): MovieDao = database.movieDao()
}