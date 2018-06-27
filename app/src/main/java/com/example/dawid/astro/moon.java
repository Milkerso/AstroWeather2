package com.example.dawid.astro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link moon.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link moon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moon extends Fragment {


    private TextView moonriseTime;
    private TextView moonsetTime;
    private TextView fullMoon;
    private TextView newMoon;
    private TextView illumination;
    private TextView moonAge;
    private AstroCalculator.MoonInfo moonInfo;
    private Handler handler;
    private int delay;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public moon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment moon.
     */
    // TODO: Rename and change types and number of parameters
    public static moon newInstance(String param1, String param2) {
        moon fragment = new moon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_moon, container, false);
        moonriseTime = (TextView) view.findViewById(R.id.moonRiseTime);
        moonsetTime = (TextView) view.findViewById(R.id.moonSetTime);
        fullMoon = (TextView) view.findViewById(R.id.fullMoonDate);
        newMoon = (TextView) view.findViewById(R.id.nowDate);
        illumination = (TextView) view.findViewById(R.id.phaseMoon);
        moonAge = (TextView) view.findViewById(R.id.syndomMonth);
        moonInfo = Tools.getAstroCalculator(getContext()).getMoonInfo();
        handler = new Handler();
        delay = Tools.getRefreshRate(getContext());
        setInfo();
        update();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    private void setInfo() {
        if (moonInfo.getMoonrise() != null)
            moonriseTime.setText(Tools.timeFormat(moonInfo.getMoonrise()));
        else
            moonriseTime.setText("AstroLib Error");
        if (moonInfo.getMoonset() != null)
            moonsetTime.setText(Tools.timeFormat(moonInfo.getMoonset()));
        else
            moonsetTime.setText("AstroLib Error");
        fullMoon.setText(Tools.dateFormat(moonInfo.getNextFullMoon()));
        newMoon.setText(Tools.dateFormat(moonInfo.getNextNewMoon()));
        illumination.setText(Tools.illuminationFormat(moonInfo.getIllumination()));
        moonAge.setText(Tools.azimuthFormat(moonInfo.getAge() / 7));
    }

    private void update() {
        handler.postDelayed(new Runnable(){
            public void run(){
                setInfo();
                handler.postDelayed(this, delay);
            }
        }, delay);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
