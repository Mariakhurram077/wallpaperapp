package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box {
        val splashImage = painterResource(id = R.drawable.splashwallpaper)
        Image(
            painter = splashImage,
            contentDescription = stringResource(id = R.string.description),
            contentScale = ContentScale.FillBounds,
            alpha = 0.8f
        )
        Column(
            modifier.align(Alignment.Center)
        ) {
            SplashText(wallpaperText = "4k")
            SplashText(wallpaperText = "Wallpapers")
        }
    }

    LaunchedEffect(key1 = Unit){
        navigateToMainScreen(navController)
    }
}

@Composable
fun SplashText(wallpaperText: String, modifier: Modifier = Modifier) {
    Text(
        text = wallpaperText,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(1.dp).fillMaxWidth(),
        color = colorResource(id = R.color.white),
        fontSize = 35.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = TextUnit.Unspecified,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = FontFamily.Serif
    )
}

private fun navigateToMainScreen(navController: NavController) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(3000)
        navController.navigate("MainScreen")
    }
}