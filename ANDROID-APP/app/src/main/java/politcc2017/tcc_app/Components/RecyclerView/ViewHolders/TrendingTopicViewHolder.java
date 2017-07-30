package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
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

public class TrendingTopicViewHolder extends GenericViewHolder{
    CustomTextView topic;
    LinearLayout topicContainer;
    public TrendingTopicViewHolder(View itemView, final CellClickListener listener) {
        super(itemView, null);
        topic = (CustomTextView) itemView.findViewById(R.id.trending_topic_text);
        topicContainer = (LinearLayout) itemView.findViewById(R.id.trending_topic_container);

        topicContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(topic.getText().toString(), getAdapterPosition());
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if(cellData.containsKey(GenericData.TRENDING_TOPIC))
            topic.setText(cellData.get(GenericData.TRENDING_TOPIC).toString());
    }
}