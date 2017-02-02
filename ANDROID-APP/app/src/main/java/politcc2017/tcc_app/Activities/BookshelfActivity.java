package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
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
        mAdapter = new GenericAdapter(mData, ViewHolderType.BOOKSHELF_VIEW_HOLDER, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.RegisterClickListener(new CellClickListener() {
            @Override
            public void onClick(View v, final int position) {
                DialogHelper.InputDialog(BookshelfActivity.this, "Digite o nome da categoria", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if(input != null && input.length() > 0){
                            AddNewCategory(input.toString(), position);
                        }

                    }
                }, "OK", "Cancelar").show();
            }

            @Override
            public void onClick(ImageView v, String link) {

            }

            @Override
            public void onClick(String message, final int position) {
                if(message.equals("edit")){
                    DialogHelper.InputDialog(BookshelfActivity.this, "Digite o novo nome da categoria", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if(input != null && input.length() > 0){
                                UpdateCategory(input.toString(), position);
                            }

                        }
                    }, "OK", "Cancelar", mData.getValue(position).get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString()).show();
                }

                else if(message.equals("addWord")){
                    DialogHelper.InputDialog(BookshelfActivity.this, "Digite um novo termo a ser adicionado nessa categoria", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if(input != null && input.length() > 0){
                                AddWordToCategory(input.toString(), position, mData.getValue(position).get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
                            }

                        }
                    }, "OK", "Cancelar", "").show();
                }

                else if(message.equals("remove")){
                    DialogHelper.CustomDialog(BookshelfActivity.this, "", R.drawable.ic_help,"Tem certeza que deseja deletar essa categoria e seu conteúdo?", "OK", "Cancelar", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            RemoveCategory(position);
                        }
                    }, null).show();
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

    private void AddWordToCategory(String input, int position, String category){

    }

    private void AddNewCategory(String input, int position){
        mData.addNewCellWithString(GenericData.BOOKSHELF_ITEM_CATEGORY,input, position+1);
        setupRecyclerView();
        recyclerView.scrollToPosition(position);
    }

    private void UpdateCategory(String input, int position){
        mData.getValue(position).put(GenericData.BOOKSHELF_ITEM_CATEGORY, input);
        mAdapter.notifyDataSetChanged();
    }

    private void RemoveCategory(int position){
        mData.removeCell(position);
        setupRecyclerView();
        if(position > 0) recyclerView.scrollToPosition(position-1);
        else recyclerView.scrollToPosition(position);
    }

    private void loadData(){ //refactor this function getting data from app database / server
        if(mData != null) return;
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
        mData.setSpecialTypeCells(labels, GenericData.CELL_HEADER_TYPE);
    }
}