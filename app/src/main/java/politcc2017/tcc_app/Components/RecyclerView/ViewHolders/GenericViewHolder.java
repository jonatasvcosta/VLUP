package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 26/10/2016.
 */

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
    protected CellClickListener mClickListener;

    public GenericViewHolder(View itemView, CellClickListener listener) {
        super(itemView);
        this.mClickListener = listener;
    }
    public static int getLayoutViewByPosition(int position, ViewHolderType type){
        if(type == ViewHolderType.DRAWER_VIEW_HOLDER) return R.layout.drawer_item_cell;
        return -1;
    }
    public abstract void setViewContent(Hashtable cellData);
}
