package com.a7amedo.ahmed.helprequest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class SendRequest extends AppCompatActivity {
    AlertDialog alertDialog;
    private FusedLocationProviderClient mFusedLocationClient;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_send_request);
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void SendRequest(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Status");
            alertDialog.setMessage("False");
            alertDialog.show();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double Longtiude, Lati;
                        if (location != null) {
                            Longtiude = location.getLongitude();
                            Lati = location.getLatitude();
                            Spinner mySpinner = (Spinner) findViewById(R.id.planets_spinner);
                            String text = mySpinner.getSelectedItem().toString();
                            EditText editText = (EditText) findViewById(R.id.text_help);
                            String Help_Request = editText.getText().toString();
                            alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Status");
                            alertDialog.setMessage(Longtiude + " " + Lati);
                            alertDialog.show();

                        } else {
                            alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("Status");
                            alertDialog.setMessage("Null error");
                            alertDialog.show();
                        }
                    }
                });

    }
}
