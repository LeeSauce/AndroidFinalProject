package com.example.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Stats extends MainActivity {
    DBAdapter dbAdapter = new DBAdapter(this);
Pokemon p = new Pokemon();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stats_constraint);
        ProgressBar pb = findViewById(R.id.progressBar);
        setupToolbar();

        pb.setMax(10);
        ImageView img = findViewById(R.id.pokePic);
        TextView name = findViewById(R.id.textName);
        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener((e)->{
            p.setOnPokedex(true);
            dbAdapter.onPokedex(p);
            pb.setVisibility(View.VISIBLE);
        });
        save.setEnabled(false);

        Intent intent = getIntent();
        new AsyncHTTPRequest<>(p, "https://pokeapi.co/api/v2/pokemon/" + intent.getStringExtra("pokemon"), (p) -> {
            name.setText(p.name);
            save.setEnabled(true);
            ArrayAdapter<String> adapter = new ArrayAdapter<>((Context) this, R.layout.pokemon_detail_item,p.moves);
            ListView moves = findViewById(R.id.listMoves);
            moves.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            new AsyncImageRequest(p.frontPic, (bitmap -> img.setImageBitmap(bitmap))).execute();


            // Load the fragment
            Bundle bundle = new Bundle();
            bundle.putString("type0", p.types.get(0));
            if(p.types.size()>1)  bundle.putString("type1", p.types.get(1));

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.types_frag, TypesFragment.class, bundle)
                    .commit();
        }).execute();

    }
}