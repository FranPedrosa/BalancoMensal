package com.example.balancomensal;

public class Movimentacao {
	private String Nome;	
	private int[] diaMes;	
	private int valor;
	private int categoria;
	
	Movimentacao(String n,int d, int m, int a, int v, int cat){
		Nome = n;
		diaMes[0] = d;
		diaMes[1] = m;
		diaMes[2] = a;
		valor = v;
		categoria = cat;
	}
	
	void setNome(String s) {
		Nome = s;
	}
	
	void setDiaMes(int[] d) {
		diaMes = d;
	}
	
	void setValor(int v) {
		valor = v;
	}

	void setCategoria(int c) {
		categoria = c;
	}
	
	public String getNome() {
		return Nome;
	}
	
	public int[] getDiaMes() {
		return diaMes;
	}
		
	public int getValor() {
		return valor;
	}
	
	public int getCategoria() {
		return categoria;
	}
}
