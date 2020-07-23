package com.example.balancomensal;

import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//Classe responsável por trabalhar com o Layout fixas.xml
public class TelaFixas {

    AppCompatActivity app;
    Dados db;

    public TelaFixas(AppCompatActivity app, Dados dados) {
        this.app = app;
        this.db = dados;

        //Muda para o layout fixas.xml
        app.setContentView(R.layout.fixas);

        //A atribui à lista de fixas os dados vindos de db, e a função de toque.
        ListaFixa lf = app.findViewById(R.id.lista2);
        lf.setDados(db.getFixas());
        lf.setOnTouchListener(toque);

        //Atribui ao botão de retornar a função de voltar.
        Button retornar = app.findViewById(R.id.return_btn2);
        retornar.setOnClickListener(voltar);
    }

    //No toque lê o x e y do toque e chama a função tocar da propriar ListaFixa
    View.OnTouchListener toque = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        ListaFixa lf = (ListaFixa) v;
        lf.tocar(app,db,x,y);
        return false;
        }
    };

    //Volta para tela principal.
    View.OnClickListener voltar = new View.OnClickListener() {
        public void onClick(View v) {
            new TelaPrincipal(app, db);
        }
    };

}

