package politcc2017.tcc_app.Volley;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jonatas on 01/11/2016.
 */
public class JSONHelper {
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";
    public static final String ERROR = "ERROR";

    //Consider using Jackson or manual object mapping
    public static String objectToJSON(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static Object jsonToObject(String s, Class c){
        Gson gson = new Gson();
        return gson.fromJson(s, c);
    }

    public static JSONObject CreateErrorMessage(String type, String message){
        JSONObject error = new JSONObject();
        try {
            error.put(TITLE, ERROR);
            error.put(TYPE, type);
            error.put(MESSAGE, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }
}
