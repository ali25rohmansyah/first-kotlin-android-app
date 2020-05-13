package com.raproject.first_kotlin_android_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var btnTap: Button
    private lateinit var txtScore: TextView
    private lateinit var txtTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTap = findViewById(R.id.btn_tap)
        txtScore = findViewById(R.id.txt_score)
        txtTime = findViewById(R.id.txt_time)
    }
}
