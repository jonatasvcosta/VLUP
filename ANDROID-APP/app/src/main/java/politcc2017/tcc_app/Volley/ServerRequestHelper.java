package politcc2017.tcc_app.Volley;

import android.content.Context;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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

import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.Entities.WordContextMenu;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.Objects.CustomJsonArrayRequest;
import politcc2017.tcc_app.Volley.Objects.CustomJsonObjectRequest;


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
        final String[] translation = new String[]{word};
        getAuthorizedJSONRequest(c, ServerConstants.TRANSLATION_ENDPOINT+"?"+ServerConstants.ORIGINAL_TEXT_KEY+"="+word+"&"+ServerConstants.ORIGINAL_LANGUAGE_KEY+"="+originalLanguage+"&"+ServerConstants.FINAL_LANGUAGE_KEY+"="+finalLanguage
                , null,new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            translation[0] = response.get(ServerConstants.TRANSLATED_TEXT_KEY).toString();
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
        String completeURL = ServerConstants.BASE_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.GET_REQUEST, c, completeURL, null, responseListener);
    }

    public static void postJsonArray(Context c, String url, JSONArray objectSent, final Response.Listener<JSONArray> responseListener){
        String completeURL = ServerConstants.BASE_URL + url;
        jsonArrayAbsoluteURLRequest(ServerConstants.POST_REQUEST, c, completeURL, objectSent, responseListener);
    }

    public static void getImage(Context c, String relativeUrl, final ImageLoader.ImageListener responseListener) {
        String completeURL = ServerConstants.BASE_URL + relativeUrl;
        imageAbsoluteURLRequest(c, completeURL, responseListener);
    }

    public static void postAuthorizedJSONRequest(Context c, String relativeURL, JSONObject params, final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener){
        CustomJsonObjectRequest request_json = new CustomJsonObjectRequest(ServerToken, Request.Method.POST, ServerConstants.BASE_URL + relativeURL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = new String(error.networkResponse.data);
                VolleyLog.e("Error: ", error.getMessage());
                if(errorListener != null) errorListener.onErrorResponse(error);
            }
        });

        AppSingleton.getInstance(c).addToRequestQueue(request_json, ServerConstants.JSON_TAG + relativeURL);
    }

    public static void postAuthorizedJSONRequest(Context c, String relativeURL, JSONObject params, final Response.Listener<JSONObject> listener){
        postAuthorizedJSONRequest(c, relativeURL, params, listener, null);
    }

    public static void getAuthorizedJSONRequest(Context c, String relativeURL, JSONObject params, final Response.Listener<JSONObject> listener) {
        CustomJsonObjectRequest request_json = new CustomJsonObjectRequest(ServerToken, Request.Method.GET, ServerConstants.BASE_URL + relativeURL, params,
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

    public static void getAuthorizedJSONArrayRequest(Context c, String relativeURL, JSONArray params, final Response.Listener<JSONArray> listener){
        final MaterialDialog loading = DialogHelper.ProgressDialog(c, c.getResources().getString(R.string.dialog_loading_title));
        loading.show();
        CustomJsonArrayRequest request_json = new CustomJsonArrayRequest(ServerToken, Request.Method.GET, ServerConstants.BASE_URL + relativeURL, params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        loading.dismiss();
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String msg = new String(error.networkResponse.data);
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

        request_json.setRetryPolicy(new DefaultRetryPolicy(40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppSingleton.getInstance(c).addToRequestQueue(request_json, ServerConstants.JSON_TAG + relativeURL);
    }

    public static void postJSONRequest(Context c, String relativeURL, HashMap<String, String> params, final Response.Listener<JSONObject> listener, final Response.ErrorListener errorListener) {
        JsonObjectRequest request_json = new JsonObjectRequest(ServerConstants.BASE_URL+relativeURL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                if(errorListener != null) errorListener.onErrorResponse(error);
            }
        });
        AppSingleton.getInstance(c).addToRequestQueue(request_json, ServerConstants.JSON_TAG+relativeURL);
    }
    public static void postJSONRequest(Context c, String relativeURL, HashMap<String, String> params, final Response.Listener<JSONObject> listener){
        postJSONRequest(c, relativeURL, params,listener, null);
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
