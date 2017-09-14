package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoOptions;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomCard;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class HomeCardViewHolder extends GenericViewHolder{
    private CustomCard card;
    private CardView adsCard;
    private Context mContext;
    private static int HOME_NEWS_CARD_MAX_LINES = 20;
    private NativeExpressAdView adView;

    public HomeCardViewHolder(View itemView, CellClickListener listener, Context c) {
        super(itemView, listener);
        mContext = c;
        card = (CustomCard) itemView.findViewById(R.id.custom_card_cell);
        adsCard = (CardView) itemView.findViewById(R.id.ads_card_cell);
        adView = (NativeExpressAdView) itemView.findViewById(R.id.ads_card_adview);
        card.setVisibility(View.VISIBLE);
        adsCard.setVisibility(View.GONE);
        card.hideFavoriteIcon();
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
            card.setCardClickListener(new CellClickListener() {
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
        String content = null;

        if(cellData.containsKey(GenericData.CUSTOM_CARD_TYPE)){
            String type = cellData.get(GenericData.CUSTOM_CARD_TYPE).toString();
            if (type.equals(GenericData.NEWS)){
                card.setMaxLines(HOME_NEWS_CARD_MAX_LINES);
            }
            else if(type.equals(GenericData.ADS_IMAGE) || type.equals(GenericData.ADS_VIDEO)){
                adsCard.setVisibility(View.VISIBLE);
                card.setVisibility(View.GONE);
                if(type.equals(GenericData.ADS_VIDEO)) {
                    adView.setVideoOptions(new VideoOptions.Builder()
                            .setStartMuted(true)
                            .build());
                }
                AdRequest request = new AdRequest.Builder()
                        .addTestDevice("379438B680AF5ECCD27F54638EE00DB8").build();
                adView.loadAd(request);
            }
            else if(type.equals(GenericData.LINK)){
                card.setRadius(mContext.getResources().getDimension(R.dimen.margin_extra_large));
                card.setCardColor(mContext.getApplicationContext().getResources().getColor(R.color.card_blue));
            }
            else if(type.equals(GenericData.MINI_CLASS)){
                card.setCardColor(mContext.getApplicationContext().getResources().getColor(R.color.card_beige));
            }
            else if(type.equals(GenericData.TRENDING_WORDS)){
                card.setRadius(mContext.getResources().getDimension(R.dimen.margin_medium));
                card.setCardColor(mContext.getApplicationContext().getResources().getColor(R.color.card_gray));
            }
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_TITLE)) {
            card.setTitle(cellData.get(GenericData.CUSTOM_CARD_TITLE).toString());
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_CATEGORIES)) {
            card.setCategory(cellData.get(GenericData.CUSTOM_CARD_CATEGORIES).toString());
        }
        if (cellData.containsKey(GenericData.CUSTOM_CARD_CONTENT)) {
            content = cellData.get(GenericData.CUSTOM_CARD_CONTENT).toString();
            card.setContent(content);
        }
        card.setVotes("");
        if (cellData.containsKey(GenericData.CUSTOM_CARD_VOTES)) {
            card.setVotes(cellData.get(GenericData.CUSTOM_CARD_VOTES).toString());
        }
        if(cellData.containsKey(GenericData.CUSTOM_CARD_URL)){
            if(content == null || content.length() == 0)card.setMovieUrl(cellData.get(GenericData.CUSTOM_CARD_URL).toString());
        }
    }
}
