package com.example.balancomensal;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Dados implements Serializable {
    private Mes listaMes[];
    private Fixa listaFixas[];
    private int tamFixas;
    private int mes_atual;
    private int numFixas;
    private int ano;

    Dados() {
        listaMes = new Mes[12];
        listaFixas = new Fixa[20];
        tamFixas = 0;
        numFixas = 0;
    }


    public void setMesAtual(int mes){mes_atual = mes;}
    public void setAno(int a){ano = a;}

    public double[] getMediaCategoriaMeses(int meses){
            double mediaCategorias[] = new double[7];
            for(int i=0;i<7;i++){
                mediaCategorias[i] = 0.0;
            }
            for(int i = 0;i<meses;i++){
                for(int j = 1;j<7;j++){
                    mediaCategorias[j] = ((mediaCategorias[j]*(i+1))+listaMes[(12 + mes_atual-i)%12].getValorCategoria(j))/(i+1);
                }
            }
            return mediaCategorias;
    }

    public double[] getTotalMeses(int meses){
        double totalMeses[] = new double[meses];
        for(int i=0;i<meses;i++){
            totalMeses[i] = listaMes[(12 + mes_atual -i)%12].getTotal();
        }
        return totalMeses;
    }

    public void add(Movimentacao m){
        listaMes[mes_atual].addMovimentacao(m);
    }

    public void add(Fixa f, int mes_atual,int ano){
        if(numFixas < tamFixas){
            for(int i=0;i<tamFixas;i++){
                if(listaFixas[i] == null) {
                    listaFixas[i] = f;
                    numFixas++;
                }
            }
        }
        listaFixas[tamFixas] = f;
        tamFixas++;
    }

    public void fixaParaMovi(Fixa f){
        Movimentacao m = new Movimentacao(f.getNome(),f.getDiaMes()[0],mes_atual,ano,f.getValor(),f.getCategoria());
        add(m);
        f.setDuracao(f.getDuracao()-1);
        if(f.getDuracao() == 0){
            removerFixa(f.getNome());
        }
    }

    public void removerFixa(String s){
        for(int i=0;i<tamFixas;i++){
            if(s.equals(listaFixas[i].getNome())){
                listaFixas[i] = null;
                --numFixas;
                return;
            }
        }
    }

    public static Dados abrir(AppCompatActivity app){

        try{
            //Creating stream to read the object
            ObjectInputStream in = new ObjectInputStream(app.openFileInput("BMdados"));
            Dados db = (Dados)in.readObject();
            //printing the data of the serialized object
            //closing the stream
            in.close();
            return db;
        }catch(Exception e){
            return null;
        }
    }

    public void salvar(AppCompatActivity app) {
        try{
            FileOutputStream arq;
            //Creating stream and writing the object
            arq = app.openFileOutput("BMdados", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(arq);
            out.writeObject(this);
            out.flush();
            //closing the stream
            out.close();
            /*Toast toast = Toast.makeText(getApplicationContext(), "Sucesso" , Toast.LENGTH_SHORT);
            toast.show();*/
        }catch(Exception e){
            /*Toast toast = Toast.makeText(getApplicationContext(), "Sem sucesso" , Toast.LENGTH_SHORT);
            toast.show();*/
        }
    }
}
