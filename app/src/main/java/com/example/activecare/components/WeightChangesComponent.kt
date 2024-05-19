package com.example.activecare.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeightChangesComponent(
    rowPadding: Dp = 24.dp,
    lastWeight: Float = 61.2f,
    preLastWeight: Float = 67.4f,
    lastDateStamp: Calendar = Calendar.getInstance(),
){
    Row(
        modifier = Modifier
            .padding(rowPadding)
            .clip(RoundedCornerShape(12))
            .background(AppTheme.colors.LightBack)
            .fillMaxWidth()
            .height(120.dp)
                ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TextComponent(
                text = "$lastWeight кг",
                modifier = Modifier,
                textSize = 40.sp,
                textColor = AppTheme.colors.LightText,
            )
            TextComponent(
                text = weightChanged(lastWeight, preLastWeight),
                modifier = Modifier,
                textSize = 22.sp,
                textColor = AppTheme.colors.LightText,
            )
        }
        TextComponent(
            text = SimpleDateFormat("dd/MM/yyyy\nhh:mm", Locale.ENGLISH).format(lastDateStamp.time),
            modifier = Modifier,
            textSize = 22.sp,
            textColor = AppTheme.colors.LightText,
        )
    }
}

private fun weightChanged(
    lastWeight: Float = 61.2f,
    preLastWeight: Float = 67.4f
): String{
    val changes = lastWeight-preLastWeight
    return if (changes==0f) ""
    else "%.1f кг".format(changes)
}
@Preview
@Composable
fun ShowWeightChangesComponent(){
    WeightChangesComponent()
}