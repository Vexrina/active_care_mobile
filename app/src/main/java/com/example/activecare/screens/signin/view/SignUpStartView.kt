package com.example.activecare.screens.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.SignInTextField
import com.example.activecare.components.TextComponent
import com.example.activecare.screens.signin.models.SignInViewState
import com.example.activecare.ui.theme.AppTheme


@Composable
fun SignUpStartView(
    viewState: SignInViewState,
    onUsernameFieldChange: (String) -> Unit,
    onEmailFieldChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = stringResource(id = R.string.sign_up_start_title),
            modifier = Modifier
                .padding(40.dp)
                .width(263.dp),
            textSize = 22.sp,
            textColor = AppTheme.colors.LightText,
        )
        SignInTextField(
            header = stringResource(id = R.string.nickname_hint),
            textFieldValue = viewState.usernameValue,
            enabled = !viewState.isProgress,
            onTextFieldChange = {
                if (!viewState.isProgress) onUsernameFieldChange.invoke(it)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .width(327.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
        )
        SignInTextField(
            header = stringResource(id = R.string.email_hint),
            textFieldValue = viewState.emailValue,
            enabled = !viewState.isProgress,
            onTextFieldChange = {
                if (!viewState.isProgress) onEmailFieldChange.invoke(it)
            },
            modifier = Modifier
                .padding(top = 32.dp)
                .width(327.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            ),
        )
        ButtonComponent(
            text = stringResource(id = R.string.sign_up_start_button),
            modifier = Modifier
                .padding(top = 32.dp)
                .height(56.dp)
                .width(327.dp)
        ) {
            if (!viewState.isProgress) {
                onRegisterClick.invoke()
            }
        }
    }
}