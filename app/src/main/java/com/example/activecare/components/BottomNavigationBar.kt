package com.example.activecare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.ui.theme.AppTheme

@Composable
fun BottomNavigationBar(
    runClick: () -> Unit = {},
    homeClick: () -> Unit = {},
    personClick: () -> Unit = {},
    activeIndex: Int = 2,
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(AppTheme.colors.LightBack)
            .wrapContentHeight(Alignment.CenterVertically),
    ) {
        Icon(
            painter =
            if (activeIndex == 1) {
                painterResource(id = R.drawable.steps_filled)
            } else {
                painterResource(id = R.drawable.steps_outlined)
            },
            contentDescription = "go workout",
            tint = AppTheme.colors.LightText,
            modifier = Modifier
                .padding(start = 48.dp)
                .size(32.dp)
                .scale(scaleX = -1f, scaleY = 1f)
                .align(Alignment.TopStart)
                .clickable(onClick = runClick),
        )
        Icon(
            imageVector = if (activeIndex == 2) Icons.Filled.Home else Icons.Outlined.Home,
            contentDescription = "go home",
            tint = AppTheme.colors.LightText,
            modifier = Modifier
                .padding()
                .size(32.dp)
                .align(Alignment.Center)
                .clickable(onClick = homeClick),
        )
        Icon(
            imageVector = if (activeIndex == 3) Icons.Filled.Person else Icons.Outlined.Person,
            contentDescription = "go person",
            tint = AppTheme.colors.LightText,
            modifier = Modifier
                .padding(end = 48.dp)
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .clickable(onClick = personClick),
        )
    }
}