package com.jetec.bleproj

import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.BaseAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    var devices: ArrayList<BluetoothDevice> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gd: GradientDrawable = GradientDrawable()
        scanButton.setOnClickListener {
            val dialog: Dialog = ScanDialogViewController(this)

            dialog.show()


        }

    }
    var devicesAdapter: DevicesAdapter? = null

    override fun onResume() {
        super.onResume()

        val gd = GradientDrawable()
        val width = window.getWidth()
        val height = window.getHeight()
        val diameter: Int = if (width > height) height / 2 else width / 2
        gd.apply {
            this.shape = GradientDrawable.OVAL
            this.setSize(diameter, diameter)
            this.cornerRadius = (diameter / 2).toFloat()
            this.colors = intArrayOf(R.color.button_material_light, R.color.colorAccent)
            this.gradientType = GradientDrawable.LINEAR_GRADIENT
            this.setGradientCenter(0.5f, 0.5f)
        }
        scanButton.background = gd

        /*
*/

    }




    fun locationCheck() {
        val LOCATION_RESULT_CODE: Int = 0
        if (Build.VERSION.SDK_INT >= 23) {
            val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
                    !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("定位服務尚未開啟，請開啟定位")
                builder.setCancelable(false)
                builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->

                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(intent, LOCATION_RESULT_CODE)
                    Log.e("TAG", "A")
                    return@OnClickListener

                })
                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                    return@OnClickListener
                })
                builder.show()
                return

            }

        }
    }


    fun initBluetoothAdapter(){
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        //如果蓝牙没有打开则向用户申请
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled)
            bluetoothAdapter.enable()
    }

    /*
    //开始扫描之前判断是否开启了蓝牙，enable 为 false 可以停止扫描
    fun scanLeDeviceWithBLE(enable:Boolean = true){
        if (mBluetoothAdapter == null)
            initBluetoothAdapter()

        if (mBluetoothAdapter?.isEnabled as Boolean){
            mBluetoothAdapter?.enable()
        }
        if (enable){
            mScanning = true
            mBluetoothAdapter?.startLeScan(mLeScanCallback)
            TimeUtilWithoutKotlin.Delay(8, TimeUnit.SECOND).setTodo {
                mBluetoothAdapter?.stopLeScan(mLeScanCallback)
                mScanning = false
            }
        }else {
            //停止扫描，在连接设备时最好调用 stopLeScan()
            mBluetoothAdapter?.stopLeScan(mLeScanCallback)
            mScanning = false
        }
    }*/

}
