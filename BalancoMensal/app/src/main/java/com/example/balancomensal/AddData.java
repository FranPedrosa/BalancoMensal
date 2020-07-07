package com.example.balancomensal;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddData implements AdapterView.OnItemSelectedListener {

    boolean desp;
    String nome;
    String data;
    String valor;
    boolean fixa;
    String categoria;

    AppCompatActivity app;
    Dados db;//Novidade

    public AddData(AppCompatActivity app,Dados db) {
        this.app = app;
        this.db = db;//Novidade

        app.setContentView(R.layout.add_data);

        Spinner spCategoria = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View fixa = app.findViewById(R.id.fixa);

        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        fixa.setOnClickListener(check);

        despesa(btn_desp);
        check(fixa);
    }

    View.OnClickListener despesa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            despesa(v);
        }
    };

    View.OnClickListener renda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            renda(v);
        }
    };

    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            check(v);
        }
    };

    View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            save(v);
        }
    };

    public void despesa(View v) {
        Button btn_desp = app.findViewById(R.id.btn_desp);
        Button btn_rend = app.findViewById(R.id.btn_rend);

        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        //s.setVisibility(View.VISIBLE);

        desp = true;

        btn_desp.setBackgroundResource(R.drawable.my_button_click);
        btn_rend.setBackgroundResource(R.drawable.my_button);


        CheckBox cb = app.findViewById(R.id.fixa);
        cb.setText(R.string.desp_fixa);
    }

    public void renda(View v) {
        Button btn_desp = app.findViewById(R.id.btn_desp);
        Button btn_rend = app.findViewById(R.id.btn_rend);

        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.nulo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        //s.setVisibility(View.INVISIBLE);

        desp = false;

        btn_desp.setBackgroundResource(R.drawable.my_button);
        btn_rend.setBackgroundResource(R.drawable.my_button_click);

        CheckBox cb = app.findViewById(R.id.fixa);
        cb.setText(R.string.rend_fixa);
    }

    public void check(View v) {
        CheckBox cb = app.findViewById(R.id.fixa);
        if(cb.isChecked())
            fixa(app.findViewById(R.id.add_data));
        else
            naoFixa(app.findViewById(R.id.add_data));
    }

    public void fixa(View v) {
        fixa = true;
        EditText edit_txt = app.findViewById(R.id.duracao);
        edit_txt.setVisibility(View.VISIBLE);
    }

    public void naoFixa(View v) {
        fixa = false;
        EditText edit_txt = app.findViewById(R.id.duracao);
        edit_txt.setVisibility(View.GONE);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void save(View v) {
        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        EditText nome = app.findViewById(R.id.nome);
        EditText data = app.findViewById(R.id.data);
        EditText valor = app.findViewById(R.id.valor);
        CheckBox fixa = app.findViewById(R.id.fixa);
        EditText duracao = app.findViewById(R.id.duracao);
        Spinner categoria = app.findViewById(R.id.categoria);

        if(fixa.isChecked()) {
            String nom = nome.getText().toString();
            String dat = data.getText().toString();
            String dur = duracao.getText().toString();
            String cat = categoria.getSelectedItem().toString();
            String val = valor.getText().toString();

            Context context = app.getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            if(todosPreenchidos(nom, dat, dur, categoria, val, fixa)) {
                Toast.makeText(context, nom+" "+dat+" "+val+" "+dur+" "+cat, duration).show();
                app.setContentView(R.layout.index);
            }
            else
                Toast.makeText(context, "Algum elemento não foi preenchido", duration).show();

        }
        else {
            String nom = nome.getText().toString();
            String dat = data.getText().toString();
            String dur = duracao.getText().toString();
            String val = valor.getText().toString();
            int cat = categoria.getSelectedItemPosition();//Eu mudei essa linha de getSelectedItem para getSel..ItemPosition.

            Context context = app.getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, nom+" "+dat+" "+val+" "+dur+" "+cat, duration).show();
            if(todosPreenchidos(nom, dat, dur, categoria, val, fixa)) {
                /*==============================================================================
                Esse código é a gambiarra para pegar os valores.
                 */
                int DDMM = Integer.parseInt(dat);
                int numValor = Integer.parseInt(val);
                int dia = DDMM / 100;
                int mes = DDMM % 100;
                Movimentacao m = new Movimentacao(nom,dia-1,mes-1,2020,numValor,cat);
                db.add(m);
                new TelaPrincipal(app,db);
                /*
                Fim da gambiarra.
                ================================================================================*/
                Toast.makeText(context, nom+" "+dat+" "+dur+" "+cat, duration).show();
            }
            else
                Toast.makeText(context, "Algum elemento não foi preenchido", duration).show();
        }
    }

    private boolean todosPreenchidos(String nome, String data, String duracao, Spinner categoria, String valor, CheckBox fixa) {
        if(nome == "") return false;
        else if(data == "") return false;
        else if(valor == "") return false;
        else if(duracao == "" && fixa.isChecked()) return false;
        else if(categoria.getSelectedItemPosition() == 0) return false;//Mudei de 1 para 0, n sei se ta funcionando.
        else return true;
    }
}