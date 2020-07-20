package com.example.balancomensal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class Confirmacao{

    Dados dados;
    int mes;
    int pos;


    public Confirmacao(AppCompatActivity app, Dados db, int m, int p) {
        dados = db;
        mes = m;
        pos = p;

        AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setMessage("Tem certeza que quer apagar a movimentação?")
                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("VOCÊ DEVIA TER APAGADO!!!!");
                        dados.remover(mes, pos);
                        System.out.println("VOCÊ DEVIA TER APAGADO!!!!");
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int id) {
                    System.out.println("VOCÊ DEVIA TER APAGADO!!!!");
                }});
        builder.show();
    }
}
