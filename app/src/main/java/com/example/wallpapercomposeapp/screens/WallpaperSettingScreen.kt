package com.example.wallpapercomposeapp.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.helpers.WallpaperUtils
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.viewmodel.FavoritesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperImage(
    imageUrl: String,
    imageId: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val favoriteViewModel: FavoritesViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    var favIcon by remember(isFavorite) {
        mutableStateOf(if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder)
    }
    val sheetState = rememberModalBottomSheetState()
    val isSheetOpen = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(imageId) {
        val isFav = withContext(Dispatchers.IO) {
            favoriteViewModel.checkFavoriteWallpaper(imageId)
        }
        isFavorite = isFav
        favIcon = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    }

    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    val gradientBrush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height * 0.75f,
                        endY = size.height
                    )
                    drawRect(brush = gradientBrush)
                })
        Image(
            painter = painterResource(id = R.drawable.backbutton),
            contentDescription = null,
            modifier = modifier
                .padding(top = 30.dp, start = 25.dp)
                .clickable {
                    navController.popBackStack()
                })
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 40.dp, start = 14.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom
        ) {
            BottomBar(
                imageVector = ImageVector.vectorResource(id = R.drawable.download_button),
                imageText = "Download", onClick = {
                    WallpaperUtils.downloadWallpaper(imageUrl, context)
                })
            BottomBar(imageVector = Icons.Rounded.Share, imageText = "Share", onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    val bitmap = WallpaperUtils.convertUrlToBitmapImage(imageUrl)
                    withContext(Dispatchers.Main) {
                        WallpaperUtils.shareWallpaper(bitmap!!, context)
                    }
                }
            })
            BottomBar(
                imageVector = Icons.Outlined.AccountBox, imageText = "Set Wallpaper",
                onClick = {
                    isSheetOpen.value = true
                    // toastMessage("Setting Wallpaper", context)
                })
            BottomBar(
                imageVector = favIcon, imageText = "Favorite",
                onClick = {
                    coroutineScope.launch {
                        if (!isFavorite) {
                            favIcon = Icons.Filled.Favorite
                            favoriteViewModel.addWallpapersToFavorite(
                                WallpapersDataModel(
                                    0,
                                    imageUrl,
                                    0,
                                    true
                                )
                            ) // Invoke ViewModel function to add wallpaper
                            toastMessage("Added to Favorite", context)
                        } else {
                            toastMessage("Already added", context)
                        }
                    }
                })
        }

    }
    if (isSheetOpen.value) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen.value = false }) {
            Column(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WallpaperSettingText(text = "Set as Home Screen Wallpaper", onClick = { toastMessage("home screen",context)
                isSheetOpen.value=false
                })
                WallpaperSettingText(text = "Set as Lock Screen Wallpaper", onClick = { toastMessage("lock screen",context)
                isSheetOpen.value=false
                })
                WallpaperSettingText(text = "Set on Both", onClick = { toastMessage("both",context)
                isSheetOpen.value=false
                })
            }
        }
    }
}

@Composable
fun BottomBar(imageVector: ImageVector, imageText: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        onClick()
    }) {
        Image(
            imageVector = imageVector,
            contentDescription = stringResource(id = R.string.description),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = imageText,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraLight,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun WallpaperSettingText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 19.sp,
        modifier = Modifier.padding(20.dp).clickable {
                onClick()
            })
}
fun toastMessage(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}