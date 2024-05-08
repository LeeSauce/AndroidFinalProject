package com.example.androidfinalproject;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.graphics.Bitmap;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class AsyncImageRequest extends AsyncTask<String, Integer, Bitmap> {
    String uri;
    OnCompleter<Bitmap> onCompleter;

    public AsyncImageRequest(String uri, OnCompleter<Bitmap> onCompleter) {
        this.uri = uri;
        this.onCompleter=onCompleter;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(uri);
            System.out.println(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream response = connection.getInputStream();
            Bitmap b = BitmapFactory.decodeStream(response);
            System.out.println(b.getWidth());
            System.out.println(b.getHeight());
            response.close();
            return b;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void onPostExecute(Bitmap t) {
        super.onPostExecute(t);
        onCompleter.OnCompleted(t);
    }
}
