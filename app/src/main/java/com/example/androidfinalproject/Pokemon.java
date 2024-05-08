package com.example.androidfinalproject;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Pokemon implements FromJSONer {
    public String frontPic;
    public String name;
    public ArrayList<String> moves;
    public ArrayList<String> types;

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
        this.moves = new ArrayList<>();
        for(int i = 0; i < moves.length(); i++) {
            JSONObject elem = moves.getJSONObject(i);
            JSONObject move = elem.getJSONObject("move");
            this.moves.add(move.getString("name"));
        }
        JSONArray types = o.getJSONArray("types");
        this.types = new ArrayList<>();
        for(int i = 0; i<types.length();i++){
          JSONObject el = types.getJSONObject(i);
          JSONObject type = el.getJSONObject("type");
          this.types.add(type.getString("name"));
        }
    }
}
