package com.example.finalProject_meatector;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Tips extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Version> versionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.tips);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(),
                                History.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.tips:
                        break;
                    case R.id.help:
                        Intent intent3 = new Intent(
                                Tips.this, Help.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recycleView);

        iniData();;
        setRecyclerView();

    }

    private void setRecyclerView(){
        VersionAdapter versionAdapter = new VersionAdapter(versionList);
        recyclerView.setAdapter(versionAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void iniData() {

        versionList = new ArrayList<>();

        versionList.add(new Version("Daging Sapi", "1. Perhatikan warna daging\n" +
                "    Daging yang segar dan berkualitas memiliki warna merah segar dan tidak pucat atau kotor.\n\n" +
                "2. Perhatikan tekstur daging\n" +
                "    Apabila tekstur daging sapi teraasa kenyal dan posisinya kembali seperti semua saat ditekan, maka daging tersebut masih baru.\n\n" +
                "3. Hindari memilih daging berair\n" +
                "    Pastikan daging yang dipilih tidak berair, karena bisa jadi daging sapi tersebut merupakan daging gelonggong. Namun kalau yang menetes dari daging tersebut adalah darah, maka tidak masalah Anda membelinya karena itu merupakan “sari”daging sapi.\n\n" +
                "4. Hindari membeli daging beku\n" +
                "    Hindari memilih daging beku. Mungkin daging beku masih bagus, namun kesegarannya tentu sudah berkurang. Jika terpaksa membeli daging beku, pilihlah daging yang tidak terdapat bunga es. Daging yang terdapat bunga es tentunya sudah lama dan tidak segar."));

        versionList.add(new Version("Daging Babi", "1. Melihat dari warna dagingnya\n" +
                "    Kandungan ini dipengaruhi umur hewan, jenis, hingga pakan yang diberikan. Daging babi yang berkualitas biasanya cenderung berwarna merah pucat hingga keabuan.\n\n" +
                "2. Perhatikan tekstur dan kondisi permukaan daging\n" +
                "Daging babi berkualitas lazimnya memiliki permukaan daging yang lembab, tidak terlalu basah, tidak terlalu kering, dan tidak ada lendir.\n\n" +
                "3. Coba cium aroma atau bau dagingnya\n" +
                "Bau daging dari hewan yang tua biasanya relatif lebih kuat dan tajam dibanding yang masih muda. Demikian pula dengan daging dari hewan jantan juga memiliki bau yang lebih kuat dan tajam daripada hewan betina."));

        versionList.add(new Version("Daging Kambing", "1. Carilah daging yang berwarna pink kecokelatan dengan lapisan lemak putih krem.\n\n" +
                "2. Hindari daging yang berwarna abu-abu, terlalu berdarah atau memiliki lemak berminyak yang sangat kuning.\n\n" +
                "3. Usia dan jenis hewan bisa membuat warna daging berbeda. Kambing yang lebih tua cenderung memiliki warna daging yang lebih gelap.\n\n" ));

    }
}