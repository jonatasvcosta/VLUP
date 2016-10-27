package politcc2017.tcc_app.RecyclerView.Data;

/**
 * Created by Jonatas on 26/10/2016.
 */

public class DrawerData {
    private String [] texts;
    public DrawerData(String texts[]){
        this.texts = texts;
    }

    public String getItemText(int pos){
        return texts[pos];
    }

    public int getItemsLength(){
        return texts.length;
    }
}
