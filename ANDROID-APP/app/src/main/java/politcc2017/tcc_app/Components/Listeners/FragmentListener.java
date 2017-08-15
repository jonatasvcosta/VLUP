package politcc2017.tcc_app.Components.Listeners;

/**
 * Created by Jonatas on 13/08/2017.
 */

public interface FragmentListener{
    public void onMessageSent(String sender, String message);
    public void onMessageSent(String sender, String message, String value);
}
