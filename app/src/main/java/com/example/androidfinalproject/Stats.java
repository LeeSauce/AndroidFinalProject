package com.example.androidfinalproject;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Stats extends AppCompatActivity  {
Pokemon p = new Pokemon();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_constraint);

        ImageView img = findViewById(R.id.pokePic);
        TextView type = findViewById(R.id.typesDynamic);
        TextView name = findViewById(R.id.textName);
        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener((e)->{
            //save some things to database
        });
        save.setEnabled(false);

        new AsyncHTTPRequest<>(p, "https://pokeapi.co/api/v2/pokemon/1", (p) -> {
            name.setText(p.name);
            save.setEnabled(true);
            System.out.println("Have pokemon in stats " +p.name + p.frontPic);
            ArrayAdapter<String> adapter = new ArrayAdapter<>((Context) this, R.layout.pokemon_detail_item,p.moves);
            ListView moves = findViewById(R.id.listMoves);
            moves.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            type.setText(p.types.toString());
            System.out.println(p.moves.toString());
            new AsyncImageRequest(p.frontPic, (bitmap -> img.setImageBitmap(bitmap))).execute();
        }).execute();

    }



}