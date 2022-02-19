package com.searchit.animestreams;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SpotlightAdapter extends RecyclerView.Adapter<SpotlightAdapter.SMyViewHolder> {
    private ArrayList<SAnime> SAnimeList;

    int size;
    private Context context;

    public SpotlightAdapter(Context context, ArrayList<SAnime> AnimeList) {
        this.context = context;
        this.SAnimeList = AnimeList;

    }

    @NonNull
    @Override
    public SpotlightAdapter.SMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spotlight_box, parent, false);

        return new SpotlightAdapter.SMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotlightAdapter.SMyViewHolder holder, int position) {
        holder.title.setText(SAnimeList.get(position).getName());
        holder.episodeno.setText(SAnimeList.get(position).getNumber());
        holder.des.setText(SAnimeList.get(position).getDes());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, selectEpisode.class);
                intent.putExtra("link", SAnimeList.get(position).getUrl());
                intent.putExtra("animename", SAnimeList.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                int ep = holder.episodeno.getText().toString().lastIndexOf(" ");
//                size = 0;
//                AnimeDatabase database = AnimeDatabase.getInstance(context);
//
//                Anime temp = SAnimeList.get(position);
//                Anime databaseAnime = database.animeDao().getAnimeByNameAndEpisodeNo(temp.getName(), temp.getEpisodeNo());
//                Log.i("yotimername", temp.getName());
//                Log.i("yotimerepisode", String.valueOf(temp.getEpisodeNo()));
//
//                String time = "0";
//                if (databaseAnime != null) {
//                    time = databaseAnime.getTime();
//                }
//                database.animeDao().deleteAnimeByNameAndEpisodeNo(temp.getName(), temp.getEpisodeNo());
//                Anime anime = new Anime(temp.getName(), temp.getLink(), temp.getEpisodeNo(), temp.getImageLink(), time);
//                database.animeDao().insertAnime(anime);
//                intent.putExtra("animename", temp.getName());
//                intent.putExtra("imagelink", temp.getImageLink());
//                intent.putExtra("time", time);
//
                context.getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(SAnimeList.get(position).getImgUrl()).into(holder.imageofanime);
    }

    @Override
    public int getItemCount() {
        return SAnimeList.size();
    }

    public class SMyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title, episodeno, des;
        private ImageView imageofanime;

        public SMyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.S_animename);
            episodeno = itemView.findViewById(R.id.S_episodeno);
            imageofanime = itemView.findViewById(R.id.S_img);
            cardView = itemView.findViewById(R.id.S_cardview);
            des = itemView.findViewById(R.id.S_animeDetails);
        }
    }
}
