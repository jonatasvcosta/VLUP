package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerViewHolder extends GenericViewHolder implements View.OnClickListener {
    TextView itemText;

    public DrawerViewHolder(View itemView, CellClickListener listener) {
        super(itemView, listener);
        itemView.setOnClickListener(this);
        itemText = (TextView) itemView.findViewById(R.id.drawer_cell_text_view);
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        itemText.setText(cellData.get(GenericData.DRAWER_ITEM_TEXT_KEY).toString());
    }

    @Override
    public void onClick(View v) {
        if(mClickListener != null) mClickListener.onClick(v, getAdapterPosition());
    }
}
