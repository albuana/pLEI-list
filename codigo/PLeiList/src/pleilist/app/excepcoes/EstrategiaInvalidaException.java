package pleilist.app.excepcoes;

public class EstrategiaInvalidaException extends Exception {
	
	//ATRIBUTOS
	private String estrategia;

	//CONSTRUTOR
	/**
	 * Contrutor de uma excecao que eh lancada quando
	 * a estrategia dada nao eh uma das disponiveis
	 * @param strategy Estrategia
	 */
	public EstrategiaInvalidaException(String strategy) {
		this.estrategia = strategy;
	}
	
	//METODOS
	public String toString() {
		return "A estrategia " + estrategia + " nao existe! Por favor escolha uma das disponiveis e volte a indicar os dados da playlist";
	}
}
