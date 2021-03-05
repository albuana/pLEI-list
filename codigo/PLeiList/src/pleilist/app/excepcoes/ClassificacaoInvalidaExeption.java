package pleilist.app.excepcoes;

public class ClassificacaoInvalidaExeption extends Exception {
	
	//ATRIBUTOS
	private int estrelas;
	
	//CONSTRUTOR
	/**
	 * Construtor de uma excecao que eh lancada quando a classificao de um video eh invalida,
	 * isto eh, quando nao estah entre 1 e 5 
	 * @param estrelas Classificacao atribuida
	 */
	public ClassificacaoInvalidaExeption(int estrelas) {
		this.estrelas = estrelas;
	}

	//METODOS
	public String toString() {
		return "A classificacao " + estrelas + " eh invalida. Os valores permitidos sao entre 1 e 5!";
	}
}
