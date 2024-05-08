package com.example.androidfinalproject;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
// Aysnc Interface that allows us to change the return type
public class AsyncHTTPRequest<T extends FromJSONer> extends AsyncTask<String, Integer, T> {
    T t;
    String uri;
    OnCompleter<T> onCompleter;

    public AsyncHTTPRequest(T t, String uri, OnCompleter<T> onCompleter) {
        this.t = t;
        this.uri = uri;
        this.onCompleter=onCompleter;
    }

    public String toString_ByteArrayOutputStream(InputStream stream) throws Exception {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int readBytes = stream.read(buffer);

        // inputStream.read() returns -1 when the end of the stream is reached
        while (readBytes != -1) {
            outputStream.write(buffer, 0, readBytes);
            readBytes = stream.read(buffer);
        }

        return outputStream.toString();
    }

    @Override
    protected T doInBackground(String... strings) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream response = connection.getInputStream();
            String content = toString_ByteArrayOutputStream(response);
            JSONObject jsonObject = new JSONObject(content);
            t.fromJSON(jsonObject);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        onCompleter.OnCompleted(t);
    }
}
