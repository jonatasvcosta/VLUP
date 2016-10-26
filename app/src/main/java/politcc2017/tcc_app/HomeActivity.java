package politcc2017.tcc_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Requisição Enviada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
