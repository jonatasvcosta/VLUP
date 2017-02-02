package politcc2017.tcc_app.Components.RecyclerView.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Hashtable;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.FontHelper;
import politcc2017.tcc_app.Components.Listeners.CellClickListener;
import politcc2017.tcc_app.Components.RecyclerView.Data.GenericData;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/01/2016.
 */

public class BookshelfViewHolder extends GenericViewHolder{
    CustomTextView title, title2;
    ImageView editBtn, removeBtn, addWordBtn;
    TagContainerLayout tagLayout;
    FoldingCell foldingCell;
    CellClickListener listener;
    ScrollView mScrollView;
    TagView.OnTagClickListener tagClickListener;

    public BookshelfViewHolder(View itemView, CellClickListener listener, TagView.OnTagClickListener tagClickListener, Context c) {
        super(itemView, listener);
        this.listener = listener;
        this.tagClickListener = tagClickListener;
        title = (CustomTextView) itemView.findViewById(R.id.category_title);
        title2 = (CustomTextView) itemView.findViewById(R.id.category_title_unfolded);
        editBtn = (ImageView) itemView.findViewById(R.id.bookshelf_category_edit_btn);
        addWordBtn = (ImageView) itemView.findViewById(R.id.btn_add_new_word);
        removeBtn = (ImageView) itemView.findViewById(R.id.bookshelf_category_remove_btn);
        foldingCell = (FoldingCell) itemView.findViewById(R.id.bookshelf_category_folding_cell);
        tagLayout = (TagContainerLayout) itemView.findViewById(R.id.bookshelf_tag_container_layout);
        mScrollView = (ScrollView) itemView.findViewById(R.id.bookshelf_content_scrollview);
        tagLayout.setTagTypeface(FontHelper.get(FontHelper.TTF_FONT, c));
        if(c != null) tagLayout.setTagTextColor(c.getResources().getColor(R.color.tag_cyan_lighter));
        setListeners();
    }

    public void setListeners(){
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldingCell.toggle(false);
            }
        });
        mScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foldingCell.toggle(false);
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("edit", getAdapterPosition());
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("remove", getAdapterPosition());
            }
        });
        addWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick("addWord", getAdapterPosition());
            }
        });
        tagLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                if(tagClickListener != null) tagClickListener.onTagClick(position, text);
            }

            @Override
            public void onTagLongClick(int position, String text) {
                if(tagClickListener != null) tagClickListener.onTagLongClick(position, Integer.toString(getAdapterPosition()));
            }

            @Override
            public void onTagCrossClick(int position) {
                if(tagClickListener != null) tagClickListener.onTagCrossClick(position);
            }
        });
    }

    @Override
    public void setViewContent(Hashtable cellData) {
        if (cellData.containsKey(GenericData.BOOKSHELF_ITEM_CATEGORY)) {
            title.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
            title2.setText(cellData.get(GenericData.BOOKSHELF_ITEM_CATEGORY).toString());
        }
        if(cellData.containsKey(GenericData.BOOKSHELF_TAGS)) tagLayout.setTags((ArrayList<String>) cellData.get(GenericData.BOOKSHELF_TAGS));
    }
}
