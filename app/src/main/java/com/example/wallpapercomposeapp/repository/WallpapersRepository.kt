package com.example.wallpapercomposeapp.repository

import android.content.Context
import android.widget.Toast
import com.example.wallpapercomposeapp.api.WallpapersApi
import com.example.wallpapercomposeapp.db.WallpaperDatabase
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class WallpapersRepository @Inject constructor(
    private val wallpapersApi: WallpapersApi,
    @ApplicationContext private val context: Context,
    private val wallpaperDatabase: WallpaperDatabase
) {
    private val _wallpapersMutableStateFlow =
        MutableStateFlow<List<WallpapersDataModel>>(emptyList())
    val wallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = _wallpapersMutableStateFlow

    private val _favoriteWallpapersMutableStateFlow =
        MutableStateFlow<List<WallpapersDataModel>>(emptyList())
    val favoriteWallpapersStateFlow: StateFlow<List<WallpapersDataModel>>
        get() = _favoriteWallpapersMutableStateFlow

    suspend fun getAllWallpapers(
        key: String, page: Int, perPage: Int,
        type: String, editorChoice: Boolean
    ) {
        try {
            val response = withTimeout(30000) {
                wallpapersApi.getImages(key, page, perPage, type, editorChoice)
            }
            if (response.isSuccessful && response.body() != null) {
                _wallpapersMutableStateFlow.emit(response.body()!!.hits)
            }
        } catch (e: TimeoutCancellationException) {
            Toast.makeText(context, "Request timed out", Toast.LENGTH_SHORT).show()
            _wallpapersMutableStateFlow.emit(emptyList())
        } catch (e: Exception) {
            _wallpapersMutableStateFlow.emit(emptyList())
        }
    }

    suspend fun addWallpapersToFavorite(wallpapersDataModel: WallpapersDataModel) {
        wallpaperDatabase.wallpapersDao().addFavorite(wallpapersDataModel)
    }
    suspend fun readFavoriteWallpapers() {
        val favorites = withContext(Dispatchers.IO) {
            wallpaperDatabase.wallpapersDao().readFavouriteWallpaper()
        }
        _favoriteWallpapersMutableStateFlow.emit(favorites)
    }
    fun checkFavoriteWallpaper(itemImageId: Int): Boolean{
        val favoriteWallpaper = wallpaperDatabase.wallpapersDao().checkFavoriteWallpaper(itemImageId)
        return favoriteWallpaper != null
    }
}

