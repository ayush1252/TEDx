package com.app.tedxdtu.tedxdtu17.NetworkServices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.tedxdtu.tedxdtu17.MainActivity;
import com.app.tedxdtu.tedxdtu17.Profile;
import com.app.tedxdtu.tedxdtu17.R;

import java.net.URI;

public class WebViewCl extends AppCompatActivity {
    WebView myWebView;
    String url= String.valueOf(URI.create("http://www.tedxdtu.in"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_web_view_client);
        // Makes Progress bar Visible
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
       Intent intent=getIntent();
        String url1=intent.getStringExtra("url");
        if(url1!=null)
            url=url1;

        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle(url);
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    setTitle(R.string.app_name);
            }
        });
        myWebView.setWebViewClient(new HelloWebViewClient());
        myWebView.loadUrl(url);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
    }
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent=new Intent();
        intent.setClass(WebViewCl.this,MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
