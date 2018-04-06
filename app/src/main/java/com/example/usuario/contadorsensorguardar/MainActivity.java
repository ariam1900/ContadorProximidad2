package com.example.usuario.contadorsensorguardar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amazonaws.mobile.client.AWSMobileClient;


public class MainActivity extends AppCompatActivity {

   /* Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();*/

    private int mCounter = 0;


    TextView txv;
    Button btnBorrar;
    Button btnGuardar;
    Button btnVer;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        txv  = (TextView) findViewById(R.id.tx);
        btnBorrar = (Button) findViewById(R.id.btBorrar);
        btnGuardar = (Button) findViewById(R.id.btGuardar);
        btnVer= (Button) findViewById(R.id.btVer);

        if(sensor==null)
            finish();

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter = 0;
                txv.setText(Integer.toString(mCounter));
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                GuardarDatos(V);
            }
        });

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                startActivity(new Intent(MainActivity.this, Guardados.class));
            }
        });

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0]<sensor.getMaximumRange()){

                    txv.setText(Integer.toString(mCounter));

                }else {
                    mCounter ++;
                    txv.setText(Integer.toString(mCounter));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        start();
    }

    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,2000*1000);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    public void GuardarDatos(View view) {
        String conteo = txv.getText().toString();

        BasedeDatos basedeDatos = new BasedeDatos(this, "DEMODB", null, 1);

        SQLiteDatabase db = basedeDatos.getWritableDatabase();
        if (db != null) {
            ContentValues registronuevo = new ContentValues();
            registronuevo.put("conteo", conteo);

            db.insert("Tarea", null, registronuevo);
            Toast.makeText(this, "Conteo guardado", Toast.LENGTH_SHORT).show();
        }


    }


}