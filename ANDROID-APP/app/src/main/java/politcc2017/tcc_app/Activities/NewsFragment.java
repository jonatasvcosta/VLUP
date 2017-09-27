package politcc2017.tcc_app.Activities;

/**
 * Created by Jonatas on 13/08/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

import politcc2017.tcc_app.Activities.Bookshelf.BookshelfActivity;
import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.Listeners.FragmentListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

import static politcc2017.tcc_app.Components.WordContextDialog.CONTEXT_ADD_TEXT;
import static politcc2017.tcc_app.Components.WordContextDialog.CONTEXT_ADD_WORD;

public class NewsFragment extends Fragment{

    private CustomTextView mNewsText;
    private CustomSearchToolbar mSearchToolbar;
    private RecyclerView mRecyclerView;
    private FloatingActionsMenu ratingMenu;
    private GenericAdapter mAdapter;
    private FloatingActionButton addBookshelfFAB, rateGoodFAB, rateMediumFAB, rateBadFAB, fabRatedGood, fabRatedMedium, fabRatedBad;
    private FragmentListener listener;
    private long referenceTime;

    public NewsFragment() {
        // Required empty public constructor
    }

    public NewsFragment(FragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_news, container, false);
        referenceTime = System.currentTimeMillis();
        mSearchToolbar = (CustomSearchToolbar) v.findViewById(R.id.news_activity_search_toolbar);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.news_activity_trendingtopics_recyclerview);
        ratingMenu = (FloatingActionsMenu) v.findViewById(R.id.news_activity_rating_floating_menu);
        addBookshelfFAB = (FloatingActionButton) v.findViewById(R.id.news_activity_add_bookshelf_btn);
        rateGoodFAB = (FloatingActionButton) v.findViewById(R.id.news_activity_rate_good_btn);
        rateMediumFAB = (FloatingActionButton) v.findViewById(R.id.news_activity_rate_neutral_btn);
        rateBadFAB = (FloatingActionButton) v.findViewById(R.id.news_activity_rate_bad_btn);
        fabRatedGood = (FloatingActionButton) v.findViewById(R.id.news_activity_rated_good);
        fabRatedMedium = (FloatingActionButton) v.findViewById(R.id.news_activity_rated_neutral);
        fabRatedBad = (FloatingActionButton) v.findViewById(R.id.news_activity_rated_bad);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSearchToolbar.setAutoCompleteSearchBar();
        mSearchToolbar.setAdvancedFilter(getActivity(), ResourcesHelper.getStringArrayAsArrayList(getContext(), R.array.news_search_advanced_filter));

        setSuggestionList();
        mNewsText = (CustomTextView) v.findViewById(R.id.activity_news_text);
        mNewsText.allowWordContextMenu();
        GenericData mData = new GenericData();
        loadTrendingTopics(mData);
        mAdapter = new GenericAdapter(mData, ViewHolderType.TRENDING_TOPICS_VIEW_HOLDER, getContext());

        setupListeners();
        mRecyclerView.setAdapter(mAdapter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mSearchToolbar.registerRecyclerViewScrollListener(mRecyclerView, displayMetrics.heightPixels);
        if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", "READY");
        return v;
    }

    private long getTimePassed(){
        return System.currentTimeMillis() - referenceTime;
    }

    private void loadTrendingTopics(GenericData data){
        //call server here
        ArrayList<String> topics = new ArrayList<>();
        topics.add("Politics");
        topics.add("Refugees");
        topics.add("Global warming");
        topics.add("North Korea");
        topics.add("G5");
        data.addStringsToAllCells(GenericData.TRENDING_TOPIC, topics);
    }

    private void loadNews(String search){
        //call server here
        mNewsText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        ratingMenu.setVisibility(View.VISIBLE);
    }

    public void switchRecyclerView(){
        mNewsText.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ratingMenu.setVisibility(View.GONE);
    }

    public void setupSpeechInput(String input){
        mSearchToolbar.setSuggestionText(input);
    }

    private void setupListeners(){
        addBookshelfFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", SqlHelper.RULE_ADD_TEXT_BOOKSHELF);
                Intent intent = new Intent(getContext(), BookshelfActivity.class);
                intent.putExtra(CONTEXT_ADD_TEXT, mNewsText.getText().toString());
                getContext().startActivity(intent);
            }
        });
        rateGoodFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Good! - " +(getTimePassed()/1000)+ " s", Toast.LENGTH_SHORT).show();
                if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", SqlHelper.RULE_RATE_TEXT);
                fabRatedGood.setVisibility(View.VISIBLE);
                ratingMenu.setVisibility(View.GONE);
            }
        });
        rateMediumFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Medium! - " +(getTimePassed()/1000)+ " s", Toast.LENGTH_SHORT).show();
                if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", SqlHelper.RULE_RATE_TEXT);
                fabRatedMedium.setVisibility(View.VISIBLE);
                ratingMenu.setVisibility(View.GONE);
            }
        });
        rateBadFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bad! - "+(getTimePassed()/1000)+ " s", Toast.LENGTH_SHORT).show();
                if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", SqlHelper.RULE_RATE_TEXT);
                fabRatedBad.setVisibility(View.VISIBLE);
                ratingMenu.setVisibility(View.GONE);
            }
        });
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onMessageSent("NEWS_FRAGMENT", "PROMPT_SPEECH");
            }
        });
        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNews(mSearchToolbar.getSuggestionText());
            }
        });

        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, int position) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                mSearchToolbar.setSuggestionText(message);
                loadNews(message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setNewsText(String text){
        mNewsText.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        ratingMenu.setVisibility(View.VISIBLE);
        mNewsText.setText(text);
    }

    private void setSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
                "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }
}