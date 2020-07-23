package com.example.balancomensal;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

//Classe responsável por trabalhar com o Layout tela_mes.xml
//Escreve todos os gráficos de um mês específico na tela.
public class TelaMes {

    AppCompatActivity app;
    Dados db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TelaMes(AppCompatActivity app, int mes, Dados dados) {
        this.app = app;
        this.db = dados;

        //Muda o layout para tela_mes.xml
        app.setContentView(R.layout.tela_mes);

        //Encontra os componentes.
        Calendario c = app.findViewById(R.id.calendario);
        ListaMov lm = app.findViewById(R.id.lista);
        Pizza p = app.findViewById(R.id.pizza);
        TextView tw = app.findViewById(R.id.total);
        Meses menu = app.findViewById(R.id.menu2);
        Button retornar = app.findViewById(R.id.return_btn);

        tw.setText(dados.getMes(mes).getTotal() + "");//Escreve o valor do balanço total na tela
        p.setMes(dados.getMes(mes));//Configura a piza para o mês representado.
        lm.setMes(dados.getMes(mes), mes); //Configura lista para o mês representado.
        int ano = mes > db.getMesAtual() ? db.getAno() - 1 : db.getAno(); //COnfigura o ano para o ano do mês reprensentado
        c.setMes(dados.getMes(mes), ano, mes);//Configura o calendário para o mês representado.
        menu.setMes(dados.getMesAtual(), dados.getAno());//Configura o menu dos meses para a data atual.

        //Atribui cada funçao para o toque dos componentes.
        c.setOnTouchListener(toque);
        lm.setOnTouchListener(toqueLis);
        menu.setOnTouchListener(fechado);
        retornar.setOnClickListener(voltar);
    }

    //Toque no calendário muda o dia da lista mês.
    View.OnTouchListener toque = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
        Calendario c = app.findViewById(R.id.calendario);
        ListaMov lm = app.findViewById(R.id.lista);
        int dia = c.getDia((int) event.getX(), (int) event.getY());
        lm.setDia(dia);
        return false;
        }
    };

    //Toque no menu de meses fechado abre o menu.
    View.OnTouchListener fechado = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Meses menu = (Meses) v;
            int x = (int)event.getX();
            int y = (int)event.getY();
            if(x > menu.left && x < menu.right && y > menu.top && y < menu.bottom) {
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
            int x = (int) event.getX();
            int y = (int) event.getY();
            int i = menu.getMes(y);
            if (i < 12 && x < 500) {
                new TelaMes(app, i, db);
                return true;
            } else {
                menu.aberto = false;
                menu.invalidate();
                menu.setOnTouchListener(fechado);
            }
            return false;
        }
    };

    //Toque no botão voltar volta para a tela principal.
    View.OnClickListener voltar = new View.OnClickListener() {
        public void onClick(View v) {
            new TelaPrincipal(app, db);
        }
    };

    //Toque na lista de movimentação chama a funcao toque da propria lista.
    View.OnTouchListener toqueLis = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            ListaMov lm = (ListaMov) v;
            lm.Toque(x, y, app, db);
            return false;
        }
    };
}

