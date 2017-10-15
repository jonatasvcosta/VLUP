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
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

import politcc2017.tcc_app.Activities.BaseActivity;
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
 * Created by Jonatas on 23/01/2017.
 */

public class BookshelfActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GenericData mData;
    private GenericAdapter mAdapter;
    private String wordToAdd = "", textToAdd;
    private boolean automaticallyAddWordToCategory = false, automaticallyAddTextToCategory = false;
    private FloatingActionButton randomWordFAB;
    private ArrayList<Integer> textType;
    private String language;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf);
        language = SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY, getApplicationContext());
        Inquiry.newInstance(this, SqlHelper.DATABASE).build();
        setActivityTitle(getResString(R.string.bookshelf_activity_title));
        initialBDSetup();
        recyclerView = (RecyclerView) findViewById(R.id.bookshelf_activity_categories_list);

        Intent i = getIntent();
        if(i != null){
            wordToAdd = i.getStringExtra(WordContextDialog.CONTEXT_ADD_WORD);
            textToAdd = i.getStringExtra(WordContextDialog.CONTEXT_ADD_TEXT);
            if((wordToAdd == null || wordToAdd.isEmpty()) && (textToAdd == null  || textToAdd.isEmpty())) setupRecyclerView("default");
            else if(wordToAdd != null && !wordToAdd.isEmpty()){
                setupRecyclerView(WordContextDialog.CONTEXT_ADD_WORD);
                automaticallyAddWordToCategory = true;
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_ADD_WORD_BOOKSHELF));
                Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_add_word_instructions), Toast.LENGTH_SHORT).show();
            }
            else if(textToAdd != null && !textToAdd.isEmpty()){
                setupRecyclerView(WordContextDialog.CONTEXT_ADD_TEXT);
                automaticallyAddTextToCategory = true;
                scorePoints("+"+getScoringPoints(SqlHelper.RULE_ADD_TEXT_BOOKSHELF));
                Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_add_text_instructions), Toast.LENGTH_SHORT).show();
            }
        }
        else setupRecyclerView("default");
    }

    private void setupCategories(){
        String [] bookshelfCategories = getResources().getStringArray(R.array.bookshelf_categories);
        Inquiry.get(this).insert(BookshelfCategory.class).values(new BookshelfCategory[]{
                new BookshelfCategory(0 ,bookshelfCategories[0], true, false, language),
                new BookshelfCategory(1 , bookshelfCategories[1], language),
                new BookshelfCategory(2 , bookshelfCategories[2], language),
                new BookshelfCategory(3 , bookshelfCategories[3], language),
                new BookshelfCategory(4 , bookshelfCategories[4], language),
                new BookshelfCategory(5 , bookshelfCategories[5], language),
                new BookshelfCategory(6 , bookshelfCategories[6], true, true, language),
                new BookshelfCategory(7 , bookshelfCategories[7], false, true, language),
                new BookshelfCategory(8 , bookshelfCategories[8], false, true, language),
                new BookshelfCategory(9 , bookshelfCategories[9], false, true, language),
                new BookshelfCategory(10 , bookshelfCategories[10], false, true, language),
                new BookshelfCategory(11 , bookshelfCategories[11], false, true, language),
                new BookshelfCategory(12, bookshelfCategories[12], true, true, language),
                new BookshelfCategory(13, bookshelfCategories[13], false, true, language),
                new BookshelfCategory(14, bookshelfCategories[14], false, true, language),
                new BookshelfCategory(15, bookshelfCategories[15], false, true, language),
        }).run();
    }

    private void initialBDSetup(){
        BookshelfCategory[] categories = Inquiry.get(this).select(BookshelfCategory.class).where("language = ?", language).all();
        String appLocale = SharedPreferencesHelper.getString(SharedPreferencesHelper.LOCALE_KEY, getApplicationContext());
        String bdLocale = SharedPreferencesHelper.getString(SharedPreferencesHelper.BOOKSHELF_BD_LOCALE_KEY, getApplicationContext());
        if((categories != null && appLocale.equals(bdLocale)) || SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, getApplicationContext())) return; //if DB has changed or is properly set return
        SharedPreferencesHelper.addString(getApplicationContext(), SharedPreferencesHelper.BOOKSHELF_BD_LOCALE_KEY, appLocale);
        setupCategories();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Creates an instance specifically for MainActivity
        Inquiry.newInstance(this, SqlHelper.DATABASE).build();
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

    public void loadData(String dataType){ //refactor this function getting data from app database / server
        int textsLimiter = 0;
        BookshelfCategory[] categories = getCategories();

        mData = new GenericData();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> labels = new ArrayList<>();
        textType = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < categories.length ; i++){
            if(dataType.equals(WordContextDialog.CONTEXT_ADD_WORD) && !categories[i].textCategory){
                words.add(categories[i].name);
                if(categories[i].header) labels.add(count);
                count++;
            }
            else if(dataType.equals(WordContextDialog.CONTEXT_ADD_TEXT) && categories[i].textCategory){
                words.add(categories[i].name);
                textType.add(count);
                if(categories[i].header) labels.add(count);
                count++;
            }
            else if(!dataType.equals(WordContextDialog.CONTEXT_ADD_TEXT) && !dataType.equals(WordContextDialog.CONTEXT_ADD_WORD)){
                words.add(categories[i].name);
                if(categories[i].header) labels.add(i);
            }
        }

        mData.addStringsToAllCells(GenericData.BOOKSHELF_ITEM_CATEGORY, words);
        mData.setSpecialTypeCells(labels, GenericData.CELL_HEADER_TYPE);
    }

    private BookshelfCategory[] getCategories(){
        BookshelfCategory[] categories =  Inquiry.get(this).select(BookshelfCategory.class).
                where("language = ?", language).all();
        if(categories == null){
            setupCategories();
            categories =  Inquiry.get(this).select(BookshelfCategory.class).
                    where("language = ?", language).all();
        }
        return categories;
    }

    private void setupRecyclerView(String dataType){
        loadData(dataType);
        mAdapter = new GenericAdapter(mData, ViewHolderType.BOOKSHELF_VIEW_HOLDER, getApplicationContext());
        randomWordFAB = (FloatingActionButton) findViewById(R.id.fab_random_word_all);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        randomWordFAB.attachToRecyclerView(recyclerView);
        if(dataType.equals(WordContextDialog.CONTEXT_ADD_TEXT) || dataType.equals(WordContextDialog.CONTEXT_ADD_WORD)) randomWordFAB.setVisibility(View.GONE);
        randomWordFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayRandomWord();
            }
        });
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, final int position) {
                DialogHelper.InputDialog(BookshelfActivity.this, getResString(R.string.bookshelf_category_name), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input != null && input.length() > 0){
                            AddNewCategory(input.toString(), position);
                        }

                    }
                }, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel)).show();
            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, final int position) {
                if(message.equals("edit")){
                    DialogHelper.InputDialog(BookshelfActivity.this, getResString(R.string.bookshelf_new_category_name), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if(input != null && input.length() > 0){
                                UpdateCategory(input.toString(), position);
                            }
                        }
                    }, getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), mData.getValue(position).get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString()).show();
                }
                else if(message.equals("remove")){
                    DialogHelper.CustomDialog(BookshelfActivity.this, "", R.drawable.ic_help, getResString(R.string.bookshelf_category_delete_confirmation), getResString(R.string.dialog_confirm), getResString(R.string.dialog_cancel), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            RemoveCategory(position);
                        }
                    }, null).show();
                }
                else{
                    if(automaticallyAddWordToCategory){
                        automaticallyAddWordToCategory = false;
                        Inquiry.newInstance(getApplicationContext(), SqlHelper.DATABASE).build();
                        Inquiry.get(getApplicationContext()).insert(BookshelfCategoryWords.class).values(new BookshelfCategoryWords[]{new BookshelfCategoryWords(position, wordToAdd, language)}).run();
                        Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_word_added), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    if(automaticallyAddTextToCategory){
                        automaticallyAddTextToCategory = false;
                        Inquiry.newInstance(getApplicationContext(), SqlHelper.DATABASE).build();
                        BookshelfCategory[] categories = getCategories();
                        int offset;
                        for(offset = 1; offset < categories.length && !categories[offset].header; offset++);
                        Inquiry.get(getApplicationContext()).insert(BookshelfTexts.class).values(new BookshelfTexts[]{new BookshelfTexts(offset+position, textToAdd, language)}).run();
                        Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_text_added), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    String type = "";
                    if(textType.contains(position)) type = "text";
                    else startOrResumeActivity(BookshelfCategoryActivity.class, message, position, type);
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

    private void AddNewCategory(String input, int position){
        if(!SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, getApplicationContext())) setChangeToBookshelfCategories();
        UpdateCellsPosition(position+1, 1);
        Inquiry.get(this)
                .insert(BookshelfCategory.class)
                .values(new BookshelfCategory[]{new BookshelfCategory(position+1, input, language)})
                .run();
        mData.addNewCellWithString(GenericData.BOOKSHELF_ITEM_CATEGORY,input, position+1);
        setupRecyclerView("default");
        recyclerView.scrollToPosition(position);
    }

    private void UpdateCellsPosition(int position, int offset){
        BookshelfCategory[] categories = Inquiry.get(this).select(BookshelfCategory.class).where("id >= ?", position).all();
        BookshelfCategoryWords[] words = Inquiry.get(this).select(BookshelfCategoryWords.class).where("id >= ?", position).all();
        if(words != null){
            for(int i = 0; i < words.length; i++) words[i].id += offset;
            Inquiry.get(this)
                    .delete(BookshelfCategoryWords.class)
                    .where("id >= ?", position)
                    .run();
            Inquiry.get(this)
                    .insert(BookshelfCategoryWords.class)
                    .values(words)
                    .run();
        }
        if(categories != null) for(int i = 0; i < categories.length; i++) categories[i].id += offset;
        else return;

        Inquiry.get(this)
                .delete(BookshelfCategory.class)
                .where("id >= ?", position)
                .run();
        Inquiry.get(this)
                .insert(BookshelfCategory.class)
                .values(categories)
                .run();
    }

    private void UpdateCategory(String input, int position){
        if(!SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, getApplicationContext())) setChangeToBookshelfCategories();
        Inquiry.get(this).update(BookshelfCategory.class).values(new BookshelfCategory[]{new BookshelfCategory(position, input, language)}).where("id = ?", position).run();
        mData.getValue(position).put(GenericData.BOOKSHELF_ITEM_CATEGORY, input);
        mAdapter.notifyDataSetChanged();
    }

    private void CleanCategories(){
        Inquiry.get(this)
                .delete(BookshelfCategory.class)
                .where("id >= ?", 0)
                .run();
    }

    private void RemoveCategory(int position){
        if(!SharedPreferencesHelper.getBoolean(SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, getApplicationContext())) setChangeToBookshelfCategories();
        Inquiry.get(this).delete(BookshelfCategory.class).where("id = ?", position).run();
        Inquiry.get(this).delete(BookshelfCategoryWords.class).where("id = ?", position).run();
        UpdateCellsPosition(position, -1);
        mData.removeCell(position);
        setupRecyclerView("default");
        if(position > 0) recyclerView.scrollToPosition(position-1);
        else recyclerView.scrollToPosition(position);
    }

    private void displayRandomWord(){
        ArrayList<String> words = loadAllCategoryWords();
        if(words.size() == 0){
            Toast.makeText(getApplicationContext(), getResString(R.string.bookshelf_no_words_random), Toast.LENGTH_SHORT).show();
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        WordContextDialog.launchDialog(this, words.get(index));
    }

    private ArrayList<String> loadAllCategoryWords(){
        ArrayList<String> words = new ArrayList<>();
        BookshelfCategoryWords[] categoriesWords = Inquiry.get(this)
                .select(BookshelfCategoryWords.class)
                .all();
        if(categoriesWords != null)
            for(int i = 0; i < categoriesWords.length; i++){
                words.add(categoriesWords[i].name);
            }
        return words;
    }

    private void setChangeToBookshelfCategories(){
        SharedPreferencesHelper.addBoolean(getApplicationContext(), SharedPreferencesHelper.BOOKSHELF_BD_CHANGED_KEY, true);
    }
}
