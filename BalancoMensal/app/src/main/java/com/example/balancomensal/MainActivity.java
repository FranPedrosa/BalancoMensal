package com.example.balancomensal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Dados db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //db = Dados.abrir();
/*
        setContentView(R.layout.teste_pizza);
        Grafico g = findViewById(R.id.pizza);
        double[] teste = {500,-302,25.6,488,-325,-123,10};
        g.setDados(teste);
*/
        setContentView(R.layout.index);

        View btn_add = findViewById(R.id.btn_add);
    }

    View.OnClickListener adicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adicionar(v);
        }
    };


    public void adicionar(View v) {
        Button btn_add = findViewById(R.id.btn_add);
        AddData addData = new AddData(this);
    }
/*

    public void save(View v) {
        TextView tv2 = findViewById(R.id.textView2);
        Dados db = new Dados();
        db.setTexto((String) tv2.getText());

=======
    public void salvar(Dados db) {
>>>>>>> 967d06a24a29e0afdcd483e11101f45826a7c13c
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
