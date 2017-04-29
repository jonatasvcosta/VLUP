package politcc2017.tcc_app.Components.RecyclerView.Data;

import java.util.ArrayList;
import java.util.Hashtable;

public class GenericData {
    //Constant ViewHolderTypes

    //Constant keys for Generic Data Cells are declared here
    public static final String DRAWER_ITEM_TEXT_KEY = "drawer_text_key";
    public static final String DRAWER_ITEM_ICON_KEY = "drawer_icon_key";
    public static final String SUGGESTION_ITEM_LINK = "suggestion_link";
    public static final String SUGGESTION_ITEM_TITLE = "suggestion_title";
    public static final String SUGGESTION_ITEM_DESCRIPTION = "suggestion_description";
    public static final String SUGGESTION_ITEM_IMAGE = "suggestion_image";
    public static final String BOOKSHELF_ITEM_CATEGORY = "bookshelf_category";
    public static final String BOOKSHELF_CATEGORY_WORD = "bookshelf_category_word";
    public static final String BOOKSHELF_TAGS = "bookshelf_tags";
    public static final String CELL_TYPE = "cell_type";
    public static final String CELL_DEFAULT_TYPE = "default";
    public static final String CELL_HEADER_TYPE = "header";
    public static final String CUSTOM_CARD_TITLE = "ctitle";
    public static final String CUSTOM_CARD_CATEGORIES = "ccategories";
    public static final String CUSTOM_CARD_VOTES = "cvotes";
    public static final String CUSTOM_CARD_CONTENT = "ccontent";
    public static final String DICTIONARY_CELL_CONTENT = "dccontent";
    public static final String DICTIONARY_CELL_SOURCE = "dcsource";


    private ArrayList<Hashtable> mData;

    public GenericData() {
        mData = new ArrayList<>();
    }

    public void addNewCellWithString(Object key, String value, int position){
        Hashtable cellData;
        cellData = new Hashtable();
        cellData.put(key, value);
        cellData.put(CELL_TYPE, CELL_DEFAULT_TYPE);
        mData.add(position, cellData);
    }

    public void setSpecialTypeCells(ArrayList<Integer> cells, String type){
        for(int i = 0; i < cells.size(); i++){
            if(cells.get(i) >= 0 && cells.get(i) < mData.size()) mData.get(cells.get(i)).put(CELL_TYPE, type);
        }
    }

    public void addStringsToAllCells(Object key, ArrayList<String> values) {
        Hashtable cellData;
        for (int i = 0; i < values.size(); i++) {
            cellData = new Hashtable();
            if (mData.size() <= i) {
                cellData.put(key, values.get(i));
                mData.add(cellData);
            } else {
                mData.get(i).put(key, values.get(i));
            }
            mData.get(i).put(CELL_TYPE, CELL_DEFAULT_TYPE);
        }
    }

    public void addStringToCellArrayList(int position, String text){
        if(mData.get(position).containsKey(BOOKSHELF_TAGS)){
            ArrayList<String> tags = (ArrayList<String>) mData.get(position).get(BOOKSHELF_TAGS);
            tags.add(text);
            mData.get(position).put(BOOKSHELF_TAGS, tags);
        } else {
            ArrayList<String> tags = new ArrayList<>();
            tags.add(text);
            mData.get(position).put(BOOKSHELF_TAGS, tags);
        }
    }

    public void removeStringInCellArrayList(int adapterPosition, int tagPosition){
        if(mData.get(adapterPosition).containsKey(BOOKSHELF_TAGS)){
            ArrayList<String> tags = (ArrayList<String>) mData.get(adapterPosition).get(BOOKSHELF_TAGS);
            tags.remove(tagPosition);
            mData.get(adapterPosition).put(BOOKSHELF_TAGS, tags);
        }
    }

    public void removeCell(int position){
        mData.remove(position);
    }

    public void addIntegersToAllCells(Object key, ArrayList<Integer> values) {
        Hashtable cellData;
        for (int i = 0; i < values.size(); i++) {
            cellData = new Hashtable();
            if (mData.size() <= i) {
                cellData.put(key, values.get(i));
                mData.add(cellData);
            } else {
                mData.get(i).put(key, values.get(i));
            }
            mData.get(i).put(CELL_TYPE, CELL_DEFAULT_TYPE);
        }
    }

    public Hashtable getValue(int position){
        return mData.get(position);
    }

    public int Size(){
        return mData.size();
    }
}
