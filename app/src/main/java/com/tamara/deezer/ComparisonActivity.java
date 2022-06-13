package com.tamara.deezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ComparisonActivity extends AppCompatActivity {

    TextView title1, title2, artist1, artist2, bpm1, bpm2;
    Button compareAgain;

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

        compareAgain = findViewById(R.id.compare_again);
        compareAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComparisonActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}