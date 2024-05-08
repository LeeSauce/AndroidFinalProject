package com.example.androidfinalproject;

import org.json.JSONException;
import org.json.JSONObject;
// Interface enforces loading from JSON
public interface FromJSONer {
    void fromJSON(JSONObject o) throws JSONException;
}
