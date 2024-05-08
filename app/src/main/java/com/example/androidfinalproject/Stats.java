package com.example.androidfinalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Stats extends AppCompatActivity  {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
    }


//    public class getInfo extends AsyncTask<String, Integer, Bitmap> {
//
//        ImageView pokepic = findViewById(R.id.pokePic);
//        public String toString_ByteArrayOutputStream(InputStream stream) throws Exception {
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            byte[] buffer = new byte[1024];
//
//            int readBytes = stream.read(buffer);
//
//            // inputStream.read() returns -1 when the end of the stream is reached
//            while (readBytes != -1) {
//                outputStream.write(buffer, 0, readBytes);
//                readBytes = stream.read(buffer);
//            }
//
//            return outputStream.toString();
//        }
//
//
//        @Override
//        protected Bitmap doInBackground(String... strings) {
//            Bitmap pokemonFront = null;
//
//            try {
//                URL url = new URL("https://pokeapi.co/api/v2/pokemon/1");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                InputStream response = connection.getInputStream();
//                String content = toString_ByteArrayOutputStream(response);
//                JSONObject jsonObject = new JSONObject(content);
//                Pokemon pokemon = new Pokemon(jsonObject);
//
//                URL urlPic = new URL(pokemon.frontPic);
//                InputStream instream = urlPic.openConnection().getInputStream();
//                pokemonFront = BitmapFactory.decodeStream(instream);
//
//
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//            return pokemonFront;
//        }
//
//        protected void onPostExecute(Bitmap s) {
//            super.onPostExecute(s);
//            pokepic.setImageBitmap(s);
//        }
//    }
}