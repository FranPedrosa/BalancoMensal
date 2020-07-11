package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

public class Meses extends View {

    private int primeiroMes;
    private int ultimoMes;
    private Paint p;
    boolean aberto;

    private String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

    public Meses(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Meses(Context context) {
        super(context);
        p = new Paint();
    }

    public void setMes(int com, int fim){
        this.primeiroMes = com;
        this.ultimoMes = fim;
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

    private void aberto(Canvas canvas, int w, int h){
        p.setColor(Color.BLACK);

        int y = 0;
        p.setTextSize(70);
        p.setTextAlign(Paint.Align.LEFT);

        for( int i = primeiroMes; i < ultimoMes; i++){
            if(i % 2 == 0){
                p.setColor(Color.LTGRAY);
            }
            else{
                p.setColor(Color.GRAY);
            }
            canvas.drawRect(0,i*80,500,i*80 + 80,p);
            p.setColor(Color.BLACK);
            canvas.drawText(meses[i],60,i*80 + 70,p);
        }
    }


    private void fechado(Canvas canvas, int w, int h){

        p.setStyle(Paint.Style.FILL);
        p.setColor(0xFFDDDDDD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(100,100,250,250,p);
        }
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawOval(100,100,250,250,p);
        }
        p.setStyle(Paint.Style.FILL);

        int y = 0;
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Meses",175,185,p);
    }

    
}
