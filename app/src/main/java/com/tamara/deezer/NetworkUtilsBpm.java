package com.tamara.deezer;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtilsBpm {
    private final static String BASE_URL = "https://api.deezer.com/track/";

    public static String getInfo(String id) throws IOException {
        Log.d("TAMI", "getInfo in NetworkUtilsBpm...");

        String URL = BASE_URL + id;

        Uri builtUri = Uri.parse(URL).buildUpon().build();
        String myurl = builtUri.toString();

        Log.d("TAMI", "in getInfo: " + id);

        InputStream inputStream = null;
        String response = null;
        HttpURLConnection conn = null;

        //json object-ot sto se vrakja
        double bpm = 0.0;
        String title = null;
        String artist = null;

        String returnValue = null; //bpm vo string format

        //baranje 1: se prakja ednokratno http get pri vklucuvanje na app
        try {
            java.net.URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();
            inputStream = conn.getInputStream();
            response = convertInputToString(inputStream);
            //Log.d("TAMI", response);

            //baranje 2: se izvlekuvaat key:value parovite od json object-ot kako odgovorot na get baranjeto

            JSONObject data = new JSONObject(response);
            bpm = data.getDouble("bpm");
            title = data.getString("title");
            artist = data.getJSONObject("artist").getString("name");

            Log.d("TAMI", bpm+""); //0.0 vrakja zaso taka e na stranata
            returnValue = bpm + "|" + title + "|" + artist;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (inputStream != null){
                inputStream.close();
            }
        }
        return returnValue;
    }

    private static String convertInputToString(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            builder.append(line + "\n");
        }
        if (builder.length() == 0){
            return null;
        }
        return builder.toString();
    }

}
