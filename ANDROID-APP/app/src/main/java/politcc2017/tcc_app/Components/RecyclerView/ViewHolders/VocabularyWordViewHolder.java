package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
    RelativeLayout wordContainer;
    public VocabularyWordViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, null);
        word = (CustomTextView) itemView.findViewById(R.id.vocabulary_word);
        count = (CustomTextView) itemView.findViewById(R.id.vocabulary_count);
        wordContainer = (RelativeLayout) itemView.findViewById(R.id.vocabulary_word_container);

        wordContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(word.getText().toString(), getAdapterPosition());
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.VOCABULARY_WORD))
            word.setText(cellData.get(GenericData.VOCABULARY_WORD).toString());
        if(cellData.containsKey(GenericData.VOCABULARY_COUNT))
            count.setText(cellData.get(GenericData.VOCABULARY_COUNT).toString());
    }
}