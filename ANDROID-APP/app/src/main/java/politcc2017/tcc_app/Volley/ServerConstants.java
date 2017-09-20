package politcc2017.tcc_app.Volley;

import com.android.volley.Request;

/**
 * Created by Jonatas on 30/10/2016.
 */

public class ServerConstants {
    public static final String BASE_URL = "http://ec2-184-73-142-34.compute-1.amazonaws.com/";
    public static final String SIGNUP_POST_URL = BASE_URL+"signup/";

    public static final String STRING_TAG = "STRING_TAG";
    public static final String JSON_TAG = "JSON_TAG";
    public static final String JSON_ARRAY_TAG = "JSON_ARRAY_TAG";
    public static final String IMAGE_TAG = "IMAGE_TAG";

    public static final int GET_REQUEST = Request.Method.GET;
    public static final int POST_REQUEST = Request.Method.POST;
    public static final int DELETE_REQUEST = Request.Method.DELETE;

    public static final String AUTHENTICATION_ENDPOINT = "authentication/api-token-auth/";
    public static final String TRANSLATION_ENDPOINT = "translation/";
}
