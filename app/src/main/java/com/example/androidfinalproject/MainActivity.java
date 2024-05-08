package com.example.androidfinalproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView pokeList = findViewById(R.id.firstList);


        List<Pokemon> pokemons = new ArrayList<>();
        ArrayAdapter<Pokemon> adapter = new ArrayAdapter<Pokemon>(this, R.layout.pokemon_list_item, pokemons);
        pokeList.setAdapter(adapter);

        for(int i = 1; i <= 10; i++) {
            new AsyncHTTPRequest<>(new Pokemon(), "https://pokeapi.co/api/v2/pokemon/"+i, (p) -> {
                pokemons.add(p);
                adapter.notifyDataSetChanged();
            }).execute();
        }

//        new AsyncHTTPRequest<>(new Pokemon(), "https://pokeapi.co/api/v2/pokemon/1", (p) -> {
//            System.out.println("Printing pokemon name with an OnCompleter");
//            System.out.println(p.name);
//            System.out.println(p.moves.keySet());
//            new AsyncHTTPRequest<>(new Move(), p.moves.get("strength"), (m)-> {
//                System.out.println("Printing move name after second AsyncHTTPRequest");
//                System.out.println(m.name);
//                System.out.println(m.effect);
//            }).execute();
//
//            new AsyncImageRequest(p.frontPic, (b)-> {
//                    System.out.println(b.getWidth());
//            }).execute();
//        }).execute();
    }
}