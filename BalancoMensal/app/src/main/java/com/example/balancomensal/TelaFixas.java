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

public class TelaFixas {

    AppCompatActivity app;
    Dados db;

    public TelaFixas(AppCompatActivity app, Dados dados) {
        this.app = app;
        this.db = dados;
        app.setContentView(R.layout.fixas);
        ListaFixa lf = app.findViewById(R.id.lista2);
        lf.setDados(db.getFixas());
        lf.setOnTouchListener(toque);
        Button retornar = app.findViewById(R.id.return_btn2);
        retornar.setOnClickListener(voltar);
    }

    View.OnTouchListener toque = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            int x = (int)event.getX();
            int y = (int)event.getY();
            int w = v.getWidth();
            final int i = (y-120)/50;
            if( i >= 0){
                if(x > 5*w/6){
                    new Confirmacao(app,"Você tem certeza que quer apagar a movimentação fixa?","Apagar","Cancelar") {
                        @Override
                        void confirm() {
                            db.removerFixa(i);
                            v.invalidate();
                        }
                    };
                } else if(x > 2*w/3) {
                    new AddData(app, db, i);
                }
            }
            return false;
        }
    };

   View.OnClickListener voltar = new View.OnClickListener() {
        public void onClick(View v) {
            new TelaPrincipal(app, db);
        }
    };

}
