package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.viewmodel.FavoritesViewModel

@Composable
fun FavoriteWallpapers(navController:NavController,favoriteViewModel: FavoritesViewModel,modifier: Modifier=Modifier){
  //  val favoriteViewModel: FavoritesViewModel = hiltViewModel()
    val favoriteWallpapers=favoriteViewModel.favoriteWallpapersStateFlow.collectAsState()
    Column(verticalArrangement = Arrangement.Top, modifier = modifier
        .fillMaxSize()
        .padding(top = 3.dp)) {
        if (favoriteWallpapers.value.isEmpty()) {
            Text(
                text = "No Favorite Wallpaper",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier=modifier.fillMaxSize().padding(top = 200.dp),
                textAlign = TextAlign.Center,
            )
        } else {
            WallpapersListColumn(wallpapersImages = favoriteWallpapers.value , navController = navController )
        }
    }
}