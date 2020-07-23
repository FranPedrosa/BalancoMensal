package com.example.balancomensal;

import java.io.Serializable;

//Representa o mês, tem uma lista de movimentações.
public class Mes implements Serializable {

	//O nome de cada categoria
	public static final String[] NOME_CATEGORIAS = {"Ganhos","Alimentação","Moradia","Lazer","Transporte","Compras","Outros"};

	private Movimentacao[] listaMov; //A lista de movimentações propriamente dita.
	private double[] valorCategoria; //O valor total de cada categoria.
	private int numMov; //O número de movimentaçẽos.
	private double total; //O valor total do balanço.

	//Construtor, inicia os vetores e variáveis.
	Mes(){
		numMov = 0;
		listaMov = new Movimentacao[50];
		valorCategoria = new double[7];
		for(int i=0;i<7;i++) {
			valorCategoria[i] = 0.0;
		}
		total = 0.0;
	}

	//Getters
	public double getTotal() {
		return total;
	}
	public double getValorCategoria(int cat) {
		return valorCategoria[cat];
	}
	public void removerMov(int j){
		atualizarMov(j,null);
	}

	//Adiciona uma nova movimentação.
	public void addMovimentacao(Movimentacao m) {
		listaMov[numMov] = m;
		numMov++;
		addValorCategoria(m);
	}

	//Incrementa ( ou decrementa) o valor de cada categoria.
	public void addValorCategoria(Movimentacao m) {
			if(m.getCategoria() != 0) {
				valorCategoria[m.getCategoria()] += m.getValor();
				total -= m.getValor();
			}
			if(m.getCategoria() == 0) {
				valorCategoria[m.getCategoria()] += m.getValor();
				total += m.getValor();
			}
	}

	//Soma todos gastos para encontrar o total.
	public double getTotalGastos() {
		double t=0.0;
		for(int i=1;i<7;i++) {
			t -= valorCategoria[i];
		}
		return t;
	}

	//Retorna todas as movimentações de uma data.
	public Movimentacao[] findMovData(int dia) {
		Movimentacao[] lm = new Movimentacao[30];
		int j=0;
		for(int i = 0; i < numMov; i++){
			if(listaMov[i] != null && listaMov[i].getDiaMes()[0] == dia) {
				lm[j] = listaMov[i];
				j++;
			}
		}
		Movimentacao[]	aux = new Movimentacao[j+1];
		for(int i = 0;i < j; i++){
			aux[i] = lm[i];
		}
		aux[j] = null;

		return aux;
	}

	//Retorna as posições das movimentações que vieram de findMovData()
	public int[] findIdData(int dia){
		int [] nov = new int[30];
		int j=0;
		for(int i = 0; i < numMov; i++){
			if(listaMov[i] != null && listaMov[i].getDiaMes()[0] == dia) {
				nov[j] = i;
				j++;
			}
		}
		int[]	aux = new int[j+1];
		for(int i = 0;i < j; i++){
			aux[i] = nov[i];
		}
		aux[j] = -1;

		return aux;
	}

	//Retorna um vetor falando quais dias do mês tem movimentação quais não, para mostrar no calendário.
	public boolean[] marcadorMes(){
		boolean[] marcador = new boolean[31];
		for(int i=0;i<numMov;i++){
			if(listaMov[i] != null) {
				marcador[listaMov[i].getDiaMes()[0]] = true;
			}
		}
		return marcador;
	}

	//Atualiza a movimentação na posição j
	public void atualizarMov(int j,Movimentacao nova){
		Movimentacao m = listaMov[j];
		if(m.getCategoria() != 0) {
			valorCategoria[m.getCategoria()] -= m.getValor();
			total += m.getValor();
		}

		if(m.getCategoria() == 0) {
			valorCategoria[m.getCategoria()] -= m.getValor();
			total -= m.getValor();
		}
		if(nova != null) {
			addValorCategoria(nova);
		}
		listaMov[j] = nova;
	}
}
