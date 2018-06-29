package com.example.dawid.astro.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item implements JSONPopulator {

    private String latitude;
    private String longitude;

    public Condition[] getForecast() {
        return forecast;
    }

    private Condition[] forecast;
    public Condition getCondition() {
        return condition;
    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    private Condition condition;
    @Override
    public void populate(JSONObject data) {
        condition=new Condition();
        condition.populate(data.optJSONObject("condition"));
;
        latitude = data.optString("lat");
        longitude = data.optString("long");

        JSONArray forecastData = data.optJSONArray("forecast");

        forecast = new Condition[6];

        for (int i = 0; i < 6; i++) {
            forecast[i] = new Condition();
            try {
                forecast[i].populate(forecastData.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        return data;
    }
}
