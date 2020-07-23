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

//Classe responsável por trabalhar com o Layout add_data.xml

//Podemos chegar nela pelo botão (+) da tela princial,
//Ou pelos botões editar da lista de fixas e lista de movimentações.


public class AddData{

    boolean desp; //Se é despesa ou renda
    boolean fixa; //Se é fixa ou simples

    int pos = -1; //A posição para rescrever a fixa/movimentação se for edição.
    int mesAntigo = -1; //Na edição de movimentação salva o mês antigo, se mudar precisa de tratamento especial.

    AppCompatActivity app; //Esse objeto e necessário para acessar os xml e similares.
    Dados db; //Os dados do aplicativos.

    //Construtor para inserção.
    public AddData(AppCompatActivity app, Dados db) {
        this.app = app;
        this.db = db;

        //Muda a tela para add_data.xml
        app.setContentView(R.layout.add_data);

        // Inicia o seletor de categorias com seus valores.
        Spinner spCategoria = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        // Inicia os botoes e a check box
        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View fixa = app.findViewById(R.id.fixa);
        View btn_cancel = this.app.findViewById(R.id.btn_cancel);

        //Associa os botões com suas respctivas funções.
        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        fixa.setOnClickListener(check);
        btn_cancel.setOnClickListener(cancelar);

        despesa(); // Inicia movimentação como uma despesa
        check(); // Inicia movimentação como não fixa
    }

    //Inicia o construtor para editar um movimentação.
    public AddData(AppCompatActivity app, Dados db, Movimentacao mov, int pos, int nMes) {
        this.app = app;
        this.db = db;
        this.pos = pos;
        this.mesAntigo = nMes;

        // Muda para a tela para add_data.xml
        app.setContentView(R.layout.add_data);

        //Inicia os componentes no texto.
        EditText nome = app.findViewById(R.id.nome);
        EditText data = app.findViewById(R.id.data);
        EditText valor = app.findViewById(R.id.valor);
        CheckBox fixa = app.findViewById(R.id.fixa);
        Spinner sp = app.findViewById(R.id.categoria);
        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View btn_cancel = app.findViewById(R.id.btn_cancel);

        //Apaga o checkbox, porque não podemos mudar de fixa para simples ou vice versa na ediçaão.
        fixa.setVisibility(View.GONE);

        //Atribui os valores antigos no nos componentes.
        nome.setText(mov.getNome());
        String dataCompleta = String.format("%02d%02d",mov.getDiaMes()[0]+1,mov.getDiaMes()[1]+1);
        data.setText(dataCompleta);
        valor.setText(mov.getValor() + "");
        fixa.setChecked(false);
        check();

        //Atribui as funções de cada botão.
        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        btn_cancel.setOnClickListener(cancelar);


        //Verifica se é renda ou despesa. (categoria zero é renda, as outras são despesa)
        if(mov.getCategoria() == 0) {
            renda(); // Inicia movimentacao como uma renda
        }
        else {
            despesa(); // Inicia movimentacao como uma despesa
            sp.setSelection(mov.getCategoria());
        }
    }

    //Constrututor para edição de fixas.
    public AddData(AppCompatActivity app, Dados db, int pos) {
        this.app = app;
        this.db = db;
        this.pos = pos;

        Fixa f = db.getFixas()[pos]; //Encontra a fixa  ser editada.
        app.setContentView(R.layout.add_data); // Muda para a tela para add_data.xml

        //Inicia os componentes
        EditText nome = app.findViewById(R.id.nome);
        EditText data = app.findViewById(R.id.data);
        EditText valor = app.findViewById(R.id.valor);
        CheckBox fixa = app.findViewById(R.id.fixa);
        Spinner sp = app.findViewById(R.id.categoria);
        EditText dur = app.findViewById(R.id.duracao);
        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View btn_cancel = app.findViewById(R.id.btn_cancel);

        //Apaga o checkbox, porque não podemos mudar de fixa para simples ou vice versa na ediçaão.
        fixa.setVisibility(View.GONE);

        //Passa os valores da Fixa para os componentes.
        nome.setText(f.getNome());
        String dataCompleta = String.format("%02d",f.getDiaMes()[0]+1);
        data.setText(dataCompleta);
        valor.setText(f.getValor() + "");
        fixa.setChecked(true);
        check();
        dur.setText(f.getDuracao() + "");

        //Atrubui as funções para cada botão.
        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        btn_cancel.setOnClickListener(cancelar);

        //Verifica se é renda ou despesa. (categoria zero é renda, as outras são despesa)
        if(f.getCategoria() == 0) {
            renda(); // Inicia movimentacao como uma renda
        }
        else {
            despesa(); // Inicia movimentacao como uma despesa
            sp.setSelection(f.getCategoria());
        }
    }

