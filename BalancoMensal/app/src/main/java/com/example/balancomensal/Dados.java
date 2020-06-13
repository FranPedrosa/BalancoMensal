package com.example.balancomensal;

import java.io.Serializable;

public class Dados implements Serializable {
    private Mes listaMes[];
    private Fixa listaFixas[];
    private int tamFixas;
    private int numFixas;

    Dados(){
        listaMes = new Mes[12];
        listaFixas = new Fixa[20];
        tamFixas = 0;
    }

    // funcao de salvar dados
    //funcao de resgatar dados

    public double[] getMediaCategoriaMeses(int meses,int mes_atual){
            double mediaCategorias[] = new double[7];
            for(int i=0;i<7;i++){
                mediaCategorias[i] = 0.0;
            }
            for(int i = 0;i<meses;i++){
                for(int j = 1;j<7;j++){
                    mediaCategorias[j] = ((mediaCategorias[j]*(i+1))+listaMes[mes_atual-1-i].getValorCategoria(j))/(i+1);
                }
            }
            return mediaCategorias;
    }

    public void add(Movimentacao m, int mes_atual){
        listaMes[mes_atual].addMovimentacao(m);
    }

    public void add(Fixa f, int mes_atual,int ano){
        listaFixas[tamFixas] = f;
        tamFixas++;
    }

    public void fixaParaMovi(Fixa f,int mes_atual,int ano){
        Movimentacao m = new Movimentacao(f.getNome(),f.getDiaMes()[0],mes_atual,ano,f.getValor(),f.getCategoria());
        f.setDuracao(f.getDuracao()-1);
        if(f.getDuracao() == 0){
            removerFixa(f.getNome());
        }
    }

    public void removerFixa(String s){
        for(int i=0;i<tamFixas;i++){
            if(s.equals(listaFixas[i].getNome())){
                listaFixas[i] = null;
                return;
            }
        }
    }
}
