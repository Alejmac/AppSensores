package com.example.sensores

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class Luz : AppCompatActivity(),SensorEventListener {

private lateinit var sensoManager:SensorManager
private var brightness :Sensor?=null
private lateinit var text:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_luz)
        text = findViewById(R.id.tv_text)
            setUpSensor()
    }

    private fun setUpSensor() {
        sensoManager= getSystemService(SENSOR_SERVICE)as SensorManager
        brightness=sensoManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }
    private fun brightness(brightness:Float):String{
        return when{

            brightness<50->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "Dark"
            }
            else->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Brightness"
            }
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(event?.sensor?.type == Sensor.TYPE_LIGHT){
            val light:Float =event.values[0]
            text.text="EL sensor : $light\n${brightness(light)}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
    override fun onResume(){
        super.onResume()
        sensoManager.registerListener(this,brightness,SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause(){
        super.onPause()
        sensoManager.unregisterListener(this)
    }
}