package com.example.activecare.screens.home.view

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.activecare.R
import com.example.activecare.common.filterByDate
import com.example.activecare.common.filterFRByDate
import com.example.activecare.ui.components.CardComponent
import com.example.activecare.ui.components.StepsComponent
import com.example.activecare.common.dataclasses.FoodRecord
import com.example.activecare.common.dataclasses.Stat
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.theme.AppTheme

@Composable
fun DefaultView(
    viewState: HomeViewState,
    onClickEvents: List<() -> Unit>,
    steps: Int,
) {
    val categories = listOf(
        stringResource(id = R.string.homeHeaderPulse),
        stringResource(id = R.string.homeHeaderWeight),
        stringResource(id = R.string.homeHeaderSleep),
        stringResource(id = R.string.homeHeaderSpO2),
        stringResource(id = R.string.homeHeaderCalories),
        stringResource(id = R.string.homeHeaderWater),
    )
    val filteredRecords = filterFRByDate(viewState.foodRecord)
    val filteredStat = filterByDate(viewState.stats)
    StepsComponent(
        currentProgress = steps,
        maxProgress = 10000,
    )
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categories.size) {
            CardComponent(
                header = categories[it],
                value = whatValue(
                    stat = if (filteredStat.isNotEmpty()) filteredStat[0] else Stat(
                        "0",
                        0f,
                        0,
                        0f,
                        0,
                        weight = if (viewState.stats.isNotEmpty()) viewState.stats[0].weight else 0f,
                        0
                    ),
                    idx = it,
                    foodRecords = filteredRecords,
                ) + whatDim(it),
                onClick = { onClickEvents[it].invoke() },
                containerColor = AppTheme.colors.LightBack
            )
        }
    }
}

private fun whatValue(
    stat: Stat,
    idx: Int,
    foodRecords: List<FoodRecord>,
): String {
    return when (idx) {
        0 -> stat.pulse
        1 -> stat.weight
        2 -> "${stat.sleep / 100} ч ${stat.sleep % 100} мин"
        3 -> stat.oxygen_blood
        4 -> foodRecords.sumOf {
            it.calories
        }

        5 -> stat.water * 250
        else -> ""
    }.toString()
}

private fun whatDim(idx: Int): String {
    return when (idx) {
        0 -> " уд/мин"
        1 -> " кг"
        2 -> ""
        3 -> "%"
        4 -> " ккал"
        5 -> " мл"
        else -> ""
    }
}