package com.searchit.animestreams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WatchVideo extends AppCompatActivity {
    ProgressBar progressBar;
    TextView title;
    String imageLink;
    AnimeDatabase animeDatabase;
    public static String url = "https://www1.gogoanime.ai/";
    ArrayList<Scraper> scrapers = new ArrayList<>();
    ArrayList<Quality> qualities;
    String vidStreamUrl;
    String link;
    private String animeName;
    int episodeNumber;
    Anime currentAnime;
    String tempGogoAnimeLink;
    //=============================================================================================================================================================//
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private String gameID = "4387583";
    private String intesitailid = "Interstitial_Android";
    private boolean test = true;


    Map<String, String> extraHeaders = new HashMap<String, String>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoviewer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Handler h = new Handler();





        animeDatabase = AnimeDatabase.getInstance(getApplicationContext());
        link = getIntent().getStringExtra("link");
        int lastIndexOfDash = link.lastIndexOf("-");
        episodeNumber = Integer.parseInt(link.substring(lastIndexOfDash + 1));
        animeName = getIntent().getStringExtra("animename");
        Log.i("linkis", link);
        imageLink = getIntent().getStringExtra("imagelink");
        // time = Long.parseLong(getIntent().getStringExtra("time"));
        currentAnime = animeDatabase.animeDao().getAnimeByNameAndEpisodeNo(animeName, String.valueOf(episodeNumber));
        Log.i("yotimertimer", "soja" + currentAnime.getTime());
        progressBar = findViewById(R.id.buffer);

        new ScrapeVideoLink(link, this).execute();

//        UnityAds.initialize(getApplicationContext(),gameID,test);
//
//        UnityAds.load(intesitailid, new IUnityAdsLoadListener() {
//            @Override
//            public void onUnityAdsAdLoaded(String s) {
//                Toast.makeText(getApplicationContext(),"Ready",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
//                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
//            }
//        });
//------------------------------------------------------------------oncreate-----------------------------------------------------------------------------------//

        webView = findViewById(R.id.ASZ);
        customViewContainer = findViewById(R.id.customViewContainer);
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings settings = webView.getSettings();
//        webView
        settings.setSafeBrowsingEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setBlockNetworkImage(true);
        settings.setLoadsImagesAutomatically(false);
        settings.setDomStorageEnabled(true);
//        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Mobile Safari/537.36");
//        Map<String, String> extraHeaders = new HashMap<String, String>();
//        extraHeaders.put("Referer", " https://gogoanime.run/");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            settings.setForceDark(WebSettings.FORCE_DARK_AUTO);
        }

//        settings.setLoadsImagesAutomatically(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);



        webView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Double Tap to Full Screen", Toast.LENGTH_LONG).show();

            }
        },2000);
    }

    @SuppressLint("StaticFieldLeak")
    class ScrapeVideoLink extends AsyncTask<Void, Void, Void> {
        String gogoAnimeUrl;
        Context context;

        ScrapeVideoLink(String gogoAnimeUrl, Context context) {
            this.gogoAnimeUrl = gogoAnimeUrl;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tempGogoAnimeLink = gogoAnimeUrl;
            progressBar.setVisibility(View.VISIBLE);
            scrapers.clear();
            qualities = new ArrayList<>();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            webView.loadUrl(vidStreamUrl);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Document gogoAnimePageDocument = null;
//            Document gogoAnimePageDocument1 = null;

            try {
                gogoAnimePageDocument = Jsoup.connect(gogoAnimeUrl).get();
                vidStreamUrl ="https://"+ gogoAnimePageDocument.getElementsByClass("play-video").get(0).getElementsByTag("iframe").get(0).attr("src");
                Log.i("Linktoplay", vidStreamUrl);
//                gogoAnimePageDocument1 = Jsoup.connect(vidStreamUrl).get();
//                Log.i("Source1", gogoAnimePageDocument1+"");

            } catch (Exception e) {
                Log.i("gogoanimeerror", e.toString());
            }
            return null;
        }


    }



}