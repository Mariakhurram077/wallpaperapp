package com.example.wallpapercomposeapp.data

import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.CategoryModel

object CategoriesData {
    fun loadCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel(R.drawable.abstracts, "Abstract"),
            CategoryModel(R.drawable.nature, "Nature"),
            CategoryModel(R.drawable.cars, "Cars"),
            CategoryModel(R.drawable.fruits, "Fruits"),
            CategoryModel(R.drawable.sky, "Sky"),
            CategoryModel(R.drawable.candles, "Candles"),
            CategoryModel(R.drawable.sea, "Sea"),
            CategoryModel(R.drawable.architecture, "Architecture"),
            CategoryModel(R.drawable.flowers, "Flowers"),
            CategoryModel(R.drawable.colorful, "Colorful"),
            CategoryModel(R.drawable.animals, "Animals"),
            CategoryModel(R.drawable.art, "Art"),
            CategoryModel(R.drawable.astronomy, "Astronomy"),
            CategoryModel(R.drawable.night, "Night"),
            CategoryModel(R.drawable.rain, "Rain"),
            CategoryModel(R.drawable.snow, "Snow"),
            CategoryModel(R.drawable.winter, "Winter")


        )
    }
}