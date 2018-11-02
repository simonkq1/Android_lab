package com.example.jetec_rd.helloworld

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.second_activity.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.concurrent.Executor
import kotlin.concurrent.thread

class SecondActivity: AppCompatActivity() {
    companion object {
        var a: String = "A"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)
        val intent: Intent = getIntent()
        val text = intent.getStringExtra("Text")
        secondText.text = text
    }

    override fun onResume() {
        super.onResume()

        val urlString = "http://localhost:8080/php_test/"
        val url = URL(urlString).readText()



    }

    fun aaa (a: () -> Void) {
        a()
    }
}