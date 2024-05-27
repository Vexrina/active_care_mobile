package com.example.activecare.screens.person.domain

import android.bluetooth.BluetoothDevice

typealias BluetoothDeviceDomain = BluetoothDeviceWrapper

data class BluetoothDeviceWrapper(
    val name: String?,
    val address: String,
    val device: BluetoothDevice,
)
