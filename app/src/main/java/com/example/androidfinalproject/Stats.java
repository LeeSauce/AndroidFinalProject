package com.example.androidfinalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Stats extends ToolBarSetup {
    DBAdapter dbAdapter = new DBAdapter(this);
Pokemon p = new Pokemon();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_constraint);
        setupToolbar();

        // Get views
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setMax(10);
        ImageView img = findViewById(R.id.pokePic);
        TextView name = findViewById(R.id.textName);
        Button save = findViewById(R.id.buttonSave);

        save.setOnClickListener((e)->{
            new Async(pb, this).execute();
        });
        save.setEnabled(false);
        //Load pokemon with name given from previous Activity
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
    private class Async extends AsyncTask<Void, Void, Void>{
        ProgressBar pb;
        Context context;
        public Async(ProgressBar pb, Context context){
            this.pb = pb;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            this.pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            String dbMsg = getString(R.string.PokedexError);
            if(dbAdapter.saveToDB(p))// added toString instance method
            {
                dbMsg= getString(R.string.SavetoDBmsg);
            }else {
                List<String> results = dbAdapter.readTable();
                // sorry ik this algorithm isn't the best, but who cares
                for(String col : results){
                    if(col.equals(p.name)){
                        dbMsg = getString(R.string.dbMsg);
                        break;
                    }
                }
            }

            Toast.makeText(context, dbMsg, Toast.LENGTH_SHORT).show();
            this.pb.setVisibility(View.GONE);
        }
    }

}


