package com.tamara.deezer;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private String sharedPrefFile = "com.tamara.deezer";
    //SharedPreferences preferences;

    EditText name, artist;
    Button search;

    private static final int JOB_ID = 1;
    private static NotificationCompat.Builder builder;
    private static NotificationManager manager;
    private static final int NOTIFICATION_ID = 0;
    private NotificationChannel notificationChannel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.title);
        artist = findViewById(R.id.artist);

        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchName = name.getText().toString();
                String searchArtist = artist.getText().toString();
                Log.d("TAMI", "in main: " + searchName + " " + searchArtist);

                scheduleJob(searchName, searchArtist);
            }
        });

        notificationChannel = new NotificationChannel("123", "notif", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(1);

        //ova mora tuka, zasto inaku javuva error vo static funkcija
        //a sendNotification mora da e static za da se povika funkcijata vo onPostExecute
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        builder = new NotificationCompat.Builder(this, "123");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void scheduleJob(String name, String artist) {

        new RestGetSongs(name, artist, MainActivity.this).execute();

//        Log.d("TAMI", "scheduling job...");
//        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        ComponentName serviceName = new ComponentName(getPackageName(), MyJobService.class.getName());
//        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName);
//        //baranje 4: da se izvrsat 1,2,3 samo ako baterijata e polna
//        builder.setRequiresBatteryNotLow(true);
//        JobInfo jobInfo = builder.build();
//        scheduler.schedule(jobInfo);
    }

    //baranje 3: notifikacija
    //se povikuva vo onPostExecute, za da se pojavi pri zavrsuvanje na baranje 1 i 2
    public static void sendNotification(String songInfo, Context context) {
        Log.d("TAMI", "preparing notification...");

        String [] res = songInfo.split("\\|");
        String bpm = res[0];

        builder.setContentTitle("Song bpm")
                .setContentText("The song bpm is " + bpm)
                .setSmallIcon(R.drawable.ic_baseline_album_24);
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, BpmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("bpm", bpm);
        intent.putExtra("title", res[1]);
        intent.putExtra("artist", res[2]);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.ic_baseline_add_24, "Add song", pendingIntent);

        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID, notification);
        Log.d("TAMI", "notification sent...");
    }

    public static void updateNotification(String prevSongInfo, String songInfo, Context context){
        String [] res1 = prevSongInfo.split("\\|");
        String [] res2 = songInfo.split("\\|");
        Log.d("TAMI", prevSongInfo);
        Log.d("TAMI", songInfo);

        builder.setContentTitle("Song bpm")
                .setContentText("The song bpm is " + res2[0])
                .setSmallIcon(R.drawable.ic_baseline_album_24);
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, ComparisonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("bpm2", res2[0]);
        intent.putExtra("title2", res2[1]);
        intent.putExtra("artist2", res2[2]);

        //ovie treba da se vlecat
        intent.putExtra("bpm1", res1[0]);
        intent.putExtra("title1", res1[1]);
        intent.putExtra("artist1", res1[2]);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(R.drawable.ic_baseline_add_24, "See info", pendingIntent);

        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID, notification);
        Log.d("TAMI", "notification updated...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sp = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name.getText().toString());
        editor.putString("artist", artist.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        @SuppressLint("WrongConstant") SharedPreferences sp = getSharedPreferences(sharedPrefFile, MODE_APPEND);
        name.setText(sp.getString("name", ""));
        artist.setText(sp.getString("artist", ""));
    }
}