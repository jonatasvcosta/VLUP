package politcc2017.tcc_app.Activities;

/**
 * Created by Jonatas on 13/08/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import politcc2017.tcc_app.Common.ResourcesHelper;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.Listeners.FragmentListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerConstants;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

public class VocabularyFragment extends Fragment{

    private CustomSearchToolbar mSearchToolbar;
    private RecyclerView wordsRecyclerView, trendingTopicsRecyclerView;
    private GenericAdapter mAdapter, trendingAdapter;
    private GenericData mData, trendingData;
    private FragmentListener listener;

    public VocabularyFragment() {
        // Required empty public constructor
    }

    public VocabularyFragment(FragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_vocabulary, container, false);

        mSearchToolbar = (CustomSearchToolbar) v.findViewById(R.id.vocabulary_activity_search_toolbar);
        mSearchToolbar.setAutoCompleteSearchBar();
        mSearchToolbar.setAdvancedFilter(getActivity(), ResourcesHelper.getStringArrayAsArrayList(getContext(), R.array.vocabulary_search_advanced_filter));
        wordsRecyclerView = (RecyclerView) v.findViewById(R.id.vocabulary_words_recyclerview);
        trendingTopicsRecyclerView = (RecyclerView) v.findViewById(R.id.vocabulary_activity_trendingtopics_recyclerview);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trendingTopicsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trendingTopicsRecyclerView.setVisibility(View.VISIBLE);
        mData = new GenericData();
        trendingData = new GenericData();
        loadTrendingTopics(trendingData);
        trendingAdapter = new GenericAdapter(trendingData, ViewHolderType.TRENDING_TOPICS_VIEW_HOLDER, getContext());
        wordsRecyclerView.setAdapter(mAdapter);
        mAdapter = new GenericAdapter(mData, ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER,getContext());
        setupListeners();
        trendingTopicsRecyclerView.setAdapter(trendingAdapter);
        //setActivityTitle(getResString(R.string.vocabulary_activity_title));

        SetSuggestionList();

        mSearchToolbar.registerSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWords(mSearchToolbar.getSuggestionText());
            }
        });
        if(listener != null) listener.onMessageSent("VOCABULARY_FRAGMENT", "READY");
        return v;
    }

    public void setupSpeechInput(String input){
        mSearchToolbar.setSuggestionText(input);
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

    public void loadSynonymWordsFromServer(String word){
        loadWordsListFromServer(word, ServerConstants.SYNONYM_ENDPOINT, "synonymous_list_final_language");
    }

    public void loadSimilarWordsFromServer(String word){
        loadWordsListFromServer(word, ServerConstants.SIMILAR_WORDS_ENDPOINT, "similar_words_final_language");
    }

    public void loadWordsListFromServer(String word, final String endpoint, final String keyRefence){
        if(word == null || word.length() == 0) return;
        mSearchToolbar.setSuggestionText(word);
        wordsRecyclerView.setVisibility(View.VISIBLE);
        if(this.listener != null) listener.onMessageSent("VOCABULARY_FRAGMENT", SqlHelper.RULE_CHECK_SIMILAR_WORDS);
        trendingTopicsRecyclerView.setVisibility(View.GONE);
        final ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("original_text", word);
        String locale = SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE, getContext());
        params.put("original_language", locale);
        params.put("final_language", locale);
        ServerRequestHelper.postAuthorizedJSONRequest(getContext(),  endpoint, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response != null && response.length() > 0){
                    JSONObject parsedResponse = null;
                    if(endpoint == ServerConstants.SIMILAR_WORDS_ENDPOINT) {
                        try {
                            parsedResponse = new JSONObject(response.getString(keyRefence));
                            JSONArray adverbs = null;
                            JSONArray nouns = null;
                            JSONArray verbs = null;
                            JSONArray adjectives = null;
                            try {
                                adverbs = parsedResponse.getJSONArray("r");
                            } catch (JSONException e) {
                            }
                            try {
                                nouns = parsedResponse.getJSONArray("n");
                            } catch (JSONException e) {
                            }
                            try {
                                verbs = parsedResponse.getJSONArray("v");
                            } catch (JSONException e) {
                            }
                            try {
                                adjectives = parsedResponse.getJSONArray("a");
                            } catch (JSONException e) {
                            }
                            addArrayToData(adverbs, words);
                            addArrayToData(nouns, words);
                            addArrayToData(adjectives, words);
                            addArrayToData(verbs, words);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if( endpoint == ServerConstants.SYNONYM_ENDPOINT){
                        try {
                            String synonymList = response.getString(keyRefence);
                            JSONArray parsedArray = new JSONArray(synonymList);
                            addArrayToData(parsedArray, words);
                        } catch (JSONException e) {}
                    }
                    mData.clearAllCells();
                    mData.addStringsToAllCells(GenericData.VOCABULARY_WORD, words);
                    wordsRecyclerView.setAdapter(mAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void addArrayToData(JSONArray array, ArrayList<String> words){
        if(array == null || array.length() == 0) return;
        for(int i = 0; i < array.length(); i++){
            try {
                words.add(array.get(i).toString());
            } catch (JSONException e) {}
        }
    }

    public void setupTrendingWords(String trendingWords){
        if(trendingWords == null || trendingWords.length() == 0) return;
        wordsRecyclerView.setVisibility(View.VISIBLE);
        trendingTopicsRecyclerView.setVisibility(View.GONE);
        String[] words = trendingWords.split("<li>");
        ArrayList<String> wordList = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            words[i] = words[i].replace("</li>", "");
            if(words[i] != null && words[i].length() > 2) wordList.add(words[i]);
        }
        mData.clearAllCells();
        mData.addStringsToAllCells(GenericData.VOCABULARY_WORD, wordList);
        wordsRecyclerView.setAdapter(mAdapter);
    }

    private void loadWords(String category){
        wordsRecyclerView.setVisibility(View.VISIBLE);
        trendingTopicsRecyclerView.setVisibility(View.GONE);
        category = category.trim().toLowerCase();
        //load words from server base on category
        if(category == null || category.equals("")) return;
        mData.clearAllCells();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();
        if(category.equals("turismo")) {
            words.add("travel");
            words.add("map");
            words.add("guide");
            count.add(20);
            count.add(3);
            count.add(2);
        }
        else if(category.equals("ti")){
            words.add("programming");
            words.add("algorithm");
            count.add(20);
            count.add(312);
        }
        mData.addStringsToAllCells(GenericData.VOCABULARY_WORD, words);
        mData.addIntegersToAllCells(GenericData.VOCABULARY_COUNT, count);
        mAdapter.notifyDataSetChanged();
        wordsRecyclerView.setAdapter(mAdapter);
    }

    private void SetSuggestionList(){
        //load suggestions from server, based on user learning language

        mSearchToolbar.setAutoCompleteSuggestionList(new String[] {
                "turismo", "viagens", "economia", "mercado financeiro", "finanças", "esportes", "lazer",
                "informática", "tecnologia", "TI", "empreendedorismo", "inovação", "ciência", "filosofia"});
    }

    private void setupListeners(){
        mSearchToolbar.registerMicrophoneListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onMessageSent("VOCABULARY_FRAGMENT", "PROMPT_SPEECH");
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
                WordContextDialog.launchDialog(getActivity(), message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
        trendingAdapter.RegisterClickListener(new CellClickListener() {
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
                loadWords(message);
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    public void switchRecyclerViews(){
        trendingTopicsRecyclerView.setVisibility(View.VISIBLE);
        wordsRecyclerView.setVisibility(View.GONE);
    }
}
