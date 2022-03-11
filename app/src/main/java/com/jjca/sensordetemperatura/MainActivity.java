package com.jjca.sensordetemperatura;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView Celsius, Fahrenheit, Kelvin;
    double gcelsius, gfahrenheit, gkelvin;
    DecimalFormat format = new DecimalFormat();
    Sensor temperatura;
    SensorManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Celsius = findViewById(R.id.Celsius);
        Fahrenheit = findViewById(R.id.Fahrenheit);
        Kelvin = findViewById(R.id.Kelvin);

        manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        temperatura = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        format.setMaximumFractionDigits(2);

        if(temperatura==null)
        {
            gcelsius = 0;
        }

        gfahrenheit = gcelsius * 1.8 + 32;
        gkelvin = gcelsius + 273.15;

        Celsius.setText(format.format(gcelsius) + " °C");
        Fahrenheit.setText(format.format(gfahrenheit) + " °F");
        Kelvin.setText(format.format(gkelvin) + " °K");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        manager.registerListener((SensorEventListener) this, temperatura, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        manager.unregisterListener((SensorEventListener) this);
    }

    public void onAccuracyChanged(Sensor sensor, int acurracy){}

    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() != Sensor.TYPE_AMBIENT_TEMPERATURE) return;

        gcelsius = event.values[0];
        gfahrenheit = gcelsius * 1.8 + 32;
        gkelvin = gcelsius + 273.15;

        Celsius.setText(format.format(gcelsius) + " °C");
        Fahrenheit.setText(format.format(gfahrenheit) + " °F");
        Kelvin.setText(format.format(gkelvin) + " °K");
    }
}