package com.example.balancomensal;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;


//Dados é a classe que salva todas as informações do usuário.
//Sempre que se abre o aplicativo ele tenta abrir uma classe dados salva na memória.

public class Dados implements Serializable {

    private Mes listaMes[]; //Os doze meses.
    private Fixa listaFixas[]; //A lista de fixas (até 20)
    private int tamFixas = 0; //A porção do vetor de fixas que já foi usada.
    private int numFixas = 0; //A quantidade de fixas que já tem
    private int ano; // O ano atual
    private int mes_atual; //O mes atual


    transient static AppCompatActivity app;

    //Contrutor recebe o mes e o ano atuais
    Dados(int mes, int ano) {
        this.mes_atual = mes;
        this.ano = ano;

        //Inicia os vetores com seus tamanhos.
        listaMes = new Mes[12];
        listaFixas = new Fixa[20];
        for(int i = 0; i < 12;i++){
            listaMes[i] = new Mes();
        }
    }

    //Getters
    public int getMesAtual(){return mes_atual;}
    public int getAno(){return ano;}
    public Mes getMes(int n){return listaMes[n];}
    public Fixa[] getFixas(){
        return listaFixas;
    }

    //Retorna a média de gastos por categoria nos ultimos n meses.
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

    //Limpa os dados do Mês atual, para quando passar um ano.
    public void novoMes(){
        Mes mes = new Mes();
        listaMes[mes_atual] = mes;
    }

    //Retorna o total de gastos nos úlimos n meses.
    public double[] getTotalMeses(int meses){
        double totalMeses[] = new double[meses];
        for(int i=0;i<meses;i++){
            totalMeses[i] = listaMes[(12 + mes_atual -i)%12].getTotal();
        }
        return totalMeses;
    }

    //Adiciona uma movimentação no mês que estiver marcado nela.
    public void add(Movimentacao m){
        listaMes[m.getDiaMes()[1]].addMovimentacao(m);
    }

    //Substitui a movimentação na posição pos.
    public void add(Movimentacao m,int pos){
        listaMes[m.getDiaMes()[1]].atualizarMov(pos,m);
    }

    //Remove a movimentação do mes mes com posição pos.
    public void remover(int mes,int pos){
        listaMes[mes].removerMov(pos);
    }

    //Adiciona uma nova fixa e já adiciona como movimentação no mês atual
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
        fixaParaMovi(listaFixas[tamFixas]);
        tamFixas++;
        numFixas++;
    }

    //Transforma um movimentação fixa em simples.
    //Deve ser chamado em cada começo de Mês
    public void fixaParaMovi(Fixa f){
        Movimentacao m = new Movimentacao(f.getNome(),f.getDiaMes()[0],mes_atual,ano,f.getValor(),f.getCategoria());
        add(m);
        f.setDuracao(f.getDuracao()-1);
        if(f.getDuracao() == 0){
            removerFixa(f.getNome());
        }
    }

    //Remove uma fixa baseado no nome dela
    public void removerFixa(String s){
        for(int i=0;i<tamFixas;i++){
            if(listaFixas[i] != null && s.equals(listaFixas[i].getNome())){
                listaFixas[i] = null;
                --numFixas;
                return;
            }
        }
    }

    //Atualiza uma fixa na posição i
    public void atualizarFixa(int i,Fixa nova){
        listaFixas[i] = nova;
    }

    //Remove a fixa baseado na sua posição.
    public void removerFixa(int i){
        listaFixas[i] = null;
    }


    //Tenta ler o arquivo BMdados para iniciar o programa.
    public static Dados abrir(AppCompatActivity app){

        try{
            ObjectInputStream in = new ObjectInputStream(app.openFileInput("BMdados"));
            Dados db = (Dados)in.readObject();
            in.close();
            Dados.app = app;
            db.atualizar();
            return db;
        }catch(Exception e){
            //Se não conseguir avisa o usuário.
            Toast toast = Toast.makeText(app.getApplicationContext(), "Não encontramos dados antigos." , Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }
    }

    //Salva as mudanças no BMdados.
    public void salvar(AppCompatActivity app) {
        try{
            FileOutputStream arq;
            arq = app.openFileOutput("BMdados", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(arq);
            out.writeObject(this);
            out.flush();
        }catch(Exception e){
            Toast toast = Toast.makeText(app.getApplicationContext(), "Erro ao salvar o arquivo." , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Cria movimentações simples para cada fixa que existe.
    //Deve ser chamado no começo de cada mês.
    private void todasFixas(){
        for(int i = 0;i < tamFixas;i++){
            if(listaFixas[i] != null){
                fixaParaMovi(listaFixas[i]);
            }
        }
    }

    //Incrementa mês atual até chegar no mês atual de verdade.
    private void atualizar(){

        LocalDate ld = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            //Lê o mês e ano do sistema.
            ld = LocalDate.now();
            int mes_novo = ld.getMonthValue()-1;
            int ano_novo = ld.getYear();

            //Mês atual tem o valor da ultima vez que foi aberto o aplicativo.

            //Incrementa o mês atual até chegar no do sistema.
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
