package com.example.dawid.astro;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawid.astro.data.Chanel;
import com.example.dawid.astro.data.Condition;
import com.example.dawid.astro.data.Item;
import com.example.dawid.astro.data.Units;
import com.example.dawid.astro.service.WeatherServiceCallback;
import com.example.dawid.astro.service.YahooWeatherService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;

public class WeatherForNextDay extends Fragment implements WeatherServiceCallback {

    private String mParam1;
    private String mParam2;
    private ImageView weatherIconImageView;
    private TextView dayLabelTextView;
    private TextView highTemperatureTextView;
    private TextView lowTemperatureTextView;

    private ImageView weatherIconImageView1;
    private TextView dayLabelTextView1;
    private TextView highTemperatureTextView1;
    private TextView lowTemperatureTextView1;

    private ImageView weatherIconImageView2;
    private TextView dayLabelTextView2;
    private TextView highTemperatureTextView2;
    private TextView lowTemperatureTextView2;

    private ImageView weatherIconImageView3;
    private TextView dayLabelTextView3;
    private TextView highTemperatureTextView3;
    private TextView lowTemperatureTextView3;

    private ImageView weatherIconImageView4;
    private TextView dayLabelTextView4;
    private TextView highTemperatureTextView4;
    private TextView lowTemperatureTextView4;

