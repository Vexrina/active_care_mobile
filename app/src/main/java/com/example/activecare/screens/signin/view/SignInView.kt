package com.example.activecare.screens.signin.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.activecare.screens.signin.models.SignInViewState
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.SignInTextField
import com.example.activecare.ui.components.TextComponent
import com.example.activecare.ui.components.TextVisuals
import com.example.activecare.ui.theme.AppTheme

@Composable
fun SignInView(
    viewState: SignInViewState,
    onEmailFieldChange: (String) -> Unit,
    onPasswordFieldChange: (String) -> Unit,
    onForgetClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = stringResource(id = R.string.sign_in_title),
            modifier = Modifier
                .padding(12.dp)
                .padding(top = 48.dp)
                .width(263.dp),
            textColor = AppTheme.colors.LightText,
            textSize = 24.sp
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
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
        )
        SignInTextField(
            header = stringResource(id = R.string.password),
            textFieldValue = viewState.passwordValue,
            enabled = !viewState.isProgress,
            onTextFieldChange = {
                if (!viewState.isProgress) onPasswordFieldChange.invoke(it)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .width(327.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            ),
            textVisuals = if (passwordVisible) TextVisuals.Text else TextVisuals.Password,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
        TextComponent(
            text = stringResource(id = R.string.forgot_button),
            modifier = Modifier
                .padding(top = 12.dp, start = 24.dp)
                .width(150.dp)
                .align(Alignment.Start)
                .clickable { onForgetClick.invoke() },
            textColor = AppTheme.colors.LightText,
        )
        ButtonComponent(
            text = stringResource(id = R.string.login_button),
            modifier = Modifier
                .padding(top = 32.dp)
                .height(56.dp)
                .width(327.dp)
        ) {
            onLoginClick.invoke()
        }
    }
}