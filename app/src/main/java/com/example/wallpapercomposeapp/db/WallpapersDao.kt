package com.example.wallpapercomposeapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import kotlinx.coroutines.flow.Flow
@Dao
interface WallpapersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(wallpapersDataModel: WallpapersDataModel)
    @Query("SELECT * FROM FavoriteWallpapers")
    fun readFavouriteWallpaper(): Flow<List<WallpapersDataModel>>

    @Query("SELECT * FROM FavoriteWallpapers WHERE id = :itemImageId")
    fun checkFavoriteWallpaper(itemImageId: Int): WallpapersDataModel?
}