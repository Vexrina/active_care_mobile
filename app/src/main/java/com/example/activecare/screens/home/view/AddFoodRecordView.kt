package com.example.activecare.screens.home.view

import androidx.compose.foundation.layout.Column
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
import com.example.activecare.components.ButtonComponent
import com.example.activecare.components.TextFieldComponent
import com.example.activecare.screens.home.models.AddCaloriesState

@Composable
fun AddFoodRecordView(
    caloriesState: AddCaloriesState,
    onFoodNameChanged: (String)->Unit,
    onCaloriesChanged: (String)->Unit,
    onProteinsChanged: (String)->Unit,
    onFatsChanged: (String)->Unit,
    onCarbohydratesChanged: (String)->Unit,
    onAddFoodRecordClicked: (String)->Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .padding(start=20.dp, end=20.dp,top=20.dp)
    ) {
        TextFieldComponent(
            modifier = Modifier.padding(top=20.dp).fillMaxWidth(),
            value = caloriesState.foodName,
            onValueChange = {onFoodNameChanged.invoke(it)},
            label = stringResource(id = R.string.addCaloriesFoodName),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        TextFieldComponent(
            modifier = Modifier.padding(top=20.dp).fillMaxWidth(),
            value = caloriesState.calories,
            onValueChange = {onCaloriesChanged.invoke(it)},
            label = stringResource(id = R.string.addCaloriesCalories),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )
        TextFieldComponent(
            modifier = Modifier.padding(top=20.dp).fillMaxWidth(),
            value = caloriesState.proteins,
            onValueChange = {onProteinsChanged.invoke(it)},
            label = stringResource(id = R.string.addCaloriesProteins),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )
        TextFieldComponent(
            modifier = Modifier.padding(top=20.dp).fillMaxWidth(),
            value = caloriesState.fats,
            onValueChange = {onFatsChanged.invoke(it)},
            label = stringResource(id = R.string.addCaloriesFats),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )
        TextFieldComponent(
            modifier = Modifier.padding(top=20.dp).fillMaxWidth(),
            value = caloriesState.carbohydrates,
            onValueChange = {onCarbohydratesChanged.invoke(it)},
            label = stringResource(id = R.string.addCaloriesCarbohydrates),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            )
        )
        ButtonComponent(
            modifier = Modifier
                .padding(top=20.dp)
                .fillMaxWidth()
                .height(60.dp),
            text = stringResource(id = R.string.addCaloriesButton),
            onClick = {
                onAddFoodRecordClicked.invoke("")
            }
        )
    }
}