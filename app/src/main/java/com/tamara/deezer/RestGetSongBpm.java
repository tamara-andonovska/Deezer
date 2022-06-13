package com.tamara.deezer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class RestGetSongBpm extends AsyncTask<Void, Void, String> {

    String id;
    Context context;

    public RestGetSongBpm(String id, Context context) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d("TAMARA", "doInBackground in RestGet...");
        String response = null;
        try {
            response = NetworkUtilsBpm.getInfo(id); //bpm|title|artist

            Log.d("TAMI", "doInBackground: " + id);
            Log.d("TAMI", "doInBackground: " + response); //taman e
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //s e bpm|title|artist
        //String[] res = s.split("\\|");
        Log.d("TAMI", "onPostExecute: " + s);
        MainActivity.sendNotification(s, context); //res[0] e bpm
    }

}
