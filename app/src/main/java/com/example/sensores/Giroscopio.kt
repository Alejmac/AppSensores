package com.example.sensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Giroscopio : AppCompatActivity() ,SensorEventListener{
    private lateinit var sensorManager: SensorManager
    private lateinit  var acelerometer:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giroscopio)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometer=findViewById(R.id.acelerometer_data)
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        acelerometer.text = "x=${event!!.values[0]}\n\n+y=${event.values[1]}\n + \n + z= ${event.values[2]}"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}