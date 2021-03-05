package pleilist.app.excepcoes;

public class UsernameInvalidoException extends Exception {

    //ATRIBUTOS
    private String username;

    //CONSTRUTOR
    /**
     * Contrutor de uma excecao que eh lancada quando
     * jah existe um utilizador com o username dado
     * @param username Username dado
     */
    public UsernameInvalidoException(String username) {
        this.username = username;
    }

    //METODOS
    @Override
    public String toString() {
        return "Ja existe um utilizador com o username " + username + ". Escolha outro por favor.";
    }
}