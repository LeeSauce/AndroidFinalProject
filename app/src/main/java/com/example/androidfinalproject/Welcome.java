package com.example.androidfinalproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.snackbar.Snackbar;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcome_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText name = findViewById(R.id.userName);
        Button submit = findViewById(R.id.nameButton);
        Button pokeapi = findViewById(R.id.PokeApiButton);

        //moved SharedPreferences objects here bc it can now display the user name when user returns
        SharedPreferences prefs = getSharedPreferences("Name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        name.setHint(prefs.getString("UserName", "Trainer"));

        //Save name to SharedPreferences when button is clicked
        submit.setOnClickListener((c) -> {
            String username = name.getText().toString();
            saveSharedPrefs(username, editor);
            Snackbar.make(name,"Thank you " + username, Snackbar.LENGTH_LONG).show();
        });
        //Launch PokeApi when button is pressed
        pokeapi.setOnClickListener((c) -> {
            String url = "https://pokeapi.co/";
            Intent toApi = new Intent(Intent.ACTION_VIEW);
            toApi.setData( Uri.parse(url) );
            startActivity(toApi);
        });
    }

    private void saveSharedPrefs(String stringToSave, SharedPreferences.Editor editor)
    {
        editor.putString("UserName", stringToSave);
        editor.commit();
        Intent next = (new Intent(this,PokemonList.class));
        startActivity(next);
    }
}
