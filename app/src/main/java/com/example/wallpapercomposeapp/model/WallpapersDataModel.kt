package com.example.wallpapercomposeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteWallpapers")
class WallpapersDataModel(
    val id: Int,
    val largeImageURL: String,
    @PrimaryKey(autoGenerate = true)
    val primaryKeyId: Int,
    var isFavorite:Boolean=false
)