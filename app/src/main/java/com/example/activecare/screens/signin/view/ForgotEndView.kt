package com.example.activecare.screens.signin.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.screens.signin.models.SignInViewState
import com.example.activecare.ui.components.LinkComponent
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.theme.AppTheme

@Composable
fun ForgotEndView(
    viewState: SignInViewState,
) {
    Column(
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        TextComponent(
            text = stringResource(id = R.string.forgot_end_title) + viewState.emailValue,
            modifier = Modifier
                .width(300.dp),
            textAlign = TextAlign.Start,
            textColor = AppTheme.colors.LightText,
            textSize = 22.sp
        )
        LinkComponent(
            commonText = stringResource(id = R.string.forgot_again),
            linkText = stringResource(id = R.string.forgot_again_link),
            rowModifier = Modifier.padding(top = 140.dp)
        ) {

        }
    }
}