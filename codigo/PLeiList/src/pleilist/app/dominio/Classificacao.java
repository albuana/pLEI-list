package pleilist.app.dominio;

import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.dominio.videos.Video;

public class Classificacao {

	//ATRIBUTOS
	private int estrelas;
	private Utilizador utilizador;

	//CONSTRUTOR
	/**
	 * Cria uma nova classificacao e associa a utilizador
	 * @param classificacao estrelas a atribuir ao video no intervalo 1 a 5 
	 *        se classificacao > 5 eh atribuido o valor 5
	 *        se classificacao < 1 eh atribuido o valor 1
	 * @param utilizador utilizador que atribuiu a classificacao
	 * @param video video ao qual eh atribuida a classificacao
	 */
	public Classificacao(int classificacao, Utilizador utilizador, Video video) {
	    if(classificacao > 5) {
	        this.estrelas = 5;
	    } else if (classificacao < 1) {
	        this.estrelas = 1;
	    } else {
	        this.estrelas = classificacao;	        
	    }
		this.utilizador = utilizador;
	}

	//METODOS
	
	/**
	 * Indica se o utilizador que atribuiu esta classificacao eh o utilizador indicado
	 * @param utilizador utilizador a testar
	 * @return true se o utilizador indicado eh o utilizador que atribuiu esta classificacao
	 */
	public boolean temUtilizador(Utilizador utilizador) {
		return this.utilizador.equals(utilizador);
	}

	//GETTERS
	
	/**
	 * Devolve o numero de estrelas desta classificacao
	 * @return numero de estrelas desta classificacao
	 */
	public int getEstrelas() {
		return this.estrelas;
	}

	//SETTERS
	
	/**
	 * Coloca o numero de estrelas desta classificacao
	 * @param classificacao numero de estrelas a atribuir a esta classificacao
	 */
	public void setClassificacao(int classificacao) {
		this.estrelas = classificacao;
	}
}