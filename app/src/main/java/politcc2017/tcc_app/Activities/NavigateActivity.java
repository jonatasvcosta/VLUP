package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 20/11/2016.
 */

public class NavigateActivity extends BaseActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_navigate);
        setActivityTitle(getResources().getString(R.string.navigate_activity_title));
        SetupWebView();
    }

    private void SetupWebView(){
        mWebView = (WebView) findViewById(R.id.navigate_activity_webview);
        mWebView.loadUrl("http://www.dw.com/de/themen/s-9077");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mWebView.setWebViewClient(new WebViewClient());
    }
}
