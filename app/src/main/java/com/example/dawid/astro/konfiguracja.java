package com.example.dawid.astro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class konfiguracja extends AppCompatActivity implements View.OnClickListener {

    private static final String NAME = "astroPreferences";//key wartosc
    private static final String LONGITUDE = "longitudeField";
    private static final String LATITUDE = "latitudeField";
    private static final String REFRESH = "refreshField";
    private SharedPreferences config; //przesylanie danych miedzy activity

    private EditText longitudeConfig;
    private EditText latitudeConfig;
    private EditText refreshConfig;
    String longitude;
    String latitude;
    String refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfiguracja);
        config = getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        longitudeConfig = (EditText) findViewById(R.id.etLongitude);
        latitudeConfig = (EditText) findViewById(R.id.btKonfiguracja);
        refreshConfig = (EditText) findViewById(R.id.etRefreshRate);
        Button okButton = (Button) findViewById(R.id.btAccept);
        okButton.setOnClickListener(this);
        loadConfig();
    }

    private void loadConfig() {
        longitudeConfig.setText(config.getString(LONGITUDE, ""));
        latitudeConfig.setText(config.getString(LATITUDE, ""));
        refreshConfig.setText(config.getString(REFRESH, ""));
    }
    private String check(String s, int min, int max) {
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        String tempString="((\\+|-)?([0-9]+)(\\.[0-9]+)?)|((\\+|-)?\\.?[0-9]+)";
        if(s.matches(tempString)) {

            format.setMaximumFractionDigits(4);
            try {
                double temp = Double.parseDouble(s);
                temp = Math.min(temp, max);
                temp = Math.max(temp, min);
              //  Toast.makeText(konfiguracja.this,"Zapisano Dane",Toast.LENGTH_SHORT).show();
                return format.format(temp);

            } catch (Exception e) {
                Toast.makeText(konfiguracja.this,"Wpisałeś złe dane",Toast.LENGTH_SHORT).show();
                return "";
            }
        }
        else {
            Toast.makeText(konfiguracja.this,"Wpisałeś złe dane",Toast.LENGTH_SHORT).show();
            return "";
        }
    }


    private void saveConfig() {
        SharedPreferences.Editor preferencesEditor = config.edit();
        longitude = check(longitudeConfig.getText().toString(), -180, 180);
        latitude = check(latitudeConfig.getText().toString(), -90, 90);
        refresh = check(refreshConfig.getText().toString(), 1, 60);
        preferencesEditor.putString(LONGITUDE, longitude);
        preferencesEditor.putString(LATITUDE, latitude);
        preferencesEditor.putString(REFRESH, refresh);
        preferencesEditor.apply();
    }



    @Override
    protected void onPause() {
        saveConfig();
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAccept: {
                saveConfig();

                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (longitudeConfig.getText().toString().length()==0||latitudeConfig.getText().toString().length()==0||refreshConfig.getText().toString().length()==0) {
            Toast.makeText(konfiguracja.this, "Wpisałeś złe dane", Toast.LENGTH_SHORT).show();
        }
        else{
finish();
        }
    }
}
