package com.example.wallpapercomposeapp.api

import com.example.wallpapercomposeapp.model.WallpapersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpapersApi {
    @GET("api/")
    suspend fun getImages(
        @Query("key") key: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("q") type: String,
        @Query("editors_choice") editorChoice:Boolean
    ): Response<WallpapersModel>
}