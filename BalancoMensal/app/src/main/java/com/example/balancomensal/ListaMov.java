package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ListaMov extends View implements View.OnTouchListener {

    private Mes mes;
    private Paint p;
    private int altura;
    private int dia = -1;

    public ListaMov(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
        setOnTouchListener(this);
        setWillNotDraw(false);
    }

    public ListaMov(Context context) {
        super(context);
        p = new Paint();
        setOnTouchListener(this);
        setWillNotDraw(false);
    }

    public void setDia(int dia){
        this.dia = dia;
        requestLayout();
        invalidate();
    }

    public void setMes(Mes mes){
        this.mes = mes;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Movimentacao[] m = mes.findMovData(dia);
        int i = 0;
        while(m[i] != null){
            i++;
        }
        setMeasuredDimension(widthMeasureSpec,i*50 + 120);
    }

    @Override
    protected void onDraw(Canvas canvas){

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);
        p.setColor(Color.BLACK);


        int y = 120;
        Movimentacao[] m = mes.findMovData(dia);
        int i = 0;
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(60);
        if(dia != -1) {
            canvas.drawText("Dia " + (dia + 1), w / 2, 80, p);
        }
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.LEFT);

        while(m[i] != null){
            p.setColor(Color.BLACK);
            canvas.drawText(m[i].getNome(),60,y+40,p);
            if(m[i].getValor() >= 0){
                p.setColor(Color.GREEN);
            }
            else{
                p.setColor(Color.RED);
            }
            canvas.drawText("R$" + Math.abs(m[i].getValor()),w/3,y+40,p);
            p.setColor(Color.GREEN);
            canvas.drawText("Editar",2*w/3,y+40,p);
            p.setColor(Color.RED);
            canvas.drawText("Apagar",5*w/6,y+40,p);
            y += 50;
            i++;
        }
        this.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,y));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int w = getWidth();
        int h = getHeight();
        int i = (y-120)/50;
        if(x > 5*w/6){
            if(i > 0){
                //TelaApagar
            }
        }else if(x > 5*w/6){
            //Tela editar
        }
        return false;
    }
}
