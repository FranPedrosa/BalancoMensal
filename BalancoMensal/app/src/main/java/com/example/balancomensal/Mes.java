package com.example.balancomensal;
public class Mes {
	/*
	0 Ganhos
	1 Alimentacao
	2 Moradia
	3 Lazer
	4 Transporte
	5 Compras
	6 OutrosGastos
	 */
	private Movimentacao[] listaMov;
	private double[] valorCategoria;
	private int numMov;
	private double total;
	
	Mes(){
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
}
