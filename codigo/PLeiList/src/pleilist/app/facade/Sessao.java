package pleilist.app.facade;

import pleilist.app.dominio.utilizadores.Curador;
import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.excepcoes.NaoEhCuradorException;

public class Sessao {

    //ATRIBUTOS
    private Utilizador utilizador;

    //CONSTRUTOR
    /**
     * Construtor de uma sessao
     * @param u utilizador a quem pertence a sessao
     */
    public Sessao(Utilizador u) {
        this.utilizador = u;
    }

    //GETTERS
    /**
     * Devolve o utilizador desta sessao
     * @return utilizador desta sessao
     */
    public Utilizador getUtilizador() {
        return this.utilizador;
    }

    /**
     * Devolve o utilizador desta sessao, se for do nivel curador
     * @return Seja s.utilizador instanceof Curador, devolve o curador desta sessao
     * @requires s.utilizador instanceof Curador
     * @throws NaoEhCuradorException se !(s.utilizador instanceof Curador)
     */
    public Curador getCurador() throws NaoEhCuradorException {
        if(!(this.utilizador instanceof Curador)) {
            throw new NaoEhCuradorException(this.utilizador.getUsername());
        } else {
            return (Curador) this.utilizador;
        }
    } 
}