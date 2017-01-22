package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class SitesSuggestionViewHolder extends GenericViewHolder{
    private FoldingCell foldingCell;
    private CustomButton link;

    public SitesSuggestionViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, listener);
        foldingCell = (FoldingCell) itemView.findViewById(R.id.folding_cell_sites_suggestion);
        link = (CustomButton) itemView.findViewById(R.id.suggestion_link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onLinkClick(link.getText().toString());
            }
        });

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
