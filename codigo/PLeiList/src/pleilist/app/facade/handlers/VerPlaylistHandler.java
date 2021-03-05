package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.List;

import pleilist.app.dominio.Playlist;
import pleilist.app.dominio.VideoInList;
import pleilist.app.dominio.catalogos.CatalogoHashtags;
import pleilist.app.dominio.catalogos.CatalogoPlaylists;
import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.excepcoes.ClassificacaoInvalidaExeption;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;
import pleilist.app.excepcoes.PlaylistTerminouException;
import pleilist.app.facade.dto.Pair;

public class VerPlaylistHandler {

	//ATRIBUTOS
	private Utilizador utilizador;
	private Playlist playlist;
	private VideoInList videoInList;
	private int indiceCorrente;
	private CatalogoHashtags catHashtags;
	private CatalogoPlaylists catPlaylists;

	//CONSTRUTOR
	/**
	 * Construtor do handler para a UC4 visualizar uma playlist
	 * @param utilizador
	 * @param catHashtags
	 * @param catPlaylists
	 */
	public VerPlaylistHandler(Utilizador utilizador, CatalogoHashtags catHashtags, CatalogoPlaylists catPlaylists) {
		this.utilizador = utilizador;
		this.indiceCorrente = -1;
		this.catHashtags = catHashtags;
		this.catPlaylists = catPlaylists;
	}

	//METODOS
	/**
	 * Devolve a lista de playlists relacionadas com tag com nome, codigo e classificacao
	 * @param tag descricao de hashtag a procurar
	 * @return lista de playlists relacionadas com tag com nome, codigo e classificacao
	 * @requires this.utilizador != null
	 */
	public List<pleilist.app.facade.dto.Playlist> procurarPorTag(String tag) {
		List<pleilist.app.dominio.Playlist> playlistsComHashtag = this.catHashtags.getPlaylistsHashtag(tag); //1.
		return this.catPlaylists.convertePlaylists(playlistsComHashtag); //2.

		//		List<Playlist> ls = new ArrayList<>();
		//		ls.add(new Playlist("Playlist 1", "teste", 4.5));
		//		return ls;
	}

	/**
	 * Devolve a duracao de uma playlist cujo código eh o dado
	 * @param codigo Codigo da playlis 
	 * @return duracao total da playlist
	 * @throws CodigoNaoConhecidoException se o codigo nao eh conhecido
	 * @ensures Seja p: Playlist tal que p.codigo = codigo
	 *          this.playlist = p
	 */
	public Duration escolhePlaylist(String codigo) throws CodigoNaoConhecidoException {
		this.playlist = this.catPlaylists.getPlaylist(codigo); //1.
		return this.playlist.getDuracao(); //2.
	}

	/**
	 * Devolve o proximo video da playlist
	 * @return Devolve o endereco e a duracao do proximo video da playlist
	 * @requires this.playlist != null
	 * @ensures Seja vl: VideoInList tal que this.playlist.contains(vl) ainda nao visto
	 *          this.videoInList = vl
	 */
	public Pair<String, Duration> verProximo() {
		try {
			this.videoInList = this.playlist.getVideoInList(++indiceCorrente); //2.
			return new Pair<>(this.videoInList.getEndereco(), this.videoInList.getDuracao()); //3. e 4.
		} catch (PlaylistTerminouException e) {
			e.printStackTrace();
		} 
		return null; 
		//return new Pair<>("yzg", Duration.ofHours(1));
	}

	/**
	 * Devolve uma nova classificacao ao video, ou altera a classificacao do mesmo 
	 * se este jeh tiver tido classificacao
	 * @param estrelas Classificacao
	 * @throws ClassificacaoInvalidaExeption se a classificacao eh invalida
	 * @requires u: this.utilizador != null && vl: this.videoInList != null
	 * @ensures Caso existe cl: Classificacao tal que cl associado a vl e cl associado a u
	 *          cc eh criado cl e associado a vl e a u
	 *             cl.estrelas = estrelas
	 */
	public void classificar(int estrelas) throws ClassificacaoInvalidaExeption {
		if(estrelas >= 1 && estrelas <= 5) {
			this.videoInList.adicionaClassificacao(estrelas, this.utilizador); //1.
		}else {
			throw new ClassificacaoInvalidaExeption(estrelas);
		}
	}
}