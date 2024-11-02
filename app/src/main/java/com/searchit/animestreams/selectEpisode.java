package com.searchit.animestreams;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.searchit.animestreams.Room.database.AnimeDatabase;
import com.searchit.animestreams.Room.entities.Anime;
import com.squareup.picasso.Picasso;


import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class selectEpisode extends AppCompatActivity {
    String link;
    String animename, animenameforrecents;
    private ArrayList<String> mEpisodeList = new ArrayList<>();
    private ArrayList<String> mSiteLink = new ArrayList<>();
    EpisodeAdapter mDataAdapter;
    String imagelink;
    ImageView imageofanime;
    TextInputEditText editText;
    TextView plotsummary;
    LinearLayout linearLayout;
    String summary;
    AnimeDatabase animeDatabase;
    Button download;
    RadioGroup radioGroup;
    String MultiQuality = "";
    ProgressBar bar;
    RecyclerView mRecyclerView;
    String cameback = "false";
    Boolean PassString = true;
    Button btn;
    Button radioBTn;
    private String gameID = "4387583";
    private String intesitailid = "Rewarded_Android";
    private boolean test = false;

    TextView AD_status;
    TextView AD_genre;
    TextView AD_release;
    TextView AD_Type;

    String typeT;
    String typeRelease;
    String typeGenre;
    String typeStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newepisodelayout);
        animenameforrecents = getIntent().getStringExtra("animename");
        Toolbar toolbar = findViewById(R.id.actiontool);
        linearLayout = findViewById(R.id.linear);
        plotsummary = findViewById(R.id.summary);
        imageofanime = findViewById(R.id.animeimage);
        download = findViewById(R.id.downloadchoice);
        btn = findViewById(R.id.btnADDserver);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(animenameforrecents);
        radioGroup = findViewById(R.id.radioGroup);

        AD_genre = findViewById(R.id.AD_genre);
        AD_status = findViewById(R.id.AD_status);
        AD_release = findViewById(R.id.AD_Release);
        AD_Type = findViewById(R.id.AD_type);



        //===============================================================================================================/
//
//        UnityAds.initialize(this, gameID, test);
//
//        UnityAds.load(intesitailid, new IUnityAdsLoadListener() {
//            @Override
//            public void onUnityAdsAdLoaded(String s) {
//                Toast.makeText(getApplicationContext(), "Ready", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
//                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.RB_fastServer:
//                        MultiQuality = "fast";
//                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.RB_MultiQuality:
//                        MultiQuality = "Multi";
//                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//            }
//        });

