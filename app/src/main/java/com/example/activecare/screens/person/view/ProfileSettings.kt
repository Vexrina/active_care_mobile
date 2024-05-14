package com.example.activecare.screens.person.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activecare.R
import com.example.activecare.components.BoxedText
import com.example.activecare.screens.person.models.PersonViewState

@Composable
fun ProfileSettings(
    viewState: PersonViewState,
) {
    BoxedText(
        modifier = Modifier
            .padding(top = 26.dp)
            .width(328.dp)
            .height(38.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.GenderNaming),
        value = if (viewState.user!!.gender) {
            stringResource(id = R.string.GenderMale)
        } else stringResource(id = R.string.GenderFemale),
        textSize = 20.sp,
    )
    BoxedText(
        modifier = Modifier
            .padding(top = 8.dp)
            .width(328.dp)
            .height(38.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.HeightNaming),
        value = "${viewState.user.height} см",
        textSize = 20.sp,
    )
    BoxedText(
        modifier = Modifier
            .padding(top = 8.dp)
            .width(328.dp)
            .height(38.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.WeightNaming),
        value = "${viewState.user.weight} кг",
        textSize = 20.sp,
    )
    BoxedText(
        modifier = Modifier
            .padding(top = 8.dp)
            .width(328.dp)
            .height(38.dp),
        textModifier = Modifier,
        naming = stringResource(id = R.string.BirthdateNaming),
        value = viewState.user.birthdate,
        textSize = 20.sp,
    )
}