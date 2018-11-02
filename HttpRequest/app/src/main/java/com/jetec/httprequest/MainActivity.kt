package com.jetec.httprequest

import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import khttp.get
import khttp.post
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.reflect.Parameter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data: Map<String, String> = mapOf<String, String>("account" to "Simon", "password" to "s40119")
        val url = "http://192.168.11.165:8080/KotlinPost.php"

        HttpRequest.post(url, data = data) {
            val body = it?.body()?.string()
            if (body != null) {
                runOnUiThread {
                    val jsonObj = JSONObject(body)
                    helloText.text = jsonObj.getString("account")
                }
            }
        }


/*
        Thread {
            try {
                val req = post(url, data = data).text
                Log.e("LOG", req)
                val jsonObj = JSONObject(req)

                runOnUiThread {
                    helloText.text = "account: ${jsonObj.getString("account")}\npassword: ${jsonObj.getString("password")}"
                }

            }catch (e: Exception) {
                Log.e("LOG", e.toString())
            }
        }.start()
        */


    }
}

class HttpMethods {
    companion object {
        val GET: String = "GET"
        val POST: String = "POST"
    }

}

class HttpRequest {
    companion object {
        fun post(url: String, data: Map<String, String>, action: ((response: Response?) -> Unit)?) {

            Thread {
                val formBody = FormBody.Builder()
                for ((k, v) in data) {
                    formBody.add(k, v)
                }
                val request = Request.Builder().post(formBody.build()).url(url)

                val client = OkHttpClient()

                client.newCall(request.build()).enqueue(object : Callback {

                    override fun onResponse(call: Call?, response: Response?) {
                        if (action != null) {
                            action(response)
                        }
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println("Falhou")
                        Log.e("TAG", e.toString())
                    }

                })

            }.start()
        }
    }
}
