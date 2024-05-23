package com.example.activecare.screens.workout.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.ui.components.ButtonComponent
import com.example.activecare.ui.components.TextFieldComponent
import com.example.activecare.screens.workout.models.WorkoutViewState

@Composable
fun TrackView(
    viewState: WorkoutViewState,
    onTimeStartChanged: (String) -> Unit,
    onTimeEndChanged: (String) -> Unit,
    onDistanceChanged: (String) -> Unit,
    onCaloriesChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    TextFieldComponent(
        modifier=textFieldModifier,
        value = viewState.trackStartTime,
        label = stringResource(id = R.string.timeStartTraining),
        onValueChange = {
            onTimeStartChanged.invoke(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),

        )
    TextFieldComponent(
        modifier=textFieldModifier,
        value = viewState.trackEndTime,
        label = stringResource(id = R.string.timeEndTraining),
        onValueChange = {
            onTimeEndChanged.invoke(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
    )
    TextFieldComponent(
        modifier=textFieldModifier,
        value = viewState.trackDistance,
        label = stringResource(id = R.string.distance),
        onValueChange = {
            onDistanceChanged.invoke(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
    )
    TextFieldComponent(
        modifier=textFieldModifier,
        value = viewState.trackCalories,
        label = stringResource(id = R.string.burnedCalories),
        onValueChange = {
            onCaloriesChanged.invoke(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.clearFocus()
            }
        ),
    )
    ButtonComponent(text = "sometext", modifier = textFieldModifier.height(60.dp) ) {

    }
}