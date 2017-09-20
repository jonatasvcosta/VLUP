package politcc2017.tcc_app.Volley.Objects;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jônatas on 19/09/2017.
 */

public class CustomJsonObjectRequest extends JsonObjectRequest {
    private String token;
    public CustomJsonObjectRequest(String token, int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.token = token;
    }

    public CustomJsonObjectRequest(String token, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("authorization","JWT "+token);
        return map;
    }
}
