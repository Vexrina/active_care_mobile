package com.example.activecare.screens.home.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.ChooseDateComponent
import com.example.activecare.components.TextComponent
import com.example.activecare.screens.home.models.HomeViewState
import com.example.activecare.ui.theme.AppTheme
import java.util.Calendar

@Composable
fun WaterView(
    viewState: HomeViewState,
){
    var currentDate by remember { mutableStateOf(Calendar.getInstance()) }
    var drinked by remember { mutableIntStateOf(0) }
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(top=48.dp)
    ){
        items(8){idx->
            Icon(
                painter = if (idx<drinked)
                    painterResource(id = R.drawable.water_filled)
                else painterResource(id = R.drawable.water_outlined),
                contentDescription = if (idx<drinked)
                    idx.toString() + stringResource(id = R.string.water_filled)
                else idx.toString() + stringResource(id = R.string.water_outlined),
                tint = AppTheme.colors.LightText,
                modifier = Modifier.size(80.dp)
            )
        }
    }
    TextComponent(
        text = stringResource(id = R.string.waterInfo),
        modifier = Modifier.padding(24.dp),
        textSize = 22.sp,
        textColor = AppTheme.colors.LightText,
        textAlign = TextAlign.Start,
    )
    ButtonComponent(
        text = "Стакан выпит",
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (drinked<8) drinked+=1
    }
    ButtonComponent(
        text = "Стакан не выпит",
        modifier = Modifier
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (drinked>0) drinked-=1
    }
}