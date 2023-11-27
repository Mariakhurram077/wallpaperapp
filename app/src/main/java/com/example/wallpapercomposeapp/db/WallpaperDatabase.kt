package com.example.wallpapercomposeapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallpapercomposeapp.model.WallpapersDataModel
@Database(entities = [WallpapersDataModel::class], version = 1)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpapersDao(): WallpapersDao

}