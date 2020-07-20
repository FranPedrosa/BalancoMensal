package com.example.balancomensal;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

public class Dados implements Serializable {
    private Mes listaMes[];
    private Fixa listaFixas[];
    private int tamFixas;
    private int mes_atual;
    private int numFixas;
    private int ano;

    transient static AppCompatActivity app;

    Dados(int mes, int ano) {
        listaMes = new Mes[12];
        listaFixas = new Fixa[20];
        tamFixas = 0;
        numFixas = 20;
        for(int i = 0; i < 12;i++){
            listaMes[i] = new Mes();
        }
        this.mes_atual = mes;
        this.ano = ano;
    }


    public int getMesAtual(){return mes_atual;}
    public int getAno(){return ano;}

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
    public void novoMes(){
        Mes mes = new Mes();
        listaMes[mes_atual] = mes;
    }

    public double[] getTotalMeses(int meses){
        double totalMeses[] = new double[meses];
        for(int i=0;i<meses;i++){
            totalMeses[i] = listaMes[(12 + mes_atual -i)%12].getTotal();
        }
        return totalMeses;
    }

    public void add(Movimentacao m){
        listaMes[m.getDiaMes()[1]].addMovimentacao(m);
    }

    public void add(Movimentacao m,int pos){
        listaMes[m.getDiaMes()[1]].atualizarMov(pos,m);
    }

    public void remover(int mes,int pos){
        System.out.println("Remove o mes do pos : " + mes +","+pos);
        listaMes[mes].removerMov(pos);
    }

    public void addFixa(Fixa f){
        if(numFixas < tamFixas){
            for(int i=0;i<tamFixas;i++){
                if(listaFixas[i] == null) {
                    listaFixas[i] = f;
                    fixaParaMovi(listaFixas[i]);
                    numFixas++;
                    return;
                }
            }
        }
        listaFixas[tamFixas] = f;
        tamFixas++;
    }

    public void fixaParaMovi(Fixa f){
        Movimentacao m = new Movimentacao(f.getNome(),f.getDiaMes()[0],f.getDiaMes()[1],ano,f.getValor(),f.getCategoria());
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

    public Mes getMes(int n){return listaMes[n];}

    public static Dados abrir(AppCompatActivity app){

        try{
            //Creating stream to read the object
            ObjectInputStream in = new ObjectInputStream(app.openFileInput("BMdados"));
            Dados db = (Dados)in.readObject();
            //printing the data of the serialized object
            //closing the stream
            in.close();

            Dados.app = app;
            db.atualizar();

            return db;
        }catch(Exception e){
            System.out.println("========================================");
            e.printStackTrace();
            System.out.println("========================================");
            Toast toast = Toast.makeText(app.getApplicationContext(), "Não encontramos dados antigos." , Toast.LENGTH_SHORT);
            toast.show();
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
        }catch(Exception e){

        }
    }

    private void todasFixas(){
        for(int i = 0;i < tamFixas;i++){
            fixaParaMovi(listaFixas[i]);
        }
    }

    private void atualizar(){

        LocalDate ld = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ld = LocalDate.now();
            int mes_novo = ld.getMonthValue()-1;
            int ano_novo = ld.getYear();

            for (int i = mes_atual; mes_atual != mes_novo || ano_novo != ano;) {
                mes_atual++;
                if (mes_atual == 12) {
                    ano++;
                    mes_atual = 0;
                }
                novoMes();
                todasFixas();
            }
        }

    }
}
