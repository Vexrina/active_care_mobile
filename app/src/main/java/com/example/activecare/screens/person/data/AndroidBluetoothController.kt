package com.example.activecare.screens.person.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.activecare.screens.person.domain.BluetoothController
import com.example.activecare.screens.person.domain.BluetoothDeviceDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission")
class AndroidBluetoothController(
    private val context: Context,
) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val bluetoothLeScanner by lazy {
        bluetoothAdapter?.bluetoothLeScanner
    }

    private val _scannedDevices = MutableStateFlow<List<com.example.activecare.screens.person.domain.BluetoothDeviceWrapper>>(emptyList())
    override val scannedDevices: StateFlow<List<com.example.activecare.screens.person.domain.BluetoothDeviceWrapper>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<com.example.activecare.screens.person.domain.BluetoothDeviceWrapper>>(emptyList())
    override val pairedDevices: StateFlow<List<com.example.activecare.screens.person.domain.BluetoothDeviceWrapper>>
        get() = _pairedDevices.asStateFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice

        }

    }

    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            _scannedDevices.update { devices ->
                val newDevice = device.toBluetoothDeviceDomain()
                if (newDevice in devices) devices else devices + newDevice
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            results.forEach { result ->
                val device = result.device
                _scannedDevices.update { devices ->
                    val newDevice = device.toBluetoothDeviceDomain()
                    if (newDevice in devices) devices else devices + newDevice
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            // Handle scan error
        }
    }

    private var bluetoothGatt: BluetoothGatt? = null
    private var currentServerSocket: BluetoothServerSocket? = null
    private var currentClientSocket: BluetoothSocket? = null

    init {
        updatePairedDevices()
    }

    override fun startDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )

        updatePairedDevices()

        bluetoothLeScanner?.startScan(leScanCallback)
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
        bluetoothLeScanner?.stopScan(leScanCallback)
    }

    override fun connectToDevice(device: BluetoothDeviceDomain) {
        bluetoothGatt = device.device.connectGatt(context, false, gattCallback)
    }


    override fun release() {
        bluetoothGatt?.close()
        bluetoothGatt = null
        context.unregisterReceiver(foundDeviceReceiver)
    }

    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map {
                it.toBluetoothDeviceDomain()
            }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }

    private val handler = Handler(Looper.getMainLooper())
    private val readCharacteristicRunnable = object : Runnable {
        override fun run() {
            bluetoothGatt?.services?.forEach { service ->
                service.characteristics.forEach { characteristic ->
                    bluetoothGatt?.readCharacteristic(characteristic)
                }
            }
            handler.postDelayed(this, 60 * 1000) // 10 minutes
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val SERVICE_UUID = "89210799-a9e4-49e5-9910-7c03ea14ea0a"
        private const val TAG = "AndroidBluetoothController"
    }

    @OptIn(ExperimentalStdlibApi::class)
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d(TAG, "Connected to GATT server.")
                    gatt.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d(TAG, "Disconnected from GATT server.")
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "GATT services discovered.")
                handler.post(readCharacteristicRunnable) // Start reading characteristics periodically
            } else {
                Log.w(TAG, "onServicesDiscovered received: $status")
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {

//                characteristic.uuid
                val data = characteristic.uuid
                Log.d(TAG, data.toString())
                val value = characteristic.value
                Log.d(TAG, value.toHexString())
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            // Handle characteristic changes
            val data = characteristic.value
            Log.d(TAG, "Characteristic changed: ${data.toHexString()}")
//            Log.d(TAG, "Characteristic changed: ${data.toHexString()}")
        }
    }
}