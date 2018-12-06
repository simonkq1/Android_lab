package com.jetec.fileio

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File

class FilePathActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.file_path_activity)

        val publicPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val privatePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        val path = "系統公共儲存路徑位於$publicPath\n\nApp私有儲存路徑位於${privatePath.path}"
        Log.e("LOG", "\n"+path)
        if (privatePath == null) return

        File(privatePath.toString() + "hello.txt").writeText("Hello World", Charsets.UTF_8)
        val contextString: String = File(privatePath.toString() + "hello.txt").readText(Charsets.UTF_8)
        Log.e("LOG", contextString)


        val dir = File(privatePath.toString())

        Log.e("LOG", dir.isDirectory.toString())
        Log.e("LOG", dir.listFiles().size.toString())

        for (i in dir.listFiles()) {
            Log.e("LOG", i.name)
        }


    }

}