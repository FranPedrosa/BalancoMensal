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
    }

    private Paint p;
    private double[] val;
    private final int[] cores= {Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.CYAN,Color.GRAY};

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p.setColor(Color.WHITE);
            canvas.drawRect(0,0,w,h,p);
            pizza(canvas,w*0.05f,h*0.05f,w*0.75f,h*0.75f,val,cores);
        }

        float left = (float)(w*0.75);
        float lefttext = (float)(w*0.81);
        float right = (float)(w*0.79);
        p.setTextSize(30);
        for(int i = 0; i < 6; i++){
            float top = (float)(h*(0.70+i*0.05));
            float toptext = (float)(h*(0.73+i*0.05));
            float bottom = (float)(h*(0.74+i*0.05));
            p.setColor(cores[i]);
            canvas.drawRect(left,top,right,bottom,p);
            canvas.drawText(Mes.NOME_CATEGORIAS[i+1],lefttext,toptext,p);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pizza(Canvas canvas, float x, float y, float w, float h,double[] valores, int[] cores){
        float theta = 0;
        p.setStyle(Paint.Style.FILL);
        for(int i = 0; i < valores.length;i++){
            float fatia = -(float)(360 * valores[i]);
            p.setColor(cores[i]);
            canvas.drawArc(x,y,x+w,y+h,theta,fatia,true,p);
            theta+=fatia;
        }
    }

}
