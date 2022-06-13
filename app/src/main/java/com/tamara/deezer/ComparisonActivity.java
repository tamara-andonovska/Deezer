package com.tamara.deezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ComparisonActivity extends AppCompatActivity {

    TextView title1, title2, artist1, artist2, bpm1, bpm2;
    //dve kopcinja za comparison za da se izmeni?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        Intent intent = getIntent();
        String t1 = intent.getStringExtra("title1");
        String t2 = intent.getStringExtra("title2");
        String a1 = intent.getStringExtra("artist1");
        String a2 = intent.getStringExtra("artist2");
        String b1 = intent.getStringExtra("bpm1");
        String b2 = intent.getStringExtra("bpm2");

        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        artist1 = findViewById(R.id.artist1);
        artist2 = findViewById(R.id.artist2);
        bpm1 = findViewById(R.id.bpm1);
        bpm2 = findViewById(R.id.bpm2);

        title1.setText(t1);
        title2.setText(t2);
        artist1.setText(a1);
        artist2.setText(a2);
        bpm1.setText(b1);
        bpm2.setText(b2);

    }
}