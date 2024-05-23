package com.example.activecare.screens.person.models

import com.example.activecare.common.dataclasses.Limitation
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain

sealed class PersonEvent {
    data class LoadData(val value: Limitation) : PersonEvent()
    data object StatClicked : PersonEvent()
    data object WorkoutClicked : PersonEvent()
    data object RecommendationsClicked : PersonEvent()
    data object DevicesClicked : PersonEvent()
    data object DevicesSettingsClicked : PersonEvent()
    data object SettingsClicked : PersonEvent()
    data object BackClicked : PersonEvent()
    data object ProfileClicked : PersonEvent()
    data object NotificationsClicked : PersonEvent()
    data object BluetoothStartScanClicked : PersonEvent()
    data object BluetoothStopScanClicked : PersonEvent()
    data class DateChanged(val value: String): PersonEvent()
    data class BluetoothDeviceClicked(val value: BluetoothDeviceDomain): PersonEvent()
}