//dto eh o que eh visivel

package pleilist.app.facade.dto;

public class Playlist {

	//ATRIBUTOS
	private String nome;
	private String codigo;
	private double pontuacao;

	//CONSTRUTOR
	public Playlist(String nome, String codigo, double pontuacao) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.pontuacao = pontuacao;
	}

	//GETTERS
	public String getNome() {
		return nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public double getPontuacao() {
		return pontuacao;
	}
}