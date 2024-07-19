package com.mock_project.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mock_project.model.Genres
import com.mock_project.model.dataRemote.RetrofitInstance
import kotlinx.coroutines.launch

class GenresViewModel  : ViewModel(){
    private val _listGenres = MutableLiveData<List<Genres>>()
    val listGenres: LiveData<List<Genres>> get() = _listGenres

    init {
        fetchListGenres()
    }

    private fun fetchListGenres() {
        viewModelScope.launch {
            try {
                val listGenres = RetrofitInstance.apiListGenres.getListGenres().genres
                _listGenres.postValue(listGenres)
            } catch (e: Exception) {
                // Handle error
                Log.d("hoang", "fetchListGenres: ${e.message}")
                _listGenres.postValue(emptyList())
            }
        }
    }
}