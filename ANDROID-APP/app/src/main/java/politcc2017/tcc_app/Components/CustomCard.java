package politcc2017.tcc_app.Components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.github.mthli.knife.KnifeText;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 22/04/2017.
 */

public class CustomCard extends LinearLayout {
    private Context mContext;
    private CardView cardLayout;
    private CustomTextView votes, title, categories;
    private CustomHTMLTextView content;
    private ImageView upvote, downvote, edit, favorite;
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
        content = (CustomHTMLTextView) findViewById(R.id.custom_card_class_content);
        cardLayout = (CardView) findViewById(R.id.custom_card_card_layout);
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


    public void setContent(String content){
        this.content.setHtml(content);
    }

    public void setVotes(String votes){
        this.votes.setText(votes);
    }

    public void setCategory(String categories){
        this.categories.setVisibility(VISIBLE);
        this.categories.setText(categories);
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

    public void setCardClickListener(final CellClickListener listener){
        cardLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("cardlayout", 0);
            }
        });
    }

}
