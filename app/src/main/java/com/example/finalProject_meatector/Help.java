package com.example.finalProject_meatector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.help);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.history:
                        Intent intent = new Intent(Help.this, History.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tips:
                        Intent intent2 = new Intent(Help.this, Tips.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        break;
                    case R.id.help:
                        break;
                }
                return false;
            }
        });



    }


}