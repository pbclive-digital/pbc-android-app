package com.kavi.pbc.droid.splash.ui.splash

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kavi.pbc.droid.lib.common.ui.R
import com.kavi.pbc.droid.splash.ui.SplashViewModel

@Composable
fun SplashUI(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {

    val isNoSupport by viewModel.isNoSupport.collectAsState()
    val isNoConnection by viewModel.isNoConnection.collectAsState()
    val navigateToAuth by viewModel.navigateToAuth.collectAsState()
    val navigateToDashboard by viewModel.navigateToDashboard.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchVersionSupportStatus() // Call your ViewModel function here
    }

    Box(
        modifier = Modifier.Companion.background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PulsarIcon()
        }
    }

    if (isNoSupport) {
        navController.navigate("splash/no-support")
    }

    if (isNoConnection) {
        navController.navigate("splash/no-connection")
    }

    if (navigateToAuth) {
        navController.navigate("splash/to/auth") {
            // Remove SplashUI from backstack
            popUpTo("splash/splash-anim") {
                inclusive = true
            }
        }
    }

    if (navigateToDashboard) {
        navController.navigate("splash/to/dashboard") {
            // Remove SplashUI from backstack
            popUpTo("splash/splash-anim") {
                inclusive = true
            }
        }
    }
}

@Composable
private fun PulsarIcon(pulseCount: Int = 2) {
    var iconSize by remember { mutableStateOf(IntSize(0, 0)) }
    val pulsarRadius = 200f
    val effects: List<Pair<Float, Float>> = List(pulseCount) {
        pulsarBuilder(pulsarRadius = pulsarRadius, size = iconSize.width, it * 500)
    }

    Box(
        contentAlignment = Alignment.Companion.Center,
        modifier = Modifier.Companion.fillMaxSize()
    ) {
        val primaryColor = MaterialTheme.colorScheme.tertiary
        Canvas(Modifier.Companion.fillMaxSize(), onDraw = {
            for (i in 0 until pulseCount) {
                val (radius, alpha) = effects[i]
                drawCircle(color = primaryColor, radius = radius, alpha = alpha)
            }
        })

        Image(
            painter = painterResource(R.drawable.image_dhamma_chakra),
            contentDescription = "Dhamma chakra icon",
            contentScale = ContentScale.Companion.Crop,
            modifier = Modifier.Companion
                .size(pulsarRadius.dp)
                .clip(CircleShape)
                .onGloballyPositioned {
                    if (it.isAttached) {
                        iconSize = it.size
                    }
                }
        )
    }
}

@Composable
private fun pulsarBuilder(pulsarRadius: Float, size: Int, delay: Int): Pair<Float, Float> {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val radius by infiniteTransition.animateFloat(
        initialValue = (size / 2).toFloat(),
        targetValue = size + pulsarRadius,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay),
            repeatMode = RepeatMode.Restart
        ), label = "Animation of growing circles"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(delay + 100),
            repeatMode = RepeatMode.Restart
        ), label = "Animation of color changes of growing circles"
    )

    return radius to alpha
}