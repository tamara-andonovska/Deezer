package com.tamara.deezer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class RestGetSongs extends AsyncTask<Void, Void, ArrayList<String>> {

    String name, artist;
    Context context;

    public RestGetSongs(String name, String artist, Context context) {
        this.context = context;
        this.name = name;
        this.artist = artist;
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

        Intent intent = new Intent(context, SearchResultActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) s);
        intent.putExtra("BUNDLE", args);
        context.startActivity(intent);
        //MainActivity.sendNotification(s);
    }
}
