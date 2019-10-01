package br.com.fiap.hospitalja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.hospitalja.Model.Hospital;

public class ThirdAcitivity extends AppCompatActivity {

    private TextView nomeTextView;
    private TextView enderecoTextView;
    private TextView filaTextView;
    private TextView tempoTextView;
    private List<Hospital> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_acitivity);
        Intent intent = getIntent();



        this.nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        this.enderecoTextView = (TextView) findViewById(R.id.enderecoTextView);
        this.filaTextView = (TextView) findViewById(R.id.filaTextView);
        this.tempoTextView = (TextView) findViewById(R.id.tempoTextView);


        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                lista = params.getParcelableArrayList("Lista");
            }
        }

        for (int i=0;i < lista.size();i++){
            nomeTextView.setText(lista.get(i).getNome());
            enderecoTextView.setText(lista.get(i).getEndereco());
            filaTextView.setText(lista.get(i).getFila());
            tempoTextView.setText(lista.get(i).getTempoEspera());
        }

    }
}
