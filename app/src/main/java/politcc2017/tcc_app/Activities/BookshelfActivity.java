package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.RecyclerView.Adapters.GenericAdapter;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 23/01/2017.
 */

public class BookshelfActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private GenericData mData;
    private GenericAdapter mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_bookshelf);
        setActivityTitle("Bookshelf");
        recyclerView = (RecyclerView) findViewById(R.id.bookshelf_activity_categories_list);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        loadData();
        mAdapter = new GenericAdapter(mData, ViewHolderType.BOOKSHELF_VIEW_HOLDER);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadData(){ //refactor this function getting data from app database / server
        mData = new GenericData();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> labels = new ArrayList<>();
        words.add("Categorias de palavras"); //a primeira palavra do array será a label
        words.add("Verbos");
        words.add("Adjetivos");
        words.add("Substantivos");
        words.add("Objetos");
        words.add("Outros");
        words.add("Categorias de textos");
        words.add("Turismo");
        words.add("Literatura");
        words.add("Economia");
        words.add("Tecnologia");
        words.add("Outros");
        words.add("Expressões idiomáticas"); //a primeira palavra do array será a label
        words.add("Viagens");
        words.add("Dia-a-dia");
        words.add("Outros");
        labels.add(0);
        labels.add(6);
        labels.add(12);
        mData.addStringsToAllCells(GenericData.BOOKSHELF_ITEM_CATEGORY, words);
        mData.typeData = labels;
    }
}