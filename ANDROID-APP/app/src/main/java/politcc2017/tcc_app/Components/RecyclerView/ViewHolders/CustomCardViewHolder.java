package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomCard;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class CustomCardViewHolder extends GenericViewHolder{
    CustomCard card;

    public CustomCardViewHolder(View itemView, CellClickListener listener, Context c) {
        super(itemView, listener);
        card = (CustomCard) itemView.findViewById(R.id.custom_card_cell);
        setListeners(listener);
    }

    public void setListeners(final CellClickListener listener){
        if(listener != null){
            card.setUpvoteIconClickListener(new CellClickListener() {
                @Override
                public void onClick(View v, int position) {

                }

                @Override
                public void onClick(ImageView v, String link) {

                }

                @Override
                public void onClick(String message, int position) {
                    listener.onClick(message, getAdapterPosition());
                }

                @Override
                public void onLinkClick(String link) {

                }

                @Override
                public void onClick(View view) {

                }
            });
            card.setDownvoteIconClickListener(new CellClickListener() {
                @Override
                public void onClick(View v, int position) {

                }

                @Override
                public void onClick(ImageView v, String link) {

                }

                @Override
                public void onClick(String message, int position) {
                    listener.onClick(message, getAdapterPosition());
                }

                @Override
                public void onLinkClick(String link) {

                }

                @Override
                public void onClick(View view) {

                }
            });
            card.setFavoriteIconClickListener(new CellClickListener() {
                @Override
                public void onClick(View v, int position) {

                }

                @Override
                public void onClick(ImageView v, String link) {

                }

                @Override
                public void onClick(String message, int position) {
                    listener.onClick(message, getAdapterPosition());
                }

                @Override
                public void onLinkClick(String link) {

                }

                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if (cellData.containsKey(GenericData.CUSTOM_CARD_TITLE)) {
            card.setTitle(cellData.get(GenericData.CUSTOM_CARD_TITLE).toString());
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_CATEGORIES)) {
            card.setCategory(cellData.get(GenericData.CUSTOM_CARD_CATEGORIES).toString());
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_CONTENT)) {
            card.setContent(cellData.get(GenericData.CUSTOM_CARD_CONTENT).toString());
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_VOTES)) {
            card.setVotes(cellData.get(GenericData.CUSTOM_CARD_VOTES).toString());
        }
    }
}
