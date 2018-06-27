package com.example.dawid.astro;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dawid.astro.service.YahooWeatherService;

public class Apka extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    private YahooWeatherService weatherService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apka);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
                adapterViewPager = new PageAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }
}
