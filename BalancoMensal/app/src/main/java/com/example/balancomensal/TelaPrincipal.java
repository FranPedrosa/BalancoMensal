package com.example.balancomensal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipal {

    AppCompatActivity app;
    Dados db;

    public TelaPrincipal(AppCompatActivity app, Dados d){

        this.app = app;
        this.db = d;

        app.setContentView(R.layout.index);

        Grafico g = app.findViewById(R.id.grafico);
        Pizza p = app.findViewById(R.id.pizza2);
        g.setDados(db.getTotalMeses(12));
        p.setDados(db,12);

        Spinner tempo = app.findViewById(R.id.tempo);
        String[] opcoes = {"Ultimo ano","Ultimo 6 meses","Ultimos 3 meses","Ultimo mês"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(app,android.R.layout.simple_spinner_item,opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tempo.setOnItemSelectedListener(mudar);
        tempo.setAdapter(adapter);

        View btn_add = app.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(adicionar);

    }

    View.OnClickListener adicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adicionar(v);
        }
    };

    AdapterView.OnItemSelectedListener mudar = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Grafico g = app.findViewById(R.id.grafico);
            Pizza p = app.findViewById(R.id.pizza2);
            int[] meses = {12,6,3,1};
            g.setDados(db.getTotalMeses(meses[position]));
            p.setDados(db,meses[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    public void adicionar(View v) {
        Button btn_add = app.findViewById(R.id.btn_add);
        AddData addData = new AddData(app,db);
    }
}
