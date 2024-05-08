package com.example.androidfinalproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Move implements FromJSONer{
    public String name;
    public String effect;
    @Override
    public void fromJSON(JSONObject o) throws JSONException {
        name = o.getString("name");
        JSONArray effects = o.getJSONArray("effect_entries");
        JSONObject firstEffect = effects.getJSONObject(0);
        effect = firstEffect.getString("effect");
    }
}
