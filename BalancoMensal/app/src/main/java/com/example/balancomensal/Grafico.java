package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

public class Grafico extends View{

    public Grafico(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Grafico(Context context) {
        super(context);
        p = new Paint();
    }

    public void setDados(double[] dados,int mesAtual){
        meses = dados;
        this.mesAtual = mesAtual;
        for (double d : dados) {
           if(Math.abs(d) > max){
               max = Math.abs(d);
           }
        }
        invalidate();
    }

    private Paint p;
    private double[] meses;
    private String[] nomes = {"JAN","FEB","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};
    private int mesAtual;
    private double max = 5;

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        if(meses.length == 0){
            return;
        }

        float larg = w/meses.length;
        float zoom = (float) (h*0.4f / max);
        p.setColor(Color.BLACK);
        canvas.drawLine(0,h/2,w,h/2,p);
        p.setTextSize(30);

        for(int i = meses.length-1; i >= 0 ;i--){
            String valor = String.format("%.2f",meses[i]);
            if(meses[i] < 0){
                p.setColor(Color.BLACK);
                canvas.drawText(nomes[mesAtual],larg*(i+0.1f),(float)(h/2 - meses[i]*zoom)-35,p);
                p.setColor(Color.RED);
                canvas.drawText(valor,larg*(i+0.1f),(float)(h/2 - meses[i]*zoom)+35,p);
            }
            else{
                p.setColor(Color.BLACK);
                canvas.drawText(nomes[mesAtual],larg*(i+0.1f),(float)(h/2 - meses[i]*zoom)+35,p);
                p.setColor(Color.GREEN);
                canvas.drawText(valor,larg*(i+0.1f),(float)(h/2 - meses[i]*zoom)-5,p);
            }
            canvas.drawRect(larg*(i+0.1f),h/2,larg*(i+0.8f),(float)(h/2 - meses[i]*zoom),p);
        }
    }
}
