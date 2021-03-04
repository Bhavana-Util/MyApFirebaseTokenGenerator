package com.example.myapfirebasetokengenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.installations.FirebaseInstallations

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFirebaseToken()
    }

    fun getFirebaseToken() {
        FirebaseInstallations.getInstance().getToken(false)
            .addOnCompleteListener(OnCompleteListener { task ->
                // 2
                if (!task.isSuccessful) {
                    Log.w("MainActivity", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // 3
                val token = task.result?.token.toString()
                Log.d("MainActivity", "Firebase Token  = $token")

                val textView = findViewById<TextView>(R.id.view_text) as TextView
                textView.setOnClickListener {
                    Toast.makeText(this@MainActivity, "token copied to clipboard", Toast.LENGTH_LONG).show()

                    val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("token", token)
                    clipboard.setPrimaryClip(clip)
                }
            })
    }


}
