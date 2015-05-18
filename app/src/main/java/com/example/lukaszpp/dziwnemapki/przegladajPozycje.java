package com.example.lukaszpp.dziwnemapki;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class przegladajPozycje extends ActionBarActivity {

  //  public static Marker nowyMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przegladaj_pozycje);



        //spinner
        Spinner spinnerObject = (Spinner) findViewById(R.id.spinner);

        List<String> list = new ArrayList<String>();

        File dirFiles = getFilesDir();
        for (String strFile : dirFiles.list())
        {
             if(strFile.contains(".marker")) {
            list.add(strFile);
             }
        }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObject.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_przegladaj_pozycje, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void czytajPozycje(View view){
        //wez nazwe pliku ze spinnera
        Spinner spinnerObject = (Spinner) findViewById(R.id.spinner);

        //ścieżka do pliku
        String file = String.valueOf(spinnerObject.getSelectedItem());


        try{

            FileInputStream input = openFileInput(file);
            DataInputStream din = new DataInputStream(input);

            String str = din.readUTF();
            String[] stringArray = str.split(",");
            double latitude = Double.parseDouble(stringArray[0]);
            double longitude = Double.parseDouble(stringArray[1]);

            MainActivity.mojMarker.setPosition(new LatLng( latitude,longitude));

            din.close();

            TextView textViewObject = (TextView) findViewById(R.id.textView);
            textViewObject.setText("Wczytano marker. Kliknij wróć!");

            finish();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
