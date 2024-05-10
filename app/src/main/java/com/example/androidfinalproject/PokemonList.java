package com.example.androidfinalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;
import java.util.List;

public class PokemonList extends ToolBarSetup {
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pokemon_list_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        super.setupToolbar();

        ListView list = (ListView)  findViewById(R.id.firstList);
        list.setAdapter(this.adapter =new ListAdapter(this));
        String uri = "https://pokeapi.co/api/v2/pokemon/";

        for(int i = 1; i <= 5; i++){
            new AsyncHTTPRequest<>(new Pokemon(), uri + i, (poke) ->{
                String frontImgURI = poke.frontPic;
                new AsyncImageRequest(frontImgURI, (b) ->{
                    PokemonListItem li = new PokemonListItem();
                    li.b = b;
                    li.p = poke;
                    adapter.listItem.add(li);
                    adapter.notifyDataSetChanged();
                }).execute();
            }).execute();
        }

        list.setOnItemClickListener((parent, view, position, id)->{
            Bundle bundle = new Bundle();
            PokemonListItem li = adapter.listItem.get(position);


            Intent intent = new Intent(this, Stats.class);
            intent.putExtra("pokemon", adapter.listItem.get(position).p.name);
            startActivity(intent);
        });
    }
}
