package com.example.balancomensal;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipal {

    AppCompatActivity app;
    Dados db;

    public TelaPrincipal(AppCompatActivity app, Dados d){

        this.app = app;
        this.db = d;

        db.salvar(app);
        System.out.println("Salvando...............");
        app.setContentView(R.layout.index);

        Grafico g = app.findViewById(R.id.grafico);
        Pizza p = app.findViewById(R.id.pizza2);
        g.setDados(db.getTotalMeses(12),db.getMesAtual());
        p.setDados(db,12);

        Spinner tempo = app.findViewById(R.id.tempo);
        String[] opcoes = {"Ultimo ano","Ultimo 6 meses","Ultimos 3 meses","Ultimo mês"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(app,android.R.layout.simple_spinner_item,opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tempo.setOnItemSelectedListener(mudar);
        tempo.setAdapter(adapter);

        View btn_add = app.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(adicionar);

        Meses btn_meses = app.findViewById(R.id.menu);
        btn_meses.setMes(db.getMesAtual(),db.getAno());
        btn_meses.setOnTouchListener(fechado);

        View fixas_btn = app.findViewById(R.id.fixas);
        fixas_btn.setOnClickListener(fixas);

    }

    View.OnClickListener adicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adicionar(v);
        }
    };

    View.OnClickListener fixas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TelaFixas(app,db);
        }
    };

    View.OnTouchListener fechado = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            if(x > 100 && x < 250 && y > 100 && y < 250){
                menu.aberto = true;
                menu.setOnTouchListener(aberto);
                menu.invalidate();
            }
            return false;
        }
    };

    View.OnTouchListener aberto = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            int i = menu.getMes(y);

            if(i < 12 && x < 600){
                new TelaMes(app,i,db);
            }
            else{
                menu.aberto = false;
                menu.invalidate();
                menu.setOnTouchListener(fechado);
            }

            return false;
        }
    };

    AdapterView.OnItemSelectedListener mudar = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Grafico g = app.findViewById(R.id.grafico);
            Pizza p = app.findViewById(R.id.pizza2);
            int[] meses = {12,6,3,1};
            g.setDados(db.getTotalMeses(meses[position]),db.getMesAtual());
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