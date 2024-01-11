package com.example.wallpapercomposeapp.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.CategoryModel
@Composable
fun Categories(
    categoriesList: List<CategoryModel>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(content = {
        items(categoriesList) {
            CategoriesCard(categoryModel = it, onClick = {
                navController.navigate("CategoriesWallpapers/${it}")
                Log.i("categoryName", "Categories: $it")
            })
        }
    })
}
@Composable
fun CategoriesCard(modifier: Modifier = Modifier, categoryModel: CategoryModel,onClick: (String) -> Unit) {
    Box(modifier = modifier
        .fillMaxSize()
        .clickable {
            onClick(categoryModel.categoryName)
        }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(categoryModel.imagePath)
                .crossfade(true).build(),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                .clip(Shapes().medium),
            alpha = 0.5f
        )
        Text(
            text = categoryModel.categoryName,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            modifier = modifier.align(Alignment.Center)
        )
    }
}