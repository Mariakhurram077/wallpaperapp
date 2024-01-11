package com.example.wallpapercomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpapercomposeapp.helpers.Constants
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.repository.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
) : ViewModel() {
    private val _wallpapersMutableStateFlow =
        MutableStateFlow<List<WallpapersDataModel>>(emptyList())
    val wallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = _wallpapersMutableStateFlow
        private var job: Job? = null

    fun updateWallpapersType(newType: String) {
        viewModelScope.launch {
            getAllWallpapers(newType)
        }
    }

    init {
        getAllWallpapers("wallpaper")
    }
     private fun getAllWallpapers(type: String) {
        job?.cancel()
        job=viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val wallpapersList=wallpapersRepository.getAllWallpapers(
                    Constants.api_KEY, 1, 200, type, true
                )
                _wallpapersMutableStateFlow.emit(wallpapersList)
            }
        }
    }

}