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

import androidx.appcompat.app.AppCompatActivity;

public class ListaMov extends View{

    private Mes mes;
    private int nMes;
    private Paint p;
    private int dia = -1;
    int[] id;
    Movimentacao[] ms;

    public ListaMov(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
        setWillNotDraw(false);
    }

    public ListaMov(Context context) {
        super(context);
        p = new Paint();
        setWillNotDraw(false);
    }

    public void setDia(int dia){
        this.dia = dia;
        requestLayout();
        invalidate();
    }

    public void setMes(Mes mes,int nMes){
        this.mes = mes;
        this.nMes = nMes;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int i = 0;
        id = mes.findIdData(dia);
        ms = mes.findMovData(dia);
        while(ms[i] != null){
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
            if(m[i].getCategoria() == 0){
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

    public void Toque(int x, int y, AppCompatActivity app, final Dados db) {
        int w = getWidth();
        final int i = (y-120)/50;
        if(i >= 0){
            if(x > 5*w/6){
                new Confirmacao(app,"Você tem certeza que quer apagar a movimentação?","Apagar","Cancelar") {
                    @Override
                    void confirm() {
                        db.remover(nMes,id[i]);
                        invalidate();
                    }
                };
            }
            else if(x > 2*w/3) {
                new AddData(app, db, ms[i], id[i], nMes);
            }
        }
    }
}
