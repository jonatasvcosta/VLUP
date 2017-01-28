package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class LabelViewHolder extends GenericViewHolder{
    CustomTextView label;
    ImageView addCategoryButton;
    public LabelViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, listener);
        label = (CustomTextView) itemView.findViewById(R.id.bookshelf_label);
        addCategoryButton = (ImageView) itemView.findViewById(R.id.btn_add_new_category);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(addCategoryButton, getAdapterPosition());
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.BOOKSHELF_ITEM_CATEGORY))
            label.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
    }
}
