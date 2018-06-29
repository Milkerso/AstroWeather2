package com.example.dawid.astro;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.dawid.astro.service.WeatherServiceCallback;
import com.example.dawid.astro.service.YahooWeatherService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;

public class advancedFragment extends Fragment implements WeatherServiceCallback{


    private TextView windStrength;
    private TextView windCourse;
    private TextView humidity;
    private TextView visibility;
    String windS;
    String windC;
    String hum;
    String visib;
    private YahooWeatherService service;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Fragment newInstance(String param1, String param2) {
        advancedFragment fragment = new advancedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public advancedFragment() {
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
        View view = inflater.inflate(R.layout.activity_advanced_fragment, container, false);
        windStrength=(TextView)view.findViewById(R.id.windStrength);
        windCourse=(TextView)view.findViewById(R.id.windCourse);
        humidity=(TextView)view.findViewById(R.id.humidity);
        visibility=(TextView) view.findViewById(R.id.visibility);
        service = new YahooWeatherService(this);
        String in = Tools.getTemperatureunit(getContext());
        service.setTemperatureUnit(in);
        String city=Tools.getCityeunit(getContext());

        service.refreshWeather(city);

        return view;

    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void serviceSucces(Chanel channel) {
        Item item =channel.getItem();
        @SuppressWarnings("deprecation")
        Condition[] forecast = channel.getItem().getForecast();

        int weatherIconImageResource = getResources().getIdentifier("icon_" + forecast[0].getCode(), "drawable", getActivity().getPackageName());
        windS =channel.getWind().getSpeed() + " " + channel.getUnits().getSpeed();
        windStrength.setText(windS);
        windC =channel.getWind().getDirection() + '\u00B0';
        windCourse.setText(windC);
        hum =channel.getAtmosphera().getHumidity() + " %";
        humidity.setText(hum);
        visib = channel.getAtmosphera().getVisibility() + "%";
        visibility.setText(visib);
        String send=windC+","+windC+","+hum+","+visib;
        writeToFile(send,getContext());
    }

    @Override
    public void serviceFailure(Exception e) {
        String send = readFromFile(getContext());

        int j = 0;
        int c = 0;
        for (int i = 0; i < send.length(); i++) {
            if (send.charAt(i) == ',') {
                if (c == 0) {
                    windS = send.substring(j, i);
                    j = i + 1;
                    c++;
                } else if (c == 1) {
                    windC = send.substring(j, i);
                    j = i + 1;
                    c++;
                } else if (c == 2) {
                    hum = send.substring(j, i);
                    j = i + 1;
                    c++;
                } else if (c == 3) {
                    visib = send.substring(j, i);
                    j = i + 1;
                    c++;
                }

                windStrength.setText(windS);

                windCourse.setText(windC);

                humidity.setText(hum);

                visibility.setText(visib);
            }
        }
    }
        private void writeToFile(String data, Context context) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("advFrag.txt", Context.MODE_PRIVATE));
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
                InputStream inputStream = context.openFileInput("advFrag.txt");

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
