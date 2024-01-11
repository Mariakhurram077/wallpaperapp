package com.example.wallpapercomposeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.repository.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val wallpapersRepository: WallpapersRepository) :
    ViewModel() {
    private var _favoriteWallpapersStateFlow =
        MutableStateFlow<List<WallpapersDataModel>>(emptyList())
    val favoriteWallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = _favoriteWallpapersStateFlow

    fun addWallpapersToFavorite(wallpapersDataModel: WallpapersDataModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wallpapersRepository.addWallpapersToFavorite(wallpapersDataModel)
            }
        }
    }
    fun readFavoriteWallpapers() {
        viewModelScope.launch {
            val favoriteWallpapers = wallpapersRepository.readFavoriteWallpapers()
            favoriteWallpapers.collect{
                _favoriteWallpapersStateFlow.emit(it)
            }
        }
    }
    init {
        readFavoriteWallpapers()
    }
    fun checkFavoriteWallpaper(itemImageId: Int): Boolean {
        return wallpapersRepository.checkFavoriteWallpaper(itemImageId)
    }
}