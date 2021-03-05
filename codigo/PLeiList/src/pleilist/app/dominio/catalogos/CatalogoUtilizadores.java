package pleilist.app.dominio.catalogos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pleilist.app.dominio.utilizadores.Curador;
import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.excepcoes.UsernameInvalidoException;

public class CatalogoUtilizadores {

    //ATRIBUTOS
    private Map<String, Utilizador> utilizadores;

    //CONSTRUTOR
    /**
     * Construtor 
     */
    public CatalogoUtilizadores() {
        this.utilizadores = new HashMap<>();
    }

    //GETTERS
    
    /**
     * Percorre todos os utilizadores registados no sistema e devolve um utilizador opcional se 
     * existir um utilizador registado com as credenciais indicadas
     * @param username username do utilizador a autenticar
     * @param password password do utilizador a autenticar
     * @return Se existir u: Utilizador registado no sistema tal que u.username.equals(username) 
     *         e u.password.equals(password) devolve um o: Optional<Utilixador> com o.get().equals(u)
     *         cc devolve um opcional vazio
     */
    public Optional<Utilizador> getUtilizador(String username, String password) {
        for(Utilizador u : this.utilizadores.values()) {
            if(u.autenticaCom(username, password)) {
                return Optional.of(u);		        
            }
        }
        return Optional.empty();
    }

    //METODOS ADICIONA
    /**
     * Cria e adiciona um utilizador ao Catalogo de Utilizadores
     * @param username username do utilizador a adicionar 
     * @param password password do utilizador a adicionar
     * @throws UsernameInvalidoException se jah existir u: Utilizador 
     *         registado no sistema com u.username.equals(username)
     */
    public void adicionarUtilizador(String username, String password) throws UsernameInvalidoException {
        // se jah existir u: Utilizador com u.username.equals(username), manda excepcao correspondente
        if(this.utilizadores.containsKey(username)) {
            throw new UsernameInvalidoException(username);
        } else {
            this.utilizadores.put(username, new Utilizador(username, password));
        }
    }

    /**
     * Cria e adiciona um curador ao Catalogo de Utilizadores
     * @param username username do curador a adicionar 
     * @param password password do curador a adicionar
     * @throws UsernameInvalidoException se jah existir u: Utilizador 
     *         registado no sistema com u.username.equals(username)
     */
    public void adicionarCurador(String username, String password) throws UsernameInvalidoException {
        // se jah existir u: Utilizador com u.username.equals(username), manda excepcao correspondente
        if(this.utilizadores.containsKey(username)) {
            throw new UsernameInvalidoException(username);
        } else {
            this.utilizadores.put(username, new Curador(username, password));
        }
    }
}