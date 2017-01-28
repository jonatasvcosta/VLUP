package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class BookshelfViewHolder extends GenericViewHolder{
    CustomTextView title, title2;
    ImageView editBtn, removeBtn;
    FoldingCell foldingCell;
    CellClickListener listener;
    public BookshelfViewHolder(View itemView, CellClickListener listener) {
        super(itemView, listener);
        this.listener = listener;
        title = (CustomTextView) itemView.findViewById(R.id.category_title);
        title2 = (CustomTextView) itemView.findViewById(R.id.category_title_unfolded);
        editBtn = (ImageView) itemView.findViewById(R.id.bookshelf_category_edit_btn);
        removeBtn = (ImageView) itemView.findViewById(R.id.bookshelf_category_remove_btn);
        foldingCell = (FoldingCell) itemView.findViewById(R.id.bookshelf_category_folding_cell);
        setListeners();
    }

    public void setListeners(){
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldingCell.toggle(false);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("edit", getAdapterPosition());
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("remove", getAdapterPosition());
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.BOOKSHELF_ITEM_CATEGORY))
            title.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
            title2.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
    }
}
