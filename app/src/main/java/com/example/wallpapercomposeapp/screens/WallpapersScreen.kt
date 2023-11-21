package com.example.wallpapercomposeapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.viewmodel.WallpaperViewModel

@Composable
fun WallpapersScreen(modifier: Modifier=Modifier) {
    val wallpaperViewModel: WallpaperViewModel = hiltViewModel()
    val wallpapersImages: State<List<WallpapersDataModel>> =
        wallpaperViewModel.wallpapersStateFlow.collectAsState()
    Column(modifier = modifier.background(colorResource(id = R.color.backgroundcolor))) {
        TopBar()
        if (wallpapersImages.value.isEmpty()) {
            SimpleCircularProgressComponent()
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
                items(wallpapersImages.value) {
                    WallpaperImageCard(imageUrl = it.largeImageURL)
                    Log.i("imageUrl", "WallpapersScreen: ${it.largeImageURL}")
                }
            })
        }
    }
}

@Composable
fun WallpaperImageCard(imageUrl: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(3.dp)
            .size(200.dp)
            .clip(Shapes().medium),
    )
}

@Composable
fun SimpleCircularProgressComponent(modifier: Modifier=Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = colorResource(id = R.color.white),
            strokeWidth = Dp(value = 4F)
        )
    }
}

@Composable
fun TopBar(modifier: Modifier=Modifier) {
    Row(modifier = modifier.background(Color.Black).fillMaxWidth()) {
        Text(text = stringResource(id = R.string.wallpapers),
            modifier = Modifier.padding(start = 20.dp, top = 14.dp, bottom = 14.dp).weight(1f),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp)
        Image(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(id = R.string.description),
            modifier = Modifier.size(50.dp).padding(end = 20.dp).align(Alignment.CenterVertically),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}

@Composable
fun tabLayout(modifier: Modifier=Modifier){
    val tabItems= listOf("Trending","Categories","Premium")
}