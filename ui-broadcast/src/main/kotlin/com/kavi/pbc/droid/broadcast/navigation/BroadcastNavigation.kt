package com.kavi.pbc.droid.broadcast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kavi.pbc.droid.broadcast.ui.create.BroadcastCreate
import com.kavi.pbc.droid.broadcast.ui.list.BroadcastMessageList
import javax.inject.Inject

class BroadcastNavigation @Inject constructor() {

    @Inject
    lateinit var broadcastCreate: BroadcastCreate

    @Inject
    lateinit var broadcastMessageList: BroadcastMessageList

    @Composable
    fun BroadcastNavGraph() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "broadcast/create") {
            composable(route = "broadcast/create") {
                broadcastCreate.BroadcastCreateUI()
            }
            composable(route = "broadcast/message-list") {
                broadcastMessageList.BroadcastMessageListUI(navController)
            }
        }
    }
}