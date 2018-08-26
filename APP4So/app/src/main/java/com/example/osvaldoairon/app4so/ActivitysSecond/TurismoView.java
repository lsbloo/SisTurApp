package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.osvaldoairon.app4so.R;

public class TurismoView extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turismo_view);


        String site = getIntent().getStringExtra("site");

        webView=(WebView)findViewById(R.id.cityview);

        webView.setWebViewClient(new Navegador());


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        if(site != null){
            open(webView,site);
        }else{
            Toast.makeText(this, ""+site, Toast.LENGTH_SHORT).show();
        }

    }

    public void open(View view, String site){

        String url = site;
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }


    public class Navegador extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
