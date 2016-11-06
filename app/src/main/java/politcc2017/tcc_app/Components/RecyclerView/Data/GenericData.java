package politcc2017.tcc_app.Components.RecyclerView.Data;

import java.util.ArrayList;
import java.util.Hashtable;

public class GenericData {
    //Constant ViewHolderTypes


    //Constant keys for Generic Data Cells are declared here
    public static final String DRAWER_ITEM_TEXT_KEY = "drawer_cell_key";

    private ArrayList<Hashtable> mData;

    public GenericData() {
        mData = new ArrayList<>();
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
        }
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
        }
    }

    public Hashtable getValue(int position){
        return mData.get(position);
    }

    public int Size(){
        return mData.size();
    }
}
