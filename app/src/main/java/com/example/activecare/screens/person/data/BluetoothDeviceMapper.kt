package com.example.activecare.screens.person.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain
import com.example.activecare.screens.person.domain.BluetoothDeviceWrapper

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceWrapper {
    return BluetoothDeviceDomain(
        name = this.name,
        address = this.address,
        device = this
    )
}