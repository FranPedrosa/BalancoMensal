package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

//Classe para desenhar um gráfico de barras.
public class Grafico extends View{

    //Construtores padrões de View
    public Grafico(Context context, AttributeSet attrs) {
        super(context,attrs);
        p = new Paint();
    }
    public Grafico(Context context) {
        super(context);
        p = new Paint();
    }

    private Paint p;
    private double[] meses;//Os valores do gráfico.
    private String[] nomes = {"JAN","FEB","MAR","ABR","MAI","JUN","JUL","AGO","SET","OUT","NOV","DEZ"};//As abreviações para legenda
    private int mesAtual; //O numero do mês para aparecer como a ultima legenda.
    private double max = 5; //O maior número, que vai ser representado por uma barra até o topo.

    //Configuras os valores a serem exibidos, e os meses para aparecerem na legenda.
    //Mes atual vai ser a legenda mais a direita.
    public void setDados(double[] dados,int mesAtual){
        meses = dados;
        this.mesAtual = mesAtual;
        for (double d : dados) {
           if(Math.abs(d) > max){
               max = Math.abs(d);
           }
        }
        invalidate();
    }

    //Desenha o gráfico
    @Override
    protected void onDraw(Canvas canvas){

        //A altura e largura máxima disponível.
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //Fundo branco.
        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        //Se não tiver meses retorna.
        if(meses.length == 0){
            return;
        }

        float larg = w/meses.length;//A largura de cada barra.
        float zoom = (float) (h*0.4f / max);//A escala que as barras vão seguir. (para o maior valor ter 40% da altura.

        //A linha que marca o zero.
        p.setColor(Color.BLACK);
        canvas.drawLine(0,h/2,w,h/2,p);
        p.setTextSize(30);

        //k é o número do mês para a legenda, jan = 0, feb = 1, mar = 2...
        //i é a posição de cada número no vetor.
        //j é a posição na tela (mes atual o mais para direita)
        int k = mesAtual;
        for(int i = 0; i < meses.length; i++){
            int j = meses.length - i -1;

            //Formata o número com duas casas decimais.
            String valor = String.format("%.2f",Math.abs(meses[i]));

            //Valor negativo
            if(meses[i] < 0){
                p.setColor(0xFFAD3523);//Cor vermelha
                canvas.drawText(valor,larg*(j+0.1f),(float)(h/2 - meses[i]*zoom)+35,p); //Valor
                canvas.drawRect(larg*(j+0.1f),h/2,larg*(j+0.8f),(float)(h/2 - meses[i]*zoom),p); //Barra

                //Legenda (JAN, FEB, MAR... )
                p.setColor(Color.BLACK);
                canvas.drawText(nomes[k],larg*(j+0.1f),(float)(h/2)-5,p);
            }
            else{
                p.setColor(0xFF338F65);//Cor verda
                canvas.drawText(valor,larg*(j+0.1f),(float)(h/2 - meses[i]*zoom)-5,p);//Valor
                canvas.drawRect(larg*(j+0.1f),h/2,larg*(j+0.8f),(float)(h/2 - meses[i]*zoom),p);//Barra

                //Legenda (JAN, FEB, MAR... )
                p.setColor(Color.BLACK);
                canvas.drawText(nomes[k],larg*(j+0.1f),(float)(h/2)+35,p);
            }

            k--;
            if(k == -1){
                k = 11;
            }
        }
    }
}
