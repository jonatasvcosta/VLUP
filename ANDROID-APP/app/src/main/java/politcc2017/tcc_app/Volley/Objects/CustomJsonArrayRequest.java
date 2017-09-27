package politcc2017.tcc_app.Volley.Objects;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jônatas on 19/09/2017.
 */

public class CustomJsonArrayRequest extends JsonArrayRequest {
    private String token;
    public CustomJsonArrayRequest(String token, int method, String url, JSONArray params, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, params, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("authorization","JWT "+token);
        return map;
    }
}
