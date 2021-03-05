// "especie de catalogo de videos"

package pleilist.app.dominio.bibliotecas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pleilist.app.dominio.videos.Video;

public abstract class Biblioteca {

	//ATRIBUTOS
	protected Map<String, Video> videos;

	//CONSTRUTOR
	/**
	 * Construtor de biblioteca generico 
	 * eh protegido para poder usar o padrao singleton na BibliotecaGeral
	 */
	public Biblioteca() {
		this.videos = new HashMap<>();
	}

	//METODOS
	/**
	 * Adiciona um novo video a biblioteca
	 * @param video video a adicionar a biblioteca
	 */
	public void associaVideo(Video video) {
		this.videos.put(video.getCodigo(), video); //3.a.1, 3.a.2, 3.b.1.1 e 3.b.1.2
	}
	
	/**
	 * Indica se tem v: Video com v.codigo.equals(codigoVideo)
	 * @param codigoVideo codigo do video a testar se existe na biblioteca
	 * @return true se nesta biblioteca existe v: Video tal que v.codigo.equals(codigoVideo)
	 */
	public boolean temVideo(String codigoVideo) {
		return this.videos.containsKey(codigoVideo); //2.3.1 e 2.4.1
	}

	//GETTERS
	/**
	 * Retorna v: Video tal que v.codigo.equals(codigoVideo)
	 * @param codigoVideo codigo do video a receber
	 * @return Se temVideo(codigoVideo) v: Video tal que v.codigo.equals(codigoVideo)
	 *         cc null
	 * @requires temVideo(codigoVideo)
	 */
	public Video getVideo(String codigoVideo) {
	    if(temVideo(codigoVideo)) {
	        return this.videos.get(codigoVideo);	        
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Devolve todos os videos contidos nesta biblioteca
	 * @return todos os videos contidos nesta biblioteca
	 */
	public List<Video> getVideos() {
		return new ArrayList<>(this.videos.values()); //1.1.1 e 1.2.1
	}
}