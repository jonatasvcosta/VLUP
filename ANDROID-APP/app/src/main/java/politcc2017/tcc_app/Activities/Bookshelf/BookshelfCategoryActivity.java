package politcc2017.tcc_app.Activities.Bookshelf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import politcc2017.tcc_app.Activities.BaseActivity;
import politcc2017.tcc_app.Activities.MainActivitiesActivity;
import politcc2017.tcc_app.Components.CustomSearchToolbar;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.BookshelfCategory;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.BookshelfCategoryWords;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.BookshelfTexts;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
import politcc2017.tcc_app.Components.Helpers.SharedPreferencesHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.WordContextDialog;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class BookshelfCategoryActivity extends BaseActivity {
    private String title = "", categoryType = "";
    private int categoryID;
    private GenericData mData;
    private RecyclerView mRecyclerView;
    private GenericAdapter mAdapter;
    private com.melnykov.fab.FloatingActionButton addWordFAB, randomWordFAB;
    private CustomSearchToolbar mSearchToolbar;
    private String language;
    private boolean alreadyCreated = false;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf_category);
        language = SharedPreferencesHelper.getString(SharedPreferencesHelper.LEARNING_LANGUAGE_LOCALE, getApplicationContext());
        Inquiry.newInstance(this, SqlHelper.DATABASE).build();
        mData = new GenericData();
        mRecyclerView = (RecyclerView) findViewById(R.id.bookshelf_category_words_recyclerview);
        addWordFAB = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.add_word_fab);
        randomWordFAB = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.random_word_fab);
        randomWordFAB.setVisibility(View.VISIBLE);
        addWordFAB.setVisibility(View.VISIBLE);
        randomWordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRandomWord();
            }
        });
        mSearchToolbar = (CustomSearchToolbar) findViewById(R.id.bookshelf_text_category_search_toolbar);
        alreadyCreated = true;
        setupToolbar();
        setupListeners();
    }

    private void setupListeners(){
        addWordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.InputDialog(BookshelfCategoryActivity.this, getResString(R.string.add_word_text), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input != null && input.length() > 0){
                            addNewWord(input.toString());
                        }

                    }
                }, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel)).show();
            }
        });
    }

    private void addNewWord(String input){
        if(!SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, getApplicationContext())) setChangeToBookshelfCategories();
        Inquiry.get(this).insert(BookshelfCategoryWords.class).values(new BookshelfCategoryWords[]{new BookshelfCategoryWords(categoryID, input, language)}).run();
        mData.addNewCellWithString(GenericData.BOOKSHELF_CATEGORY_WORD,input, mData.Size());
        mAdapter.notifyDataSetChanged();
    }

    private BookshelfCategory[] getSelectedCategory(int id){
        return Inquiry.get(this).select(BookshelfCategory.class).where("id = ?", id).all();
    }

    private void setupToolbar(){
        Intent intent = getIntent();
        if(intent != null) title = intent.getStringExtra("parameter");
        categoryID = intent.getIntExtra("id", -1);
        categoryType = intent.getStringExtra("type");
        mSearchToolbar.setVisibility(View.GONE);
        if(getSelectedCategory(categoryID)[0].textCategory){
            categoryType = "text";
            mSearchToolbar.setVisibility(View.VISIBLE);
            mSearchToolbar.registerSearchListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mData = getTextsCardsData(mSearchToolbar.getRawURL());
                    randomWordFAB.setVisibility(View.GONE);
                    addWordFAB.setVisibility(View.GONE);
                    setupRecyclerView(categoryType);
                }
            });
        }
        setActivityTitle(title);
        if(categoryType == null || !categoryType.equals("text")) {
            ArrayList<String> words = loadCategoryWords();
            mData = new GenericData();
            mData.addStringsToAllCells(GenericData.BOOKSHELF_CATEGORY_WORD, words);
        }
        else{
            mData = getTextsCardsData("");
            randomWordFAB.setVisibility(View.GONE);
            addWordFAB.setVisibility(View.GONE);
        }
        setupRecyclerView(categoryType);
    }

    private void setupRecyclerView(String categoryType){
        if(categoryType != null && categoryType.equals("text")){
            mAdapter = new GenericAdapter(mData, ViewHolderType.HOME_CARD_VIEW_HOLDER, getApplicationContext());
        }
        else mAdapter = new GenericAdapter(mData, ViewHolderType.BOOKSHELF_WORD_VIEW_HOLDER, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, final int position) {
                if(message.equals("edit")){
                    DialogHelper.InputDialog(BookshelfCategoryActivity.this, getResString(R.string.bookshelf_new_category_name), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if(input != null && input.length() > 0){
                                UpdateWord(input.toString(), position);
                            }
                        }
                    }, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), mData.getValue(position).get(GenericData.BOOKSHELF_CATEGORY_WORD).toString()).show();
                }
                else if(message.equals("remove")){
                    RemoveWord(position);
                }
                else if(message.equals("word")){
                    WordContextDialog.launchDialog(BookshelfCategoryActivity.this, mData.getValue(position).get(GenericData.BOOKSHELF_CATEGORY_WORD).toString());
                }
            }

            @Override
            public void onLinkClick(String link) {

            }

            @Override
            public void onClick(View view) {

            }
        });
    }

    private GenericData getTextsCardsData(String filter){
        GenericData data = new GenericData();
        ArrayList<String> descriptions = loadCategoryTexts();
        ArrayList<String> filteredDescription = new ArrayList<>();
        ArrayList<String> cardType = new ArrayList<>();
        ArrayList<String> filters = new ArrayList<>();
        if(filter != null && !filter.isEmpty()){
            for(int i = 0; i < descriptions.size(); i++) if(descriptions.get(i).toLowerCase().contains(filter.toString())) filteredDescription.add(descriptions.get(i));
            descriptions = filteredDescription;
        }
        for(int i = 0; i < descriptions.size(); i++){
            cardType.add(GenericData.BOOKSHELF_TEXT);
            if(filter != null && !filter.isEmpty()) filters.add(filter);
        }
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_CONTENT, descriptions);
        data.addStringsToAllCells(GenericData.TEXT_FILTER, filters);
        data.addStringsToAllCells(GenericData.CUSTOM_CARD_TYPE, cardType);
        return data;
    }

    private ArrayList<String> loadCategoryTexts(){
        ArrayList<String> textsList = new ArrayList<>();
        BookshelfTexts[] texts = Inquiry.get(this)
                .select(BookshelfTexts.class).where("id = ?", categoryID)
                .all();
        if(texts != null)
            for(int i = 0; i < texts.length; i++){
                textsList.add(texts[i].content);
            }
        return textsList;
    }

    private ArrayList<String> loadCategoryWords(){
        ArrayList<String> words = new ArrayList<>();
        BookshelfCategoryWords[] categoriesWords = Inquiry.get(this)
                .select(BookshelfCategoryWords.class).where("id = ? and language = ?", categoryID, language)
                .all();
        if(categoriesWords != null)
            for(int i = 0; i < categoriesWords.length; i++){
                words.add(categoriesWords[i].name);
            }
        return words;
    }

    private void UpdateWord(String input, int position){
        String previousInput = mData.getValue(position).get(GenericData.BOOKSHELF_CATEGORY_WORD).toString();
        Inquiry.get(this).update(BookshelfCategoryWords.class).values(new BookshelfCategoryWords[]{new BookshelfCategoryWords(categoryID, input, language)}).where("id = ? AND name = ?", categoryID, previousInput).run();
        mData.getValue(position).put(GenericData.BOOKSHELF_CATEGORY_WORD, input);
        mAdapter.notifyDataSetChanged();
    }

    private void RemoveWord(int position){
        String deletedWord = mData.getValue(position).get(GenericData.BOOKSHELF_CATEGORY_WORD).toString();
        Inquiry.get(this).delete(BookshelfCategoryWords.class).where("id = ? AND name = ?", categoryID, deletedWord).run();
        mData.removeCell(position);
        setupRecyclerView("");
    }

    private void displayRandomWord(){
        ArrayList<String> words = loadCategoryWords();
        if(words.size() == 0){
            Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_no_words_random), Toast.LENGTH_SHORT).show();
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        WordContextDialog.launchDialog(this, words.get(index));
    }

    @Override
    public void onResume() {
        setupToolbar();
        super.onResume();

        // Creates an instance specifically for MainActivity
        Inquiry.newInstance(this, SqlHelper.DATABASE).build();
    }

    @Override
    public void handleLearningLanguageChange(){
        if(alreadyCreated){
            mData = new GenericData();
            mRecyclerView = (RecyclerView) findViewById(R.id.bookshelf_category_words_recyclerview);
            setupToolbar();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Checking for isFinishing() makes sure the Activity is actually closing.
        // onPause() can also be called when a Dialog opens, such as a permissions dialog.
        if (isFinishing()) {
            // Destroys only MainActivity's instance
            Inquiry.destroy(this);
        }
    }

    private void setChangeToBookshelfCategories(){
        SharedPreferencesHelper.addBoolean(getApplicationContext(), SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, true);
    }
}
