package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

//Desenha um gráfico em pizza da distribuição de gastos entre as categorias de um mês ou vários.

public class Pizza extends View{

    //Construtores padrẽos de View
    public Pizza(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }
    public Pizza(Context context) {
        super(context);
        p = new Paint();
    }

    private Paint p;
    private double[] val;//Os valores de cada fatia.
    //As cores de cada fatia.
    private final int[] cores= { Color.RED, Color.RED, 0xffE8E000, 0xff40A040, Color.BLUE, 0xff80D0F0, 0xffFFA0D0 };

    //Solicita o mesmo valor que foi sugerido.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }

    //Define o mês a ser grafado.
    //Define cada valor para ser o valor da categoria divido pelo valor total.
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

    //O mesmo que set meses, mas para os útlimos m meses.
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

    //Desenha a pizza e as legendas.
    @Override
    protected void onDraw(Canvas canvas){

        //Largura e altura
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //Desenhas a pizza
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            p.setColor(Color.WHITE);
            canvas.drawRect(0,0,w,h,p);
            pizza(canvas,w*0.25f,h*0.05f,w*0.70f,h*0.70f,val,cores);
        }

        //Desenha a legenda

        float left = (float)(w*0.05);//Posição mais a esquerda dos quadradinhos: 5% de w
        float right = (float)(w*0.09); //Posição mais a direitas dos quadradnhos: 9% de w
        float lefttext = (float)(w*0.11);//Posiçao mais a esquerda do texto. 11% de w

        //Configura tamanho do texto.
        p.setTextSize(30);

        //Para cada categoria:
        for(int i = 1; i < 7; i++){
            float top = (float)(h*(0.70+(i-1)*0.05));//Posição mais a cima do quadradinho
            float bottom = (float)(h*(0.74+i*0.05)); //Posição mais a baixo do quadradinho.
            float toptext = (float)(h*(0.73+(i-1)*0.05)); //Posição mais a esquerda do texto.
            p.setColor(cores[i]);//Escolhe a cor da categoria.
            canvas.drawRect(left,top,right,bottom,p);//Desenha o quadradinho.
            canvas.drawText(Mes.NOME_CATEGORIAS[i],lefttext,toptext,p);//Escreve o nome da categoria
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pizza(Canvas canvas, float x, float y, float w, float h,double[] valores, int[] cores){
        float theta = 0;//O angulo acumulado
        p.setStyle(Paint.Style.FILL);
        for(int i=0; i<valores.length; i++){
            //A fatia tem 360º * o valor.
            float fatia = -(float)(360 * valores[i]);
            //Desenha a fatia do circulo com o tamanho proporcional.
            p.setColor(cores[i]);
            canvas.drawArc(x,y,x+w,y+h,theta,fatia,true,p);
            theta+=fatia;//Incrementa theta.
        }
    }
}
