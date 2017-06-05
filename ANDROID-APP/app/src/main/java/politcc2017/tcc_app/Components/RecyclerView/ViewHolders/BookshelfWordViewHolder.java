package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class BookshelfWordViewHolder extends GenericViewHolder{
    CustomTextView word;
    ImageView edit, remove;
    RelativeLayout wordContainer;

    public BookshelfWordViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, listener);
        word = (CustomTextView) itemView.findViewById(R.id.bookshelf_word);
        edit = (ImageView) itemView.findViewById(R.id.bookshelf_word_edit);
        remove = (ImageView) itemView.findViewById(R.id.bookshelf_word_remove);
        wordContainer = (RelativeLayout) itemView.findViewById(R.id.bookshelf_word_container);

        setListeners();
    }

    private void setListeners(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener != null) mClickListener.onClick("edit", getAdapterPosition());
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener != null) mClickListener.onClick("remove", getAdapterPosition());
            }
        });
        wordContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mClickListener != null) mClickListener.onClick("word", getAdapterPosition());
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.BOOKSHELF_CATEGORY_WORD))
            word.setText(cellData.get(GenericData.BOOKSHELF_CATEGORY_WORD).toString());
    }
}