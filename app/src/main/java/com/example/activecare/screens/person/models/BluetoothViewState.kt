package com.example.activecare.screens.person.models

import com.example.activecare.screens.person.domain.BluetoothDevice

data class BluetoothViewState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),

    )
