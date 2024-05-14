package com.example.activecare.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.activecare.ui.theme.AppTheme

@Composable
fun SignInTextField(
    modifier: Modifier = Modifier,
    header: String,
    textFieldValue: String,
    enabled: Boolean = true,
    onTextFieldChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textVisuals: TextVisuals = TextVisuals.Text,
    trailingIcon: (@Composable () -> Unit)? = {},
) {
    Column(modifier = modifier)
    {
        TextComponent(
            text = header,
            modifier = Modifier,
            textColor = AppTheme.colors.LightText,
        )
        DTextField(
            enabled = enabled,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            value = textFieldValue,
            onValueChange = onTextFieldChange,
            placeholder = header,
            textVisuals = textVisuals,
            trailingIcon = trailingIcon,
        )
    }
}