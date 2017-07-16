package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 08/02/2017.
 */

public class RankingViewHolder extends GenericViewHolder{
    CustomTextView userPosition, userName, userScore;
    ImageView medal;

    public RankingViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, listener);
        userPosition = (CustomTextView) itemView.findViewById(R.id.user_position);
        userName = (CustomTextView) itemView.findViewById(R.id.user_name);
        userScore = (CustomTextView) itemView.findViewById(R.id.user_score);
        medal = (ImageView) itemView.findViewById(R.id.user_medal_icon);
    }


    @Override
    public void setViewContent(Hashtable cellData) {
        userPosition.setText(Integer.toString(getAdapterPosition()+1));
        if(cellData.containsKey(GenericData.USER_NAME))
            userName.setText(cellData.get(GenericData.USER_NAME).toString());
        if(cellData.containsKey(GenericData.USER_SCORE))
            userScore.setText(cellData.get(GenericData.USER_SCORE).toString());
        medal.setVisibility(View.GONE);
        if(cellData.containsKey(GenericData.CELL_TYPE))
            if(cellData.get(GenericData.CELL_TYPE).toString().toLowerCase().equals("medal")) medal.setVisibility(View.VISIBLE);

    }
}