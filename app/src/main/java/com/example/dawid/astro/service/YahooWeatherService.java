package com.example.dawid.astro.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.dawid.astro.Tools;
import com.example.dawid.astro.data.Chanel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;

public class YahooWeatherService {
    private  WeatherServiceCallback callback;
    private String location;
    private Exception error;
    private String temperatureUnit;
    private String langitude;
    private String lattitude;

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
    public void setLangitude(String temperatureUnit) {
        this.langitude =temperatureUnit;
    }
    public void setLattitude(String temperatureUnit) {
        this.lattitude = lattitude;
    }

    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }

    @SuppressLint("StaticFieldLeak")
    public  void  refreshWeather(final String l)
    {
        this.location=l;
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String[] locations){
                String location = locations[0];
                String unit = getTemperatureUnit().equalsIgnoreCase("f") ? "f" : "c";
                String YQL;

                    YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();


                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                }catch (Exception e)
                {
                    error=e;
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s)
            {
                if (s == null && error != null) {
                    callback.serviceFailure(error);
                    return;
                } try{
                    JSONObject data =new JSONObject(s);


                JSONObject queryResults= data.optJSONObject("query");
                int count =queryResults.optInt("count");
                if(count==0)
                {
                    callback.serviceFailure(new LocationWeatherException("no weather information found for "+location));
                }
                Chanel channel =new Chanel();
                channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                callback.serviceSucces(channel);

                }catch (JSONException e)
            {
                callback.serviceFailure(e);
            }
            catch (NullPointerException e)
            {
                callback.serviceFailure(e);
            }
            }
        }.execute((location));
    }
    @SuppressLint("StaticFieldLeak")
    public  void  refreshWeather(final String longitude, final String lattitude)
    {
        this.langitude=longitude;
        this.lattitude=lattitude;
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String[] locations){
                String location = locations[0];
                String unit = getTemperatureUnit().equalsIgnoreCase("f") ? "f" : "c";
                String YQL;
                String coordinates =  lattitude+","+ longitude;

                    YQL = String.format("select * from weather.forecast where woeid in (SELECT woeid FROM geo.places WHERE text=\"(%s)\") and u='c'", coordinates);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();


                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                }catch (Exception e)
                {
                    error=e;
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s)
            {
                if (s == null && error != null) {
                    callback.serviceFailure(error);
                    return;
                } try{
                JSONObject data =new JSONObject(s);


                JSONObject queryResults= data.optJSONObject("query");
                int count =queryResults.optInt("count");
                if(count==0)
                {
                    callback.serviceFailure(new LocationWeatherException("no weather information found for "+location));
                }
                Chanel channel =new Chanel();
                channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                callback.serviceSucces(channel);

            }catch (JSONException e)
            {
                callback.serviceFailure(e);
            }
            catch (NullPointerException e)
            {
                callback.serviceFailure(e);
            }
            }
        }.execute((location));
    }

}
class LocationWeatherException extends Exception{
    public LocationWeatherException(String detailMessage){
        super(detailMessage);
    }
}
