package com.example.dawid.astro.service;

import com.example.dawid.astro.data.Chanel;

public interface WeatherServiceCallback {
    public void serviceSucces(Chanel channel);
    public void serviceFailure(Exception e);
}
