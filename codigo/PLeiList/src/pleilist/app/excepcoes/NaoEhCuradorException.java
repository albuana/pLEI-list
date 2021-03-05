package pleilist.app.excepcoes;

public class NaoEhCuradorException extends Exception {
    
    //ATRIBUTOS
    private String username;
    
    /**
     * Contrutor de uma excecao que eh lancada quando
     * o username dado nao eh um Curador
     * @param username Username
     */
    //CONSTRUTOR
    public NaoEhCuradorException(String username) {
        this.username = username;
    }
    
    //METODOS
    @Override
    public String toString() {
        return username + " nao eh um Curador!";
    }
}