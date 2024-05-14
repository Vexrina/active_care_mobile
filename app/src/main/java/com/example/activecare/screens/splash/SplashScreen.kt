package com.example.activecare.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.ui.theme.AppTheme

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navController: NavController,
) {
    val isLoad = viewModel.isLoading.collectAsState()
    val nextScreen = viewModel.nextScreen.collectAsState()
    if (isLoad.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.LightBack),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.splash_pic),
                contentDescription = "splash picture",
                modifier = Modifier
                    .width(275.dp)
                    .height(300.dp)
            )
        }
    } else {
        navController.navigate(nextScreen.value)
    }
}