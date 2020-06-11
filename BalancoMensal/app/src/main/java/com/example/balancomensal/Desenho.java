package com.example.balancomensal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

public class Desenho extends View {

    public Desenho(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }

    public Desenho(Context context) {
        super(context);
        p = new Paint();
    }

    Paint p;
    float[] val = {0.2f,0.3f,0.1f,0.4f};
    int[] arco_iris = {Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE};

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p.setColor(Color.WHITE);
            canvas.drawRect(0,0,w,h,p);
            pizza(canvas,w*0.1f,h*0.1f,w*0.8f,h*0.8f,val,arco_iris);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pizza(Canvas canvas, float x, float y, float w, float h, float[] valores, int[] cores){
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
