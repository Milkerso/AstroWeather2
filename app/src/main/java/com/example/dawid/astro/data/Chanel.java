package com.example.dawid.astro.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Chanel implements JSONPopulator {
    private  Units units;
    private  Item item;
    private  Wind wind;
    private  Location location;

    public Location getLocation() {
        return location;
    }

    public Atmosphera getAtmosphera() {
        return atmosphera;
    }

    private  Atmosphera atmosphera;
    public Wind getWind() {
        return wind;
    }
    public Units getUnits() {
        return units;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void populate(JSONObject data) {
        units= new Units();
        units.populate(data.optJSONObject("units"));
        location=new Location();
        location.populate(data.optJSONObject("location"));
        wind = new Wind();
        wind.populate(data.optJSONObject("wind"));

        item=new Item();
        item.populate(data.optJSONObject("item"));

        atmosphera = new Atmosphera();
        atmosphera.populate(data.optJSONObject("atmosphere"));

    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();



        return data;
    }
}
