package com.searchit.animestreams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SelectContent extends AppCompatActivity {

    Button Anime;
    Button Manga;
    Button TV;
    private String gameID = "4387583";
    private String intesitailid = "Interstitial_Android";
    private boolean test = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_content);

        Anime = findViewById(R.id.AnimeWindow);
        Manga = findViewById(R.id.MangaWindow);
        TV = findViewById(R.id.TVWindow);

//        UnityAds.initialize(this,gameID,test);
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

        Anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (UnityAds.isReady(intesitailid)){
//                    UnityAds.show(SelectContent.this, intesitailid);
//                }

                Intent intent = new Intent(SelectContent.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Manga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (UnityAds.isReady(intesitailid)){
//                    UnityAds.show(SelectContent.this, intesitailid);
//                }

                Intent intent = new Intent(SelectContent.this,MainActivity2.class);
                intent.putExtra("Site","https://mangareader.to");
                startActivity(intent);
            }
        });
        TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (UnityAds.isReady(intesitailid)){
//                    UnityAds.show(SelectContent.this, intesitailid);
//                }

                Intent intent = new Intent(SelectContent.this,MainActivity2.class);
                intent.putExtra("Site","https://dopebox.net/home/");
                startActivity(intent);
            }
        });

    }

}