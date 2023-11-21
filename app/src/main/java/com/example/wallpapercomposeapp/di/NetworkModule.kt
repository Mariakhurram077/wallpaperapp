package com.example.wallpapercomposeapp.di

import com.example.wallpapercomposeapp.api.WallpapersApi
import com.example.wallpapercomposeapp.helpers.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesWallpapersApi(retrofit: Retrofit):WallpapersApi{
        return retrofit.create(WallpapersApi::class.java)
    }

}