package com.example.wallpapercomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpapercomposeapp.helpers.Constants
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.repository.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WallpaperViewModel @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
) : ViewModel() {
    val wallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = wallpapersRepository.wallpapersStateFlow

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wallpapersRepository.getAllWallpapers(Constants.api_KEY, 1, 200, "nature",true)
            }
        }
    }
}