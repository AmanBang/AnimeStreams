package com.searchit.animestreams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.searchit.animestreams.Room.database.AnimeDatabase;
import com.searchit.animestreams.Room.entities.Anime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;


public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyViewHolder> {
    private ArrayList<String> mSiteLink;
    private ArrayList<String> mEpisodeList;
    String animename;
    Activity activity;
    private Context context;
    private String imagelink;
    Dialog dialog;

    private final String gameID = "4387583";
    private String intesitailid = "Interstitial_Android";
    private boolean test = false;

    public EpisodeAdapter(Context context, ArrayList<String> SiteList, ArrayList<String> EpisodeList, String imagelink, String animename, Activity activity) {
        this.mSiteLink = SiteList;
        this.context = context;
        this.animename = animename;
        this.mEpisodeList = EpisodeList;
        this.imagelink = imagelink;
        this.activity = activity;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.downloadsheet);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = context.getDrawable(R.drawable.background);
            dialog.getWindow().setBackgroundDrawable(drawable);
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView button;
        private Button download;
        private LinearLayout layout;
        private RelativeLayout relativeLayout;

        MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.linearlayouta);
            button = view.findViewById(R.id.notbutton);
            download = view.findViewById(R.id.downloadchoice);
            relativeLayout = view.findViewById(R.id.episodeRelativeLayout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterforepisode, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.button.setText(animename + " Episode " + (position + 1));

        AnimeDatabase database = AnimeDatabase.getInstance(context);
        if (database.getInstance(context).animeDao().checkAnimeByNameAndEpisodeNo(animename,String.valueOf(position +1)) != 0) {
            Log.i("check","checked");
            holder.relativeLayout.setBackgroundColor(Color.GRAY);
            holder.layout.setBackgroundColor(Color.GRAY);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WatchVideo.class);
                intent.putExtra("link", mSiteLink.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AnimeDatabase database = AnimeDatabase.getInstance(context);


                Anime temp = new Anime(animename, mSiteLink.get(position), String.valueOf(position + 1),imagelink,"0");
                Anime databaseAnime = database.animeDao().getAnimeByNameAndEpisodeNo(temp.getName(), temp.getEpisodeNo());

                String time = "0";
                if (databaseAnime != null) {
                    time = databaseAnime.getTime();
                }
                database.animeDao().deleteAnimeByNameAndEpisodeNo(temp.getName(), temp.getEpisodeNo());
                Anime anime = new Anime(temp.getName(), temp.getLink(), temp.getEpisodeNo(), temp.getImageLink(), time);
                database.animeDao().insertAnime(anime);
                intent.putExtra("animename", animename);
                intent.putExtra("imagelink", imagelink);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noofepisodes", String.valueOf(mEpisodeList.size()));
                intent.putExtra("animenames", animename);
                intent.putExtra("selectepisodelink", mSiteLink.get(position));
                intent.putExtra("camefrom", "selectepisode");
                context.getApplicationContext().startActivity(intent);
                holder.relativeLayout.setBackgroundColor(Color.GRAY);

            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Document[] gogoAnimePageDocument = {null};

                final String[] splitString = new String[1];
                new Thread( new Runnable() { @Override public void run() {
                    try {
                        gogoAnimePageDocument[0] = Jsoup.connect(mSiteLink.get(position)).get();
                        String vidStreamUrl = "https:" + gogoAnimePageDocument[0].getElementsByClass("play-video").get(0).getElementsByTag("iframe").get(0).attr("src");
                        Log.i("VideoLinkTOPlay",vidStreamUrl);
                        Log.i("VideoLinkTOPlay",mSiteLink.get(position));

//                        gogoAnimePageDocument[1] = Jsoup.connect(vidStreamUrl).get();


                         splitString[0] =vidStreamUrl.replace("streaming.php","download");
                        Log.i("VideoLinkTOPlay2", splitString[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } } ).start();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse(splitString[0]));
                        context.startActivity(intent);
                    }
                },2000);


            }
        });
    }

    @Override
    public int getItemCount() {
        return mEpisodeList.size();
    }

}

