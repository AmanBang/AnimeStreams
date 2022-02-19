package com.searchit.animestreams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;

public class webvideo extends Activity {
    private WebView webView;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;

    String url;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.webviewer);





        try {
            url = getIntent().getStringExtra("videostreamlink");
        }catch (Exception e)
        {
            e.printStackTrace();
        }

       Log.i("checkingstring",url);



    }

}



