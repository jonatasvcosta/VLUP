package politcc2017.tcc_app.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.Volley.Objects.CustomJsonObjectRequest;

import static politcc2017.tcc_app.Volley.ServerConstants.BASE_URL;

/**
 * Created by Jonatas on 30/10/2016.
 */

public class ServerRequestHelper {
    private static String ServerToken;

    public static void SetToken(String token){
        ServerToken = token;
    }

    public static WordContextMenu getWordInformation(final Context c, String locale, String word){
        return new WordContextMenu("Translation of "+word, "Synonym of "+word, "Antonym of "+word, new String[] {"similar word1", "similat word 2"});
    }

    public static void getWordTranslation(final Context c, String finalLanguage, String originalLanguage, String word, final Response.Listener<String> listener){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("original_text", word);
        params.put("original_language", originalLanguage);
        params.put("final_language", finalLanguage);
        final String[] translation = new String[]{word};
        postAuthorizedJSONRequest(c, ServerConstants.TRANSLATION_ENDPOINT, params ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    translation[0] = response.get("translated_text").toString();
                    listener.onResponse(translation[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onResponse(translation[0]);
                }
            }
        });
    }

    public static void getString(Context c, String url, final String defaultString, final Response.Listener<String> responseListener){
        String completeURL = ServerConstants.BASE_URL + url;
        stringAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, defaultString, responseListener);
    }

    public static void postString (Context c, String url, String defaultString, final Response.Listener<String> responseListener){
        String completeURL = url;//ServerConstants.API_URL + url;
        stringAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, defaultString, responseListener);
    }

    public static void getJsonArray(Context c, String url, final Response.Listener<JSONArray> responseListener){
        String completeURL = BASE_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, null, responseListener);
    }

    public static void postJsonArray(Context c, String url, JSONArray objectSent, final Response.Listener<JSONArray> responseListener){
        String completeURL = BASE_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, objectSent, responseListener);
    }

    public static void getImage(Context c, String relativeUrl, final ImageLoader.ImageListener responseListener) {
        String completeURL = BASE_URL + relativeUrl;
        imageAbsoluteURLRequest(c, completeURL, responseListener);
    }

    public static void postAuthorizedJSONRequest(Context c, String relativeURL, HashMap<String, String> params, final Response.Listener<JSONObject> listener){
        CustomJsonObjectRequest request_json = new CustomJsonObjectRequest(ServerToken, BASE_URL + relativeURL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        AppSingleton.getInstance(c).addToRequestQueue(request_json, ServerConstants.JSON_TAG + relativeURL);
    }

    public static void postJSONRequest(Context c, String relativeURL, HashMap<String, String> params, final Response.Listener<JSONObject> listener){
        JsonObjectRequest request_json = new JsonObjectRequest(BASE_URL+relativeURL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        AppSingleton.getInstance(c).addToRequestQueue(request_json, ServerConstants.JSON_TAG+relativeURL);
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
       // if(url.contains(secureBaseAdress)) url = url.replace(secureBaseAdress, baseAdress);
        ImageLoader imageLoader = AppSingleton.getInstance(c.getApplicationContext()).getImageLoader();
        imageLoader.get(url, responseListener);
    }
}
