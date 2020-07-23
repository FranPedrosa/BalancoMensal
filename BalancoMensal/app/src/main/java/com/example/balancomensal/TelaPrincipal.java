package com.example.balancomensal;

import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class TelaPrincipal {

    AppCompatActivity app;
    Dados db;

    public TelaPrincipal(AppCompatActivity app, Dados d){

        this.app = app;
        this.db = d;

        //Salva os dados
        db.salvar(app);

        //Muda o layout para a tela principal.
        app.setContentView(R.layout.index);

        //Encontra os componentes
        Grafico g = app.findViewById(R.id.grafico);
        Pizza p = app.findViewById(R.id.pizza2);
        Spinner tempo = app.findViewById(R.id.tempo);
        View fixas_btn = app.findViewById(R.id.fixas);
        Meses btn_meses = app.findViewById(R.id.menu);

        //Configura as opções de tempo nos gráficos
        String[] opcoes = {"Ultimos 3 meses","Ultimo 6 meses","Ultimo ano",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(app,android.R.layout.simple_spinner_item,opcoes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempo.setAdapter(adapter);

        //Configura o grafico de barras e a pizza e as opções de tempo para mostrarem os ultimos 6 meses (padrão)
        g.setDados(db.getTotalMeses(6),db.getMesAtual());
        p.setDados(db,6);
        tempo.setSelection(1);

        //Confugra o menu de meses para a data atual.
        btn_meses.setMes(db.getMesAtual(),db.getAno());

        //Atribui uma função para o toque de cada componente.
        View btn_add = app.findViewById(R.id.btn_add);
        tempo.setOnItemSelectedListener(mudar);
        btn_add.setOnClickListener(adicionar);
        btn_meses.setOnTouchListener(fechado);
        fixas_btn.setOnClickListener(fixas);
    }

    //O botão (+) vai para a tela de adicionar.
    View.OnClickListener adicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddData(app,db);
        }
    };

    //O botão de fixas vai para tela de fixas.
    View.OnClickListener fixas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TelaFixas(app,db);
        }
    };

    //Toque no menu de meses fechado abre o menu.
    View.OnTouchListener fechado = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            if(x > menu.left && x < menu.right && y > menu.top && y < menu.bottom){
                menu.aberto = true;
                menu.setOnTouchListener(aberto);
                menu.invalidate();
            }
            return false;
        }
    };

    //Toque no menu de meses aberto recerrega a tela para o mês atual.
    //Ou fecha o menu.
    View.OnTouchListener aberto = new View.OnTouchListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            int i = menu.getMes(y);
            if(i < 12 && x < 600){
                new TelaMes(app,i,db);
            }
            else{
                menu.aberto = false;
                menu.invalidate();
                menu.setOnTouchListener(fechado);
            }
            return false;
        }
    };

    //A mudança de valor das opções de tempo mudam o tempo de cada gráfico (pizza e barras)
    AdapterView.OnItemSelectedListener mudar = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Grafico g = app.findViewById(R.id.grafico);
            Pizza p = app.findViewById(R.id.pizza2);
            int[] meses = {3,6,12};
            g.setDados(db.getTotalMeses(meses[position]),db.getMesAtual());
            p.setDados(db,meses[position]);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
}