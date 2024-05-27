package com.example.activecare.screens.home.view

import androidx.compose.runtime.Composable
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.getCurrentDate
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.components.FilterComponent

@Composable
fun BloodView(
    onDateClick: (Limitation)->Unit,
    whatValue: String,
    viewState: HomeViewState,
){
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
        whatValue = whatValue,
        statList = viewState.stats
    )
}