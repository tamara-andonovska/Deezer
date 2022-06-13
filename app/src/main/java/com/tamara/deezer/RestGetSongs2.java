package com.tamara.deezer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class RestGetSongs2 extends AsyncTask<Void, Void, ArrayList<String>> {

    String name, artist, name1, artist1, bpm1;
    Context context;

    public RestGetSongs2(String name1, String artist1, String bpm1, String name, String artist, Context context) {
        this.context = context;
        this.name = name;
        this.artist = artist;
        this.name1 = name1;
        this.artist1 = artist1;
        this.bpm1 = bpm1;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        Log.d("TAMARA", "doInBackground in RestGet...");
        ArrayList<String> response = null;
        try {
            response = NetworkUtils.getInfo(name, artist); //vrakja arraylist rez od site pesni
            Log.d("TAMI", "doInBackground: " + name + " " + artist);
            //Log.d("TAMI", "doInBackground: " + response); //taman e
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);
        //s stringot e vsusnost age
        //tuka treba da se isprati notifikacija
        Log.d("TAMI", "onPostExecute...");
        Log.d("TAMI", "onPostExecute: " + s.size());

        Intent intent = new Intent(context, CompareResultActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) s);
        intent.putExtra("BUNDLE", args);
        //tuka da se stavat vo intetn name1, i ostali za da se vlecat niz aktivnosti

        intent.putExtra("title1", name1);
        intent.putExtra("artist1", artist1);
        intent.putExtra("bpm1", bpm1);

        context.startActivity(intent);
        //MainActivity.sendNotification(s);
    }
}
