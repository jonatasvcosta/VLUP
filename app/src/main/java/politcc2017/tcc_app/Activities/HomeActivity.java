package politcc2017.tcc_app.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import politcc2017.tcc_app.Common.Languages;
import politcc2017.tcc_app.Components.DialogHelper;
import politcc2017.tcc_app.R;
import politcc2017.tcc_app.Volley.ServerRequestHelper;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        setActivityTitle("Home Activity");

        Button stringButton = (Button) findViewById(R.id.string_request_button);
        Button jsonButton = (Button) findViewById(R.id.json_request_button);
        Button jsonArrayButton = (Button) findViewById(R.id.json_array_request_button);
        Button imageButton = (Button) findViewById(R.id.image_request_button);
        Button englishButton = (Button) findViewById(R.id.english_button);
        Button portugueseButton = (Button) findViewById(R.id.portuguese_button);
        Button defaultDialogButton = (Button) findViewById(R.id.default_dialog_button);
        Button progressDialogButton = (Button) findViewById(R.id.progress_dialog_button);
        Button listSingleButton = (Button) findViewById(R.id.list_single_dialog_button);
        Button listMultiButton = (Button) findViewById(R.id.list_multi_dialog_button);

        final ImageView img = (ImageView) findViewById(R.id.image_response);

        final ArrayList<String> list = new ArrayList();
        list.add("Português");
        list.add("Alemão");
        list.add("Holandês");

        listSingleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.ListSingleChoiceDialog(HomeActivity.this, "Escolha um idioma", list, "OK", "Cancelar", new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }).show();
            }
        });

        listMultiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.ListMultiChoiceDialog(HomeActivity.this, "Escolha idiomas", list, "OK", "Cancelar", new MaterialDialog.ListCallbackMultiChoice() {

                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        String a = "";
                        for (int i = 0; i < text.length; i++) a += String.valueOf(text[i]);
                        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }).show();
            }
        });

        stringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequestHelper.getString(getApplicationContext(), "volleyString", "randomString", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        defaultDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.CustomDialog(HomeActivity.this, "Título", R.drawable.maintenance_icon,"Texto qualquer explicando esse dialog", "Ok", "Cancelar").show();
            }
        });

        progressDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.ProgressDialog(HomeActivity.this, "Carregando", "Aguarde").show();
            }
        });

        jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequestHelper.getJson(getApplicationContext(), "volleyJsonObject", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        jsonArrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequestHelper.getJsonArray(getApplicationContext(), "volleyJsonArray", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequestHelper.getImage(getApplicationContext(), "lg_nexus_5x", new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        img.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        });

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppLanguage(Languages.ENGLISH);
            }
        });

        portugueseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppLanguage(Languages.PORTUGUESE);
            }
        });
    }
}
