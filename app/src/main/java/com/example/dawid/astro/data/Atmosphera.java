package com.example.dawid.astro.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Atmosphera implements JSONPopulator{
    private String pressure;
    private String humidity;
    private String visibility;

    public String getVisibility() {
        return visibility;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure()
    {
        return pressure;
    }
    @Override
    public void populate(JSONObject data) {
        pressure = data.optString("pressure");
        humidity = data.optString("humidity");
        visibility = data.optString("visibility");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();



        return data;
    }
}
