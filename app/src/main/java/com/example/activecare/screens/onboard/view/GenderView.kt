package com.example.activecare.screens.onboard.view

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.TextComponent
import com.example.activecare.screens.onboard.models.OnboardViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun GenderView(
    viewState: OnboardViewState,
    navController: NavController,
    onGenderClicked: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = stringResource(id = R.string.genderChooseHint),
            textColor = AppTheme.colors.LightText,
            textSize = 30.sp,
            modifier = Modifier
        )
        ButtonComponent(
            text = stringResource(id = R.string.maleButtonText),
            modifier = Modifier
                .padding(top = 80.dp)
                .height(56.dp)
                .width(328.dp),
            buttonColors = if (viewState.female != null && !viewState.female) AppTheme.colors.DarkText else AppTheme.colors.LightBack,
        ) {
            onGenderClicked.invoke(false)
        }
        ButtonComponent(
            text = stringResource(id = R.string.femaleButtonText),
            modifier = Modifier
                .padding(top = 80.dp)
                .height(56.dp)
                .width(328.dp),
            buttonColors = if (viewState.female != null && viewState.female) AppTheme.colors.DarkText else AppTheme.colors.LightBack,
        ) {
            onGenderClicked.invoke(true)
        }
    }
}