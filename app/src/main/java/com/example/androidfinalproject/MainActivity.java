package com.example.androidfinalproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ASD");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        ListView list = (ListView)  findViewById(R.id.firstList);
        list.setAdapter(adapter = new Adapter());
        String uri = "https://pokeapi.co/api/v2/pokemon/";

        // this loop will make two http requests to download an image from the API and adds to the list adapter
        for(int i = 1; i <= 10; i++){
            new AsyncHTTPRequest<>(new Pokemon(), uri + i, (poke) ->{
                String frontImgURI = poke.frontPic;
                new AsyncImageRequest(frontImgURI, (b) ->{
                    adapter.listItem.add(b);
                    adapter.notifyDataSetChanged();
                }).execute();
            }).execute();
        }

        list.setOnItemClickListener((parent, view, position, id)->{
            //TODO : for new intents
        });


    }
// adapter to inflate list objects
    private class Adapter extends BaseAdapter{

        ArrayList<Bitmap> listItem = new ArrayList<>();

        @Override
        public int getCount() {

            return listItem.size();
        }

        @Override
        public Object getItem(int position) {

            return listItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View pokemonList = convertView;

            if(pokemonList == null){
                pokemonList = inflater.inflate(R.layout.pokemon_list_item, parent, false);
            }

            ImageButton item = (ImageButton) pokemonList.findViewById(R.id.imgBtn);
            item.setImageBitmap((Bitmap) getItem(position));

            return pokemonList;

        }
    }
}