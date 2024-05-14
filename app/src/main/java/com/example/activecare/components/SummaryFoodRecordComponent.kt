package com.example.activecare.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.ui.theme.AppTheme

@Composable
fun SummaryFoodRecordComponent(
    boxModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    calories: Int = 0,
    fats: Int = 0,
    carbohydrates: Int = 0,
    proteins: Int = 0,
) {
    TextComponent(
        text = stringResource(id = R.string.Total),
        modifier = Modifier.fillMaxWidth().padding(top=12.dp, start =24.dp, bottom = 12.dp),
        textSize = 22.sp,
        textAlign = TextAlign.Start,
        textColor = AppTheme.colors.LightText
    )
    BoxedText(
        modifier = boxModifier,
        textModifier = textModifier,
        naming = stringResource(id = R.string.Calories),
        value = "$calories ккал"
    )
    BoxedText(
        modifier = boxModifier,
        textModifier = textModifier,
        naming = stringResource(id = R.string.Proteins),
        value = "$proteins гр",
        textSize = 22.sp,
    )
    BoxedText(
        modifier = boxModifier,
        textModifier = textModifier,
        naming = stringResource(id = R.string.Сarbohydrates),
        value = "$carbohydrates гр"
    )
    BoxedText(
        modifier = boxModifier,
        textModifier = textModifier,
        naming = stringResource(id = R.string.Fats),
        value = "$fats гр"
    )
}
