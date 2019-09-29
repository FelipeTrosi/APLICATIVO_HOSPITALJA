package br.com.fiap.hospitalja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private Button buttonSend;
    private EditText SelectEspec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.SelectEspec = (EditText)findViewById(R.id.SelectEspec);

        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, second_activity.class);
                Bundle params = new Bundle();
                params.putString("SelectEspec", SelectEspec.getText().toString());
                intent.putExtras(params);
                startActivity(intent);
            }
        });

    }
}