    private ImageView weatherIconImageView5;
    private TextView dayLabelTextView5;
    private TextView highTemperatureTextView5;
    private TextView lowTemperatureTextView5;
    private YahooWeatherService service;
    String icon;
    String day;
    String high;
    String low;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public View view;
    public static WeatherForNextDay newInstance(String param1, String param2) {
        WeatherForNextDay fragment = new WeatherForNextDay();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public WeatherForNextDay() {
        // Required empty public constructor
    }

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
        view = inflater.inflate(R.layout.activity_weather_for_next_day, container, false);
        weatherIconImageView = (ImageView) view.findViewById(R.id.weatherIconImageView);
        dayLabelTextView = (TextView) view.findViewById(R.id.dayTextView);
        highTemperatureTextView = (TextView) view.findViewById(R.id.highTemperatureTextView);
        lowTemperatureTextView = (TextView) view.findViewById(R.id.lowTemperatureTextView);

        weatherIconImageView1 = (ImageView) view.findViewById(R.id.weatherIconImageView1);
        dayLabelTextView1 = (TextView) view.findViewById(R.id.dayTextView1);
        highTemperatureTextView1 = (TextView) view.findViewById(R.id.highTemperatureTextView1);
        lowTemperatureTextView1 = (TextView) view.findViewById(R.id.lowTemperatureTextView1);

        weatherIconImageView2 = (ImageView) view.findViewById(R.id.weatherIconImageView2);
        dayLabelTextView2 = (TextView) view.findViewById(R.id.dayTextView2);
        highTemperatureTextView2 = (TextView) view.findViewById(R.id.highTemperatureTextView2);
        lowTemperatureTextView2 = (TextView) view.findViewById(R.id.lowTemperatureTextView2);

        weatherIconImageView3 = (ImageView) view.findViewById(R.id.weatherIconImageView3);
        dayLabelTextView3 = (TextView) view.findViewById(R.id.dayTextView3);
        highTemperatureTextView3 = (TextView) view.findViewById(R.id.highTemperatureTextView3);
        lowTemperatureTextView3 = (TextView) view.findViewById(R.id.lowTemperatureTextView3);

        weatherIconImageView4 = (ImageView) view.findViewById(R.id.weatherIconImageView4);
        dayLabelTextView4 = (TextView) view.findViewById(R.id.dayTextView4);
        highTemperatureTextView4 = (TextView) view.findViewById(R.id.highTemperatureTextView4);
        lowTemperatureTextView4 = (TextView) view.findViewById(R.id.lowTemperatureTextView4);

        weatherIconImageView5 = (ImageView) view.findViewById(R.id.weatherIconImageView5);
        dayLabelTextView5 = (TextView) view.findViewById(R.id.dayTextView5);
        highTemperatureTextView5 = (TextView) view.findViewById(R.id.highTemperatureTextView5);
        lowTemperatureTextView5 = (TextView) view.findViewById(R.id.lowTemperatureTextView5);

        service = new YahooWeatherService(this);
        String in = Tools.getTemperatureunit(getContext());
        service.setTemperatureUnit(in);
        String city=Tools.getCityeunit(getContext());

        service.refreshWeather(city);
        return view;

    }
    public void loadForecast(Chanel channel) {
        Item item =channel.getItem();
        int resourceId= getResources().getIdentifier("drawable/icon_"+item.getCondition().getCode(),null,getActivity().getPackageName());
        @SuppressWarnings("deprecation")
        Condition[] forecast = channel.getItem().getForecast();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[0].getDay().toString();
        high=Integer.toString(forecast[0].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[0].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView.setImageResource(weatherIconImageResource);
        dayLabelTextView.setText(day);
        highTemperatureTextView.setText(high);
        lowTemperatureTextView.setText(low);
        String temp=icon+","+day+","+high+","+low;

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[1].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[1].getDay().toString();
        high=Integer.toString(forecast[1].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[1].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView1.setImageResource(weatherIconImageResource);
        dayLabelTextView1.setText(day);
        highTemperatureTextView1.setText(high);
        lowTemperatureTextView1.setText(low);
        temp=temp+","+icon+","+day+","+high+","+low;

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[2].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[2].getDay().toString();
        high=Integer.toString(forecast[2].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[2].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView2.setImageResource(weatherIconImageResource);
        dayLabelTextView2.setText(day);
        highTemperatureTextView2.setText(high);
        lowTemperatureTextView2.setText(low);
        temp=temp+","+icon+","+day+","+high+","+low;

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[1].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[3].getDay().toString();
        high=Integer.toString(forecast[3].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[3].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView3.setImageResource(weatherIconImageResource);
        dayLabelTextView3.setText(day);
        highTemperatureTextView3.setText(high);
        lowTemperatureTextView3.setText(low);
        temp=temp+","+icon+","+day+","+high+","+low;

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[4].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[4].getDay().toString();
        high=Integer.toString(forecast[4].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[4].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView4.setImageResource(weatherIconImageResource);
        dayLabelTextView4.setText(day);
        highTemperatureTextView4.setText(high);
        lowTemperatureTextView4.setText(low);
        temp=temp+","+icon+","+day+","+high+","+low;

        weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[5].getCode(), "drawable", getActivity().getPackageName());
        icon=Integer.toString(weatherIconImageResource);
        day=forecast[5].getDay().toString();
        high=Integer.toString(forecast[5].getHighTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        low=Integer.toString(forecast[5].getLowTemperature())+ "\u00B0"+channel.getUnits().getTemperature();
        weatherIconImageView5.setImageResource(weatherIconImageResource);
        dayLabelTextView5.setText(day);
        highTemperatureTextView5.setText(high);
        lowTemperatureTextView5.setText(low);
        temp=temp+","+icon+","+day+","+high+","+low;
        writeToFile(temp,getContext());


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
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("weather.txt", Context.MODE_PRIVATE));
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
            InputStream inputStream = context.openFileInput("weather.txt");

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
    @Override
    public void serviceSucces(Chanel channel) {
    loadForecast(channel);
    }

    @Override
    public void serviceFailure(Exception e) {
        String send=readFromFile(getContext());
        int [] resourceId=new int[6];
        String days[]=new String[6];
        String highs[]=new String[6];
        String lows[]=new String[6];
        int j=0;
        int c=0;
        int d=0;
        for(int i=0;i<send.length();i++)
        {
            if(send.charAt(i)==',') {
                if (c == 0) {
                    resourceId[d] = Integer.parseInt(send.substring(j, i));
                    j = i + 1;
                    c++;
                } else if (c == 1) {
                    days[d] = send.substring(j, i);
                    j = i + 1;
                    c++;
                } else if (c == 2) {
                    highs[d] = send.substring(j, i);
                    j = i + 1;
                    c++;
                } else if (c == 3) {
                    lows[d] = send.substring(j, i);
                    j = i + 1;
                    c++;
                }
            }
                if(c==4)
                {

                    d++;
                    send =send.substring(i,send.length());
                    i=0;
                    c=0;
                    j=1;
                }

        }
        weatherIconImageView.setImageResource(resourceId[0]);
        dayLabelTextView.setText(days[0]);
        highTemperatureTextView.setText(highs[0]);
        lowTemperatureTextView.setText(lows[0]);

        weatherIconImageView1.setImageResource(resourceId[1]);
        dayLabelTextView1.setText(days[1]);
        highTemperatureTextView1.setText(highs[1]);
        lowTemperatureTextView1.setText(lows[1]);

        weatherIconImageView2.setImageResource(resourceId[2]);
        dayLabelTextView2.setText(days[2]);
        highTemperatureTextView2.setText(highs[2]);
        lowTemperatureTextView2.setText(lows[2]);

        weatherIconImageView3.setImageResource(resourceId[3]);
        dayLabelTextView3.setText(days[3]);
        highTemperatureTextView3.setText(highs[3]);
        lowTemperatureTextView3.setText(lows[3]);

        weatherIconImageView4.setImageResource(resourceId[4]);
        dayLabelTextView4.setText(days[4]);
        highTemperatureTextView4.setText(highs[4]);
        lowTemperatureTextView4.setText(lows[4]);

        weatherIconImageView5.setImageResource(resourceId[5]);
        dayLabelTextView5.setText(days[5]);
        highTemperatureTextView5.setText(highs[5]);
        lowTemperatureTextView5.setText(lows[5]);

    }
}