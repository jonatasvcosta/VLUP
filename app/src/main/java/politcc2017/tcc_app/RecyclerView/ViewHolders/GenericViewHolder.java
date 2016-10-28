package politcc2017.tcc_app.RecyclerView.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Hashtable;

import politcc2017.tcc_app.RecyclerView.Data.GenericData;

/**
 * Created by Jonatas on 26/10/2016.
 */

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {

    public GenericViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setViewContent(Hashtable cellData);
}
