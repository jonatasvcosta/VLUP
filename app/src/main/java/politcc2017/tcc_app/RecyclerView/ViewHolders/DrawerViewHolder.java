package politcc2017.tcc_app.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerViewHolder extends RecyclerView.ViewHolder {
    TextView itemText;

    public DrawerViewHolder(View itemView) {
        super(itemView);
        itemText = (TextView) itemView.findViewById(R.id.drawer_cell_text_view);
    }

    public void setItemText(String text){
        this.itemText.setText(text);
    }
}
