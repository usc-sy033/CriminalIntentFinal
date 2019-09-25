package android.bignerdranch.criminalIntent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class HelpWebPageActivity extends AppCompatActivity {
    private WebView  mWebView;

    public static Intent webIntent(Context packageContext, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikihow.com/Check-In-on-Facebook"));
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpwebpage);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

        });

        mWebView.loadUrl("https://www.wikihow.com/Check-In-on-Facebook");
    }
}
