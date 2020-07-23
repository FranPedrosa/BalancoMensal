package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

//Classe feita para desenhar um  calendário com as movimentações marcadas.
//Ela estende uma view então pode ser usada como um componente nos xmls.

public class Calendario extends View{

    private Mes mes; //O mes contendo todas movimentações daquele mês.
    int numeroDoMes = 0; //O número do mês.
    private int dia1; //O dia da semana que é/foi o dia primeiro.
    private Paint p; //Classe paint para configurar os desenhos.

    //Nome e duração de cada mês
    private String[] nomeDosMeses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
    private int[] meses = {31,28,31,30,31,30,31,31,30,31,30,31};

    //Construtores padrões de Views
    public Calendario(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }
    public Calendario(Context context) {
        super(context);
        p = new Paint();
    }

    //Set mês configura o mês a ser exebido no calendário.
    @RequiresApi(api = Build.VERSION_CODES.O)//Alugem requisito de sistema para usar LocalDate
    public void setMes(Mes mes, int ano, int numeroDoMes){
        this.mes = mes;
        this.numeroDoMes = numeroDoMes;

        //Descobre que dia da semana foi o dia 1
        LocalDate ld = LocalDate.of(ano, numeroDoMes+1, 1);
        this.dia1 = ld.getDayOfWeek().getValue();

        //Verifica se é bissexto, e dá 29 dias para fevereiro.
        if(ano % 400 == 0 || (ano % 4 == 0 && ano % 100 != 0)){
            meses[1] = 29;
        }
    }


    //Função que é chamada da para desenhar o calendário.
    @Override
    protected void onDraw(Canvas canvas){

        //Salva em w e h os tamanhos de tela
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //Fundo branco
        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        //Largura e altura de cada dia.
        int larg = w/8;
        int alt = 800/7;

        //A barra azul na parte de cima
        p.setColor(0xff6000FF);
        canvas.drawRect(0,0,w,alt,p);

        //O escrito do nome do mês
        p.setTextSize(50);
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(nomeDosMeses[numeroDoMes],w/2,alt/2 + 25,p);

        //Muda o tamanho cor e alinhamento do texto para escrever o numero dos dias.
        p.setTextSize(30);
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.LEFT);

        //Pega do mês quais dias tem movimentações.
        boolean[] resumo = mes.marcadorMes();

        //Para cada dia
        for(int i = 0; i < meses[numeroDoMes];i++){

            //Descobre a posição no calendário
            int x = ((i + dia1) % 7)*larg + larg/2;
            int y = ((i + dia1) / 7)*alt + 3*alt/2;

            //Pinta de azul se tiver movimentação
            if(resumo[i]){
                p.setColor(0xFFAAAAFF);
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(x,y-30,x+larg,y-30+alt,p);
                p.setColor(Color.BLACK);
            }

            //Escreve o número do dia.
            canvas.drawText((i+1)+"",x,y ,p);
        }
    }

    //Dado um ponto (x,y) descobre sobre qual dia do calendário ele fica.
    public int getDia(int x, int y) {
        //Tamanho do calendário
        int w = this.getWidth();
        int h = this.getHeight();

        //Tamanho de cada dia
        int larg = w/8;
        int alt = h/7;

        //Acha o dia
        int dia = (((y-3*alt/2)/alt)*7 + ((x-larg/2)/larg % 7)) - dia1;

        //Descobre se está dentros dos limites do mês.
        if(dia < 0 || dia >= meses[numeroDoMes]){
            dia = -1;
        }
        return dia;
    }

    //onMesure define a dimensão que a view quer, se tiver com tamanho wrap_content
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        //Por padrão a atura é 800 pixels.
        setMeasuredDimension(widthMeasureSpec,800);
    }
}
