package com.example.activecare.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.dataclasses.FoodRecord
import com.example.activecare.ui.theme.AppTheme

@Composable
fun FoodRecordComponent(
    title:String,
    records: List<FoodRecord> = emptyList(),
    onAddFoodRecord: ()->Unit = {},
){
    Column(
        modifier= Modifier
            .padding(start = 24.dp, end = 24.dp,top=20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextComponent(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textSize = 22.sp,
            textAlign = TextAlign.Start,
            textColor = AppTheme.colors.LightText
        )
        records.forEach{
            BoxedText(
                modifier = Modifier
                    .padding(top=6.dp)
                    .fillMaxWidth()
                    .height(44.dp),
                textModifier = Modifier,
                naming = it.foodname,
                value = "${it.calories} ккал",
            )
        }
        CircleButton(
            modifier = Modifier
                .padding(top=6.dp)
                .height(50.dp),
            onClick = {onAddFoodRecord.invoke()},
            plus = true,
            color = Color.Transparent,
        )
    }
}