package com.example.finalProject_meatector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private RecyclerView rvHistory;
    DatabaseHelper db;
    adapterHistory adapterHistory;
    ArrayList<String> image,predict, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rv_history);
        db = new DatabaseHelper(History.this);

        image = new ArrayList<>();
        predict = new ArrayList<>();
        date = new ArrayList<>();

        getData();

        adapterHistory = new adapterHistory(History.this, image, predict, date);
        rvHistory.setAdapter(adapterHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(History.this));


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.history:
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tips:
                        startActivity(new Intent(getApplicationContext(),
                                Tips.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.help:
                        Intent intent3 = new Intent(
                                History.this, Help.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

    }



    private void getData(){
        Cursor cursor = db.readData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"No data!!",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                image.add(cursor.getString(1));
                predict.add(cursor.getString(2));
                date.add(cursor.getString(3));
            }
        }
    }


}