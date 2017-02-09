package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class BookshelfCategoryActivity extends BaseActivity {
    private String title = "";
    private GenericData mData;
    private RecyclerView mRecyclerView;
    private GenericAdapter mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf_category);
        mRecyclerView = (RecyclerView) findViewById(R.id.bookshelf_category_words_recyclerview);
        setupToolbar();
    }

    private void setupToolbar(){
        Intent intent = getIntent();
        if(intent != null) title = intent.getStringExtra("parameter");
        setActivityTitle(title);
        loadData();
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        mAdapter = new GenericAdapter(mData, ViewHolderType.BOOKSHELF_WORD_VIEW_HOLDER, getApplicationContext());
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
                    DialogHelper.InputDialog(BookshelfCategoryActivity.this, "Digite o novo nome da categoria", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if(input != null && input.length() > 0){
                                UpdateWord(input.toString(), position);
                            }
                        }
                    }, "OK", "Cancelar", mData.getValue(position).get(GenericData.BOOKSHELF_CATEGORY_WORD).toString()).show();
                }
                else if(message.equals("remove")){
                    RemoveWord(position);
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

    private void UpdateWord(String input, int position){
        mData.getValue(position).put(GenericData.BOOKSHELF_CATEGORY_WORD, input);
        mAdapter.notifyDataSetChanged();
    }

    private void RemoveWord(int position){
        mData.removeCell(position);
        setupRecyclerView();
    }

    private void loadData(){
        mData = new GenericData();
        ArrayList<String> words = new ArrayList<>();
        words.add("Schneiden");
        words.add("Schreien");
        words.add("Scheinen");
        words.add("Nachdenken");
        words.add("Füttern");
        words.add("Hüpfen");
        words.add("Schlürfen");
        words.add("Putzen");
        mData.addStringsToAllCells(GenericData.BOOKSHELF_CATEGORY_WORD, words);
    }

    protected void onResume() {
        setupToolbar();
        super.onResume();
    }
}
