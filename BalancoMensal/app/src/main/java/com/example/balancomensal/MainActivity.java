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

    //Chama quando o aplicativo é aberto.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tenta abrir os dados salvos.
        db = Dados.abrir(this);

        //Se não encontrar cria dados vazios novos.
        if(db == null){
            LocalDate ld = LocalDate.now();
            db = new Dados(ld.getMonthValue()-1,ld.getYear());
        }

        //Abre a tela principal.
        new TelaPrincipal(this,db);
    }
}

