package com.hoangpd15.smartmovie.ui.genresScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangpd15.smartmovie.model.Genres
import com.hoangpd15.smartmovie.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class GenresViewModel  : ViewModel(){
    private val _listGenres = MutableLiveData<List<Genres>>()
    val listGenres: LiveData<List<Genres>> get() = _listGenres

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchListGenres()
    }

    private fun fetchListGenres() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val listGenres = RetrofitInstance.apiListGenres.getListGenres().genres
                _listGenres.postValue(listGenres)
            } catch (e: Exception) {
                _listGenres.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}