package politcc2017.tcc_app.Activities;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.Listeners.ContextMenuClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

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
    private boolean contextWordDialogOpened = false;
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
        searchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        searchToolbar.registerRecyclerViewScrollListener(sitesRecyclerView, displayMetrics.heightPixels);
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

        Intent i = getIntent();
        if(i != null){
            String url = i.getStringExtra(GenericData.LINK);
            if(url != null && url.length() > 0) loadWebSite(url);
        }
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
                String selectedWord = clipboard.getText().toString();
                if(!contextWordDialogOpened) {
                    contextWordDialogOpened = true;
                    WordContextMenu wordData = ServerRequestHelper.getWordInformation(getBaseContext(), SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY), selectedWord);
                    MaterialDialog dialog = WordContextDialog.createDialog(NavigateActivity.this, selectedWord, "", new ContextMenuClickListener() {
                        @Override
                        public void onClick(View v, String action) {
                            Toast.makeText(getBaseContext(), action, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onClick(View view) {

                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            contextWordDialogOpened = false;
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchToolbar.setSearchUrl(result.get(0));
                    searchToolbar.notifySearchComplete();
                }
                break;
            }

        }
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

        //These fake data will be replaced by data from server:
        ArrayList<String> linkArray = new ArrayList<>();
        ArrayList<String> titleArray = new ArrayList<>();
        ArrayList<String> descriptionArray = new ArrayList<>();
        ArrayList<String> imageArray = new ArrayList<>();

        linkArray.add("http://www.bbc.com/news");
        titleArray.add("BBC");
        descriptionArray.add("A British Broadcasting Corporation é uma emissora pública de rádio e televisão do Reino Unido fundada em 1922. Possui uma boa reputação nacional e internacional. Por vezes, é chamada afetuosamente pelos ingleses como Beeb, The Corporation ou Auntie.");
        imageArray.add("https://www.bbc.co.uk/news/special/2015/newsspec_11063/brasil_1024x576.png");
        linkArray.add("http://www.dw.com/en/top-stories/s-9097");
        titleArray.add("Deutsche Welle");
        descriptionArray.add("Deutsche Welle é uma empresa de radiodifusão da Alemanha, com sedes em Bonn e Berlim, que transmite para o exterior programas de rádio, além de oferecer uma programação televisiva e um amplo portal de conteúdo online em 30 línguas.");
        imageArray.add("http://2.bp.blogspot.com/-oK-e8hUeW1M/Tyfi-bDXxiI/AAAAAAAAD_o/h5Msp1u-tLs/s1600/Deutsche+Welle+logo.png");
        linkArray.add("https://www.nytimes.com/");
        titleArray.add("The New York Times");
        descriptionArray.add("The New York Times: Find breaking news, multimedia, reviews & opinion on Washington, business, sports, movies, travel, books, jobs, education, real estate...");
        imageArray.add("http://vignette4.wikia.nocookie.net/turtledove/images/9/9a/New_York_Times_logo_500.gif/revision/latest?cb=20151126221853");
        linkArray.add("https://www.bloomberg.com/");
        titleArray.add("Bloomberg");
        descriptionArray.add("Bloomberg delivers business and markets news, data, analysis, and video to the world, featuring stories from Businessweek and Bloomberg News.");
        imageArray.add("http://www.coinbuzz.com/wp-content/uploads/2014/05/Bloomberg.png");

        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_LINK, linkArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_TITLE, titleArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_DESCRIPTION, descriptionArray);
        data.addStringsToAllCells(GenericData.SUGGESTION_ITEM_IMAGE, imageArray);

        //end of fake data

        mAdapter = new GenericAdapter(data, ViewHolderType.BROWSER_SUGGESTION_ITEM_VIEW_HOLDER);
        sitesRecyclerView.setAdapter(mAdapter);
        sitesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.RegisterClickListener(new CellClickListener() {

            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(final ImageView v, String link) {
                ServerRequestHelper.imageAbsoluteURLRequest(getApplicationContext(), link, new ImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        v.setImageBitmap(response.getBitmap());
                    }
                });
            }

            @Override
            public void onClick(String message, int position) {

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

    private void loadWebSite(String url){
        searchToolbar.setSearchUrl(url);
        loadNewWebsite();
    }

    @Override
    public void handleLearningLanguageChange(){
        Toast.makeText(getBaseContext(), "Language was changed, NavigateActivity must reload all the content", Toast.LENGTH_SHORT).show();
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
