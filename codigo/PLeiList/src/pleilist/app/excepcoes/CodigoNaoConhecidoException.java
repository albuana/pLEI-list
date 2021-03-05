package pleilist.app.excepcoes;

public class CodigoNaoConhecidoException extends Exception {

	//ATRIBUTOS
	private String objeto;
	private String codigo;

	//CONSTRUTOR
	/**
	 * Construtor de uma excecao que eh lancada quando naoh
	 * existe nenhum objeto com o codigo recebido
	 * @param objeto Objeto
	 * @param codigo Codigo
	 */
	public CodigoNaoConhecidoException(String objeto, String codigo) {
		this.codigo = codigo;
	}

	//METODOS
	@Override
	public String toString() {
		return "Nao existe nenhum " + objeto + " com o codigo: " + codigo + ". Por favor insira um novo codigo.";
	}
}
