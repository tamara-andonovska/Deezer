package com.tamara.deezer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class compareResultAdapter extends RecyclerView.Adapter<compareResultAdapter.MyViewHolder> {

    ArrayList<String> songs;
    Context context;

    public compareResultAdapter(Context context, ArrayList<String> songs){
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public compareResultAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.song, parent, false);
        return new compareResultAdapter.MyViewHolder(v).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull compareResultAdapter.MyViewHolder holder, int position) {
        String song = songs.get(position);
        String [] s = song.split("\\|");
        holder.id.setText(s[0]);
        holder.title.setText(s[1]);
        holder.artist.setText(s[2]);
        holder.album.setText(s[3]);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private compareResultAdapter compareResultAdapter;
        TextView id, title, artist, album;
        Button bpm;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            id = itemView.findViewById(R.id.show_id);
            title = itemView.findViewById(R.id.show_title);
            artist = itemView.findViewById(R.id.show_artist);
            album = itemView.findViewById(R.id.show_album);
            bpm = itemView.findViewById(R.id.get_bpm);

            bpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String prevSongInfo = CompareResultActivity.getPrevSongInfo();
                    new RestGetSongBpm2(prevSongInfo, id.getText().toString(), itemView.getContext()).execute();
                }
            });
        }

        public compareResultAdapter.MyViewHolder linkAdapter(compareResultAdapter compareResultAdapter){
            this.compareResultAdapter = compareResultAdapter;
            return this;
        }
    }

}
