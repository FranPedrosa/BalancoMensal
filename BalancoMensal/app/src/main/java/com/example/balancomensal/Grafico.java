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

    private Paint p;
    private Double[] meses = {0.2,5.0,-0.3,3.2,0.2,5.0,-0.3,3.2,0.2,5.0,-0.3,3.2,0.2,5.0,-0.3,3.2};
    private final int[] cores= {Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.CYAN,Color.GRAY};

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
        p.setColor(Color.BLACK);
        canvas.drawLine(0,h/2,w,h/2,p);

        for(int i = 0; i < meses.length;i++){
            if(meses[i] < 0){
                p.setColor(Color.RED);
            }
            else{
                p.setColor(Color.GREEN);
            }
            canvas.drawRect(larg*(i+0.1f),h/2,larg*(i+0.8f),(float)(h/2 - meses[i]*30),p);
        }

        /*float left = (float)(w*0.75);
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
        }*/
    }
}
