package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ListaMov extends View implements View.OnTouchListener {

    private Mes mes;
    private Paint p;
    private int dia = -1;

    public ListaMov(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
        setOnTouchListener(this);
    }

    public ListaMov(Context context) {
        super(context);
        p = new Paint();
        setOnTouchListener(this);
    }

    public void setDia(int dia){
        this.dia = dia;
        invalidate();
    }

    public void setMes(Mes mes){
        this.dia = dia;
    }

    @Override
    protected void onDraw(Canvas canvas){
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        p.setColor(Color.WHITE);
        canvas.drawRect(0,800,w,h,p);
        p.setColor(Color.BLACK);

        int y = 0;
        Movimentacao[] m = mes.findMovData(dia);
        int i = 0;
        while(m[i] != null){
            System.out.println(m[i].getNome() + " " + m[i].getValor());
            p.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(m[i].getNome(),0,y,p);
            p.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(m[i].getValor()+"",w,y,p);
            y += 100;
            i++;
        }
        this.setMinimumHeight(y);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
