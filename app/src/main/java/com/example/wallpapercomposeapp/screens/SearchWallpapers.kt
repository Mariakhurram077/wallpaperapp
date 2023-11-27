package com.example.wallpapercomposeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.model.WallpapersDataModel
import com.example.wallpapercomposeapp.viewmodel.WallpaperViewModel

@Composable
fun SearchWallpapers(navController: NavController) {
    Column(modifier = Modifier.background(Color.Black)) {
        SearchBar(navController)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController:NavController) {
    val wallpaperViewModel: WallpaperViewModel = hiltViewModel()
    var searchInput by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchInput,
        label = { Text(text= "Enter Category to Search", color = Color.White, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.SansSerif) },
        onValueChange = {
            searchInput = it
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = colorResource(id = R.color.indicatorcolor),
        textColor = Color.White
        )
    ,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
    Button(
        onClick = {
            wallpaperViewModel.updateWallpapersType(searchInput)
            wallpaperViewModel.getAllWallpapers()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(text="Search", color = Color.Black, fontSize = 15.sp)
    }
    val wallpapersImages: State<List<WallpapersDataModel>> =
        wallpaperViewModel.wallpapersStateFlow.collectAsState()
    WallpapersListColumn(wallpapersImages = wallpapersImages.value, navController = navController)
}
