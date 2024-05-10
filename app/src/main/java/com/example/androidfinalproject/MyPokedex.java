package com.example.androidfinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MyPokedex extends ToolBarSetup {
    List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.my_pokedex);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupToolbar();

        TextView userPokedex = findViewById(R.id.UserPokedexText);
        ListView pokedex = findViewById(R.id.PokedexList);

        SharedPreferences prefs = getSharedPreferences("name", Context.MODE_PRIVATE);
        String username = prefs.getString("Username", "Stranger");

        userPokedex.setText(username + "'s Pokedex");


    }
}
