package com.example.activecare.screens.onboard.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.screens.onboard.models.OnboardViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun ChooseView(
    viewState: OnboardViewState,
    onSignUp: () -> Unit,
    onLogIn: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
        TextComponent(
            text = stringResource(id = R.string.greetings),
            modifier = Modifier
                .padding(top = 64.dp),
            textSize = 22.sp,
            textColor = AppTheme.colors.LightText
        )
        ButtonComponent(
            text = stringResource(id = R.string.login),
            modifier = Modifier
                .padding(top = 40.dp)
                .width(327.dp)
                .height(56.dp),
            onClick = onLogIn
        )
        ButtonComponent(
            text = stringResource(id = R.string.signUp),
            modifier = Modifier
                .padding(top = 40.dp)
                .width(327.dp)
                .height(56.dp),
            onClick = onSignUp
        )
    }
}