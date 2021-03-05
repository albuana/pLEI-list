package pleilist.app.excepcoes;

public class EnderecoInvalidoException extends Exception {
    //ATRIBUTOS
	private String endereco;

	//CONSTRUTOR
	/**
	 * Contrutor de uma excecao que eh lancada quando o
	 * endereco dado eh invalido
	 * @param address Endereco
	 */
    public EnderecoInvalidoException(String address) {
        this.endereco = address;
    }
    
    //METODOS
    public String toString() {
        return "O endereco " + endereco + " eh invalido.\nPor favor indique novamente os dados do video.";
    }

}
