package br.com.fiap.hospitalja.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import br.com.fiap.hospitalja.Model.Especialidade;
import br.com.fiap.hospitalja.Model.Hospital;
import br.com.fiap.hospitalja.R;
import br.com.fiap.hospitalja.SecondActivity;

public class AdapterListHospitais extends BaseAdapter {

    private Context context;
    private List<Hospital> hospitalList;
    Random random = new Random();



    public AdapterListHospitais(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }


    @Override
    public int getCount() {
        return this.hospitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.hospitalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            View view = View.inflate(this.context, R.layout.lista,null);

            TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            TextView distanceTextView = (TextView) view.findViewById(R.id.distanceTextView);
            TextView filaTextView = (TextView) view.findViewById(R.id.filaTextView);
            TextView tempoEsperaTextView = (TextView) view.findViewById(R.id.tempoEsperaTextView);

            nameTextView.setText(this.hospitalList.get(position).getNome());
            if (this.hospitalList.get(position).getCodigo() == 1){
                distanceTextView.setText(("29 km"));
            }else {
                distanceTextView.setText((random.nextInt(25) + 1) + " km");
            }
            filaTextView.setText( this.hospitalList.get(position).getFila());
            tempoEsperaTextView.setText(this.hospitalList.get(position).getTempoEspera());

        return view;
    }

        /*Double.parseDouble(this.hospitalList.get(position).getLatitude())
                    ,Double.parseDouble(this.hospitalList.get(position).getLongitude())*/
}
