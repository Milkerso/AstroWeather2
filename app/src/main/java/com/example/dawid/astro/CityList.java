package com.example.dawid.astro;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.dawid.astro.data.Chanel;
import com.example.dawid.astro.database.DatabaseHelper;
import com.example.dawid.astro.service.WeatherServiceCallback;
import com.example.dawid.astro.service.YahooWeatherService;

import java.util.ArrayList;

public class CityList extends AppCompatActivity implements WeatherServiceCallback {
    private EditText cityName;
    private Button addButton;
    private ListView listView;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    private static final String NAME = "astroPreferences";
    private static final String CITYCHOICE = "citychoice";
    private SharedPreferences config;
    private YahooWeatherService service;
    private static final String LONGITUDE = "longitudeField";
    private static final String LATITUDE = "latitudeField";
    private String city;
    private String goodCity;
    boolean checkCity;
    String location;

    String longitude;
    String Lattitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        config = getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        cityName = findViewById(R.id.cityName);
        addButton = findViewById(R.id.addButton);
        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("yahoo.xml", 0);
        populateView();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = cityName.getText().toString();
                if (newEntry.length() > 0) {
                    goodCity=newEntry;
                    addData(newEntry);
                    cityName.setText("");
                    populateView();
                }
            }
        });
    }
    private void refresh() {
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    private void populateView() {
        refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCity(parent.getItemAtPosition(position).toString());
                Intent intent = new Intent(CityList.this, Apka.class);
                String strName = "4";
                intent.putExtra("STRING_I_NEED", strName);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                databaseHelper.delete(parent.getItemAtPosition(position).toString());
                refresh();
                return true;
            }
        });
    }
    public void setCity(String city){

        service = new YahooWeatherService(this);
        service.setTemperatureUnit("c");
        service.refreshWeather(city);
        this.city=city;

        Toast.makeText(this, "Miasto " + city + " zostalo wybrane", Toast.LENGTH_SHORT).show();
    }

    public void addData(String newEntry) {

        service = new YahooWeatherService(this);
        service.setTemperatureUnit("c");
        service.refreshWeather(newEntry);
        goodCity=newEntry;

    }

    @Override
    public void serviceSucces(Chanel channel) {

        SharedPreferences.Editor preferencesEditor = config.edit();
        location=service.getLocation();
        longitude=channel.getItem().getLatitude();
        Lattitude=channel.getItem().getLongitude();
       for(int i=0;i<longitude.length();i++)
       {
           if(longitude.charAt(i)=='.')

             longitude=  longitude.substring(0,i+2);
       }
        for(int i=0;i<Lattitude.length();i++)
        {
            if(Lattitude.charAt(i)=='.')
             Lattitude=Lattitude.substring(0,i+2);
        }
        preferencesEditor.putString(CITYCHOICE, location);
        preferencesEditor.putString(LONGITUDE, longitude);
        preferencesEditor.putString(LATITUDE, Lattitude);
        preferencesEditor.apply();
        String gCity=channel.getLocation().getCity();
        wrzuc(gCity);
        goodCity=new String();

    }

    @Override
    public void serviceFailure(Exception e) {

        goodCity=new String();
        refresh();
    }
    public void wrzuc(String gCity)
    {
        boolean isInBase=true;
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {

            listData.add(data.getString(1));

        }
        for(int i=0;i<listData.size();i++)
        {
            if(listData.contains(gCity))
            {
                isInBase=false;
            }
        }


        if(isInBase) {
            checkCity=true;
        }
        else
        {

            checkCity=false;
        }
        if(checkCity&&goodCity.length()>0) {
            databaseHelper.addData(gCity);
            checkCity=false;
        }
        else {
            Toast.makeText(this, "Zle miasto ", Toast.LENGTH_SHORT).show();
        }
        refresh();
    }
    public void wrzuc2(String gCity) {
        boolean isInBase = true;
        databaseHelper = new DatabaseHelper(this);
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {

            listData.add(data.getString(1));

        }
        for (int i = 0; i < listData.size(); i++) {
            if (listData.contains(gCity)) {
                isInBase = false;
            }
        }


        if (isInBase) {
            checkCity = true;
        } else {

            checkCity = false;
        }
        if (checkCity && goodCity.length() > 0) {
            databaseHelper.addData(gCity);
            checkCity = false;
        } else {
            Toast.makeText(this, "Zle miasto ", Toast.LENGTH_SHORT).show();
        }
        refresh();
    }
}