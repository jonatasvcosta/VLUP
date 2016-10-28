package politcc2017.tcc_app.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Hashtable;

import politcc2017.tcc_app.R;
import politcc2017.tcc_app.RecyclerView.Data.GenericData;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerViewHolder extends GenericViewHolder {
    TextView itemText;

    public DrawerViewHolder(View itemView) {
        super(itemView);
        itemText = (TextView) itemView.findViewById(R.id.drawer_cell_text_view);
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        itemText.setText(cellData.get(GenericData.DRAWER_ITEM_TEXT_KEY).toString());
    }
}
