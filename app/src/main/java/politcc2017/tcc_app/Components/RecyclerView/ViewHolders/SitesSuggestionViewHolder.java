package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomButton;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 14/12/2016.
 */

public class SitesSuggestionViewHolder extends GenericViewHolder{
    private FoldingCell foldingCell;
    private CustomButton link, link2;
    private CustomTextView title, description, title2;
    private ImageView image;
    private String imageLink;
    private final CellClickListener listener;

    public SitesSuggestionViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, listener);
        this.listener = listener;
        foldingCell = (FoldingCell) itemView.findViewById(R.id.folding_cell_sites_suggestion);
        link = (CustomButton) itemView.findViewById(R.id.suggestion_link);
        link2 = (CustomButton) itemView.findViewById(R.id.suggestion_link_unfolded);
        title = (CustomTextView) itemView.findViewById(R.id.suggestion_title);
        title2 = (CustomTextView) itemView.findViewById(R.id.suggestion_title_unfolded);
        description = (CustomTextView) itemView.findViewById(R.id.suggestion_description);
        image = (ImageView) itemView.findViewById(R.id.suggestion_image);
        setListeners();
    }

    private void setListeners(){
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onLinkClick(link.getText().toString());
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onLinkClick(link.getText().toString());
            }
        });

        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(image, imageLink); //pass the image as a parameter so that it can be downloaded and replaced
                foldingCell.toggle(false);
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.SUGGESTION_ITEM_LINK)){
            link.setText(cellData.get(GenericData.SUGGESTION_ITEM_LINK).toString());
            link2.setText(cellData.get(GenericData.SUGGESTION_ITEM_LINK).toString());
        }
        if(cellData.containsKey(GenericData.SUGGESTION_ITEM_TITLE)){
            title.setText(cellData.get(GenericData.SUGGESTION_ITEM_TITLE).toString());
            title2.setText(cellData.get(GenericData.SUGGESTION_ITEM_TITLE).toString());
        }
        if(cellData.containsKey(GenericData.SUGGESTION_ITEM_DESCRIPTION)) description.setText(cellData.get(GenericData.SUGGESTION_ITEM_DESCRIPTION).toString());
        if(cellData.containsKey(GenericData.SUGGESTION_ITEM_IMAGE)) imageLink = cellData.get(GenericData.SUGGESTION_ITEM_IMAGE).toString();
    }
}
