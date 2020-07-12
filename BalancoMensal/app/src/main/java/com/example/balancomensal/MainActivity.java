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
            db = new Dados(ld.getMonthValue(),ld.getYear());
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


/*
    public void save(View v) {
        TextView tv2 = findViewById(R.id.textView2);
        Dados db = new Dados();
        db.setTexto((String) tv2.getText());

    public void salvar(Dados db) {
        try{
            FileOutputStream arq;
            //Creating stream and writing the object
            arq = openFileOutput("f.txt", Context.MODE_PRIVATE);

            ObjectOutputStream out = new ObjectOutputStream(arq);
            out.writeObject(db);
            out.flush();
            //closing the stream
            out.close();
            Toast toast = Toast.makeText(getApplicationContext(), "Sucesso" , Toast.LENGTH_SHORT);
            toast.show();
        }catch(Exception e){Toast toast = Toast.makeText(getApplicationContext(), "Sem sucesso" , Toast.LENGTH_SHORT);
            toast.show();}

    }


    /*public void abrir{

        try{
            //Creating stream to read the object
            ObjectInputStream in = new ObjectInputStream(openFileInput("f.txt"));
            Dados db=(Dados)in.readObject();
            //printing the data of the serialized object
            object.setText(db.texto);
            //closing the stream
            in.close();
            Toast toast = Toast.makeText(getApplicationContext(), "Sucesso" , Toast.LENGTH_SHORT);
            toast.show();
        }catch(Exception e){System.out.println(e); Toast toast = Toast.makeText(getApplicationContext(), "Sem sucesso" , Toast.LENGTH_SHORT);
            toast.show();}
    }*/

}
