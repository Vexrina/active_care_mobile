package com.example.activecare.screens.signin.view

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.screens.signin.models.SignInViewState
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.SignInTextField
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.theme.AppTheme

@Composable
fun ForgotStartView(
    viewState: SignInViewState,
    onEmailFieldChange: (String) -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = stringResource(id = R.string.forgot_start_title),
            modifier = Modifier
                .width(300.dp),
            textSize = 22.sp,
            textColor = AppTheme.colors.LightText,
            textAlign = TextAlign.Start
        )
        SignInTextField(
            header = stringResource(id = R.string.email_hint),
            textFieldValue = viewState.emailValue,
            onTextFieldChange = onEmailFieldChange,
            modifier = Modifier
                .padding(top = 40.dp)
                .width(320.dp)
        )
        ButtonComponent(
            text = stringResource(id = R.string.sign_up_start_button),
            modifier = Modifier
                .padding(top = 128.dp)
                .width(320.dp)
                .height(60.dp),
            onClick = onButtonClick,
        )
    }
}