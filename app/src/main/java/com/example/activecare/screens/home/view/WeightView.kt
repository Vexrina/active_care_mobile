package com.example.activecare.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.ChooseDateComponent
import com.example.activecare.components.TextComponent
import com.example.activecare.components.TextFieldComponent
import com.example.activecare.components.WeightChangesComponent
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun WeightView(
    value: String?,
    onValueChange: (String)->Unit
){
    val focusManager = LocalFocusManager.current
    var currentDate by remember { mutableStateOf(Calendar.getInstance()) }

    ChooseDateComponent(
        date = currentDate,
        onBackClick = {
            val newDate = Calendar.getInstance()
            newDate.time = currentDate.time
            newDate.add(Calendar.DATE, -1)
            currentDate = newDate
        },
        onNextClick = {
            val newDate = Calendar.getInstance()
            newDate.time = currentDate.time
            newDate.add(Calendar.DATE, 1)
            if (newDate.before(Calendar.getInstance())) {
                currentDate = newDate
            }
        }
    )
    WeightChangesComponent()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextFieldComponent(
            value = value ?: "",
            onValueChange = { onValueChange(it) },
            label = stringResource(id = R.string.weightHint),
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
            text = "КГ",
            modifier = Modifier
                .padding(start = 16.dp),
            textSize = 30.sp,
            textColor = AppTheme.colors.LightText
        )
    }
    ButtonComponent(
        text = stringResource(id = R.string.addWeight),
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {

    }
}