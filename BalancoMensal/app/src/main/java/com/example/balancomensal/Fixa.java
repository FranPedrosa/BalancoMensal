
public class Fixa {
	private String Nome;	
	private int[] diaMes;
	private int duracao;
	private int valor;
	private int categoria;
	
	Fixa(String n,int d, int m, int dur, int v, int cat){
		Nome = n;
		diaMes[0] = d;
		diaMes[1] = m;
		duracao = dur;
		valor = v;
		categoria = cat;
	}
	
	void setNome(String s) {
		Nome = s;
	}
	
	void setDiaMes(int[] d) {
		diaMes = d;
	}

	void setDuracao(int d) {
		duracao = d;
	}
	
	void reduzDuracao() {
		duracao--;
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
