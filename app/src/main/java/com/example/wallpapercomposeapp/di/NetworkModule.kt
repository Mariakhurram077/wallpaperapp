package com.example.wallpapercomposeapp.di

import android.content.Context
import androidx.room.Room
import com.example.wallpapercomposeapp.api.WallpapersApi
import com.example.wallpapercomposeapp.db.WallpaperDatabase
import com.example.wallpapercomposeapp.helpers.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun providesWallpapersApi(retrofit: Retrofit): WallpapersApi {
        return retrofit.create(WallpapersApi::class.java)
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): WallpaperDatabase {
        return Room.databaseBuilder(context, WallpaperDatabase::class.java, "WallpaperDatabase")
            .build()
    }

}