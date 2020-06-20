package com.example.balancomensal;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.teste_pizza);
        Grafico g = findViewById(R.id.pizza);
        double[] teste = {500,-302,25.6,488,-325,-123,10};
        g.setDados(teste);
        */

        setContentView(R.layout.add_data);
        Spinner spinner = findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        despesa(findViewById(R.id.add_data));
        checkFixa(findViewById(R.id.add_data));
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

        btn_desp.setBackgroundResource(R.drawable.my_button_click);
        btn_rend.setBackgroundResource(R.drawable.my_button);
        s.setVisibility(View.VISIBLE);

        CheckBox cb = findViewById(R.id.fixa);
        cb.setText(R.string.desp_fixa);
    }

    public void renda(View v) {
        Button btn_desp = findViewById(R.id.btn_desp);
        Button btn_rend = findViewById(R.id.btn_rend);
        Spinner s = findViewById(R.id.category);

        btn_desp.setBackgroundResource(R.drawable.my_button);
        btn_rend.setBackgroundResource(R.drawable.my_button_click);
        s.setVisibility(View.INVISIBLE);

        CheckBox cb = findViewById(R.id.fixa);
        cb.setText(R.string.rend_fixa);
    }

    public void checkFixa(View v) {
        CheckBox cb = findViewById(R.id.fixa);
        if(cb.isChecked())
            fixa(findViewById(R.id.add_data));
        else
            naoFixa(findViewById(R.id.add_data));
    }

    public void fixa(View v) {
        EditText edit_txt = findViewById(R.id.duracao);
        edit_txt.setVisibility(View.VISIBLE);
    }

    public void naoFixa(View v) {
        EditText edit_txt = findViewById(R.id.duracao);
        edit_txt.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
