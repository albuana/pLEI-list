package pleilist.app.dominio.utilizadores;

public class Utilizador {

    //ATRIBUTOS
    protected String username; // CHAVE (id unico) deste objeto
    protected String password;

    //CONSTRUTOR
    /**
     * Construtor do utilizador
     * @param username username do Utilizador a criar
     * @param password password do Utilizador a criar
     */
    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //METODOS
    @Override
    public boolean equals(Object u) {
        return u instanceof Utilizador 
                // nao testamos a password, porque o username eh a chave deste objeto
                && this.username.equals(((Utilizador) u).username); 
    }

    /**
     * Devolve um boolean que indica se o utilizador se autentica com os dados indicados
     * @param username username a testar
     * @param password password a testar
     * @return true se os dados deste utilizador forem iguais aos dados indicados
     */
    public boolean autenticaCom(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    //GETTERS
    
    /**
     * Devolve o username deste utilizador
     * @return username deste utilizador
     */
    public String getUsername() {
        return this.username;
    }
}