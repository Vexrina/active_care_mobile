package com.example.activecare.screens.home.view

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.activecare.R
import com.example.activecare.components.CardComponent
import com.example.activecare.components.StepsComponent
import com.example.activecare.screens.home.models.HomeEvent
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun DefaultView(
    viewState: HomeViewState,
    onClickEvents: List<()->Unit>
) {
    val categories = listOf(
        stringResource(id = R.string.homeHeaderPulse),
        stringResource(id = R.string.homeHeaderWeight),
        stringResource(id = R.string.homeHeaderSleep),
        stringResource(id = R.string.homeHeaderSpO2),
        stringResource(id = R.string.homeHeaderCalories),
        stringResource(id = R.string.homeHeaderWater),
    )
    val values = listOf(
        "71 уд/мин",
        "65.5 кг",
        "7ч 45мин",
        "98%",
        "2500 ккал",
        "750 мл"
    )
    StepsComponent(currentProgress = 500, maxProgress = 10000)
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categories.size) {
            CardComponent(
                header = categories[it],
                value = values[it],
                onClick = {onClickEvents[it].invoke()},
                containerColor = if (it in listOf(1,4,5)) AppTheme.colors.LightBack else Color.Gray
            )
        }
    }
}