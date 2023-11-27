package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.viewmodel.WallpaperViewModel
@Composable
fun CategoriesWallpapers(modifier: Modifier = Modifier,categoryName:String,navController: NavController) {
    val wallpaperViewModel: WallpaperViewModel = hiltViewModel()
    val wallpapersImages: State<List<WallpapersDataModel>> =
        wallpaperViewModel.wallpapersStateFlow.collectAsState()
    wallpaperViewModel.updateWallpapersType(categoryName)
    Column(modifier = modifier.background(Color.Black)) {
        CategoriesTopBar(categoryName)
        if (wallpapersImages.value.isEmpty()){
            SimpleCircularProgressComponent()
        }
        else{
        WallpapersListColumn(wallpapersImages =wallpapersImages.value,navController )
    }}
}
@Composable
fun CategoriesTopBar(categoryName: String) {
    Row(modifier = Modifier.padding(start = 12.dp, top = 12.dp)) {
        Image(
            painter = painterResource(id = R.drawable.backbutton),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = categoryName,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 25.dp)
        )
    }
}