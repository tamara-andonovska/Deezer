package com.tamara.deezer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class CompareResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private compareResultAdapter compareResultAdapter;
    public static ArrayList<String> songs;

    TextView t1, a1, b1;
    public static String title1, artist1, bpm1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_result);

        t1 = findViewById(R.id.title1);
        a1 = findViewById(R.id.artist1);
        b1 = findViewById(R.id.bpm1);

        Intent i = getIntent();
        title1 = i.getStringExtra("title1");
        artist1 = i.getStringExtra("artist1");
        bpm1 = i.getStringExtra("bpm1");

        t1.setText(title1);
        a1.setText(artist1);
        b1.setText(bpm1);

        recyclerView = findViewById(R.id.recyclerviewsongs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songs = new ArrayList<>();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        songs = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        compareResultAdapter = new compareResultAdapter(this, songs);

        recyclerView.setAdapter(compareResultAdapter);

        Log.d("TAMI", "SRA: " + songs); //gi ima vnatre site
    }

    public static String getPrevSongInfo(){
        return bpm1 + "|" + title1 + "|" + artist1;
    }
}