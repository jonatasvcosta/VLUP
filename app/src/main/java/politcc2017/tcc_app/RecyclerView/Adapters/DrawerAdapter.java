package politcc2017.tcc_app.RecyclerView.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politcc2017.tcc_app.R;
import politcc2017.tcc_app.RecyclerView.Data.DrawerData;
import politcc2017.tcc_app.RecyclerView.ViewHolders.DrawerViewHolder;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerViewHolder> {
    private DrawerData mData;
    public DrawerAdapter(DrawerData data){
        this.mData = data;
    }
    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_cell, null);
        DrawerViewHolder viewHolder = new DrawerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        holder.setItemText(mData.getItemText(position));
    }

    @Override
    public int getItemCount() {
        return mData.getItemsLength();
    }
}
