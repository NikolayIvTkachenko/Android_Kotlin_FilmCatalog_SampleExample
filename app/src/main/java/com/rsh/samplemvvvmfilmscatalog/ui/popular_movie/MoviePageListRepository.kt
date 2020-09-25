package com.rsh.samplemvvvmfilmscatalog.ui.popular_movie

import com.rsh.samplemvvvmfilmscatalog.data.vo.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rsh.samplemvvvmfilmscatalog.data.api.POST_PER_PAGE
import com.rsh.samplemvvvmfilmscatalog.data.api.TheMovieDBInterface
import com.rsh.samplemvvvmfilmscatalog.data.repository.MovieDataSource
import com.rsh.samplemvvvmfilmscatalog.data.repository.MovieDataSourceFactory
import com.rsh.samplemvvvmfilmscatalog.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePageListRepository(private val apiService: TheMovieDBInterface) {

    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePageList
    }

    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }


}