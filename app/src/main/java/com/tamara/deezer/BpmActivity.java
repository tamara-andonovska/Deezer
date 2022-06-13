package com.tamara.deezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BpmActivity extends AppCompatActivity {

    Button compare;
    EditText title, artist;
    TextView titletext, artisttext, bpmtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpm);

        Intent intent = getIntent();
        String bpm = intent.getStringExtra("bpm");
        String title1 = intent.getStringExtra("title");
        String artist1 = intent.getStringExtra("artist");

        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);

        titletext = findViewById(R.id.title1);
        artisttext = findViewById(R.id.artist1);
        bpmtext = findViewById(R.id.bpm1);

        titletext.setText(title1);
        artisttext.setText(artist1);
        bpmtext.setText(bpm);

        compare = findViewById(R.id.compare_bpm);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RestGetSongs2(title1, artist1, bpm, title.getText().toString(), artist.getText().toString(), BpmActivity.this).execute();
            }
        });
    }
}