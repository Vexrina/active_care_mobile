package com.example.activecare.screens.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.activecare.common.dataclasses.Stat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PlotView(
    statList: List<Stat> = defStatList,
    whatValue: String = "Stat"
){


    val (pointsList, editedStats) = createPlotDataFromList(statList, whatValue)
    if (pointsList.isEmpty()) return
    val steps = editedStats.size
    val max = getMax(pointsList)
    val min = getMin(pointsList)

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.White)
        .steps(pointsList.size - 1)
        .labelData { i -> editedStats[i].date_stamp.substring(0, 10) }
        .labelAndAxisLinePadding(10.dp)
        .axisLabelAngle(10f)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = (max-min) / steps.toFloat()
            String.format("%.0f",(i*yScale)+min)
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsList,
                    LineStyle(width = 6f),
                    IntersectionPoint(radius = 6.dp),
                    SelectionHighlightPoint(Color.Red),
                    ShadowUnderLine(Color.Magenta),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.Transparent),
        backgroundColor = Color.White,
        paddingRight = 0.dp,
    )
    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
    ){
        LineChart(
            modifier = Modifier
                .background(Color.White)
                .height(300.dp)
            ,
            lineChartData = lineChartData
        )
    }
}

private val dateList = listOf<String>(
    "2024-05-25T12:00:00",//0
    "2024-05-24T12:00:00",
    "2024-05-23T12:00:00",
    "2024-05-22T12:00:00",
    "2024-05-21T12:00:00",
    "2024-05-20T12:00:00",
    "2024-05-19T12:00:00",//6
)


private val defStatList = listOf<Stat>(
    Stat(date_stamp = dateList[0], 0f, 0,0f,553, 0f,0),
    Stat(date_stamp = dateList[1], 0f, 0,0f,634, 0f,0),
    Stat(date_stamp = dateList[2], 0f, 0,0f,745, 0f,0),
    Stat(date_stamp = dateList[3], 0f, 0,0f,0, 0f,0),
    Stat(date_stamp = dateList[4], 0f, 0,0f,634, 0f,0),
    Stat(date_stamp = dateList[5], 0f, 0,0f,745, 0f,0),
    Stat(date_stamp = dateList[6], 0f, 0,0f,553, 0f,0),

)

private fun createPlotDataFromListSleep(list: List<Stat>): Pair<List<Point>, List<Stat>>{
    // Создание SimpleDateFormat для парсинга и форматирования даты
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    // Преобразование и сбор уникальных объектов по дате
    val uniqueDataMap = mutableMapOf<String, Stat>()
    for (item in list) {
        val date: Date = inputFormat.parse(item.date_stamp)!!
        val formattedDate = outputFormat.format(date)
        if (formattedDate !in uniqueDataMap) {
            uniqueDataMap[formattedDate] = item
        }
    }

    // Преобразование значений карты в сет
    val uniqueDataSet = uniqueDataMap.values.toSet().toList().sortedBy {
        inputFormat.parse(it.date_stamp)
    }
    val pointList = ArrayList<Point>()
    for (i in uniqueDataSet.indices){
        pointList.add(
            Point(
                i.toFloat(),
                uniqueDataSet[i].sleep.toFloat()/100
            )
        )
    }

    return Pair(pointList, uniqueDataSet)
}

private fun createPlotDataFromListPulse(list: List<Stat>): Pair<List<Point>, List<Stat>>{
    val pointList = ArrayList<Point>()
    for (i in list.indices){
        pointList.add(
            Point(
                i.toFloat(),
                list[i].pulse
            )
        )
    }
    return Pair(pointList, list)
}

private fun createPlotDataFromListSpO2(list: List<Stat>): Pair<List<Point>, List<Stat>>{
    val pointList = ArrayList<Point>()
    for (i in list.indices){
        pointList.add(
            Point(
                i.toFloat(),
                list[i].oxygen_blood
            )
        )
    }
    return Pair(pointList, list)
}

private fun createPlotDataFromList(
    list: List<Stat>,
    whatValue: String
): Pair<List<Point>, List<Stat>>{
    return if (list.isEmpty()){
         Pair(emptyList(), emptyList())
    } else when(whatValue){
        "pulse"-> createPlotDataFromListPulse(list)
        "sleep"-> createPlotDataFromListSleep(list)
        "spO2"-> createPlotDataFromListSpO2(list)
        else -> Pair(emptyList(), emptyList())
    }
}



private fun getMax(list: List<Point>): Float{
    if (list.isEmpty())
        return 0f
    var max = 0f
    list.forEach{point->
        max = maxOf(max, point.y)
    }
    return max
}


private fun getMin(list: List<Point>): Float{
    if (list.isEmpty())
        return 0f
    var min = list[0].y
    list.forEach{point->
        min = minOf(min, point.y)
    }
    return min
}