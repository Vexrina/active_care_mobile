package com.example.activecare.screens.networkerror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.TextComponent
import com.example.activecare.ui.theme.AppTheme

@Composable
fun NetworkErrorScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = stringResource(id = R.string.networkErrorScreen),
            modifier = Modifier.width(260.dp),
            textSize = 22.sp,
            textColor = AppTheme.colors.LightText,
        )
    }
}

@Preview
@Composable
fun ShowNetworkErrorScreen(){
    NetworkErrorScreen()
}