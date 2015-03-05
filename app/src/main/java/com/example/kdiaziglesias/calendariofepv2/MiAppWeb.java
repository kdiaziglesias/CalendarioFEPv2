package com.example.kdiaziglesias.calendariofepv2;


import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MiAppWeb extends WebViewClient {


    public boolean shouldOverrideUrlLoading(WebView view, String url) {


        if (Uri.parse(url).getHost().endsWith("rfep.es")) {

            return false;

        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;


    }
}