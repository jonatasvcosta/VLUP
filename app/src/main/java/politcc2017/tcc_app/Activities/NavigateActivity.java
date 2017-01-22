package politcc2017.tcc_app.Activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 20/11/2016.
 */

public class NavigateActivity extends BaseActivity implements View.OnClickListener{
    private WebView mWebView;
    private RecyclerView sitesRecyclerView;
    private CustomSearchToolbar searchToolbar;
    private String baseUrl = "https://www.google.com/search?q=";
    private ImageView toolbarSearchIcon, toolbarListIcon;
    private float SCROL_DY;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_navigate);
        SCROL_DY = getResources().getDimension(R.dimen.margin_extra_large);
        setActivityTitle(getResources().getString(R.string.navigate_activity_title));
        sitesRecyclerView = (RecyclerView) findViewById(R.id.browse_activity_suggestion_list);
        toolbarSearchIcon = (ImageView) findViewById(R.id.base_toolbar_rightmost_icon);
        toolbarListIcon = (ImageView) findViewById(R.id.base_toolbar_righ_icon);
        searchToolbar = (CustomSearchToolbar) findViewById(R.id.navigate_activity_search_toolbar);
        searchToolbar.registerSearchListener(this);
        searchToolbar.registerRecyclerViewScrollListener(sitesRecyclerView);
        mWebView = (WebView) findViewById(R.id.navigate_activity_webview);
        SetSuggestionListData();
        toolbarListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sitesRecyclerView.getVisibility() != View.VISIBLE){
                    mWebView.setVisibility(View.GONE);
                    sitesRecyclerView.setVisibility(View.VISIBLE);
                    searchToolbar.setVisibility(View.VISIBLE);
                }
            }
        });

        toolbarSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchToolbar.getVisibility() != View.VISIBLE) searchToolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupWebView(String url){
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String customErrorPageHtml = "<html></html>";
                mWebView.loadData(customErrorPageHtml, "text/html", null); //prevent from loading error page, because the default behaviour will be to google
                setupWebView(baseUrl+searchToolbar.getRawURL()); //search URL isn´t a complete website
            }
        });
        registerForContextMenu(mWebView);
        setupClipboardListener();
    }

    private void setupClipboardListener(){ //insert WordContext here
        final ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);

        clipboard.addPrimaryClipChangedListener( new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                String a = clipboard.getText().toString();
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
        //Toast.makeText(getBaseContext(), clipboard.getText().toString(), Toast.LENGTH_SHORT).show();
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void SetSuggestionListData(){
        GenericData data = new GenericData();
        ArrayList<String> drawerItemTexts = ResourcesHelper.getStringArrayAsArrayList(getBaseContext(), appLanguage, R.array.drawer_items);
        data.addStringsToAllCells(GenericData.DRAWER_ITEM_TEXT_KEY, drawerItemTexts);
        mAdapter = new GenericAdapter(data, ViewHolderType.BROSER_SUGGESTION_ITEM_VIEW_HOLDER, R.layout.browser_activity_suggestion_cell);
        sitesRecyclerView.setAdapter(mAdapter);
        sitesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {
            }

            @Override
            public void onLinkClick(String link) {
                searchToolbar.setSearchUrl(link);
                loadNewWebsite();
            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        if(mWebView.getVisibility() == View.VISIBLE) showToolbarRightIcons();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        loadNewWebsite();
    }

    private void loadNewWebsite(){
        if(searchToolbar.getSearchUrl() != null && !searchToolbar.getSearchUrl().equals("")) {
            mWebView.setVisibility(View.VISIBLE);
            setupWebView(searchToolbar.getSearchUrl());
            sitesRecyclerView.setVisibility(View.GONE);
            showToolbarRightIcons();
            searchToolbar.setVisibility(View.GONE);
        }
    }
}
