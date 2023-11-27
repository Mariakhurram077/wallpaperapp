package com.example.wallpapercomposeapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wallpapercomposeapp.data.CategoriesData
import com.example.wallpapercomposeapp.screens.Categories
import com.example.wallpapercomposeapp.screens.CategoriesWallpapers
import com.example.wallpapercomposeapp.screens.SplashScreen
import com.example.wallpapercomposeapp.screens.WallpaperImage
import com.example.wallpapercomposeapp.screens.WallpapersScreen
import com.example.wallpapercomposeapp.ui.theme.WallpaperComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    WallpapersApp()
                }
            }
        }
    }
}
@Composable
fun WallpapersApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable(route = "SplashScreen") {
            SplashScreen(navController)
        }

        composable(route = "MainScreen") {
            WallpapersScreen(navController)
        }

        composable(route = "WallpaperSettingScreen/{imageUrl}/{imageId}", arguments = listOf(
            navArgument("imageUrl") {
                type = NavType.StringType
            },
            navArgument("imageId"){
                type= NavType.IntType
            })) {
            val imageUrl=it.arguments?.getString("imageUrl")
            val imageId=it.arguments?.getInt("imageId")
            WallpaperImage(imageUrl = imageUrl!!,imageId!!,navController)
        }

        composable(route="CategoriesScreen"){
            Categories(categoriesList = CategoriesData.loadCategories(), navController = navController)
        }

        composable(route="CategoriesWallpapers/{categoryName}", arguments = listOf(
            navArgument("categoryName"){
                type= NavType.StringType
            }
        )){
            val categoryName=it.arguments?.getString("categoryName")
            CategoriesWallpapers(categoryName = categoryName!!, navController = navController)
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WallpaperComposeAppTheme {
        //
}
}