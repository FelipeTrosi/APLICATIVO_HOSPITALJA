package br.com.fiap.hospitalja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.hospitalja.Model.Especialidade;
import br.com.fiap.hospitalja.Model.Hospital;

public class ThirdAcitivity extends AppCompatActivity {

    private TextView nomeTextView;
    private TextView enderecoTextView;
    private TextView filaTextView;
    private TextView tempoTextView;
    private TextView especialidadeTextView;

    private List<Hospital> lista = new ArrayList<>();
    private int _position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_acitivity);
        Intent intent = getIntent();



        this.nomeTextView = (TextView) findViewById(R.id.nomeTextView);
        this.enderecoTextView = (TextView) findViewById(R.id.enderecoTextView);
        this.filaTextView = (TextView) findViewById(R.id.filaTextView);
        this.tempoTextView = (TextView) findViewById(R.id.tempoTextView);
        this.especialidadeTextView = (TextView) findViewById(R.id.especialidadeTextView);


        if(intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                lista = params.getParcelableArrayList("Lista");
                _position = params.getInt("Position");

            }
        }


        for(Hospital h: lista){

            nomeTextView.setText(h.getNome());
            enderecoTextView.setText(h.getEndereco());
            filaTextView.setText(h.getFila());
            tempoTextView.setText(h.getTempoEspera());
            for(Especialidade e: h.getEspecialidades()){

                if(especialidadeTextView.getText().equals("")){
                    especialidadeTextView.setText(e.getNome());
                }else {
                    especialidadeTextView.setText(", "+e.getNome());
                }
            }
        }

    }
}
