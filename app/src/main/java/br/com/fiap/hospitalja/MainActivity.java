package br.com.fiap.hospitalja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {


    private Button buttonSend;
    private Spinner SelectEspec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonSend = (Button) findViewById(R.id.buttonSend);
        this.SelectEspec = (Spinner) findViewById(R.id.SelectEspec);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.especialidades_selecionada, android.R.layout.simple_spinner_item);

        SelectEspec.setAdapter(adapter);

        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                Bundle params = new Bundle();
                params.putString("SelectEspec", SelectEspec.getSelectedItem().toString());
                intent.putExtras(params);

                startActivity(intent);
            }
        });

    }
}
