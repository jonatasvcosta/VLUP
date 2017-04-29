package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomHTMLTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class DictionaryViewHolder extends GenericViewHolder{
    CustomHTMLTextView textView;
    LinearLayout container;
    public DictionaryViewHolder(View itemView, final CellClickListener listener, Context c) {
        super(itemView, listener);
        textView = (CustomHTMLTextView) itemView.findViewById(R.id.dictionary_cell_text);
        textView.allowWordContextMenu();
        container = (LinearLayout) itemView.findViewById(R.id.dictionary_cell_container);
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.DICTIONARY_CELL_CONTENT))
            textView.setHtml(cellData.get(GenericData.DICTIONARY_CELL_CONTENT).toString());
        if(getAdapterPosition() == 0)
                container.setBackgroundColor(Color.parseColor("#00BCD4"));
    }
}
