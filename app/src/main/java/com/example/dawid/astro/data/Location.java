package com.example.dawid.astro.data;

import org.json.JSONObject;


public class Location implements JSONPopulator {
    String city;

    public String getCity() {
        return city;
    }

    @Override
    public void populate(JSONObject data) {
        city = data.optString("city");
    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
