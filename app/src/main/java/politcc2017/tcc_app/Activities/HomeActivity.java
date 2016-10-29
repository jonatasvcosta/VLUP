package politcc2017.tcc_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import politcc2017.tcc_app.Common.Languages;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 25/10/2016.
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);
        setActivityTitle("Home Activity");

        Button dialogButton = (Button) findViewById(R.id.open_dialog_button);
        Button englishButton = (Button) findViewById(R.id.english_button);
        Button portugueseButton = (Button) findViewById(R.id.portuguese_button);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Requisição Enviada", Toast.LENGTH_SHORT).show();
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
