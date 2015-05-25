package com.example.lukaszpp.dziwnemapki;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends ActionBarActivity implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private static GoogleMap googleMap;
    public static Marker mojMarker;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createMapView();
        addMarker();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                FragmentManager myFragmentManager = getFragmentManager();
                MapFragment myMapFragment
                        = (MapFragment)myFragmentManager.findFragmentById(R.id.mapView);
                googleMap = myMapFragment.getMap();

                googleMap.setOnMarkerClickListener(this);
                googleMap.setOnMarkerDragListener(this);
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Błąd tworzenia mapy", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * dodanie markera do mapy
     */
    private void addMarker(){


        try {
            mojMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(0, 0))
                    .title("Marker")
                    .draggable(true));


            Log.i("GoogleMapActivity", "Marker Stworzony");
        }
        catch(Exception e){
            e.printStackTrace();
            Log.i("GoogleMapActivity", "Błąd markera");
        }
    }

    /* zapisanie pozycji */
    public void zapiszPozycje(View view){
        if(googleMap != null){

            //nazwa pliku
            Long tsLong = System.currentTimeMillis()/1000;
            String filename = tsLong.toString() + ".marker";


            String filecontent =  mojMarker.getPosition().latitude + "," + mojMarker.getPosition().longitude;

            TextView textViewObject = (TextView) findViewById(R.id.textView2);

            textViewObject.setText(filename + " " + filecontent);

            //zapisz marker do pliku
            try {

                // Modes: MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
                FileOutputStream output = openFileOutput(filename, Context.MODE_PRIVATE);
                DataOutputStream dout = new DataOutputStream(output);
                dout.writeUTF(filecontent);
                dout.flush();
                dout.close();
                output.close();

                Log.d(TAG, "Pozycja zapiana");
            }
            catch(Exception e){
                Log.d(TAG, "Pozycja nie zapisana");
                e.printStackTrace();
            }


        }
    }

    /* przeglądanie pozycji */
    public void przegladajPozycje(View view){
        //create intent
        Intent intent = new Intent(this, przegladajPozycje.class);

        //wystartowanie aktywnosci
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("GoogleMapActivity", "onMarkerClick");
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.i("GoogleMapActivity", "onMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.i("GoogleMapActivity", "onMarkerDragEnd");
    }
}
