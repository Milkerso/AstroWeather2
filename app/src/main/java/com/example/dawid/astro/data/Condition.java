package com.example.dawid.astro.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;
    private int highTemperature;
    private int lowTemperature;
    private String day;
    public int getCode() {
        return code;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public String getDay() {
        return day;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code=data.optInt("code");
        temperature=data.optInt("temp");
        description=data.optString("text");
        highTemperature = data.optInt("high");
        lowTemperature = data.optInt("low");
        day = data.optString("day");

    }

    @Override
    public JSONObject toJSON() {
        return null;
    }
}
