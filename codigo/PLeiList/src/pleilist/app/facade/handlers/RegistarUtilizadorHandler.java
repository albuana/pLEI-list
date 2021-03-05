package pleilist.app.facade.handlers;

import pleilist.app.dominio.catalogos.CatalogoUtilizadores;
import pleilist.app.excepcoes.UsernameInvalidoException;

public class RegistarUtilizadorHandler {

    //ATRIBUTOS
    private CatalogoUtilizadores catUtilizadores;

    //CONSTRUTOR
    /**
     *  Construtor do handler para a UC registar utilizador
     * @param catUti catalogo de utilizadores do sistema
     */
    public RegistarUtilizadorHandler(CatalogoUtilizadores catUti) {
        this.catUtilizadores = catUti;
    }

    //METODOS
    /**
     * Regista um utilizador normal.
     * @param username username do utilizador a adicionar
     * @param password password do utilizador a adicionar
     * @throws UsernameInvalidoException se jah existir u: Utilizador 
     *         registado no sistema com u.username.equals(username)
     * @ensures Seja username um username que nao existe no sistema
     *          Eh criado u: Utilizador tal que u.username = username, 
     *          u.password = password e this.catUtilizadores.contains(u)
     */
    public void registarUtilizador(String username, String password) throws UsernameInvalidoException {
        this.catUtilizadores.adicionarUtilizador(username, password);
    }

    /**
     * Regista um utilizador com nível de curador.
     * @param Username username do curador a adicionar
     * @param Password password do curador a adicionar
     * @throws UsernameInvalidoException se jah existir u: Utilizador 
     *         registado no sistema com u.username.equals(username)
     * @ensures Seja username um username que nao existe no sistema
     *          Eh criado u: Curador tal que u.username = username, 
     *          u.password = password e this.catUtilizadores.contains(u)
     */
    public void registarCurador(String username, String password) throws UsernameInvalidoException {
        this.catUtilizadores.adicionarCurador(username, password);
    }
}