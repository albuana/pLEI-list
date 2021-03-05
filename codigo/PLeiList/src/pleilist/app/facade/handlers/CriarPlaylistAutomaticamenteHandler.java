package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pleilist.app.dominio.Playlist;
import pleilist.app.dominio.VideoInList;
import pleilist.app.dominio.bibliotecas.BibliotecaGeral;
import pleilist.app.dominio.catalogos.CatalogoPlaylists;
import pleilist.app.dominio.utilizadores.Utilizador;
import pleilist.app.dominio.videos.Stream;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.EstrategiaInvalidaException;
import pleilist.app.playlistStrategies.PickVideosStrategy;
import pleilist.app.playlistStrategies.strategies.RandomVideosStrategy;
import utils.Configuration;

public class CriarPlaylistAutomaticamenteHandler {
	//ATRIBUTOS
	private Playlist playlist;
	private Utilizador utilizador;
	private CatalogoPlaylists catPlaylists;
	private BibliotecaGeral bGeral;
	private Configuration config;

	//CONSTRUTOR
	/**
	 * Construtor de handler para UC3 criar playlist automaticamente
	 * @param utilizador Utilizador
	 * @param catPlaylists Catalogo de Playlists
	 * @param bGeral biblioteca Geral
	 */
	public CriarPlaylistAutomaticamenteHandler(Utilizador utilizador, CatalogoPlaylists catPlaylists, BibliotecaGeral bGeral) {
		this.utilizador = utilizador;
		this.catPlaylists = catPlaylists;
		this.bGeral = bGeral;
		this.config = Configuration.getInstance();
	}

	//METODOS
	/**
	 * @param nome nome da playlist
	 * @return lista de estrategias que foram configuradas na aplicacao
	 * @requires Existe u: utilizador autenticado e corrente
	 * @ensures Seja cp: catalogo de playlists, 
	 *          eh criado p: playlist com p.nome = nome
	 *          cp.contains(p)
	 *          p eh a playlist corrente
	 */
	public List<String> iniciaCriacao(String nome) {
		this.playlist = this.catPlaylists.criaPlaylist(nome); //1.
		return Arrays.asList(config.getStringArray("playlistStrategies"));
	}
	
	/**
	 * Atribui os valores dos atributos
	 * @param strategy Estrategia a usar 
	 * @param nVideos Numero de videos a incluir
	 * @return código da playlist criada
	 * @throws EstrategiaInvalidaException se a estrategia nao eh valida
	 * @requires Existe p: playlist corrente, strategy existe no sistema
	 */
	public String indicaDadosPaylist(String strategy, int nVideos) throws EstrategiaInvalidaException {

		List<String> strategies = Arrays.asList(config.getStringArray("playlistStrategies"));

		if(strategies.contains(strategy)) {

			PickVideosStrategy s = config.createInstance(strategy, new RandomVideosStrategy());
			List<Video> videosAAdicionar = s.pickVideos(nVideos, this.bGeral.getVideos());

			for(Video v : videosAAdicionar) {
				VideoInList vl = playlist.associaVideo(v);
				if(v instanceof Stream) {
					Random random = new Random();
					vl.setDuracao(Duration.ofMinutes(random.nextInt(11) + 5));
				}
			}

		} else {
			throw new EstrategiaInvalidaException(strategy);
		}
		return this.playlist.getCodigo();
	}
}

