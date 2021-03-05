package pleilist.app.dominio.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pleilist.app.dominio.Playlist;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;

public class CatalogoPlaylists {

	//ATRIBUTOS
	private Map<String, Playlist> playlists;
	private int idPlaylist; // nao usamos o playlists.size() por causa da eventual UC em que se apagam playlists 


	//CONSTRUTOR
	/**
	 * Construtor 
	 */
	public CatalogoPlaylists() {
		this.playlists = new HashMap<>();
	}

	//METODOS
	/**
	 * Cria uma playlist com os dados indicados
	 * @param nomePlaylist nome a dar a nova playlist
	 * @param utilizador utilizador a associar a playlist
	 * @return playlist criada
	 */
	public Playlist criaPlaylist(String nomePlaylist) {
		Playlist p = new Playlist(String.valueOf(idPlaylist), nomePlaylist); //1.1
		this.playlists.put(String.valueOf(idPlaylist), p); //1.2 e 1.3
		idPlaylist ++;
		return p;
	}

	/**
	 * Converte uma lista de playlists do dominio numa lista de playlists dto
	 * @param playlists Lista de playlists a serem convertidas
	 * @return lista de playlists dto
	 */
	public List<pleilist.app.facade.dto.Playlist> convertePlaylists(List<Playlist> playlists) {
		List<pleilist.app.facade.dto.Playlist> dtoPlaylists = new ArrayList<>(); //1.

		for(pleilist.app.dominio.Playlist p : playlists) {
			dtoPlaylists.add(new pleilist.app.facade.dto.Playlist(p.getNome(), 
					p.getCodigo(), p.getClassificacao())); //2.
		}
		return dtoPlaylists;
	}

	//GETTERS
	/**
	 * Devolve a playlist cujo codigo corresponde ao codigo indicado
	 * @param codigo codigo da playlist a procurar
	 * @return Se this.playlists.containsKey(codigo), devolve a playlist 
	 *         cujo codigo corresponde ao codigo indicado 
	 * @throws CodigoNaoConhecidoException 
	 */
	public Playlist getPlaylist(String codigo) throws CodigoNaoConhecidoException {
		if (this.playlists.containsKey(codigo)) {
			return this.playlists.get(codigo); //1.1
		} else {
			throw new CodigoNaoConhecidoException("playlist", codigo);
		}
	}

//    Usado nos testes
//    public boolean contains(Playlist playlist) {
//        return this.playlists.containsValue(playlist);
//    }
}