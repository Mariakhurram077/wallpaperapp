package com.example.wallpapercomposeapp.repository

import com.example.wallpapercomposeapp.api.WallpapersApi
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WallpapersRepository @Inject constructor(private val wallpapersApi: WallpapersApi) {
    private val _wallpapersMutableStateFlow =
        MutableStateFlow<List<WallpapersDataModel>>(emptyList())
    val wallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = _wallpapersMutableStateFlow

    suspend fun getAllWallpapers(key: String, page: Int, perPage: Int, type: String,editorChoice:Boolean) {
        val response = wallpapersApi.getImages(key, page, perPage, type,editorChoice)
        if (response.isSuccessful && response.body() != null) {
            _wallpapersMutableStateFlow.emit(response.body()!!.hits)
        }
    }
}