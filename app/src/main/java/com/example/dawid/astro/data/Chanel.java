package com.example.dawid.astro.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Chanel implements JSONPopulator {
    private  Units units;
    private  Item item;

    public Atmosphera getAtmosphera() {
        return atmosphera;
    }

    private  Atmosphera atmosphera;
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

        item=new Item();
        item.populate(data.optJSONObject("item"));

        atmosphera = new Atmosphera();
        atmosphera.populate(data.optJSONObject("atmosphere"));

    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("atmosphere", atmosphera.toJSON());

            data.put("units", units.toJSON());
            data.put("item", item.toJSON());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
