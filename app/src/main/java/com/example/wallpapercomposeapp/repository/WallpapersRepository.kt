package com.example.wallpapercomposeapp.repository

import android.content.Context
import android.widget.Toast
import com.example.wallpapercomposeapp.api.WallpapersApi
import com.example.wallpapercomposeapp.db.WallpaperDatabase
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
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
    suspend fun getAllWallpapers(
        key: String, page: Int, perPage: Int,
        type: String, editorChoice: Boolean
    ):List<WallpapersDataModel> {
        val wallpapersList= mutableListOf<WallpapersDataModel>()
        try {
            val response = withTimeout(30000) {
                wallpapersApi.getImages(key, page, perPage, type, editorChoice)
            }
            if (response.isSuccessful && response.body() != null) {
                wallpapersList.addAll(response.body()!!.hits)
            }
        } catch (e: TimeoutCancellationException) {
            Toast.makeText(context, "Request timed out", Toast.LENGTH_SHORT).show()
           // _wallpapersMutableStateFlow.emit(emptyList())
        } catch (e: Exception) {
            //_wallpapersMutableStateFlow.emit(emptyList())
        }
        return wallpapersList
    }

    suspend fun addWallpapersToFavorite(wallpapersDataModel: WallpapersDataModel) {
        wallpaperDatabase.wallpapersDao().addFavorite(wallpapersDataModel)
    }

    suspend fun readFavoriteWallpapers(): Flow<List<WallpapersDataModel>> = withContext(Dispatchers.IO) {
        return@withContext wallpaperDatabase.wallpapersDao().readFavouriteWallpaper()
    }

    fun checkFavoriteWallpaper(itemImageId: Int): Boolean {
        val favoriteWallpaper =
            wallpaperDatabase.wallpapersDao().checkFavoriteWallpaper(itemImageId)
        return favoriteWallpaper != null
    }
}

