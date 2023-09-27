package com.example.sensores

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnHUella = findViewById<Button>(R.id.huella)
        val btnLuz = findViewById<Button>(R.id.luz)
        val btnGiroscopio = findViewById<Button>(R.id.giroscopio)

        btnHUella.setOnClickListener {  navigateHuella()}
        btnLuz.setOnClickListener { navigateLuz() }
        btnGiroscopio.setOnClickListener {navigateGiroscopio() }
    }

    private fun navigateHuella(){
            val intent= Intent(this,Huella::class.java)
            startActivity(intent)
    }
    private fun navigateLuz(){
        val intent= Intent(this,Luz::class.java)
        startActivity(intent)
    }
    private fun navigateGiroscopio(){
        val intent= Intent(this,Giroscopio::class.java)
        startActivity(intent)
    }
}