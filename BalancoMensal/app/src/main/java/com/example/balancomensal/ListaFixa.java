package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ListaFixa extends View{

    private Fixa[] fixas;
    private int[] id = new int[20];
    private Paint p;
    private int dia = -1;

    public ListaFixa(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
        setWillNotDraw(false);
    }

    public ListaFixa(Context context) {
        super(context);
        p = new Paint();
        setWillNotDraw(false);
    }

    public void setDados(Fixa[] fixas){
        this.fixas = fixas;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = 0;
        for (int j = 0; j < 20;j++) {
            if (fixas[j] != null) {
                id[i]=j;
                i++;
            }
        }
        setMeasuredDimension(widthMeasureSpec,i*50 );
    }

    public void tocar(AppCompatActivity app, final Dados db, int x, int y) {
        int w = getWidth();
        final int i = y/50;
        if( i >= 0){
            if(x > 5*w/6){
                new Confirmacao(app,"Você tem certeza que quer apagar a movimentação fixa?","Apagar","Cancelar") {
                    @Override
                    void confirm() {
                        db.removerFixa(id[i]);
                        invalidate();
                    }
                };
            } else if(x > 2*w/3) {
                new AddData(app, db, id[i]);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){

        System.out.println("\n\n\n\n\n" + fixas.length + "\n\n\n\n\n");

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);
        p.setColor(Color.BLACK);


        int y = 0;
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.LEFT);
        for(int i = 0; i < 20; i++){
            if(fixas[i] == null){
                continue;
            }
            System.out.println("\n" + i + "\n");
            p.setColor(Color.BLACK);
            canvas.drawText(fixas[i].getNome(),60,y+40,p);
            if(fixas[i].getCategoria() == 0){
                p.setColor(Color.GREEN);
            }
            else{
                p.setColor(Color.RED);
            }
            canvas.drawText("R$" + Math.abs(fixas[i].getValor()),w/3,y+40,p);
            p.setColor(Color.GREEN);
            canvas.drawText("Editar",2*w/3,y+40,p);
            p.setColor(Color.RED);
            canvas.drawText("Apagar",5*w/6,y+40,p);
            y += 50;
        }
        this.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,y));
    }
}