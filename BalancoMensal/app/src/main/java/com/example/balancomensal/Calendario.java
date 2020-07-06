package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Calendario extends View{

    private Mes mes;
    int numeroDoMes = 3;
    private int dia1 = 2;
    private Paint p;
    private String[] nomeDosMeses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
    private int[] meses = {31,28,31,30,31,30,31,31,30,31,30,31};

    public Calendario(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Calendario(Context context) {
        super(context);
        p = new Paint();
    }

    public void setMes(Mes mes,int ano, int numeroDoMes){
        this.mes = mes;
        this.numeroDoMes = numeroDoMes;
        if(ano % 400 == 0 || (ano % 4 == 0 && ano % 100 != 0)){
            meses[1] = 29;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        int larg = w/8;
        int alt = 800/7;

        p.setColor(Color.BLUE);
        canvas.drawRect(0,0,w,alt,p);
        p.setTextSize(50);
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(nomeDosMeses[numeroDoMes],w/2,alt/2 + 25,p);
        p.setTextSize(30);
        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.LEFT);

        boolean[] resumo = mes.marcadorMes();

        for(int i = 0; i < meses[numeroDoMes];i++){
            int x = ((i + dia1) % 7)*larg + larg/2;
            int y = ((i + dia1) / 7)*alt + 3*alt/2;
            if(resumo[i]){
                p.setColor(0xFFAAAAFF);
                p.setStyle(Paint.Style.FILL);
                canvas.drawRect(x,y-30,x+larg,y-30+alt,p);
                p.setColor(Color.BLACK);
            }
            canvas.drawText((i+1)+"",x,y ,p);
        }
    }

    public int getDia(int x, int y) {
        int w = this.getWidth();
        int h = this.getHeight();

        int larg = w/8;
        int alt = h/7;

        //int y = ((i + dia1) / 7)*alt + 3*alt/2;
        int dia = (((y-3*alt/2)/alt)*7 + ((x-larg/2)/larg % 7)) - dia1;

        return dia;
    }
}
