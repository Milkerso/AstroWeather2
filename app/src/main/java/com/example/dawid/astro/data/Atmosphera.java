package com.example.dawid.astro.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Atmosphera implements JSONPopulator{
    private String pressure;
    public String getPressure()
    {
        return pressure;
    }
    @Override
    public void populate(JSONObject data) {
        pressure = data.optString("pressure");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("pressure", pressure);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
