package politcc2017.tcc_app.RecyclerView.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politcc2017.tcc_app.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.RecyclerView.ViewHolders.DrawerViewHolder;
import politcc2017.tcc_app.RecyclerView.ViewHolders.GenericViewHolder;
import politcc2017.tcc_app.RecyclerView.ViewHolders.ViewHolderType;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class GenericAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private GenericData mData;
    private GenericViewHolder mGenericViewHolder;
    private ViewHolderType viewHolderType;
    private int mLayout;
    public GenericAdapter(GenericData data, ViewHolderType vhType, int layout){
        this.mData = data;
        this.viewHolderType = vhType;
        this.mLayout = layout;
    }
    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayout, null);
        if(viewHolderType == ViewHolderType.DRAWER_VIEW_HOLDER) return new DrawerViewHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setViewContent(mData.getValue(position));
    }

    @Override
    public int getItemCount() {
        return mData.Size();
    }
}
