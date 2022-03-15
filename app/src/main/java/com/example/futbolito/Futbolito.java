package com.example.futbolito;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Futbolito extends View implements SensorEventListener {

    Bitmap background;
    Paint pincel = new Paint();
    int alto, ancho;
    int tamanio = 40;
    int borde = 12;

    Rect rect;

    float ejeX=0, ejeY=0, ejeZ=0;
    String X,Y,Z;

    public Futbolito(Context context) {
        super(context);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener(this, snsRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext() .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        background = BitmapFactory.decodeResource(getResources(), R.drawable.soccer);

        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();

        rect = new Rect(0,0,ancho,alto-170);
    }

    @Override
    public void onSensorChanged(SensorEvent cambio) {
        ejeX-=cambio.values[0];
        X = Float.toString(ejeX);
        if(ejeX < (tamanio+borde)){
            ejeX = (tamanio+borde);
        }
        else if(ejeX > (ancho-(tamanio+borde))){
            ejeX = ancho -(tamanio+borde);
        }
        ejeY += cambio.values[1];
        Y = Float.toString(ejeY);
        if(ejeY < (tamanio+borde)){
            ejeY = (tamanio+borde);
        }
        else if(ejeY > (alto-tamanio-170)){
            ejeY = alto-tamanio-170;
        }
        ejeZ = cambio.values[2];
        Z = String.format("%.2f",ejeZ);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDraw(Canvas lienzo){


        lienzo.drawBitmap(background,null,rect,null);

        pincel.setColor(Color.RED);
        lienzo.drawCircle(ejeX,ejeY, ejeZ+tamanio, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);

        lienzo.drawText("",ejeX-35,ejeY+3,pincel);
    }
}
