package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.viewmodel.WallpaperViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun TrendingWallpapers(navController: NavController) {
    val wallpaperViewModel: WallpaperViewModel = hiltViewModel()
    val wallpapersImages: State<List<WallpapersDataModel>> =
        wallpaperViewModel.wallpapersStateFlow.collectAsState()
    if (wallpapersImages.value.isEmpty()) {
        SimpleCircularProgressComponent()
    } else {
        WallpapersListColumn(
            wallpapersImages = wallpapersImages.value,
            navController = navController)
    }}
@Composable
fun WallpapersListColumn(
    wallpapersImages: List<WallpapersDataModel>,
    navController: NavController
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        content = {
        items(wallpapersImages) {
            WallpaperImageCard(
                imageUrl = it.largeImageURL,
                it.id,
                onClick = { imageUrl, isFavorite ->
                    val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate("WallpaperSettingScreen/${encodedUrl}/${isFavorite}")
                })
        }
    })
}
@Composable
fun WallpaperImageCard(
    imageUrl: String,
    imageId: Int,
    onClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build(),
        contentDescription = stringResource(R.string.description),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(3.dp)
            .size(200.dp)
            .clip(Shapes().medium)
            .clickable {
                onClick(imageUrl, imageId)
            },
    )
}
@Composable
fun SimpleCircularProgressComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = colorResource(id = R.color.white),
            strokeWidth = Dp(value = 4F)
        )
    }
}