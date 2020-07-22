package com.example.balancomensal;
import android.content.Context;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TelaMes {

    AppCompatActivity app;
    Dados db;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        lm.setMes(dados.getMes(mes), mes);
        int ano = mes > db.getMesAtual() ? db.getAno() - 1 : db.getAno();
        c.setMes(dados.getMes(mes), ano, mes);
        c.setOnTouchListener(toque);

        lm.setOnTouchListener(toqueLis);

        Meses menu = app.findViewById(R.id.menu2);
        menu.setMes(dados.getMesAtual(), dados.getAno());
        menu.setOnTouchListener(fechado);
        Button retornar = app.findViewById(R.id.return_btn);
        retornar.setOnClickListener(voltar);
    }

    View.OnTouchListener toque = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Calendario c = app.findViewById(R.id.calendario);
            ListaMov lm = app.findViewById(R.id.lista);
            int dia = c.getDia((int) event.getX(), (int) event.getY());
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

            if(x > menu.left && x < menu.right && y > menu.top && y < menu.bottom) {
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
            int x = (int) event.getX();
            int y = (int) event.getY();
            int i = menu.getMes(y);

            if (i < 12 && x < 500) {
                new TelaMes(app, i, db);
                return true;
            } else {
                menu.aberto = false;
                menu.invalidate();
                menu.setOnTouchListener(fechado);
            }

            return false;
        }
    };

    View.OnClickListener voltar = new View.OnClickListener() {
        public void onClick(View v) {
            new TelaPrincipal(app, db);
        }
    };

    View.OnTouchListener toqueLis = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            ListaMov lm = (ListaMov) v;
            lm.Toque(x, y, app, db);
            return false;
        }
    };
}

