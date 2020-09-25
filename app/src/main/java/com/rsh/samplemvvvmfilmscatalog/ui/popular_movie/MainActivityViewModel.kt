package com.rsh.samplemvvvmfilmscatalog.ui.popular_movie

import com.rsh.samplemvvvmfilmscatalog.data.vo.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.rsh.samplemvvvmfilmscatalog.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePageListRepository):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty():Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }

}