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

public class NetworkUtils {

    private final static String BASE_URL = "https://api.deezer.com/search?q=artist:\"";

    public static ArrayList<String> getInfo(String sname, String sartist) throws IOException {
        Log.d("TAMI", "getInfo in NetworkUtils...");

        String URL = BASE_URL + sartist + "\" track:\"" + sname + "\"";

        Uri builtUri = Uri.parse(URL).buildUpon().build();
        String myurl = builtUri.toString();

        Log.d("TAMI", "in getInfo: " + sname + " " + sartist);

        InputStream inputStream = null;
        String response = null;
        HttpURLConnection conn = null;

        //json object-ot sto se vrakja
        String title = null;
        String artist = null; //vo artist vo name
        String album = null; //vo album vo title
        double bpm = 0.0;
        int id = 0;

        ArrayList<String> resp = new ArrayList<>();

        String returnValue = null; //bpm vo string format

        //baranje 1: se prakja ednokratno http get pri vklucuvanje na app
        try {
            URL url = new URL(myurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.connect();
            inputStream = conn.getInputStream();
            response = convertInputToString(inputStream);
            //Log.d("TAMI", response);

            String song = null;

            //baranje 2: se izvlekuvaat key:value parovite od json object-ot kako odgovorot na get baranjeto

            JSONObject data = new JSONObject(response);
            JSONArray array = data.getJSONArray("data"); //taman ja vlece ovde
            Log.d("TAMI", array.toString());

            Log.d("TAMI", ""+array.length());
            for (int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                Log.d("TAMI", i+obj.toString());
                id = obj.getInt("id");
                title = obj.getString("title");
                artist = obj.getJSONObject("artist").getString("name");
                album = obj.getJSONObject("album").getString("title");

                Log.d("TAMI", Integer.toString(id));
                Log.d("TAMI", title);
                Log.d("TAMI", artist);
                Log.d("TAMI", album);

                song = id + "|" + title + "|" + artist + "|" + album;

                resp.add(song);
            }

            //Log.d("TAMI", Double.toString(bpm));
            //Log.d("TAMI", returnValue);
            //returnValue = String.valueOf(bpm);

            //i se pecatat vo log

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (inputStream != null){
                inputStream.close();
            }
        }
        return resp;
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
