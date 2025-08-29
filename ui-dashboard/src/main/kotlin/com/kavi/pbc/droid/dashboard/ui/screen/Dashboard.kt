package com.kavi.pbc.droid.dashboard.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kavi.droid.color.palette.util.ColorUtil
import com.kavi.pbc.droid.dashboard.R
import com.kavi.pbc.droid.dashboard.ui.model.TabItem
import com.kavi.pbc.droid.dashboard.ui.screen.event.Event
import com.kavi.pbc.droid.dashboard.ui.screen.home.Home
import com.kavi.pbc.droid.dashboard.ui.screen.meditation.MeditationUI
import com.kavi.pbc.droid.dashboard.ui.screen.temple.TempleUI
import com.kavi.pbc.droid.lib.common.ui.theme.BottomNavBarHeight
import javax.inject.Inject

class Dashboard @Inject constructor() {

    @Inject
    lateinit var event: Event

    @Inject
    lateinit var home: Home

    @Composable
    fun DashboardUI(navController: NavController) {
        val tabItemList = listOf(
            TabItem(name = "Home", icon = R.drawable.icon_lotus),
            TabItem(name = "Events", icon = R.drawable.icon_event),
            TabItem(name = "Meditation", icon = R.drawable.icon_meditation),
            TabItem(name = "Temple", icon = R.drawable.icon_temple)
        )
        var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Scaffold(
                bottomBar = {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.height(BottomNavBarHeight)
                    ) {
                        tabItemList.forEachIndexed { index, tabItem ->
                            NavigationBarItem(
                                modifier = Modifier
                                    .padding(4.dp),
                                colors = navigationBarColors(),
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                label = { Text(tabItem.name) },
                                icon = {
                                    Icon(
                                        painterResource(id = tabItem.icon),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(45.dp)
                                            .height(45.dp)
                                            .padding(8.dp),
                                    )
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                // Content displayed above the bottom bar
                TabContent(
                    navController = navController,
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .padding(bottom = innerPadding.calculateBottomPadding())
                        .fillMaxSize()
                )
            }
        }
    }

    @Composable
    fun TabContent(
        navController: NavController,
        selectedTabIndex: Int,
        modifier: Modifier = Modifier
    ) {
        when (selectedTabIndex) {
            0 -> home.HomeUI(navController = navController, modifier = modifier)
            1 -> event.EventUI(navController = navController, modifier = modifier)
            2 -> MeditationUI(navController = navController, modifier = modifier)
            3 -> TempleUI(navController = navController, modifier = modifier)
        }
    }

    @Composable
    fun navigationBarColors(): NavigationBarItemColors {
        return NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = ColorUtil.blendColors(Color.Gray, Color.White),
            unselectedTextColor = ColorUtil.blendColors(Color.Gray, Color.White),
            selectedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            disabledIconColor = Color.Gray,
            disabledTextColor = Color.Gray,
        )
    }
}