package com.example.balancomensal;

import java.io.Serializable;

public class Mes implements Serializable {

	/*
	public static final int GANHOS = 0;
	public static final int ALIMENTACAO = 1;
	public static final int MORADIA = 2;
	public static final int LAZER = 3;
	public static final int TRANPORTE = 4;
	public static final int COMPRAS = 5;
	public static final int OUTROS = 6;
	 */

	public static final String[] NOME_CATEGORIAS = {"Ganhos","Alimentação","Moradia","Lazer","Transporte","Compras","Outros"};

	private Movimentacao[] listaMov;
	private double[] valorCategoria;
	private int numMov;
	private double total;
	
	Mes(){
		numMov = 0;
		listaMov = new Movimentacao[50];
		valorCategoria = new double[7];
		for(int i=0;i<7;i++) {
			valorCategoria[i] = 0.0;
		}
		total = 0.0;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void addMovimentacao(Movimentacao m) {
		listaMov[numMov] = m;
		numMov++;
		addValorCategoria(m);
		
	}
	
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
	
	public double getValorCategoria(int cat) {
		return valorCategoria[cat];
	}
	
	public double getTotalGastos() {
		double t=0.0;
		for(int i=1;i<7;i++) {
			t -= valorCategoria[i];
		}
		return t;
	}

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

	public boolean[] marcadorMes(){
		boolean[] marcador = new boolean[31];
		for(int i=0;i<numMov;i++){
			if(listaMov[i] != null) {
				marcador[listaMov[i].getDiaMes()[0]] = true;
			}
		}
		return marcador;
	}

	public Movimentacao[] proxConta(int dia){
		boolean[] marc = marcadorMes();
		while(marc[dia]!= true){
			dia++;
		}
		return findMovData(dia);
	}

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

	public void removerMov(int j){
		atualizarMov(j,null);
	}

}
