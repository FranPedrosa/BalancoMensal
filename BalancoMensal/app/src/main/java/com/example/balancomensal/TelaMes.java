package com.example.balancomensal;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaMes{

    AppCompatActivity app;
    Dados db;

    public TelaMes(AppCompatActivity app, int mes, Dados dados) {
        this.app = app;
        this.db = dados;
        app.setContentView(R.layout.tela_mes);
        Calendario c = app.findViewById(R.id.calendario);
        ListaMov lm = app.findViewById(R.id.lista);
        Pizza p = app.findViewById(R.id.pizza);
        TextView tw = app.findViewById(R.id.total);
        tw.setText(dados.getMes(mes).getTotal() + "");
        p.setMes(dados.getMes(mes));
        lm.setMes(dados.getMes(mes));
        c.setMes(dados.getMes(mes),2020,mes);
        c.setOnTouchListener(toque);

        Meses menu = app.findViewById(R.id.menu2);
        menu.setMes(0,11);
        menu.setOnTouchListener(fechado);
        Button retornar = app.findViewById(R.id.return_btn);
        retornar.setOnClickListener(voltar);
    }

    View.OnTouchListener toque = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Calendario c = app.findViewById(R.id.calendario);
            ListaMov lm = app.findViewById(R.id.lista);
            int dia = c.getDia((int)event.getX(),(int)event.getY());
            lm.setDia(dia);
            return false;
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
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            int i = y/80;

            if(i < 12 && x < 500){
                new TelaMes(app,i,db);
                return true;
            }
            else{
                menu.aberto = false;
                menu.invalidate();
                menu.setOnTouchListener(fechado);
            }

            return false;
        }
    };

    View.OnClickListener voltar = new View.OnClickListener() {
        public void onClick(View v) {
            new TelaPrincipal(app,db);
        }
    };
}