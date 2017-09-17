package politcc2017.tcc_app.Components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 22/04/2017.
 */

public class CustomCard extends LinearLayout {
    private Context mContext;
    private CardView cardLayout;
    private CustomTextView votes, title, categories;
    private CustomHTMLEditText content;
    private ImageView upvote, downvote, edit, favorite;
    private LinearLayout bottomContainer;

    public CustomCard(Context context) {
        super(context);
        ComponentSetup(context, null);
    }

    public CustomCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        ComponentSetup(context, attrs);
    }

    public CustomCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ComponentSetup(context, attrs);
    }

    protected void ComponentSetup(Context c, AttributeSet attrs){
        mContext = c;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_card_component, this, true);
        upvote = (ImageView) findViewById(R.id.custom_card_upvote_icon);
        downvote = (ImageView) findViewById(R.id.custom_card_downvote_icon);
        edit = (ImageView) findViewById(R.id.custom_card_edit_icon);
        favorite = (ImageView) findViewById(R.id.custom_card_favorite_icon);
        votes = (CustomTextView) findViewById(R.id.custom_card_votes);
        title = (CustomTextView) findViewById(R.id.custom_card_title);
        categories = (CustomTextView) findViewById(R.id.custom_card_categories);
        content = (CustomHTMLEditText) findViewById(R.id.custom_card_class_content);
        cardLayout = (CardView) findViewById(R.id.custom_card_card_layout);
        bottomContainer = (LinearLayout) findViewById(R.id.custom_card_bottom_icons_container);
        edit.setVisibility(View.GONE);
        categories.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
    }

    public void setTitle(String title){
        this.title.setVisibility(VISIBLE);
        this.title.setText(title);
    }

    public void setUnlimitedLines(){
        content.setMaxLines(Integer.MAX_VALUE);
    }

    public void setMaxLines(int maxLines){
        content.setMaxLines(maxLines);
    }
    public void setContent(String content){
        this.content.fromHtml(content);
    }

    public void setMovieUrl(final String url){
        LinearLayout movieContainer = (LinearLayout) findViewById(R.id.card_class_movie_container);
        final YouTubePlayerView moviePlayer = (YouTubePlayerView) findViewById(R.id.card_class_movie_view);

        movieContainer.setVisibility(View.VISIBLE);
        moviePlayer.initialize(new AbstractYouTubeListener() {
            @Override
            public void onReady() {
                moviePlayer.loadVideo(url, 0);
                moviePlayer.pauseVideo();
            }
        }, true);
    }

    public void setRadius(float newRadius){
        cardLayout.setRadius(newRadius);
    }

    public void setCardColor(int color){
        cardLayout.setCardBackgroundColor(color);
    }

    public void setVotes(String votes){
        this.votes.setText(votes);
    }

    public void setCategory(String categories){
        this.categories.setVisibility(VISIBLE);
        this.categories.setText(categories);
    }

    public void filterContent(String textFilter){
        int start = this.content.toHtml().indexOf(textFilter);
        int end = start+textFilter.length();
        String content = this.content.toHtml();
        String str = content.substring(0, start)+"<b><u>"+content.substring(start, end)+"</u></b>"+content.substring(end, content.length());
        this.content.fromHtml(str);
    }

    public void hideFavoriteIcon(){
        this.favorite.setVisibility(GONE);
    }

    public void setContentMarkable(){
        content.allowWordContextMenu();
    }

    public void setEditIconClickListener(final CellClickListener listener){
        edit.setVisibility(VISIBLE);
        edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("edit", 0);
            }
        });
    }

    public void setFavoriteIconClickListener(final CellClickListener listener){
        favorite.setVisibility(VISIBLE);
        favorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("favorite", 0);
            }
        });
    }

    public void setUpvoteIconClickListener(final CellClickListener listener){
        upvote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("upvote", 0);
            }
        });
    }

    public void setDownvoteIconClickListener(final CellClickListener listener){
        downvote.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("downvote", 0);
            }
        });
    }

    public void hideBottomContainer(){
        bottomContainer.setVisibility(GONE);
    }

    public void setTopRightIcon(int res){
        favorite.setImageResource(res);
    }

    public void setCardClickListener(final CellClickListener listener){
        content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("cardlayout", 0);
            }
        });
        cardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("cardlayout", 0);
            }
        });
    }

}
