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

/**
 * Created by Jonatas on 30/10/2016.
 */

public class ServerRequestHelper {
    public static void stringRequest(Context c, String url, final String defaultString, final Response.Listener<String> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        stringAbsoluteURLRequest(c, completeURL, defaultString, responseListener);
    }

    public static void jsonRequest(Context c, String url, final Response.Listener<JSONObject> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonAbsoluteURLRequest(c, completeURL, responseListener);
    }

    public static void jsonArrayRequest(Context c, String url, final Response.Listener<JSONArray> responseListener){
        String completeURL = ServerConstants.API_URL + url;
        jsonArrayAbsoluteURLRequest(c, completeURL, responseListener);
    }

    public static void imageRequest(Context c, String url, final ImageLoader.ImageListener responseListener) {
        String completeURL = ServerConstants.API_URL + url;
        imageAbsoluteURLRequest(c, completeURL, responseListener);
    }

    public static void stringAbsoluteURLRequest(Context c, String url, final String defaultString, final Response.Listener<String> responseListener){

        String  REQUEST_TAG = ServerConstants.STRING_TAG+url;
        StringRequest strReq = new StringRequest(url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(responseListener != null) responseListener.onResponse(defaultString);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public static void jsonAbsoluteURLRequest(Context c, String url, final Response.Listener<JSONObject> responseListener){

        String  REQUEST_TAG = ServerConstants.JSON_TAG+url;
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Use JSON Helper (to be created) to return a JSON object with error code
            }
        });
        // Adding Json request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(jsonObjectReq, REQUEST_TAG);
    }

    public static void jsonArrayAbsoluteURLRequest(Context c, String url, final Response.Listener<JSONArray> responseListener){

        String  REQUEST_TAG = ServerConstants.JSON_ARRAY_TAG+url;
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Use JSON Helper (to be created) to return a JSON object with error code
            }
        });
        // Adding JsonArray request to request queue
        AppSingleton.getInstance(c.getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    public static void imageAbsoluteURLRequest(Context c, String url, final ImageLoader.ImageListener responseListener){
        ImageLoader imageLoader = AppSingleton.getInstance(c.getApplicationContext()).getImageLoader();
        imageLoader.get(url, responseListener);
    }
}
