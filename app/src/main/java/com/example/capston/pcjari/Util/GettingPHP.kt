package com.example.capston.pcjari.Util

import android.os.AsyncTask
import android.util.Log
import com.example.capston.pcjari.Activity.A0_BaseActivity.Companion.TAG
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GettingPHP : AsyncTask<String?, Int?, String?>() {
    var jObject: JSONObject? = null
    override fun doInBackground(vararg params: String?): String? {
        val jsonHtml = StringBuilder()
        try {
            val phpUrl = URL(params[0])
            val conn = phpUrl.openConnection() as HttpURLConnection
            conn.connectTimeout = 10000
            conn.useCaches = false
            if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                val br = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"))
                while (true) {
                    val line = br.readLine() ?: break
                    jsonHtml.append(line.trimIndent())
                }
                br.close()
            }
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.d(TAG, "gettingPHP result : $jsonHtml")

        return jsonHtml.toString()
    }

    override fun onPostExecute(str: String?) {
        try {
            jObject = JSONObject(str)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}