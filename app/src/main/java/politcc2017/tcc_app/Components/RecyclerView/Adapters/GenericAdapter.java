package politcc2017.tcc_app.Components.RecyclerView.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.DrawerViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.GenericViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.SitesSuggestionViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class GenericAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private GenericData mData;
    private GenericViewHolder mGenericViewHolder;
    private ViewHolderType viewHolderType;
    private CellClickListener mCLickListener;

    public GenericAdapter(GenericData data, ViewHolderType vhType, int layout){
        this.mData = data;
        this.viewHolderType = vhType;
    }

    public void RegisterClickListener(CellClickListener listener){
        this.mCLickListener = listener;
    }


    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if(viewHolderType == ViewHolderType.DRAWER_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.DRAWER_VIEW_HOLDER), null);
            return new DrawerViewHolder(itemView, mCLickListener);
        }
        if(viewHolderType == ViewHolderType.BROSER_SUGGESTION_ITEM_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.BROSER_SUGGESTION_ITEM_VIEW_HOLDER), null);
            return new SitesSuggestionViewHolder(itemView, mCLickListener);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
