package com.example.balancomensal;

public class Mes {

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
		listaMov = new Movimentacao[50];
		numMov = 0;
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
		for(int i =0;i<7;i++) {
			
			if(m.getCategoria() == i && i != 0) {
				valorCategoria[i] += m.getValor();
				total -= m.getValor();
			}
			
			if(m.getCategoria() == i && i == 0) {
				valorCategoria[i] += m.getValor();
				total += m.getCategoria();
			}
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
			if(listaMov[i].getDiaMes()[0] == dia) {
				lm[j] = listaMov[i];
				j++;
			}
		}
		Movimentacao[]	aux = new Movimentacao[j+1];
		aux[j] = null;
		
		return aux;
	}
}
