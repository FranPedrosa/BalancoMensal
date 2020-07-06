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

    public TelaMes(AppCompatActivity app, int mes, Dados dados) {
        this.app = app;
        app.setContentView(R.layout.tela_mes);
        Calendario c = app.findViewById(R.id.calendario);
        ListaMov lm = app.findViewById(R.id.lista);
        Pizza p = app.findViewById(R.id.pizza);
        TextView tw = app.findViewById(R.id.total);
        tw.setText(dados.getMes(mes).getTotal);
        p.setMes(dados.getMes(mes));
        lm.setMes(dados.getMes(mes));
        c.setMes(dados.getMes(mes),2020,mes);
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
}