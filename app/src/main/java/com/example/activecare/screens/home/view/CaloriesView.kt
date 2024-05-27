package com.example.activecare.screens.home.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.activecare.common.calculateEndDate
import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.common.filterByFoodTypeAndDate
import com.example.activecare.common.filterFRByDate
import com.example.activecare.common.simpleDateTimeParser
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.components.ChooseDateComponent
import com.example.activecare.ui.components.FoodRecordComponent
import com.example.activecare.ui.components.SummaryFoodRecordComponent
import java.util.Calendar

@Composable
fun CaloriesView(
    viewState: HomeViewState,
    limit: Limitation,
    onAddCaloriesClicked: (String) -> Unit = {},
    onChangeDate: (String) -> Unit,
    onDataLoad: (Limitation) -> Unit,
    date: Calendar,
) {
    var currentDate by remember { mutableStateOf(date) }
    var currentDateString by remember {
        mutableStateOf(simpleDateTimeParser(currentDate))
    }
    var endDate by remember {
        mutableStateOf(calculateEndDate(limit).substring(0, 10))
    }
    LaunchedEffect(currentDateString) {
        onChangeDate.invoke(currentDateString)
        Log.d("CVLE", currentDateString)
        if (currentDateString.substring(0, 10) == calculateEndDate(limit).substring(0, 10)) {
            Log.d("CVLE", "ALARM")
            val newLimit =
                Limitation(date = currentDateString)
            endDate = calculateEndDate(newLimit)
            onDataLoad.invoke(newLimit)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            ChooseDateComponent(
                date = currentDate,
                onBackClick = {
                    val newDate = Calendar.getInstance()
                    newDate.time = currentDate.time
                    newDate.add(Calendar.DATE, -1)
                    currentDate = newDate
                    currentDateString = simpleDateTimeParser(currentDate)
                    Log.d("CV", calculateEndDate(limit))
                },
                onNextClick = {
                    val newDate = Calendar.getInstance()
                    newDate.time = currentDate.time
                    newDate.add(Calendar.DATE, 1)
                    if (newDate.before(Calendar.getInstance())) {
                        currentDate = newDate
                        currentDateString = simpleDateTimeParser(currentDate)
                    }
                }
            )
        }
        item {
            FoodRecordComponent(
                title = "Завтрак",
                records = filterByFoodTypeAndDate(
                    0,
                    viewState.foodRecord,
                    currentDate
                ),
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Завтрак") })
        }
        item {
            FoodRecordComponent(
                title = "Обед",
                records = filterByFoodTypeAndDate(
                    1,
                    viewState.foodRecord,
                    currentDate
                ),
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Обед") })
        }
        item {
            FoodRecordComponent(
                title = "Ужин",
                records = filterByFoodTypeAndDate(
                    2,
                    viewState.foodRecord,
                    currentDate
                ),
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Ужин") })
        }
        item {
            FoodRecordComponent(
                title = "Перекус",
                records = filterByFoodTypeAndDate(
                    3,
                    viewState.foodRecord,
                    currentDate
                ),
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Перекус") })
        }
        item {
            val filteredRecords = filterFRByDate(viewState.foodRecord, currentDate)
            SummaryFoodRecordComponent(
                calories = filteredRecords.sumOf { it.calories },
                carbohydrates = filteredRecords.sumOf { it.carbohydrates },
                fats = filteredRecords.sumOf { it.fats },
                proteins = filteredRecords.sumOf { it.proteins },
                boxModifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }
    }
}