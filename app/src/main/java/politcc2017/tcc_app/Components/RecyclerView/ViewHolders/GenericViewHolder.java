package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Hashtable;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;

/**
 * Created by Jonatas on 26/10/2016.
 */

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
    protected CellClickListener mClickListener;

    public GenericViewHolder(View itemView, CellClickListener listener) {
        super(itemView);
        this.mClickListener = listener;
    }

    public abstract void setViewContent(Hashtable cellData);
}
