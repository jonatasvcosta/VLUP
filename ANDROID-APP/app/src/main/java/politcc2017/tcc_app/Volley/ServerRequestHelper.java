package politcc2017.tcc_app.Volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import politcc2017.tcc_app.Entities.WordContextMenu;

/**
 * Created by Jonatas on 30/10/2016.
 */

public class ServerRequestHelper {
    private static final String baseAdress = "http://";
    private static final String secureBaseAdress = "https://";

    public static WordContextMenu getWordInformation(Context c, String locale, String word){
        //change this after this is implemented on server
        return new WordContextMenu("Translation of "+word, "Synonym of "+word, "Antonym of "+word, new String[] {"similar word1", "similat word 2"});
    }

    public static void getString(Context c, String url, final String defaultString, final Response.Listener<String> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        stringAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, defaultString, responseListener);
    }

    public static void postString (Context c, String url, String defaultString, final Response.Listener<String> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        stringAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, defaultString, responseListener);
    }

    public static void getJson(Context c, String url, final Response.Listener<JSONObject> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, null,  responseListener);
    }

    public static void postJson(Context c, String url, JSONObject objectSent,final Response.Listener<JSONObject> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, objectSent, responseListener);
    }

    public static void getJsonArray(Context c, String url, final Response.Listener<JSONArray> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, null, responseListener);
    }

    public static void postJsonArray(Context c, String url, JSONArray objectSent, final Response.Listener<JSONArray> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, objectSent, responseListener);
    }

    public static void getImage(Context c, String relativeUrl, final ImageLoader.ImageListener responseListener) {
        String completeURL = ServerConstants.API_URL + relativeUrl;
        imageAbsoluteURLRequest(c, completeURL, responseListener);
    }


    public static void stringAbsoluteURLRequest(int method, Context c, String url, final String defaultString, final Response.Listener<String> responseListener){

        String  REQUEST_TAG = ServerConstants.STRING_TAG+url;
        StringRequest strReq = new StringRequest(method, url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(responseListener != null) responseListener.onResponse(defaultString);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public static void jsonAbsoluteURLRequest(int method, Context c, String url, JSONObject objectSent, final Response.Listener<JSONObject> responseListener){

        String  REQUEST_TAG = ServerConstants.JSON_TAG+url;
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(method, url, objectSent, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onResponse(JSONHelper.CreateErrorMessage(error.getClass().toString(),error.getMessage()));
            }
        });
        // Adding Json request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public static void jsonArrayAbsoluteURLRequest(int method, Context c, String url, JSONArray objectSent , final Response.Listener<JSONArray> responseListener){

        String  REQUEST_TAG = ServerConstants.JSON_ARRAY_TAG+url;
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(ServerConstants.GET_REQUEST, url, objectSent, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONArray jsonArray= new JSONArray();
                jsonArray.put(JSONHelper.CreateErrorMessage(error.getClass().toString(), error.getMessage()));
                responseListener.onResponse(jsonArray);
            }
        });
        // Adding JsonArray request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    public static void imageAbsoluteURLRequest(Context c, String url, final ImageLoader.ImageListener responseListener){
        if(url.contains(secureBaseAdress)) url = url.replace(secureBaseAdress, baseAdress);
        ImageLoader imageLoader = AppSingleton.getInstance(c.getApplicationContext()).getImageLoader();
        imageLoader.get(url, responseListener);
    }
}
