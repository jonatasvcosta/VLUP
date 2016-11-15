package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerViewHolder extends GenericViewHolder implements View.OnClickListener {
    TextView itemText;
    ImageView itemIcon;
    public DrawerViewHolder(View itemView, CellClickListener listener) {
        super(itemView, listener);
        itemView.setOnClickListener(this);
        itemText = (TextView) itemView.findViewById(R.id.drawer_cell_text_view);
        itemIcon = (ImageView) itemView.findViewById(R.id.drawer_cell_icon);
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        int icon = -1;
        if(cellData.containsKey(GenericData.DRAWER_ITEM_TEXT_KEY))itemText.setText(cellData.get(GenericData.DRAWER_ITEM_TEXT_KEY).toString());
        if(cellData.containsKey(GenericData.DRAWER_ITEM_ICON_KEY)) icon = (int) cellData.get(GenericData.DRAWER_ITEM_ICON_KEY);
        if(icon != -1) itemIcon.setImageResource(icon);
    }

    @Override
    public void onClick(View v) {
        if(mClickListener != null) mClickListener.onClick(v, getAdapterPosition());
    }
}
