package politcc2017.tcc_app.Components.Listeners;

import android.view.View;

/**
 * Created by Jonatas on 29/10/2016.
 */

public interface CellClickListener extends View.OnClickListener {
    public void onClick(View v, int position);
    public void onLinkClick(String link);
}
