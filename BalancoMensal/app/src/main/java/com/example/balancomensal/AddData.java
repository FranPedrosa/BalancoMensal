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
    int pos = -1;
    int mesAntigo = -1;
    String categoria;

    AppCompatActivity app;
    Dados db;//Novidade


    public AddData(AppCompatActivity app, Dados db) {
        this.app = app;
        this.db = db; // Novidade

        app.setContentView(R.layout.add_data); // Muda para a tela para adicionar movimentacoes

        // Inicia o spinner com suas categorias
        Spinner spCategoria = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        // Inicia os botoes e a check box
        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View fixa = app.findViewById(R.id.fixa);

        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        fixa.setOnClickListener(check);

        despesa(btn_desp); // Inicia movimentacao como uma despesa
        check(fixa); // Inicia movimentacao como nao fixa
    }

    public AddData(AppCompatActivity app, Dados db, Movimentacao mov, int pos, int nMes) {
        this.app = app;
        this.db = db; // Novidade

        app.setContentView(R.layout.add_data); // Muda para a tela para adicionar movimentacoes

        this.pos = pos;
        this.mesAntigo = nMes;

        EditText nome = this.app.findViewById(R.id.nome);
        EditText data = this.app.findViewById(R.id.data);
        EditText valor = this.app.findViewById(R.id.valor);
        CheckBox fixa = this.app.findViewById(R.id.fixa);
        Spinner sp = this.app.findViewById(R.id.categoria);

        nome.setText(mov.getNome());
        String dataCompleta = mov.getDiaMes()[0] + " " + mov.getDiaMes()[1];
        data.setText(dataCompleta);
        valor.setText(mov.getValor() + "");
        fixa.setChecked(true);
        check(fixa);

        View btn_rend = this.app.findViewById(R.id.btn_rend);
        View btn_desp = this.app.findViewById(R.id.btn_desp);
        View btn_save = this.app.findViewById(R.id.btn_save);

        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);

        if(mov.getValor() > 0) {
            despesa(btn_rend); // Inicia movimentacao como uma renda
        }
        else {
            despesa(btn_desp); // Inicia movimentacao como uma despesa
        }
    }

    public AddData(AppCompatActivity app, Dados db, int pos) {
        this.app = app;
        this.db = db;

        Fixa f = db.getFixas()[pos];

        app.setContentView(R.layout.add_data); // Muda para a tela para adicionar movimentacoes

        this.pos = pos;

        EditText nome = this.app.findViewById(R.id.nome);
        EditText data = this.app.findViewById(R.id.data);
        EditText valor = this.app.findViewById(R.id.valor);
        CheckBox fixa = this.app.findViewById(R.id.fixa);
        Spinner sp = this.app.findViewById(R.id.categoria);
        EditText dur = this.app.findViewById(R.id.duracao);

        nome.setText(f.getNome());
        String dataCompleta = String.format("%02d",f.getDiaMes()[0]+1);
        data.setText(dataCompleta);
        valor.setText(f.getValor() + "");
        fixa.setChecked(true);
        check(fixa);
        dur.setText(f.getDuracao() + "");

        View btn_rend = this.app.findViewById(R.id.btn_rend);
        View btn_desp = this.app.findViewById(R.id.btn_desp);
        View btn_save = this.app.findViewById(R.id.btn_save);

        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);

        if(f.getCategoria() == 0) {
            renda(btn_rend); // Inicia movimentacao como uma renda
        }
        else {
            despesa(btn_desp); // Inicia movimentacao como uma despesa
            sp.setSelection(f.getCategoria());
        }
    }

    // Chamadas de listeners de botoes
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
        Button btn_desp = app.findViewById(R.id.btn_desp); // Inicia o botao despesa
        Button btn_rend = app.findViewById(R.id.btn_rend); // Inicia o botao renda

        CheckBox cb = app.findViewById(R.id.fixa); // Inicia a checkbox

        // Inicia o spinner de categorias
        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setVisibility(View.VISIBLE); // Mostra o spinner de categorias

        desp = true; // Coloca o parametro despesa como verdadeiro

        btn_desp.setBackgroundResource(R.drawable.my_button_click); // Escurece o botao despesa
        btn_rend.setBackgroundResource(R.drawable.my_button); // Volta o botao renda ao normal


        cb.setText(R.string.desp_fixa); // Muda o texto da checkbox
    }

    public void renda(View v) {
        Button btn_desp = app.findViewById(R.id.btn_desp); // Inicia o botao despesa
        Button btn_rend = app.findViewById(R.id.btn_rend); // Inicia o botao renda

        CheckBox cb = app.findViewById(R.id.fixa); // Inicia a checkbox

        // Inicia o spinner de categorias
        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.nulo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setVisibility(View.INVISIBLE); // Mostra o spinner de categorias

        desp = false; // Coloca o parametro despesa como falso

        btn_desp.setBackgroundResource(R.drawable.my_button); // Volta o botao renda ao despesa
        btn_rend.setBackgroundResource(R.drawable.my_button_click); // Escurece o botao renda

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
        EditText edit_txt = app.findViewById(R.id.duracao); // Inicia EditText de duracao
        edit_txt.setVisibility(View.VISIBLE); // Deixa duracao visivel
    }

    public void naoFixa(View v) {
        fixa = false;
        EditText edit_txt = app.findViewById(R.id.duracao); // Inicia EditText de duracao
        edit_txt.setVisibility(View.GONE); // Deixa duracao invisivel
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
            int cat = categoria.getSelectedItemPosition();
            String val = valor.getText().toString();

            Context context = app.getApplicationContext();
            int duration = Toast.LENGTH_LONG;

            if(todosPreenchidos(nom, dat + "01", dur, categoria, val, fixa)) {
                /*==============================================================================
                Esse código é a gambiarra para pegar os valores.
                */
                int dia = Integer.parseInt(dat);
                int duraca = Integer.parseInt(dur);
                int numValor = Integer.parseInt(val);
                Fixa f = new Fixa(nom,dia-1,db.getMesAtual(), duraca, numValor,cat);
                if(pos == -1) {
                    db.addFixa(f);
                }
                else{
                    db.atualizarFixa(pos,f);
                }
                new TelaPrincipal(app,db);
                /*
                Fim da gambiarra.
                ================================================================================*/
            }
            else
                Toast.makeText(context, "Algum elemento não foi preenchido ou o mês está em formato inválido.", duration).show();
        }
        else {
            String nom = nome.getText().toString();
            String dat = data.getText().toString();
            String dur = duracao.getText().toString();
            String val = valor.getText().toString();
            int cat = categoria.getSelectedItemPosition(); // Eu mudei essa linha de getSelectedItem para getSel..ItemPosition.

            Context context = app.getApplicationContext();
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, nom+" "+dat+" "+val+" "+dur+" "+cat, duration).show();
            if(todosPreenchidos(nom, dat, dur, categoria, val, fixa)) {

                int DDMM = Integer.parseInt(dat);
                int numValor = Integer.parseInt(val);
                int dia = DDMM / 100;
                int mes = DDMM % 100;
                Movimentacao m = new Movimentacao(nom,dia-1,mes-1,2020,numValor,cat);
                if(pos == -1){
                    db.add(m);
                }
                else{
                    if(mes == mesAntigo){
                        db.add(m,pos);
                    }
                    else{
                        System.out.println("Oi tudo bem, eu estou mudando de Mês.");
                        db.remover(mesAntigo,pos);
                        db.add(m);
                    }
                }
                new TelaPrincipal(app,db);
            }
            else
                Toast.makeText(context, "Algum elemento não foi preenchido ou o mês está em formato inválido.", duration).show();
        }
    }

    private boolean todosPreenchidos(String nome, String data, String duracao, Spinner categoria, String valor, CheckBox fixa) {
        if(data.equals("")) {
            System.out.println("a string é: '" + data + "'");
            return false;
        }
        int DDMM = Integer.parseInt(data);
        if(DDMM/100 > 31 || DDMM/100 < 1) return false;
        else if(DDMM%100 > 12 || DDMM%100 < 1) return false;
        if(nome.equals("")) return false;
        else if(data.equals("")) return false;
        else if(valor.equals("")) return false;
        else if(duracao.equals("") && fixa.isChecked()) return false;
        else if(desp && categoria.getSelectedItemPosition() == 0)
            return false;
        else if(!desp && categoria.getSelectedItemPosition() == 1)
            return false;
        else return true;
    }
}