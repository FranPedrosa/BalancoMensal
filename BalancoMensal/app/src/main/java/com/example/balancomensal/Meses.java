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

public class Meses extends View {

    private int primeiroMes;
    private int ultimoMes;
    private int ano;
    private Paint p;
    boolean aberto;
    public int top;
    public int right;
    public int bottom;
    public int left;

    private String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

    public Meses(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Meses(Context context) {
        super(context);
        p = new Paint();
    }

    public void setMes(int mes, int ano){
        this.primeiroMes = (mes+1) % 12;
        this.ultimoMes = mes;
        this.ano = ano;
    }

    @Override
    protected void onDraw(Canvas canvas){

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        if(aberto){
            aberto(canvas,w,h);
        }
        else{
            fechado(canvas,w,h);
        }
    }

    private void aberto(Canvas canvas, int w, int h) {
        int y = 0;
        p.setTextSize(60);
        p.setTextAlign(Paint.Align.LEFT);
        ano = ano -1;
        for( int j = 0; j < 12; j++){
            int i = (primeiroMes + j) % 12;
            if(i == 0){
                ano++;
            }
            System.out.println(i + " " + j);
            if(i % 2 == 0){
                p.setColor(0xfff0f0f0);
            }
            else{
                p.setColor(0xfff8f8f8);
            }
            canvas.drawRect(0,j*80,600,j*80 + 80,p);
            p.setColor(0xff101010);
            canvas.drawText(meses[i] + " " + ano,60,j*80 + 70,p);
        }
    }


    private void fechado(Canvas canvas, int w, int h) {

        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int px = Math.round(32 * (dm.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        left = w-3*px;
        top = (int)(h-(5.5)*px);
        right = w-px;
        bottom = (int)(h-(3.5)*px);

        p.setStyle(Paint.Style.FILL);
        p.setColor(0xff4000DF);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left,top,right,bottom,p);
        }
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(4);
        p.setColor(0xff3800D8);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(left,top,right,bottom,p);
        }
        p.setStyle(Paint.Style.FILL);
        p.setColor(0xfff0f0f0);
        int y = 0;
        p.setTextSize(30);
        p.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Meses",(right+left)/2,(top+bottom)/2+15,p);
    }

    public int getMes(int y){
        return (y/80 - primeiroMes +14) % 12;
    }


}
