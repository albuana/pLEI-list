package pleilist.app.excepcoes;

public class PlaylistTerminouException extends Exception {

    //ATRIBUTOS
    private String nome;
    
    //CONSTRUTOR
    /**
     * Contrutor de uma excecao que eh lancada quando
     * a playlist nao tem mais videos
     * @param nome Nome da playlist
     */
    public PlaylistTerminouException(String nome) {
        this.nome = nome;
    }
    
    //METODOS
    @Override
    public String toString() {
        return "A playlist " + nome + " nao tem mais videos!";
    }
}