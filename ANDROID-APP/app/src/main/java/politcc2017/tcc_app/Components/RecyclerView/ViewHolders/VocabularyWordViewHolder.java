package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class VocabularyWordViewHolder extends GenericViewHolder{
    CustomTextView word, count;

    public VocabularyWordViewHolder(View itemView, final View.OnClickListener listener) {
        super(itemView, listener);
        word = (CustomTextView) itemView.findViewById(R.id.vocabulary_word);
        count = (CustomTextView) itemView.findViewById(R.id.vocabulary_count);
        if(listener != null){
            
        }
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.VOCABULARY_WORD))
            word.setText(cellData.get(GenericData.VOCABULARY_WORD).toString());
        if(cellData.containsKey(GenericData.VOCABULARY_COUNT))
            count.setText(cellData.get(GenericData.VOCABULARY_COUNT).toString());
    }
}