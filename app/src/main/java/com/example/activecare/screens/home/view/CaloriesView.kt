package com.example.activecare.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.activecare.components.ChooseDateComponent
import com.example.activecare.components.FoodRecordComponent
import com.example.activecare.components.SummaryFoodRecordComponent
import com.example.activecare.screens.home.models.HomeViewState
import java.util.Calendar

@Composable
fun CaloriesView(
    viewState: HomeViewState,
    onAddCaloriesClicked: (String) -> Unit = {},
) {
    var currentDate by remember { mutableStateOf(Calendar.getInstance()) }

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
        }
        item {
            FoodRecordComponent(
                title = "Завтрак",
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Завтрак") })
        }
        item {
            FoodRecordComponent(
                title = "Обед",
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Обед") })
        }
        item {
            FoodRecordComponent(
                title = "Ужин",
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Ужин") })
        }
        item {
            FoodRecordComponent(
                title = "Перекус",
                onAddFoodRecord = { onAddCaloriesClicked.invoke("Перекус") })
        }
        item {
            SummaryFoodRecordComponent(
                boxModifier = Modifier
                    .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }
    }
}