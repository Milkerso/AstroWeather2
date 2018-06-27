package com.example.dawid.astro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astrocalculator.AstroCalculator;
import com.example.dawid.astro.data.Chanel;
import com.example.dawid.astro.data.Item;
import com.example.dawid.astro.service.WeatherServiceCallback;
import com.example.dawid.astro.service.YahooWeatherService;

public class SimpleFragment extends Fragment implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private TextView longitude;
    private TextView Lattitude;
    private TextView Pressure;
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
        Pressure=(TextView) view.findViewById(R.id.Pressure);
        longitude=(TextView)view.findViewById(R.id.longitude);
        Lattitude=(TextView)view.findViewById(R.id.Lattidude);
        locationTextView=(TextView)view.findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);
        String in = Tools.getTemperatureunit(getContext());
        service.setTemperatureUnit(in);


        service.refreshWeather("Warszawa");

        return view;
    }

    @Override
    public void serviceSucces(Chanel channel) {
        Item item =channel.getItem();
        int resourceId= getResources().getIdentifier("drawable/icon_"+item.getCondition().getCode(),null,getActivity().getPackageName());
    @SuppressWarnings("deprecation")
    Drawable weatherIconDrawable=getResources().getDrawable(resourceId);
    weatherIconImageView.setImageDrawable(weatherIconDrawable);
    temperatureTextView.setText(item.getCondition().getTemperature()+ "\u00B0"+channel.getUnits().getTemperature());
    conditionTextView.setText(item.getCondition().getDescription());
    locationTextView.setText(service.getLocation());
    longitude.setText(channel.getItem().getLatitude());
    Lattitude.setText(channel.getItem().getLongitude());
    Pressure.setText(channel.getAtmosphera().getPressure());
    }

    @Override
    public void serviceFailure(Exception e) {
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
}
