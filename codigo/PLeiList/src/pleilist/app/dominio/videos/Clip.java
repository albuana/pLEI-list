package pleilist.app.dominio.videos;

import java.time.Duration;

public class Clip extends Video {

	//ATRIBUTOS
	private Duration duracao;

	//CONSTRUTOR
	/**
	 * Cria um clip
	 */
	public Clip() {
		super();
		this.duracao = Duration.ZERO;
	}

	//GETTERS
	
	/**
	 * Devolve a duracao deste clip se ja tiver sido atribuida, cc devolve Duration.ZERO
	 * @return duracao deste clip se ja tiver sido atribuida, cc Duration.ZERO
	 */
	public Duration getDuracao() {
		return this.duracao;
	}

	//SETTERS
	
	/**
	 * Atribui a duracao a este clip
	 * @param duration duracao do clip
	 */
	public void setDuracao(Duration duration) {
		this.duracao = duration;
	}
}