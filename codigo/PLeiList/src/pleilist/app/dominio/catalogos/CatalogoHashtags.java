package pleilist.app.dominio.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pleilist.app.dominio.Hashtag;
import pleilist.app.dominio.Playlist;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.NaoHaVideosComTagException;

public class CatalogoHashtags {

    //ATRIBUTOS
    private Map<String, Hashtag> hashtags;

    //CONSTRUTOR
    /**
     * Construtor 
     */
    public CatalogoHashtags() {
        this.hashtags = new HashMap<>();
    }

    //GETTERS

    /**
     * Devolve todos os videos associados h: Hashtag tal que h.descricao.equals(tag)
     * @param tag descricao do Hashtag que se quer os videos associados
     * @return Seja h: Hashtag tal que h.descricao.equals(tag), devolve todos os videos associados a h
     *         cc manda excepcao
     * @throws NaoHaVideosComTagException se nao existir h: Hashtag no sistema tal que h.descricao.equals(tag)
     */
    public List<Video> getVideosHashtag(String tag) throws NaoHaVideosComTagException {
        if(!this.hashtags.containsKey(tag)) {
            throw new NaoHaVideosComTagException(); // Ext 7a
        } else {
            Hashtag h = this.hashtags.get(tag); //1.1
            // Sabemos que nunca vai acontecer o caso de h.getVideos().isEmpty() pq 
            // os hashtags so sao criados quando sao adicionados aos videos
            return h.getVideos(); //1.2   
        }
    }

    /**
     * Devolve todos as playlists associadas h: Hashtag tal que h.descricao.equals(tag)
     * @param tag descricao do Hashtag que se quer as playlists associadas
     * @return Seja h: Hashtag tal que h.descricao.equals(tag), devolve todos as playlists associadas a h
     *         cc devolve uma lista vazia
     */
    public List<Playlist> getPlaylistsHashtag(String tag) {
        if(this.hashtags.containsKey(tag)) {
            Hashtag h = this.hashtags.get(tag); //1.1
            return h.getPlaylists(); //1.2	        
        }
        return new ArrayList<>();
    }

    // METODOS ASSOCIA
    /**
     * Associa o video ao h: Hashtag com h.descricao.equals(tag)
     * @param video video a associar a ao Hashtag
     * @param tag descricao do hashtag ao qual video deve ser associado
     * @return h: Hashtag tal que h.descricao.equals(tag)
     */
    public Hashtag associaVideoHashtag(Video video, String tag) {
        Hashtag h;

        if(this.hashtags.containsKey(tag)) { //1.1
            h = this.hashtags.get(tag); //1.2.a
        } else {
            h = new Hashtag(tag); //1.2.b
            this.hashtags.put(tag, h); //1.3.b
        }
        h.associaVideo(video); //1.4

        return h;
    }
}