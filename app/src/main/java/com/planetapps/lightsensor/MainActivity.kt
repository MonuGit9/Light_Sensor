package com.planetapps.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor?= null
    var sensorManager: SensorManager?= null
    lateinit var image: ImageView

    var isRunning= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        image = findViewById(R.id.imageView)
        image.visibility = View.VISIBLE

        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor= sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    // register a listener for the sensor
    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {


        Log.d(TAG, "onSensorChanged: " + event!!.values[0])
        if (event!!.values[0] > 30 && !isRunning) {
            isRunning = true
            try {

                image.visibility = View.INVISIBLE
                window.decorView.setBackgroundColor(resources.getColor(android.R.color.white, null))

            } catch (e: Exception) {
                Log.d(TAG, "onSensorChanged:  + ${e.message}")
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    // unregister the sensor when activity is paused
    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }


}