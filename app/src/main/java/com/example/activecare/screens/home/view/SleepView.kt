package com.example.activecare.screens.home.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.getCurrentDate
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.FilterComponent
import com.example.activecare.ui.components.TextFieldComponent

@Composable
fun SleepView(
    viewState: HomeViewState,
    value: String? = null,
    onValueChange: (String)->Unit,
    onDateClick: (Limitation)->Unit,
    onButtonClick: ()->Unit,
){
    val focusManager = LocalFocusManager.current
    TextFieldComponent(
        value = value ?: "",
        onValueChange = { onValueChange(it) },
        label = stringResource(id = R.string.sleepHint),
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(100.dp),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.clearFocus() }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        )
    )
    ButtonComponent(
        text = "sometext",
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(60.dp),
        ) {
        onButtonClick.invoke()
    }
    FilterComponent(
        onWeekClicked = {
            onDateClick.invoke(
                Limitation(
                    date = getCurrentDate(),
                    date_offset = 1,
                    deltatype = "week"
                )
            )
        },
        onMonthClicked = {
            onDateClick.invoke(
                Limitation(
                    date = getCurrentDate(),
                    date_offset = 1,
                    deltatype = "month"
                )
            )
        },
        onYearClicked = {
            onDateClick.invoke(
                Limitation(
                    date = getCurrentDate(),
                    date_offset = 1,
                    deltatype = "year"
                )
            )
        },
    )
    PlotView(
        whatValue = "sleep",
        statList = viewState.stats
    )
}