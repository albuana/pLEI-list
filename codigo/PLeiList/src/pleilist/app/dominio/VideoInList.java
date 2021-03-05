package pleilist.app.dominio;

import java.time.Duration;

import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.dominio.videos.Clip;
import pleilist.app.dominio.videos.Video;

public class VideoInList {

	//ATRIBUTOS
	private Duration duracao;
	private Video video;

	//CONSTRUTOR
	/**
	 * Construtor de 
	 * Se o video eh um clip entao atualizar a sua duracao 
	 * cc eh pq o video eh um stream e a duracao eh "zero"
	 * @param v Video da playlist
	 * @param playlist Playlist de videos
	 */
	public VideoInList(Video v, Playlist playlist) {
		this.video = v;
		if(v instanceof Clip) { //2.1.1
			this.duracao = ((Clip) v).getDuracao();	//2.1.2	
			playlist.atualizaDuracao(this.duracao); //2.1.3
		} else {
			this.duracao = Duration.ZERO;
		}
	}

	//METODOS
	/**
	 * Adiciona uma nova classificacao a este video, ou altera a classificacao
	 * que utilizador jah tinha atribuido a este video
	 * @param classificacao Classifcacao atribuida ao video
	 * @param utilizador Utilizador que adiciona a classificacao
	 */
	public void adicionaClassificacao(int classificacao, Utilizador utilizador) {
		this.video.adicionaClassificacao(classificacao, utilizador); //1.1
	}

	//GETTERS
    /**
     * Devolve o nome deste video, se este ja tiver sido atribuido
     * cc devolve uma string vazia
     * @return o nome deste video se ja tiver sido atribuido, cc uma string vazia
     */
	public String getNome() {
		return this.video.getNome(); //1.2.1
	}

	/**
	 * Devolve a duracao deste video
	 * @return duracao Duracao deste video
	 */
	public Duration getDuracao() {
		return this.duracao;
	}

	/**
	 * 
	 * @return
	 */
    /**
     * Devolve a classificacao atribuida a este video
     * @return classificao Classificacao deste video
     */
	public double getClassificacao() {
		return this.video.getClassificacao();
	}

    /**
     * Devolve o endereco deste video, se este ja tiver sido atribuido
     * cc devolve uma string vazia
     * @return endereco Endereco deste video se ja tiver sido atribuido, cc uma string vazia
     */
	public String getEndereco() {
		return this.video.getEndereco(); //4.1
	}

	//SETTERS
	/**
	 * Coloca duration como duracao do video
	 * @param duration Duracao a atribuir ao video
	 */
	public void setDuracao(Duration duration) {
		this.duracao = duration;
	}
}