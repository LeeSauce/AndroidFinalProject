package com.example.androidfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;

    public ListAdapter(Context context){
        this.context = context;
    }

    List<PokemonListItem> listItem = new ArrayList<>();

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public PokemonListItem getItem(int position) {

        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        //LayoutInflater.from() is used to set the context if it's not a nested class
        View pokemonList = convertView;

        if(pokemonList == null){
            pokemonList = inflater.inflate(R.layout.pokemon_list_item, parent, false);
        }

        ImageView item = pokemonList.findViewById(R.id.imgBtn);
        TextView text = pokemonList.findViewById(R.id.pokemonName);
        PokemonListItem li = getItem(position);
        item.setImageBitmap(li.b);
        text.setText(li.p.name);

        return pokemonList;

    }
}