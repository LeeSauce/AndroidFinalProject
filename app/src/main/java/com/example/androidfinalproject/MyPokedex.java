package com.example.androidfinalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MyPokedex extends ToolBarSetup {
    List<String> names;
    ListAdapter adapter;

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
        setupToolbar(getString(R.string.Pokedex));

        TextView userPokedex = findViewById(R.id.UserPokedexText);
        ListView pokedex = findViewById(R.id.PokedexList);
        pokedex.setAdapter(adapter = new ListAdapter(this));

        SharedPreferences prefs = getSharedPreferences("Name", Context.MODE_PRIVATE);
        String username = prefs.getString("UserName", "Trainer");

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        DBAdapter db = new DBAdapter(this);
        userPokedex.setText(username + "'s Pokedex");

        List<String> pokemons = db.readTable();

        String uri = "https://pokeapi.co/api/v2/pokemon/";
        if(pokemons != null){
            for(int i = 1; i <= 5; i++){
                new AsyncHTTPRequest<>(new Pokemon(), uri + i, (poke) ->{
                    String frontImgURI = poke.frontPic;
                    new AsyncImageRequest(frontImgURI, (b) ->{
                        for(String pokemon : pokemons){
                            if(pokemon.equals(poke.name)){
                                PokemonListItem li = new PokemonListItem();
                                li.b = b;
                                li.p = poke;
                                adapter.listItem.add(li);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }).execute();
                }).execute();
            }
        }
    pokedex.setOnItemClickListener((par, view, pos, id) ->{
        Pokemon pokemon = adapter.getItem(pos).p;
        alert.setTitle(R.string.Alert).setMessage(getString(R.string.AreYouSure) + " " + pokemon.name)
                .setPositiveButton(R.string.Yes, (click, arg)->{
                    db.delete(pokemon);
                    adapter.listItem.remove(pos);
                    adapter.notifyDataSetChanged();
                }).setNegativeButton(R.string.No, (click, arg)->{}).create().show();
    });

    }
}
