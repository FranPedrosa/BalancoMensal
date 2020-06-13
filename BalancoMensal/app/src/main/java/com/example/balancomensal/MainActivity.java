package com.example.balancomensal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_pizza);
        Grafico g = findViewById(R.id.pizza);
    }
    /*
    public void save(View v) {
        TextView tv2 = findViewById(R.id.textView2);
        Dados db = new Dados();
        db.setTexto((String) tv2.getText());

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

    public void open(View v) {

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
    }
    */

    public void despesa(View v) {
        Button btn_desp = findViewById(R.id.btn_desp);
        Button btn_rend = findViewById(R.id.btn_rend);
        Spinner s = findViewById(R.id.category);


        btn_desp.setBackgroundColor(Color.parseColor("#00FF00"));
        btn_rend.setBackgroundColor(Color.parseColor("#FFFFFF"));
        s.setVisibility(View.VISIBLE);
    }

    public void renda(View v) {
        Button btn_desp = findViewById(R.id.btn_desp);
        Button btn_rend = findViewById(R.id.btn_rend);
        Spinner s = findViewById(R.id.category);

        btn_desp.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btn_rend.setBackgroundColor(Color.parseColor("#00FF00"));
        s.setVisibility(View.GONE);
    }
}
