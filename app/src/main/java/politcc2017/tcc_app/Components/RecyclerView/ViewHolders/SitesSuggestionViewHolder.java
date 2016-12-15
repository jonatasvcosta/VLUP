package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class SitesSuggestionViewHolder extends GenericViewHolder{
    FoldingCell foldingCell;

    public SitesSuggestionViewHolder(View itemView, CellClickListener listener) {
        super(itemView, listener);
        foldingCell = (FoldingCell) itemView.findViewById(R.id.folding_cell_sites_suggestion);
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldingCell.toggle(false);
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {

    }
}
