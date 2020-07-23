package com.example.balancomensal;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

//Um menu para viajar entre os meses.
//Tem duas posições fechado e aberto.

//Fechado ele é um circulo escrito meses no canto inferior direito.
//Aberto ele é um é uma lista com cada mês no canto superio esquerdo.

public class Meses extends View {

    private int primeiroMes;//O número do mês que fica em cima na lista.
    private int ano;//O ano atual
    private Paint p;
    boolean aberto; //O estado aberto ou fechado.

    //As posições de no estado fechado para calcular o toque.
    public int top;
    public int right;
    public int bottom;
    public int left;

    //O nome dos meses.
    private String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

    //Construtores padrões de view
    public Meses(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }
    public Meses(Context context) {
        super(context);
        p = new Paint();
    }

    //Configura o mês e ano atuais, para ser o ultimo da lista.
    public void setMes(int mes, int ano){
        this.primeiroMes = (mes+1) % 12;
        this.ano = ano;
    }

    @Override
    protected void onDraw(Canvas canvas){

        //Chama a função correspondente conforme o estado. Aberto ou fechado.
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        if(aberto){
            aberto(canvas,w,h);
        }
        else{
            fechado(canvas,w,h);
        }
    }

    //Desenha o menu aberto.
    private void aberto(Canvas canvas, int w, int h) {
        //Configura tamanho  e alinhamento do texto.
        p.setTextSize(60);
        p.setTextAlign(Paint.Align.LEFT);

        //Muda o ano para o ano passado( o do primeiro mês).
        ano = ano -1;
        for( int j = 0; j < 12; j++){

            //i recebe o número do mês j
            int i = (primeiroMes + j) % 12;

            //Se for janeiro o ano incrementa para o ano atual.
            if(i == 0){
                ano++;
            }

            //Faz o fundo alternando tons de cinza claro.
            if(i % 2 == 0){
                p.setColor(0xfff0f0f0);
            }
            else{
                p.setColor(0xfff8f8f8);
            }
            canvas.drawRect(0,j*80,600,j*80 + 80,p);

            //Escreve o nome do mês e o ano.
            p.setColor(0xff101010);
            canvas.drawText(meses[i] + " " + ano,60,j*80 + 70,p);
        }
    }

    //Desenha o menu fechado.
    private void fechado(Canvas canvas, int w, int h) {

        //Descobre quanto são 32 dps em pixels para alinhar com o botão (+)
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int px = Math.round(32 * (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        //Calcula a posição.
        left = w-3*px;
        top = (int)(h-(5.5)*px);
        right = w-px;
        bottom = (int)(h-(3.5)*px);

        //Desenha o meio do circulo.
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xff4000DF);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left,top,right,bottom,p);
        }

        //Desenha a borda do círculo
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        p.setColor(0xff3800D8);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left,top,right,bottom,p);
        }

        //Escreve meses no meio do botão.
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xfff0f0f0);
        p.setTextSize(30);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Meses",(right+left)/2,(top+bottom)/2+15,p);
    }

    //Retorna o mês baseado na posição y de um toque.
    public int getMes(int y){
        return (y/80 - primeiroMes +14) % 12;
    }


}
