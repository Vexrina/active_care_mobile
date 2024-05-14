package com.example.activecare.screens.person.models

sealed class PersonEvent {
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
}