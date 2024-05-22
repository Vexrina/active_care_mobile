package com.example.activecare.screens.person.models

import com.example.activecare.screens.person.domain.BluetoothDeviceDomain

data class BluetoothViewState(
    val scannedDevices: List<BluetoothDeviceDomain> = emptyList(),
    val pairedDevices: List<BluetoothDeviceDomain> = emptyList(),

    )
