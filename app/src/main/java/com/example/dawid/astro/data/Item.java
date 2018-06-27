package com.example.dawid.astro.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Item implements JSONPopulator {

    private String latitude;
    private String longitude;

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
    }
    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();
        try {

            data.put("lat", latitude);
            data.put("long", longitude);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
