package com.rsh.samplemvvvmfilmscatalog.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rsh.samplemvvvmfilmscatalog.data.api.TheMovieDBInterface
import com.rsh.samplemvvvmfilmscatalog.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(private val apiService : TheMovieDBInterface,
                             private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}