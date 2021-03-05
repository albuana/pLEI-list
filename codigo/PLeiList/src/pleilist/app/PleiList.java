package pleilist.app;

import java.util.Optional;

import pleilist.app.dominio.bibliotecas.BibliotecaGeral;
import pleilist.app.dominio.catalogos.CatalogoHashtags;
import pleilist.app.dominio.catalogos.CatalogoPlaylists;
import pleilist.app.dominio.catalogos.CatalogoUtilizadores;
import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.excepcoes.NaoEhCuradorException;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.CriarPlaylistAutomaticamenteHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

/**
 * Esta é a classe do sistema.
 */
public class PleiList {

    //ATRIBUTOS
    private CatalogoUtilizadores catUtilizadores;
    private CatalogoHashtags catHashtags;
    private CatalogoPlaylists catPlaylists;
    private BibliotecaGeral bGeral;

    //CONSTRUTOR
    /**
     * Construtor do sistema pLEI list
     */
    public PleiList() {
        this.catUtilizadores = new CatalogoUtilizadores();
        this.catHashtags =  new CatalogoHashtags();
        this.catPlaylists = new CatalogoPlaylists();
        this.bGeral = new BibliotecaGeral();
    }

    //METODOS
    
    /**
     * Cria e devolve um novo handler para a UC registar um utilizador
     * @return handler para adicionar um novo utilizador ao sistema
     */
    public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
        return new RegistarUtilizadorHandler(this.catUtilizadores);
    }

    /**
     * Devolve uma Sessao opcional representado o utilizador autenticado 
     * @param username nome do utilizador a autenticar
     * @param password password do utilizador a autenticar
     * @return Seja u: Utilizador registado no sistema tal que u.username.equals(username) 
     *         && u.password.equals(password)
     *              eh criado s: Sessao, tal que s.getUtilizador().equals(u)
     *         cc 
     *              eh criada uma sessao vazia 
     * @ensures Seja u: Utilizador tal que u.username.equals(username) e u.password.equals(password), 
     *          eh criada s: Sessao com s.utlizador.equals(u)
     */
    public Optional<Sessao> autenticar(String username, String password) {
        Optional<Utilizador> talvezUtilizador = this.catUtilizadores.getUtilizador(username, password);
        return talvezUtilizador.map((u) -> new Sessao(u));
    }

    /**
     * Devolve um handler para adicionar videos
     * @param s sessao do curador que quer adicionar um video
     * @return handler para adicionar videos
     * @requires s.getUtilizador() instanceof Curador
     * @ensures Seja c: Curador tal que c = s.getUtilizador()
     *          eh criado h: AdicionarVideoHandler em que c é o curador corrente
     * @throws NaoEhCuradorException se !(s.getUtilizador() instanceof Curador)
     */
    public AdicionarVideoHandler getAdicionarVideoHandler(Sessao s) throws NaoEhCuradorException {
        return new AdicionarVideoHandler(s.getCurador(), bGeral, catHashtags);
    }

    /**
     * Devolve um handler para UC2 - criar playlist manualmente
     * @param s sessao do curador que quer criar um playlist manualmente
     * @return handler para criar playlist manualmente
     * @requires s.getUtilizador() instanceof Curador
     * @ensures Seja c: Curador tal que c = s.getUtilizador()
     *          eh criado h: AdicionarVideoHandler em que c é o curador corrente
     * @throws NaoEhCuradorException se !(s.getUtilizador() instanceof Curador)
     */
    public CriarPlayListHandler getCriarPlayListHandler(Sessao s) throws NaoEhCuradorException {
        return new CriarPlayListHandler(s.getCurador(), catHashtags, bGeral, catPlaylists);
    }
    
    /**
     * Devolve um handler para UC3 - criar playlist automaticamente
     * @param s sessao do utilizador que quer criar um playlist automaticamente
     * @return handler para criar playlist automaticamente
     */
    public CriarPlaylistAutomaticamenteHandler getCriarPlaylistAutomaticamenteHandler(Sessao s) {
        return new CriarPlaylistAutomaticamenteHandler(s.getUtilizador(), catPlaylists, bGeral);
    }
    
    /**
     * Devolve um handler para UC4 - visualizar playlist
     * @param s sessao do utilizador que quer criar visualizar uma playlist 
     * @return handler para criar visualizar playlist
     */
    public VerPlaylistHandler getVerPlaylistHandler(Sessao s) {
        return new VerPlaylistHandler(s.getUtilizador(), catHashtags, catPlaylists);
    }
}