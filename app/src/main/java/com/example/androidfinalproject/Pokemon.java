package com.example.androidfinalproject;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Pokemon implements FromJSONer { public String frontPic;
    public String name;
    public HashMap<String,String> moves;

    @NonNull
    public String toString() {
        return name;
    }

    @Override
    public void fromJSON(JSONObject o)throws JSONException {
        JSONObject sprites = o.getJSONObject("sprites");
        frontPic = sprites.getString("front_default");
        name = o.getString("name");
        JSONArray moves = o.getJSONArray("moves");
        this.moves = new HashMap<>();
        for(int i = 0; i < moves.length(); i++) {
            JSONObject elem = moves.getJSONObject(i);
            JSONObject move = elem.getJSONObject("move");
            this.moves.put(move.getString("name"), move.getString("url"));
        }
    }
}
