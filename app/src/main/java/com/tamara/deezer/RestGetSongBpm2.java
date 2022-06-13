package com.tamara.deezer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class RestGetSongBpm2 extends AsyncTask<Void, Void, String> {

    String id, prevSongInfo;
    Context context;

    public RestGetSongBpm2(String prevSongInfo, String id, Context context) {
        this.prevSongInfo = prevSongInfo;
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
        MainActivity.updateNotification(prevSongInfo, s, context); //res[0] e bpm
    }

}

