package com.example.balancomensal;

import java.io.Serializable;

//Classe para representar uma movimentação fixa (que repete todo mês).
public class Fixa implements Serializable {
	private String Nome;
	private int[] diaMes = new int[2]; //Um vetor com o dia e o mês da data
	private int duracao;
	private int valor;
	private int categoria;

	//Inicia a fixa passando um valor para cada propriedade
	Fixa(String n,int d, int m, int dur, int v, int cat){
		Nome = n;
		diaMes[0] = d;
		diaMes[1] = m;
		duracao = dur;
		valor = v;
		categoria = cat;
	}

	//Setters e Getters
	void setDuracao(int d) {
		duracao = d;
	}
	public String getNome() {
		return Nome;
	}
	public int[] getDiaMes() {
		return diaMes;
	}
	public int getDuracao() {
		return duracao;
	}
	public int getValor() {
		return valor;
	}
	public int getCategoria() {
		return categoria;
	}
}
