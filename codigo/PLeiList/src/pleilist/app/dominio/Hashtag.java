package pleilist.app.dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import pleilist.app.dominio.videos.Video;

public class Hashtag {

	//ATRIBUTOS
	private String descricao;
	private Set<Video> videos; // para nao haver problema de duplicacao
	private Set<Playlist> playlists; // para nao haver problema de duplicacao

	//CONSTRUTOR
	/**
	 * Construtor de hashtag
	 * @param tag descricao do hashtag a ser criado
	 */
	public Hashtag(String tag) {
		this.descricao = tag;
		this.videos = new HashSet<>(); //1.2.b.1
		this.playlists = new HashSet<>(); //1.2.b.2
	}

	//METODOS
	@Override
	public boolean equals(Object h) {
		return h instanceof Hashtag && this.descricao.equals(((Hashtag) h).descricao);
	}

	//GETTERS
	/**
	 * Devolve todos os videos associados
	 * @return todos os videos associados
	 */
	public List<Video> getVideos() {
		return new ArrayList<>(this.videos);
	}
	
	/**
	 * Devolve todas as playlists associadas
	 * @return todas as playlists associadas
	 */
	public List<Playlist> getPlaylists() {
		return new ArrayList<>(this.playlists);
	}

	//METODOS ASSOCIA 
	/**
	 * Associa video, i.e. adiciona-o aos seus videos
	 * @param video video a associar
	 */
	public void associaVideo(Video video) {
		videos.add(video); //1.4.1
	}

	/**
	 * Associa playlist, i.e. adiciona-a as suas playlists
	 * @param playlist playlist a associar
	 */
	public void associaPlaylist(Playlist playlist) {
		this.playlists.add(playlist); //2.5.1
	}
}