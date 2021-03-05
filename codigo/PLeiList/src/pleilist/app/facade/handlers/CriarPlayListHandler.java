package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.List;

import pleilist.app.dominio.Playlist;
import pleilist.app.dominio.VideoInList;
import pleilist.app.dominio.bibliotecas.BibliotecaGeral;
import pleilist.app.dominio.catalogos.CatalogoHashtags;
import pleilist.app.dominio.catalogos.CatalogoPlaylists;
import pleilist.app.dominio.utilizadores.Curador;
import pleilist.app.dominio.videos.Stream;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;
import pleilist.app.excepcoes.NaoHaVideosComTagException;
import pleilist.app.facade.dto.Entrada;
import pleilist.app.facade.dto.Pair;

public class CriarPlayListHandler {

    //ATRIBUTOS
    private VideoInList videoInList;
    private Playlist playlist;
    private Curador curador;
    private CatalogoPlaylists catPlaylists;
    private CatalogoHashtags catHashtags;
    private BibliotecaGeral bGeral;

    //CONSTRUTOR
    /**
     * Construtor de handler para UC2 criar playlist manualmente handler
     * @param curador Curador 
     * @param catHashtags Catalogo de Hashtags
     * @param bGeral Biblioteca Geral
     * @param catPlaylists Catalgo de Playlists
     */
    public CriarPlayListHandler(Curador curador, CatalogoHashtags catHashtags, BibliotecaGeral bGeral, CatalogoPlaylists catPlaylists) {
        this.curador = curador;
        this.catPlaylists = catPlaylists;
        this.catHashtags = catHashtags;
        this.bGeral = bGeral;
    }

    //METODOS
    /**
     * Iniciar a criacao de uma playlist
     * @param nomePlaylist Nome da playlist
     * @requires this.courador != null
     * @ensures this.playlist != null &&
     *          this.playlist.nome = nomePlaylist &&
     *          this.catPlaylists.contains(this.playlist) &&
     */
    public void iniciaCriacao(String nomePlaylist) {
        this.playlist = this.catPlaylists.criaPlaylist(nomePlaylist); //1.
    }

    /**
     * 
     * @return constituicao Constituicao da playlist
     * @requires this.playlist != null
     */
    public List<Entrada> obterEntradasActuais() {
        return this.playlist.getConstituicao(); //1.
    }

    /**
     * Devolve a lista de v�deos com uma determinada hashtag.
     * @param tag hashTag de v�deos a procurar
     * @return uma lista de pares Titulo, C�digo
     * @requires this.curador != null
     */
    public List<Pair<String, String>> obterVideosComHashtag(String tag) {
        try {
            List<Video> videosComHashtag = this.catHashtags.getVideosHashtag(tag); //1.
            return this.curador.getVideosPermitidos(videosComHashtag, this.bGeral);
        } catch (NaoHaVideosComTagException e) {
            e.printStackTrace();
            return querTop10Video();
        }
        //		List<Pair<String, String>> ls = new ArrayList<>();
        //		ls.add(new Pair<>("Um Video", "XPTO"));
        //		return ls;
    }

    /**
     * Seleciona o v�deo a adicionar com um determinado c�digo.
     * @param codigo
     * @return se o v�deo � uma stream ou n�o
     * @throws CodigoNaoConhecidoException 
     * @requires this.playlist != null && this.curador != null
     * @ensures Seja v: video tal que v.codigo = codigo e tal que 
     *             this.curador.bPessoal.temVideo(codigo) || this.bGeral.temVideo(codigo)
     *          eh criado vl: VideoInList tal que vl.video = v vl eh a VideoInList corrente
     *          this.playlist.constains(vl)
     *          Para todo o h: Hashtag tal que v.hashtags.contains(h), this.playlist.hashtags.contains(h)
     *          Caso v instanceof Clip, vl.duracao = v.duracao
     */
    public boolean indicarCodigo(String codigo) throws CodigoNaoConhecidoException {
        Video v = this.curador.getVideo(codigo, bGeral); //1.
        this.videoInList = this.playlist.associaVideo(v); //2.
        return v instanceof Stream;
    }

    /**
     * Indica a duracao de um determinado video
     * @param duration Duracao
     * @requires this.videoInList != null && this.videoInList.video instance of Stream
     * @ensures this.videoInList.duracao = duration
     */
    public void indicaDuracao(Duration duration) {
        this.videoInList.setDuracao(duration); //1.
        this.playlist.atualizaDuracao(duration); //2.
    }

    //METODOS PRIVADOS
    private List<Pair<String, String>> querTop10Video() {
        return this.curador.getTop10VideosPermitidos(bGeral); //1.	
    }
}