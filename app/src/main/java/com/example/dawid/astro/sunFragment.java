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
 * {@link sunFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link sunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sunFragment extends Fragment {


    private TextView sunriseAzimuth;
    private TextView sunriseTime;
    private TextView sunsetAzimuth;
    private TextView sunsetTime;
    private TextView morningTime;
    private TextView eveningTime;
    private AstroCalculator.SunInfo sunInfo;
    private Handler h;
    private int delay;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public sunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sunFragment newInstance(String param1, String param2) {
        sunFragment fragment = new sunFragment();
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
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        sunriseAzimuth = (TextView) view.findViewById(R.id.sunRiseAzimuth);
        sunriseTime = (TextView) view.findViewById(R.id.sunRiseTime);
        sunsetAzimuth = (TextView) view.findViewById(R.id.sunsetAzimuth);
        sunsetTime = (TextView) view.findViewById(R.id.sunSetTime);
        morningTime = (TextView) view.findViewById(R.id.twilightMorningTime);
        eveningTime = (TextView) view.findViewById(R.id.twilightEveningTime);
        sunInfo = Tools.getAstroCalculator(getContext()).getSunInfo();
        h = new Handler();
        delay = Tools.getRefreshRate(getContext());
        setInfo();
        update();
        return view;
        // Inflate the layout for this fragment
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setInfo() {
        sunriseAzimuth.setText(Tools.azimuthFormat(sunInfo.getAzimuthRise()));
        sunriseTime.setText(Tools.timeFormat(sunInfo.getSunrise()));
        sunsetAzimuth.setText(Tools.azimuthFormat(sunInfo.getAzimuthSet()));
        sunsetTime.setText(Tools.timeFormat(sunInfo.getSunset()));
        morningTime.setText(Tools.timeFormat(sunInfo.getTwilightMorning()));
        eveningTime.setText(Tools.timeFormat(sunInfo.getTwilightEvening()));
    }

    private void update() {
        h.postDelayed(new Runnable(){
            public void run(){
                setInfo();
                h.postDelayed(this, delay);
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
