package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

public class Pizza extends View{

    public Pizza(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Pizza(Context context) {
        super(context);
        p = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }

    public void setMes(Mes mes){
        double total = mes.getTotalGastos();
        if(total == 0){
            val = new double[1];
            val[0] = 1;
        }
        else {
            val = new double[6];
            for (int i = 0; i < 6; i++) {
                val[i] = mes.getValorCategoria(i + 1) / total;
            }
        }
        invalidate();
    }

    public void setDados(Dados dados, int m){
        val = dados.getMediaCategoriaMeses(m);
        double total = 0;
        for(double d : val){
            total += d;
        }
        if(total == 0) total = 1;
        for(int i = 0; i < val.length;i++){
            val[i] = val[i]/total;
        }
        invalidate();
    }

    private Paint p;
    private double[] val;
    private final int[] cores= { Color.RED,Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.CYAN,Color.GRAY,Color.MAGENTA};

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p.setColor(Color.WHITE);
            canvas.drawRect(0,0,w,h,p);
            pizza(canvas,w*0.25f,h*0.05f,w*0.70f,h*0.70f,val,cores);
        }

        float left = (float)(w*0.05);
        float lefttext = (float)(w*0.11);
        float right = (float)(w*0.09);
        p.setTextSize(30);
        for(int i = 1; i < 7; i++){
            float top = (float)(h*(0.70+(i-1)*0.05));
            float toptext = (float)(h*(0.73+(i-1)*0.05));
            float bottom = (float)(h*(0.74+i*0.05));
            p.setColor(cores[i]);
            canvas.drawRect(left,top,right,bottom,p);
            canvas.drawText(Mes.NOME_CATEGORIAS[i],lefttext,toptext,p);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pizza(Canvas canvas, float x, float y, float w, float h,double[] valores, int[] cores){
        float theta = 0;
        p.setStyle(Paint.Style.FILL);
        for(int i=0; i<valores.length; i++){
            float fatia = -(float)(360 * valores[i]);
            p.setColor(cores[i]);
            canvas.drawArc(x,y,x+w,y+h,theta,fatia,true,p);
            theta+=fatia;
        }
    }
}
