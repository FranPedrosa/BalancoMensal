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

//Representação gráfica de uma lista de movimentações fixas.
public class ListaMov extends View{

    private Mes mes; //O mês onde as fixas estão.
    private int nMes;
    private Paint p;
    private int dia = -1; //O dia do mês que está listado.
    int[] id; //O id de cada movimentação
    Movimentacao[] ms; //As movimentações daquele dia daquele mês

    //Os construtores padrões de View
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

    //Muda o dia
    public void setDia(int dia){
        this.dia = dia;
        requestLayout();
        invalidate();
    }

    //Muda o mês
    public void setMes(Mes mes,int nMes){
        this.mes = mes;
        this.nMes = nMes;
    }

    //Solicita o tamanho referente o número de movimentações.
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

    //Desenho da lista.
    @Override
    protected void onDraw(Canvas canvas){

        //A largura e altura
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //O fundo do branco
        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        //Começa a posição y no pixel de 120.
        int y = 120;

        //Atualiza as movimentações.
        Movimentacao[] m = mes.findMovData(dia);

        //O título com o número do dia.
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(60);
        if(dia != -1) {
            canvas.drawText("Dia " + (dia + 1), w / 2, 80, p);
        }

        //Configura o tamanho e alinhamento do texto.
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.LEFT);

        int i = 0;
        while(m[i] != null){

            //Nome da movimentação.
            p.setColor(Color.BLACK);
            canvas.drawText(m[i].getNome(),60,y+40,p);

            //Escreve o valor em verde ou vermelho.
            if(m[i].getCategoria() == 0){
                p.setColor(Color.GREEN);
            }
            else{
                p.setColor(Color.RED);
            }
            canvas.drawText("R$" + Math.abs(m[i].getValor()),w/3,y+40,p);

            //Editar em verde.
            p.setColor(Color.GREEN);
            canvas.drawText("Editar",2*w/3,y+40,p);

            //Apagar em vermelho
            p.setColor(Color.RED);
            canvas.drawText("Apagar",5*w/6,y+40,p);

            y += 50;
            i++;
        }
        this.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,y));
    }

    //Processa um toque na lista
    public void Toque(int x, int y, AppCompatActivity app, final Dados db) {
        int w = getWidth();//Largura da tela
        final int i = (y-120)/50;//Posição na lista.
        if(i >= 0){
            if(x > 5*w/6){//Se for em cima do apagar, pede a confirmação.
                new Confirmacao(app,"Você tem certeza que quer apagar a movimentação?","Apagar","Cancelar") {
                    @Override
                    void confirm() {
                        db.remover(nMes,id[i]);
                        invalidate();
                    }
                };
            }
            else if(x > 2*w/3) {//Se for em cima do editar vai para tela de edição.
                new AddData(app, db, ms[i], id[i], nMes);
            }
        }
    }
}
