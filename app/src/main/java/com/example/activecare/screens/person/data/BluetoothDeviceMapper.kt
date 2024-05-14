package com.example.activecare.screens.person.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}