package com.example.balancomensal;


import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//Essa classe é uma simplificação do AlertDialog.
//Ela escreve com pedido de confirmação na tela.

//Para usar basta chamar new Confirmacao(textoGrande, textoA, textoB), e sobreescrever a função confirm.

/*Ela vai parecer mais ou menos isso:

        +-------------------+
        |Texto Grande...    |
        |                   |
        +---------+---------+
        |  TextoA |  TextoB |
        +---------+---------+

    Se você apertar textoA vai chamar a função confirm.
 */

public abstract class Confirmacao{

    public Confirmacao(AppCompatActivity app, String msg, String confirm, String cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(app);
        builder.setMessage(msg)
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirm();
                    }
                })
                .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                }});
        builder.show();
    }

    abstract void confirm();
}