//        btn.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public void onClick(View v) {
//                int radioID = radioGroup.getCheckedRadioButtonId();
//                radioBTn = findViewById(radioID);
//                switch (radioID) {
//                    case R.id.RB_fastServer:
//                        PassString = false;
//                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.RB_MultiQuality:
//                        PassString = true;
//                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//            }
//        });
        animeDatabase = AnimeDatabase.getInstance(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom);

            }
        });
        link = getIntent().getStringExtra("link");

        if (getIntent().getStringExtra("cameback") != null)
            cameback = getIntent().getStringExtra("cameback");
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < link.length(); i++) {
            if (link.charAt(i) == 'y') {
                if (link.charAt(i + 1) == '/') {
                    for (int j = i + 2; j < link.length(); j++)
                        b.append(link.charAt(j));
                    break;
                }
            }

        }
        animename = b.toString();
        new SearchingEpisodes().execute();
        editText = findViewById(R.id.episodeno);
        Button button = findViewById(R.id.episodeselector);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!editText.getText().toString().equals("")) {
                    //=================================ads/////////////////////////////////////////

//                    if (UnityAds.isReady(intesitailid)) {
//                        UnityAds.show(com.example.searchitwatcher.selectEpisode.this, intesitailid);
//                    }


                    int episodeno = Integer.parseInt(String.valueOf(editText.getText()));
                    Intent intent = new Intent(getApplicationContext(), WatchVideo.class);
                    intent.putExtra("link", mSiteLink.get(episodeno - 1));
                    intent.putExtra("noofepisodes", String.valueOf(mEpisodeList.size()));
                    AnimeDatabase database = AnimeDatabase.getInstance(getApplicationContext());
                    Anime temp = database.animeDao().getAnimeByNameAndEpisodeNo(animenameforrecents, String.valueOf(episodeno));
                    String time = "0";
                    if (temp != null)
                        time = temp.getTime();
                    database.animeDao().deleteAnimeByNameAndEpisodeNo(animenameforrecents, String.valueOf(episodeno));
                    Anime anime = new Anime(animenameforrecents, mSiteLink.get(episodeno - 1), String.valueOf(episodeno), imagelink, time);
                    database.animeDao().insertAnime(anime);
                    intent.putExtra("Multiquality", PassString);
                    intent.putExtra("imagelink", imagelink);
                    intent.putExtra("animename", animenameforrecents);
                    intent.putExtra("animenames", animenameforrecents);
                    intent.putExtra("camefrom", "selectepisode");

//                       Toast.makeText(getApplicationContext(),"condition",Toast.LENGTH_LONG).show();
                    intent.putExtra("selectepisodelink", link);
                    Log.i("linkPass", link);
                    intent.putExtra("time", time);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    editText.requestFocus();
                    editText.setError("Enter episode no first");
                }
            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private class SearchingEpisodes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            linearLayout.setVisibility(View.GONE);
            mRecyclerView = findViewById(R.id.xyza);
            mRecyclerView.setVisibility(View.GONE);
            plotsummary.setVisibility(View.GONE);
            imageofanime.setVisibility(View.GONE);
            bar = findViewById(R.id.loading);
            bar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                org.jsoup.nodes.Document searching = Jsoup.connect(link).get();
                Elements li = searching.select("div[class=anime_video_body]").select("ul[id=episode_page]").select("li");
                imagelink = "https://gogoanime.dev"+searching.select("div[class=anime_info_body_bg]").select("img").attr("src");
                summary = searching.select("div[class=anime_info_body_bg]").select("p[class=type]").eq(1).text();

//                AD_Type.setText();
                typeT = searching.getElementsByClass("type").get(0).getElementsByTag("a").get(0).text();
                typeRelease = searching.getElementsByClass("type").get(3).text();
                typeGenre = searching.getElementsByClass("type").get(2).getElementsByTag("a").text();
                typeStatus = searching.getElementsByClass("type").get(4).getElementsByTag("a").text();

//                Log.i("typeDoc", ""+type);
//                AD_Type.setText(searching.gett);
                String a = String.valueOf(li.select("a").eq(li.size() - 1).html());
                int end;
                if (a.contains("-")) {
                    int index = a.indexOf('-');
                    end = Integer.parseInt(a.substring(index + 1));
                } else {
                    end = Integer.parseInt(a);
                }
                Log.d("episodesare", String.valueOf(end));
                if (end != 0)
                    for (int i = 1; i <= end; i++) {
                        String c = Constants.url + "/" + animename + "-episode-" + i;
                        Log.i("cis", c);
                        mEpisodeList.add(String.valueOf(i));

                        mSiteLink.add(c);
                    }
                else {
                    String c = Constants.url + "/" + animename + "-episode-" + 0;
                    mEpisodeList.add(String.valueOf(0));

                    mSiteLink.add(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setVisibility(View.VISIBLE);
            plotsummary.setVisibility(View.VISIBLE);
            imageofanime.setVisibility(View.VISIBLE);
            bar.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.view).setVisibility(View.VISIBLE);
            findViewById(R.id.text12).setVisibility(View.VISIBLE);
            mDataAdapter = new EpisodeAdapter(getApplicationContext(), mSiteLink, mEpisodeList, imagelink, animenameforrecents, selectEpisode.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            plotsummary.setText(summary);
            Picasso.get().load(imagelink).into(imageofanime);


            mRecyclerView.setHasFixedSize(true);

            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mDataAdapter);
            editText.setHint("Episode no between 1 to " + mEpisodeList.size());
            editText.setFilters(new InputFilter[]{
                    new InputFilterMinMax(1, mEpisodeList.size())
            })
            ;
            AD_Type.setText(typeT);
            AD_genre.setText(typeGenre);
            AD_release.setText(typeRelease);
            AD_status.setText(typeStatus);
            editText.requestFocus();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!cameback.equals("false")) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("playtuturu", "play");
                startActivity(intent);
            }

        }
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom);
        return false;
    }

    public void checked(View view) {

    }

}