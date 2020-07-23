package com.example.balancomensal;

import java.io.Serializable;

//Uma movientação simples.
public class Movimentacao implements Serializable {
	private String Nome;	
	private int[] diaMes = new int[3]; //Vetor [dia,mes,ano]
	private int valor;
	private int categoria;

	//Contrutor com todas as proprieadadas
	Movimentacao(String n, int d, int m, int a, int v, int cat) {
		Nome = n;
		diaMes[0] = d;
		diaMes[1] = m;
		diaMes[2] = a;
		valor = v;
		categoria = cat;
	}

	//Getters
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
