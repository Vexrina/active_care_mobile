package com.example.activecare.screens.workout.views


import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.activecare.R
import com.example.activecare.components.BoxedText
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.CircleButton
import com.example.activecare.screens.workout.models.WorkoutViewState
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay

@Composable
fun OsmdroidMap(
    drawLineEvent: (MapView) -> Unit,
    viewState: WorkoutViewState,
    onPauseClicked: ()->Unit,
    onContinueClicked: (MapView)->Unit,
    workoutStarted: ()->Unit,
) {
    var mapViewValue: MapView? =null
    if (viewState.currentLocation == null) {
        CircularProgressIndicator()
    } else {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    setUseDataConnection(true)
                    isTilesScaledToDpi = true
                    setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
                    val scaleBarOverlay = ScaleBarOverlay(this)
                    this.overlays.add(scaleBarOverlay)
                    this.controller.setZoom(18.0)
                    setMultiTouchControls(true)
                    this.controller.setCenter(
                        GeoPoint(
                            viewState.currentLocation.latitude,
                            viewState.currentLocation.longitude
                        )
                    )
                }
            },
            update = { mapView ->
                mapViewValue = mapView
                if (viewState.isWorkout) {
                    drawLineEvent(mapView)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        if (viewState.isWorkout) {
            val buttonModifier = Modifier
                .size(100.dp)
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CircleButton(
                        onClick = { onPauseClicked.invoke() },
                        pause = true,
                        modifier = buttonModifier,
                    )
                    CircleButton(
                        onClick = { onContinueClicked.invoke(mapViewValue!!) },
                        play = true,
                        modifier = buttonModifier,
                    )
                    CircleButton(
                        onClick = { /*TODO*/ },
                        modifier = buttonModifier,
                        stop = true
                    )
                }
                val boxModifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp)
                    .height(60.dp)
                BoxedText(
                    modifier = boxModifier,
                    textModifier = Modifier,
                    naming = stringResource(id = R.string.distance),
                    value = String.format("%.2f", viewState.distance) + " м."
                )
                val hours = viewState.endTime.hours
                val minutes = viewState.endTime.minutes
                val seconds = viewState.endTime.seconds

                val timeString = if (hours > 0) {
                    "${hours}:${minutes.toString().padStart(2, '0')}:${seconds}"
                } else {
                    "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
                }
                BoxedText(
                    modifier = boxModifier,
                    textModifier = Modifier,
                    naming = stringResource(id = R.string.time),
                    value = timeString
                )
            }
        } else {
            ButtonComponent(
                text = stringResource(id = R.string.startWorkoutButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .height(68.dp)
            ) {
                workoutStarted.invoke()
            }
        }
    }
}
