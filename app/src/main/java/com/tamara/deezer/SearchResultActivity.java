package com.tamara.deezer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private searchResultAdapter searchResultAdapter;
    public static ArrayList<String> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recyclerviewsongs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songs = new ArrayList<>();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        songs = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        searchResultAdapter = new searchResultAdapter(this, songs);

        recyclerView.setAdapter(searchResultAdapter);

        Log.d("TAMI", "SRA: " + songs); //gi ima vnatre site

    }
}