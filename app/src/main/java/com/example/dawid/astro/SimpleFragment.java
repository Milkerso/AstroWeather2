package com.example.dawid.astro;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.dawid.astro.data.Chanel;
import com.example.dawid.astro.data.Condition;
import com.example.dawid.astro.data.Item;
import com.example.dawid.astro.service.WeatherServiceCallback;
import com.example.dawid.astro.service.YahooWeatherService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;

public class SimpleFragment extends Fragment implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView longitudeTextView;
    private TextView LattitudeTextView;
    private TextView PressureTextView;
    String temperature;
    String condition;
    String location;

    String longitude;
    String Lattitude;
    String Pressure;
    private AstroCalculator.SunInfo simpleInfo;
    private YahooWeatherService service;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_simple_fragment, container, false);
        weatherIconImageView=(ImageView)view.findViewById(R.id.weatherIconImageView);
        temperatureTextView=(TextView)view.findViewById(R.id.temperatureTextView);
        conditionTextView=(TextView)view.findViewById(R.id.conditionTextView);
        PressureTextView=(TextView) view.findViewById(R.id.Pressure);
        longitudeTextView=(TextView)view.findViewById(R.id.longitude);
        LattitudeTextView=(TextView)view.findViewById(R.id.Lattidude);
        locationTextView=(TextView)view.findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);
        String in = Tools.getTemperatureunit(getContext());
        service.setTemperatureUnit(in);
        String city=Tools.getCityeunit(getContext());

        service.refreshWeather(city);

        return view;
    }

    @Override
    public void serviceSucces(Chanel channel) {
        Item item =channel.getItem();
        int resourceId= getResources().getIdentifier("drawable/icon_"+item.getCondition().getCode(),null,getActivity().getPackageName());
    @SuppressWarnings("deprecation")
    Condition[] forecast = channel.getItem().getForecast();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());
    temperature=item.getCondition().getTemperature()+ "\u00B0"+channel.getUnits().getTemperature();
    condition=item.getCondition().getDescription();
    location=service.getLocation();
    longitude=Integer.toString(forecast[1].getCode());
    Lattitude=channel.getItem().getLongitude();
    Pressure=channel.getAtmosphera().getPressure();
    Pressure=Double.toString(Double.parseDouble(Pressure)/33.86);
    String send=Integer.toString(weatherIconImageResource)+","+temperature+","+condition+","+location+","+longitude+","+Lattitude+","+Pressure+",  s";
    weatherIconImageView.setImageResource(weatherIconImageResource);
    temperatureTextView.setText(temperature);
    conditionTextView.setText(condition);
    locationTextView.setText(location);
    longitudeTextView.setText(longitude);
    LattitudeTextView.setText(Lattitude);
    PressureTextView.setText(Pressure);
    writeToFile(send,getContext());
    }

    @Override
    public void serviceFailure(Exception e) {

        String send=readFromFile(getContext());
        int resourceId=0;
        int j=0;
        int c=0;
        for(int i=0;i<send.length();i++)
        {
            if(send.charAt(i)==',') {
                if (c == 0) {
                    resourceId = Integer.parseInt(send.substring(0, i));
                    j=i+1;
                    c++;
                } else if (c == 1) {
                    temperature=send.substring(j,i);
                    j=i+1;
                    c++;
                }
                else if (c == 2) {
                    condition=send.substring(j,i);
                    j=i+1;
                    c++;
                }
                else if (c == 3) {
                    location=send.substring(j,i);
                    j=i+1;
                    c++;
                }
                else if (c == 4) {
                    longitude=send.substring(j,i);
                    j=i+1;
                    c++;
                }
                else if (c == 5) {
                    Lattitude=send.substring(j,i);
                    j=i+1;
                    c++;
                }
                else if (c == 6) {
                    Pressure=send.substring(j,i);
                    j=i+1;
                    c++;
                }
            }
        }


      weatherIconImageView.setImageResource(resourceId);
        temperatureTextView.setText(temperature);
        conditionTextView.setText(condition);
        locationTextView.setText(location);
        longitudeTextView.setText(longitude);
        LattitudeTextView.setText(Lattitude);
        PressureTextView.setText(Pressure);
        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        System.out.print(e.getMessage());
    }

    public static Fragment newInstance(String param1, String param2) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
