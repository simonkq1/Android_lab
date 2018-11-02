package com.example.jetec_rd.helloworld

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.longToast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewButton.text = "Hello"

//        mainViewButton.background = Canvas()
/*
        mainViewButton.setOnClickListener { sayHello() }
//        findViewById<M>()
        val view = View(this)
        val mainBtn = mainViewButton.viewTreeObserver
        mainBtn.addOnGlobalLayoutListener {
            mainViewButton.apply {
//                background = buttonBg()
            }
        }*/


        val url = "http://127.0.0.1:8080/KotlinPost.php"
        var body: Map<String, String> = mapOf(
                "account" to "Simon",
                "password" to "s40119"
        )
        sendPostRequest(url, body) {
            Log.e("TAG", it.data)
        }
//        Thread {
//        }.start()

    }

    override fun onStart() {
        super.onStart()

    }


    fun buttonBg() = GradientDrawable().apply {
        println("AAAAAAAAAAAAAAAA")
        println(mainViewButton.measuredWidth)
        val width = mainViewButton.width.toFloat()
        val height = mainViewButton.height.toFloat()
        val radius = if (width >= height) height / 2 else width / 2
//        shape = GradientDrawable.RING
        cornerRadius = radius

        setColor(Color.BLUE)
        setStroke(2, Color.GRAY)
////        shape = GradientDrawable.LINEAR_GRADIENT

    }

    fun sayHello() {
//        Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show()
        val intent: Intent = Intent()
        val second_vc = SecondActivity::class.java
        intent.setClass(this, second_vc)
        intent.putExtra("Text", "BBBB")
        startActivity(intent)

    }
    fun sendPostRequest(url: String, body: Map<String, String>, action: ((request: HttpRequestData)-> Unit)?) {

        var reqParam: String = ""
        var count: Int = 0
        for ((k, v) in body) {
            reqParam += URLEncoder.encode(k, "UTF-8") + "=" + URLEncoder.encode(v, "UTF-8")
            if (count != body.count() - 1) {
                reqParam += "&"
            }
            count++
        }
        val mURL = URL(url)

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET

            requestMethod = "POST"
            instanceFollowRedirects = false
            doOutput = true
            connect()

            val os = outputStream
            val ps = PrintStream(os)
            ps.print(reqParam)
            ps.close()




            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                if (action != null) {
                    action(HttpRequestData(responseCode, response.toString()))
                }
            }
        }
    }


}
