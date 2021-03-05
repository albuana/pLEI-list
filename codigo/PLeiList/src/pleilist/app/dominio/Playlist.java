package pleilist.app.dominio;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import pleilist.app.dominio.VideoInList;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.PlaylistTerminouException;
import pleilist.app.facade.dto.Entrada;

public class Playlist implements Observer {

	//ATRIBUTOS
	private String codigo; // CHAVE (id unico)
	private String nome;
	private Duration duracaoTotal;
	private Set<Hashtag> hashtags;
	private List<VideoInList> videosInList;
	private double classificacao;

	//CONSTRUTOR
	/**
	 * Cria uma nova playlist
	 * @param codigo codigo da playlist
	 * @param nomePlaylist nome da playlist
	 * @param utilizador utilizador que criou a playlist
	 */
	public Playlist(String codigo, String nomePlaylist) {
		this.codigo = codigo;
		this.nome = nomePlaylist;
		this.duracaoTotal = Duration.ZERO;
		this.hashtags = new HashSet<>(); //1.1.1
		this.videosInList = new ArrayList<>(); //1.1.2
		this.classificacao = 0; // dummy value
	}

	//METODOS
	@Override
	public boolean equals(Object p) {
		return p instanceof Playlist 
				&& this.codigo.equals(((Playlist) p).codigo);
	}

	//GETTERS
	
	/**
	 * Devolve o nome da playlist
	 * @return nome da playlist
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Devolve o codigo da playlist
	 * @return codigo da playlist
	 */
	public String getCodigo() {
		return this.codigo;
	}

	/**
	 * Devolve a media da classificacao dos videos desta playlist
	 * @return classificacao media dos videos desta playlist 
	 */
	public double getClassificacao() {
	    return this.classificacao;
	}

	/**
	 * Devolve a duracao total da playlist
	 * @return duracao da playlist
	 */
	public Duration getDuracao() {
		return this.duracaoTotal;
	}

	/**
	 * 
	 * @param indiceCorrente
	 * @return
	 * @throws PlaylistTerminouException
	 */
	public VideoInList getVideoInList(int indiceCorrente) throws PlaylistTerminouException {
	    if (this.videosInList.size() <= indiceCorrente) {
	        throw new PlaylistTerminouException(this.nome); 
	    } else {
	        return this.videosInList.get(indiceCorrente); //2.1	        
	    }
	}

	/**
	 * Devolve a constituicao da playlist, lista videos e hora inicio
	 * @return constituicao constituicao da playlist
	 */
	public List<Entrada> getConstituicao() {
		List<Entrada> constituicao = new ArrayList<>(); //1.1
		String nomeVideo;
		Duration horaInicio = Duration.ZERO;

		for (VideoInList vl : this.videosInList) {
			nomeVideo = vl.getNome(); //1.2
			constituicao.add(new Entrada(nomeVideo, horaInicio)); //1.4
			horaInicio =  horaInicio.plus(vl.getDuracao()); //1.3
		}
		return constituicao;
	}

	//METODOS ASSOCIA E ATUALIZA
	/**
	 * 
	 * @param v
	 * @return
	 */
	public VideoInList associaVideo(Video v) {
		v.addObserver(this);
		
		VideoInList vl = new VideoInList(v, this); //2.1
		this.videosInList.add(vl); //2.2

		List<Hashtag> hashtagsVideo = v.getHashtags(); //2.3
		for(Hashtag h : hashtagsVideo) { //2.4
			h.associaPlaylist(this); //2.5
			this.hashtags.add(h); //2.6
		}
		this.atualizaClassificacao();
		return vl;
	}

	/**
	 * Devolve a duracao de um video atualizada
	 * @param duracao duracao de um video
	 */
	public void atualizaDuracao(Duration duracao) {
		this.duracaoTotal = this.duracaoTotal.plus(duracao);		
	}
	
	/**
	 * Atualiza a classificacao deste video, percorrendo todas as
	 * classificacoes atribuidas a este video
	 */
	private void atualizaClassificacao() {
        double somatorio = 0;

        for(VideoInList vl : this.videosInList) {
            somatorio += vl.getClassificacao();
        }
        this.classificacao =  somatorio / this.videosInList.size();
    }

    @Override
    public void update(Observable o, Object arg) {
        atualizaClassificacao();
    }
}