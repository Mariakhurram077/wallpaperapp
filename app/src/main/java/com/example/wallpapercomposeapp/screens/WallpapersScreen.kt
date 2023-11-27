package com.example.wallpapercomposeapp.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wallpapercomposeapp.R
import com.example.wallpapercomposeapp.data.CategoriesData
import com.example.wallpapercomposeapp.helpers.BackPressHelper
import com.example.wallpapercomposeapp.model.TabItemModel
@Composable
fun WallpapersScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(modifier = modifier.background(colorResource(id = R.color.backgroundcolor))) {
        TopBar(navController)
        TabLayout(navController)
    }

    BackHandler {
        BackPressHelper.onBackPressing(context)
    }
}
@Composable
fun TopBar(navController: NavController,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.Black)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.wallpapers),
            modifier = Modifier
                .padding(start = 24.dp, top = 18.dp, bottom = 14.dp)
                .weight(1f),
            color = Color.White, fontWeight = FontWeight.Bold, fontSize = 23.sp
        )
        Image(
            imageVector = Icons.Outlined.Search,
            contentDescription = stringResource(id = R.string.description),
            modifier = Modifier
                .size(50.dp)
                .padding(end = 20.dp)
                .align(Alignment.CenterVertically).clickable {
                        navController.navigate("SearchWallpapers")
                },
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(navController: NavController, modifier: Modifier = Modifier) {
    val tabItems = listOf(TabItemModel("Trending"), TabItemModel("Categories"), TabItemModel("Favorites"))
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color.Black,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp)
                        .padding(horizontal = 36.dp)
                        .background(
                            color = colorResource(id = R.color.indicatorcolor),
                            shape = RoundedCornerShape(18.dp)
                        )
                )
            }) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        when (index) {
                            selectedTabIndex -> {
                                Text(
                                    text = item.title,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            else -> {
                                Text(
                                    text = item.title,
                                    color = colorResource(id = R.color.unselectedcolor),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    })
            }
        }
        HorizontalPager(
            state = pagerState, modifier = modifier
                .fillMaxSize()
                .weight(1f)
        ) { index ->
            when (index) {
                0 -> TrendingWallpapers(navController)
                1 -> Categories(categoriesList = CategoriesData.loadCategories(), navController)
                2 -> FavoriteWallpapers(navController = navController)
            }
        }
    }
}

