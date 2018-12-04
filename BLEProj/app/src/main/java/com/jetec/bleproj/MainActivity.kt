package com.jetec.bleproj

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val ble = BluetoothAdapter.getDefaultAdapter()
        if (ble != null) {
            while (!ble.isEnabled) {
                ble.enable()
                Thread.sleep(1000)
            }
        } else {


        }

        button.setOnClickListener {

            locationCheck()
        }

    }

    fun locationCheck() {
        val LOCATION_RESULT_CODE: Int = 0
//        if (Build.VERSION.SDK_INT >= 23) {
//            val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) &&
//                    !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                builder.setMessage("訂位服務尚未開啟，請開啟定位")
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

//            }

//        }
    }


}
