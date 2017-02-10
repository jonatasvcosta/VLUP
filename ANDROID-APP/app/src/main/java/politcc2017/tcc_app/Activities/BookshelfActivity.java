package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.inquiry.Inquiry;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.BookshelfCategory;
import politcc2017.tcc_app.Components.Helpers.SQLiteHelper.SqlHelper;
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
        Inquiry.newInstance(this, SqlHelper.DATABASE).build();
        setActivityTitle("Bookshelf");
        initialBDSetup();
        recyclerView = (RecyclerView) findViewById(R.id.bookshelf_activity_categories_list);
        setupRecyclerView();
    }

    private void initialBDSetup(){
        BookshelfCategory[] categories = Inquiry.get(this).select(BookshelfCategory.class).all();
        if(categories != null) return;
        Inquiry.get(this).insert(BookshelfCategory.class).values(new BookshelfCategory[]{
                new BookshelfCategory(0 ,"Categorias de palavras", true),
                new BookshelfCategory(1 , "Verbos"),
                new BookshelfCategory(2 , "Adjetivos"),
                new BookshelfCategory(3 , "Substantivos"),
                new BookshelfCategory(4 , "Objetos"),
                new BookshelfCategory(5 , "Outros"),
                new BookshelfCategory(6 , "Categorias de textos", true),
                new BookshelfCategory(7 , "Turismo"),
                new BookshelfCategory(8 , "Literatura"),
                new BookshelfCategory(9 , "Economia"),
                new BookshelfCategory(10 , "Tecnologia"),
                new BookshelfCategory(11 , "Outros"),
                new BookshelfCategory(12, "Expressões idiomáticas", true),
                new BookshelfCategory(13, "Viagens"),
                new BookshelfCategory(14, "Dia-a-dia"),
                new BookshelfCategory(15, "Outros"),
        }).run();
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
                else if(message.equals("remove")){
                    DialogHelper.CustomDialog(BookshelfActivity.this, "", R.drawable.ic_help,"Tem certeza que deseja deletar essa categoria e seu conteúdo?", "OK", "Cancelar", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            RemoveCategory(position);
                        }
                    }, null).show();
                }
                else startOrResumeActivity(BookshelfCategoryActivity.class, message);
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
        UpdateCellsPosition(position+1, 1);
        Inquiry.get(this)
                .insert(BookshelfCategory.class)
                .values(new BookshelfCategory[]{new BookshelfCategory(position+1, input)})
                .run();
        mData.addNewCellWithString(GenericData.BOOKSHELF_ITEM_CATEGORY,input, position+1);
        setupRecyclerView();
        recyclerView.scrollToPosition(position);
    }

    private void UpdateCellsPosition(int position, int offset){
        BookshelfCategory[] categories = Inquiry.get(this).select(BookshelfCategory.class).where("id >= ?", position).all();
        if(categories != null) for(int i = 0; i < categories.length; i++) categories[i].id = categories[i].id+offset;
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
        Inquiry.get(this).update(BookshelfCategory.class).values(new BookshelfCategory[]{new BookshelfCategory(position, input)}).where("id = ?", position).run();
        mData.getValue(position).put(GenericData.BOOKSHELF_ITEM_CATEGORY, input);
        mAdapter.notifyDataSetChanged();
    }

    private void RemoveCategory(int position){
        UpdateCellsPosition(position, -1);
        mData.removeCell(position);
        setupRecyclerView();
        if(position > 0) recyclerView.scrollToPosition(position-1);
        else recyclerView.scrollToPosition(position);
    }

    private void loadData(){ //refactor this function getting data from app database / server
        BookshelfCategory[] categories = Inquiry.get(this)
                .select(BookshelfCategory.class)
                .all();
        if(mData != null) return;
        mData = new GenericData();
        ArrayList<String> words = new ArrayList<>();
        ArrayList<Integer> labels = new ArrayList<>();
        if(categories != null)
            for(int i = 0; i < categories.length; i++){
                words.add(categories[i].name);
                if(categories[i].header) labels.add(i);
            }
        mData.addStringsToAllCells(GenericData.BOOKSHELF_ITEM_CATEGORY, words);
        mData.setSpecialTypeCells(labels, GenericData.CELL_HEADER_TYPE);
    }
}