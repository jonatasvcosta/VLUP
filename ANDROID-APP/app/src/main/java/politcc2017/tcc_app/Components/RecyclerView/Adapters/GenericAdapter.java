package politcc2017.tcc_app.Components.RecyclerView.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.BookshelfViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.BookshelfWordViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.CustomCardViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.DictionaryViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.DrawerViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.GenericViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.LabelViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.SitesSuggestionViewHolder;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.ViewHolderType;
import politcc2017.tcc_app.Components.RecyclerView.ViewHolders.VocabularyWordViewHolder;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class GenericAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private GenericData mData;
    private GenericViewHolder mGenericViewHolder;
    private ViewHolderType viewHolderType;
    private CellClickListener mCLickListener;
    private Context mContext;

    public GenericAdapter(GenericData data, ViewHolderType vhType){
        this.mData = data;
        this.viewHolderType = vhType;
    }

    public GenericAdapter(GenericData data, ViewHolderType vhType, Context c){
        this.mData = data;
        this.viewHolderType = vhType;
        this.mContext = c;
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
        if(viewHolderType == ViewHolderType.BROWSER_SUGGESTION_ITEM_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.BROWSER_SUGGESTION_ITEM_VIEW_HOLDER), null);
            return new SitesSuggestionViewHolder(itemView, mCLickListener);
        }
        if(viewHolderType == ViewHolderType.BOOKSHELF_VIEW_HOLDER) {
            final View itemView;
            if(mData.getValue(position).get(GenericData.CELL_TYPE).toString().equals(GenericData.CELL_HEADER_TYPE)){
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookshelf_label_cell, null);
                return new LabelViewHolder(itemView, mCLickListener);
            }
            else{
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookshelf_category_cell, null);
                return new BookshelfViewHolder(itemView, mCLickListener, mContext);
            }
        }
        if(viewHolderType == ViewHolderType.BOOKSHELF_WORD_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.BOOKSHELF_WORD_VIEW_HOLDER), null);
            return new BookshelfWordViewHolder(itemView, mCLickListener);
        }

        if(viewHolderType == ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.VOCABULARY_WORD_VIEW_HOLDER), null);
            return new VocabularyWordViewHolder(itemView, mCLickListener);
        }

        if(viewHolderType == ViewHolderType.CUSTOM_CARD_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.CUSTOM_CARD_VIEW_HOLDER), null);
            return new CustomCardViewHolder(itemView, mCLickListener, mContext);
        }
        if(viewHolderType == ViewHolderType.DICTIONARY_CELL_VIEW_HOLDER) {
            final View itemView = LayoutInflater.from(parent.getContext()).inflate(GenericViewHolder.getLayoutViewByPosition(position, ViewHolderType.DICTIONARY_CELL_VIEW_HOLDER), parent, false);
            return new DictionaryViewHolder(itemView, mCLickListener, mContext);
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
