package com.example.activecare.screens.onboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.activecare.R
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.components.TextFieldComponent
import com.example.activecare.screens.onboard.models.OnboardSubState
import com.example.activecare.screens.onboard.models.OnboardViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun ValueView(
    viewState: OnboardViewState,
    navController: NavController,
    value: String?,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val subState = viewState.onboardSubState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = when (subState) {
                OnboardSubState.Choose -> ""
                OnboardSubState.Gender -> ""
                OnboardSubState.Weight -> stringResource(id = R.string.onboardHintWeight)
                OnboardSubState.Height -> stringResource(id = R.string.onboardHintHeight)
                OnboardSubState.Age -> stringResource(id = R.string.onboardHintAge)
            },
            modifier = Modifier
                .width(300.dp),
            textSize = 30.sp,
            textColor = AppTheme.colors.LightText
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldComponent(
                value = value ?: "",
                onValueChange = { onValueChange(it) },
                label = when (subState) {
                    OnboardSubState.Choose -> ""
                    OnboardSubState.Gender -> ""
                    OnboardSubState.Weight -> stringResource(id = R.string.weightHint)
                    OnboardSubState.Height -> stringResource(id = R.string.heightHint)
                    OnboardSubState.Age -> stringResource(id = R.string.onboardHintAge)
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                )
            )
            TextComponent(
                text = when (subState) {
                    OnboardSubState.Choose -> ""
                    OnboardSubState.Gender -> ""
                    OnboardSubState.Weight -> "КГ"
                    OnboardSubState.Height -> "СМ"
                    OnboardSubState.Age -> ""
                },
                modifier = Modifier
                    .padding(start = 16.dp),
                textSize = 30.sp,
                textColor = AppTheme.colors.LightText
            )
        }

    }

}