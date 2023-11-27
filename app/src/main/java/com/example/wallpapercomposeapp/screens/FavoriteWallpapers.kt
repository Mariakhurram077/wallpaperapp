package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.viewmodel.FavoritesViewModel
@Composable
fun FavoriteWallpapers(navController:NavController,modifier: Modifier=Modifier){
    val favoriteViewModel: FavoritesViewModel = hiltViewModel()
    val favoriteWallpapers=favoriteViewModel.favoriteWallpapersStateFlow.collectAsState()
    Column(verticalArrangement = Arrangement.Top, modifier = modifier.fillMaxSize().padding(top = 3.dp)) {
        if (favoriteWallpapers.value.isEmpty()) {
            SimpleCircularProgressComponent()
        } else {
            WallpapersListColumn(wallpapersImages = favoriteWallpapers.value , navController = navController )
        }
    }
}