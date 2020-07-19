package com.example.balancomensal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    Dados db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Dados.abrir(this);

        if(db == null){
            LocalDate ld = LocalDate.now();
            db = new Dados(ld.getMonthValue()-1,ld.getYear());
            System.out.println("Dados não encontrados criando novos.");
            //Deveria avisar o cliente que não achou o histórico de transações?
        }

        new TelaPrincipal(this,db);

        /* ==================================================================
        Agora temos uma mini main, tudo que tava aqui foi para Tela Principal.
        para mudar de tela basta chamar.
            new TelaPrincipal(app,db);
                    ou
            new AddData(app,db);
         ====================================================================*/
    }
}

