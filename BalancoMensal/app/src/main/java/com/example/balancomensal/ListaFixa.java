package com.example.balancomensal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//Representação gráfica de uma lista de movimentações fixas.
public class ListaFixa extends View{

    private Fixa[] fixas;// As fixas a serem representadas.
    private int[] id = new int[20]; //A posição original delas no vetor de dados.
    private Paint p;

    //Contrutores padrões de view
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

    //Atualiza a lista de fixas
    public void setDados(Fixa[] fixas){
        this.fixas = fixas;
        requestLayout();
        invalidate();
    }

    //De altura solicita 50 * o número de fixas não nulas na lista.
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

    //Processa um toque na lista
    public void tocar(AppCompatActivity app, final Dados db, int x, int y) {
        int w = getWidth();//Largura da tela.
        final int i = y/50;//A posição na lista
        if( i >= 0){
            if(x > 5*w/6){//Se for en cima do botão de apagar, pede a confirmação.
                new Confirmacao(app,"Você tem certeza que quer apagar a movimentação fixa?","Apagar","Cancelar") {
                    @Override
                    void confirm() {
                        db.removerFixa(id[i]);
                        invalidate();
                    }
                };
            } else if(x > 2*w/3) {//Se for em cima do editar abre a tela de edição.
                new AddData(app, db, id[i]);
            }
        }
    }

    //Desenha a a lista
    @Override
    protected void onDraw(Canvas canvas){

        //largura  e altura disponível.
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        //Fundo branco.
        p.setColor(Color.WHITE);
        canvas.drawRect(0,0,w,h,p);

        //Configura tamanho e alinhamento da letra.
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.LEFT);

        //Posição atual do texto.
        int y=0;

        for(int i = 0; i < 20; i++){

            //Se for nula pula
            if(fixas[i] == null){
                continue;
            }

            //Escreve o nome da fixa
            p.setColor(Color.BLACK);
            canvas.drawText(fixas[i].getNome(),60,y+40,p);

            //Escreve o valor em verde ou vermelho.
            if(fixas[i].getCategoria() == 0){
                p.setColor(Color.GREEN);
            }
            else{
                p.setColor(Color.RED);
            }
            canvas.drawText("R$" + Math.abs(fixas[i].getValor()),w/3,y+40,p);

            //Escreve editar em verde.
            p.setColor(Color.GREEN);
            canvas.drawText("Editar",2*w/3,y+40,p);
            //Escreve apagar em vermelho.
            p.setColor(Color.RED);
            canvas.drawText("Apagar",5*w/6,y+40,p);
            //Incrementa a posição em 50px.
            y += 50;
        }
    }
}