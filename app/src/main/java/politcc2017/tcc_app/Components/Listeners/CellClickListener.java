package politcc2017.tcc_app.Components.Listeners;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jonatas on 29/10/2016.
 */

public interface CellClickListener extends View.OnClickListener {
    public void onClick(View v, int position);
    public void onClick(ImageView v, String link); //used to download image from web
    public void onLinkClick(String link);
}
