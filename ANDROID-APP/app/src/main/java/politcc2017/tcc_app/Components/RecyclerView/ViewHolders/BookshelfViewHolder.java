package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class BookshelfViewHolder extends GenericViewHolder{
    CustomTextView title;
    public BookshelfViewHolder(View itemView, CellClickListener listener) {
        super(itemView, listener);
        title = (CustomTextView) itemView.findViewById(R.id.category_title);
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.BOOKSHELF_ITEM_CATEGORY))
            title.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
    }
}
