package com.example.activecare.screens.person.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.activecare.R
import com.example.activecare.screens.person.models.BluetoothViewState
import com.example.activecare.ui.components.BluetoothDeviceList
import com.example.activecare.ui.components.ButtonComponent

@Composable
fun BluetoothDevicesView(
    state: BluetoothViewState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    onDeviceClicked: (com.example.activecare.screens.person.domain.BluetoothDeviceDomain) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxSize()
    ) {
        BluetoothDeviceList(
            title = stringResource(id = R.string.PairedDevices),
            devices = state.pairedDevices,
            onClick = {
                onDeviceClicked.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonComponent(
                text = stringResource(id = R.string.BluetoothStartScan),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(160.dp),
                onClick = onStartScan
            )
            ButtonComponent(
                text = stringResource(id = R.string.BluetoothStopScan),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(160.dp),
                onClick = onStopScan
            )
        }
        BluetoothDeviceList(
            title = stringResource(id = R.string.ScannedDevices),
            devices = state.scannedDevices,
            onClick = {
                onDeviceClicked.invoke(it)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )
    }
}