package politcc2017.tcc_app.Activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
        registerForContextMenu(mWebView);
        setupClipboardListener();
    }

    private void setupClipboardListener(){
        final ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);

        clipboard.addPrimaryClipChangedListener( new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                String a = (String) clipboard.getText();
                Toast.makeText(getBaseContext(),a,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //user has long pressed your TextView

        //menu.add(0, v.getId(), 0, "text that you want to show in the context menu - I use simply Copy");
        //cast the received View to TextView so that you can get its text

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        Toast.makeText(getBaseContext(), clipboard.getText().toString(), Toast.LENGTH_SHORT).show();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

}