    // Cria um onClicklistener para cada função.
    View.OnClickListener despesa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            despesa();
        }
    };
    View.OnClickListener renda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            renda();
        }
    };
    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            check();
        }
    };
    View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            save();
        }
    };
    View.OnClickListener cancelar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelar();
        }
    };

    public void despesa() {
        Button btn_desp = app.findViewById(R.id.btn_desp); // Inicia o botao despesa
        Button btn_rend = app.findViewById(R.id.btn_rend); // Inicia o botao renda
        CheckBox cb = app.findViewById(R.id.fixa); // Inicia a checkbox

        // Inicia o spinner de categorias com as categorias
        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        // Mostra o spinner de categorias
        s.setVisibility(View.VISIBLE);
        desp = true; // Coloca o parametro despesa como verdadeiro
        btn_desp.setBackgroundResource(R.drawable.my_button_click); // Escurece o botao despesa
        btn_rend.setBackgroundResource(R.drawable.my_button); // Volta o botao renda ao normal
        cb.setText(R.string.desp_fixa); // Muda o texto da checkbox
    }

    public void renda() {
        Button btn_desp = app.findViewById(R.id.btn_desp); // Inicia o botao despesa
        Button btn_rend = app.findViewById(R.id.btn_rend); // Inicia o botao renda
        CheckBox cb = app.findViewById(R.id.fixa); // Inicia a checkbox

        // Inicia o spinner de categorias vazio
        Spinner s = app.findViewById(R.id.categoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.nulo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setVisibility(View.INVISIBLE); //Não Mostra o spinner de categorias
        desp = false; // Coloca o parametro despesa como falso
        btn_desp.setBackgroundResource(R.drawable.my_button); // Volta o botao renda ao despesa
        btn_rend.setBackgroundResource(R.drawable.my_button_click); // Escurece o botao renda
        cb.setText(R.string.rend_fixa);//Muda o texto da check box
    }

    public void check() {
        //Verifica o estado da check box e chama fixa ou naofixa
        CheckBox cb = app.findViewById(R.id.fixa);
        if(cb.isChecked())
            fixa();
        else
            naoFixa();
    }

    public void fixa() {
        fixa = true;
        EditText edit_txt = app.findViewById(R.id.duracao); // Inicia EditText de duracao
        EditText dt = app.findViewById(R.id.data); // Inicia EditText da data
        edit_txt.setVisibility(View.VISIBLE); // Deixa duracao visivel
        dt.setHint("DD");//Muda a dica para DD porque fixa não tem mês.
    }

    public void naoFixa() {
        fixa = false;
        EditText edit_txt = app.findViewById(R.id.duracao); // Inicia EditText de duracao
        EditText dt = app.findViewById(R.id.data); //Inicia o Edit Text da data
        edit_txt.setVisibility(View.GONE); // Deixa duracao invisivel
        dt.setHint("DDMM"); //Muda a dica para DDMM
    }

    public void save() {
        //Acha todos os componentes.
        EditText nome = app.findViewById(R.id.nome);
        EditText data = app.findViewById(R.id.data);
        EditText valor = app.findViewById(R.id.valor);
        CheckBox fixa = app.findViewById(R.id.fixa);
        EditText duracao = app.findViewById(R.id.duracao);
        Spinner categoria = app.findViewById(R.id.categoria);

        //Pega todos os valores.
        String nom = nome.getText().toString();
        String dat = data.getText().toString();
        String dur = duracao.getText().toString();
        int cat = categoria.getSelectedItemPosition();
        String val = valor.getText().toString();

        //Prepara para mostrar recados na tela.
        Context context = app.getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        //Se for fixa
        if(fixa.isChecked()) {

            //Verifica se todos estão preenchidos.
            if(todosPreenchidos(nom, dat + "01", dur, categoria, val, fixa)) {

                //Pega os valores numéricos
                int dia = Integer.parseInt(dat);
                int duraca = Integer.parseInt(dur);
                int numValor = Integer.parseInt(val);

                //Cria a nova fixa.
                Fixa f = new Fixa(nom,dia-1,db.getMesAtual(), duraca, numValor,cat);

                //Se for uma inserção, faz a inserção simples.
                if(pos == -1) {
                    db.addFixa(f);
                }
                //Se for uma atualização, faz a inserção na posição pos.
                else{
                    db.atualizarFixa(pos,f);
                }
                //Volta para tela principal
                new TelaPrincipal(app,db);
            }
            //Se algum campo não foi preenchido escreve a mensagem.
            else
                Toast.makeText(context, "Algum elemento não foi preenchido ou o mês está em formato inválido.", duration).show();
        }

        //Se for uma movimentação simples (não fixa)
        else {

            if(todosPreenchidos(nom, dat, dur, categoria, val, fixa)) {

                //Pega os valores numéricos.
                int DDMM = Integer.parseInt(dat);
                int numValor = Integer.parseInt(val);
                int dia = DDMM / 100;
                int mes = DDMM % 100;

                //Cria a movimentação
                Movimentacao m = new Movimentacao(nom,dia-1,mes-1,db.getAno(),numValor,cat);

                //Se for uma inserção, faz a inserção.
                if(pos == -1){
                    db.add(m);
                }
                else{
                    //Se não tiver mudado o mês só adiciona novamente na posição.
                    if(mes == mesAntigo){
                        db.add(m,pos);
                    }
                    //Se mudar o mês remove no mês antigo e adiciona no novo.
                    else{
                        db.remover(mesAntigo,pos);
                        db.add(m);
                    }
                }

                //Volta para tela principal
                new TelaPrincipal(app,db);
            }

            //Se algum campo for preenchido errado, mostra a mensagem na tela.
            else {
                Toast.makeText(context, "Algum elemento não foi preenchido ou o mês está em formato inválido.", duration).show();
            }
        }
    }

    //Volta para tela principal.
    public void cancelar() {
        new TelaPrincipal(app, db);
    }

    //Verifica se todos os itens foram preenchidos.
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