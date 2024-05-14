package com.example.activecare.screens.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kotlin.random.Random

@Composable
fun PlotView(

){

    val steps = 10

    val pointsList = createFakePlotData()
    val max = getMax(pointsList)
    val min = getMin(pointsList)

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.White)
        .steps(pointsList.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(10.dp)
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


private fun createFakePlotData(): List<Point>{
    val list = ArrayList<Point>()
    for (i in 0..31){
        list.add(
            Point(
                i.toFloat(),
                Random.nextInt(50,90).toFloat()
            )
        )
    }
    return list
}

private fun getMax(list: List<Point>): Float{
    var max = 0f
    list.forEach{point->
        max = maxOf(max, point.y)
    }
    return max
}


private fun getMin(list: List<Point>): Float{
    var min = list[0].y
    list.forEach{point->
        min = minOf(min, point.y)
    }
    return min
}