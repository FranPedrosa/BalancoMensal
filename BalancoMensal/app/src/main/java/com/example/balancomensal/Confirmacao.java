package com.example.balancomensal;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public abstract class Confirmacao{

    public Confirmacao(AppCompatActivity app, String msg, final String confirm, String cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setMessage(msg)
                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirm();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                }});
        builder.show();
    }

    abstract void confirm();
}
