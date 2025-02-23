package com.example.androidfinalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class ToolBarSetup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String helpTitle;
    String helpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpTitle = getString(R.string.Help);
        helpContent = getString(R.string.HelpMessage);
    }
    protected void setupToolbar(String title) {
        //Bringing in Toolbar and Navigation Drawer
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tb, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //End toolbar and Nav Drawer
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_items, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(helpContent);
            builder.setTitle(helpTitle);
            builder.setCancelable(true);
            builder.setNegativeButton(R.string.close, (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.PokemonList) {
            Intent main = new Intent(this, PokemonList.class);
            startActivity(main);
        } else if (id == R.id.Pokedex) {
            Intent home = new Intent(this, MyPokedex.class);
            startActivity(home);
        } else if (id == R.id.Welcome) {
            Intent welcome = new Intent(this, Welcome.class);
            startActivity(welcome);
        }else if (id==R.id.Exit){
            finishAffinity();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}