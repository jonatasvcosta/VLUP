package politcc2017.tcc_app.Volley;

import com.android.volley.Request;

/**
 * Created by Jonatas on 30/10/2016.
 */

public class ServerConstants {
    public static final String BASE_URL = "http://api.vlup.com.br/";//"http://ec2-54-233-186-115.sa-east-1.compute.amazonaws.com/";
    public static final String SIGNUP_POST_URL = BASE_URL+"signup/";

    public static final String STRING_TAG = "STRING_TAG";
    public static final String JSON_TAG = "JSON_TAG";
    public static final String JSON_ARRAY_TAG = "JSON_ARRAY_TAG";
    public static final String IMAGE_TAG = "IMAGE_TAG";

    public static final int GET_REQUEST = Request.Method.GET;
    public static final int POST_REQUEST = Request.Method.POST;
    public static final int DELETE_REQUEST = Request.Method.DELETE;

    public static final String AUTHENTICATION_ENDPOINT = "authentication/api-token-auth/";
    public static final String SIGNUP_ENDPOINT = "/authentication/users/";
    public static final String TRANSLATION_ENDPOINT = "translation/";
    public static final String SYNONYM_ENDPOINT = "synonymous/";
    public static final String SIMILAR_WORDS_ENDPOINT = "similar_words/";
    public static final String WEBSITES_LIST_ENDPOINT = "news/websites/";
    public static final String NEWS_SEARCH_ENDPOINT = "news/article/similarity";

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String EMAIL_KEY = "email";
    public static final String FIRST_NAME_KEY = "first_name";
    public static final String LAST_NAME_KEY = "last_name";
    public static final String NAME_KEY = "name";
    public static final String NATIVE_LANGUAGE_KEY = "native_language";
    public static final String LANGUAGE_KEY = "language";
    public static final String LEARNING_LANGUAGE_KEY = "learning_language";
    public static final String LATITUDE_KEY = "latitude";
    public static final String LONGITUDE_KEY = "longitude";
    public static final String TOKEN_KEY = "token";
    public static final String ORIGINAL_TEXT_KEY = "original_text";
    public static final String ORIGINAL_LANGUAGE_KEY = "original_language";
    public static final String FINAL_LANGUAGE_KEY = "final_language";
    public static final String TRANSLATED_TEXT_KEY = "translated_text";

}
