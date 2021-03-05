package pleilist.app.dominio.utilizadores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import pleilist.app.dominio.bibliotecas.BibliotecaGeral;
import pleilist.app.dominio.bibliotecas.BibliotecaPessoal;
import pleilist.app.dominio.videos.Clip;
import pleilist.app.dominio.videos.Stream;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;
import pleilist.app.excepcoes.NaoHaVideosComTagException;
import pleilist.app.facade.dto.Pair;

public class Curador extends Utilizador {

    //ATRIBUTOS
    private BibliotecaPessoal bPessoal;

    //CONSTRUTOR
    /**
     * Construtor do curador 
     * @param username username do Curador a criar
     * @param password password do Utilizador a criar
     */
    public Curador(String username, String password) {
        super(username, password);
        this.bPessoal = new BibliotecaPessoal();
    }

    /**
     * Cria v: Video, sendo que se isClip, cria v: Clip, cc cria v: Stream
     * @param isClip boolean que indica se o tipo de video a criar eh do tipo Clip
     * @return o video criado
     * @ensures Se isClip eh criado v: Clip
     *          cc eh criado v: Stream 
     */
    public Video criaVideo(boolean isClip) {
        if(isClip) {
            return new Clip(); //1.1.a
        } else {
            return new Stream(); //1.1.b
        }
    }

    //GETTERS

    /**
     * Devolve video cujo codigo corresponde ao codigoVideo, se este se encontrar na 
     * bilioteca geral do sistema ou na biblioteca pessoal deste curdador
     * @param codigoVideo codigo do video a procurar
     * @param bGeral biblioteca geral do sistema
     * @return Seja v: Video tal que v existe na bGeral no na bPessoal e v.codigo.equals(codigoVideo)
     *              eh devolvido v
     * @throws CodigoNaoConhecidoException se !bPessoal.temVideo(codigoVideo) && !bGeral.temVideo(codigoVideo)
     *
     */
    public Video getVideo(String codigoVideo, BibliotecaGeral bGeral) throws CodigoNaoConhecidoException {
        if(this.bPessoal.temVideo(codigoVideo)) { //1.1
            return this.bPessoal.getVideo(codigoVideo); //1.2.a
        } else if(bGeral.temVideo(codigoVideo)) {
            return bGeral.getVideo(codigoVideo); //1.2.b
        } else {
            throw new CodigoNaoConhecidoException("video", codigoVideo);
        }
    }

    /**
     * Devolve os 10 videos mais visto que este curador pode ter acesso
     * No caso de existirem menos de 10 videos, devolve todos.
     * @param bGeral biblioteca geral do sistema
     * @return lista com os 10 videos mais visto que este curador pode ter acesso 
     */
    public List<Pair<String, String>> getTop10VideosPermitidos(BibliotecaGeral bGeral) {

        // videosPermitidos := lista com todos os videos que este utilizador pode ter acesso
        List<Video> videosPermitidos = this.bPessoal.getVideos(); //1.1
        videosPermitidos.addAll(bGeral.getVideos()); //1.2 e 1.3

        List<Pair<String, String>> result = new ArrayList<>(); //1.6.a
        
        // procura o vmax: Video com o maximo numero de visualizacoes 10x
        // de cada vez adiciona vmax a lista result e retira da lista videosPermitidos
        // (senao estava sempre a devolver o mesmo)
        for(int i = 0; i < 10; i++) {
            Optional<Video> talvezVmax = videosPermitidos.stream().max(new Comparator<Video>() {
                @Override
                public int compare(Video v1, Video v2) {
                    return v1.getNVisualizacoes() - v2.getNVisualizacoes();
                }
            });
            
            if(talvezVmax.isPresent()) {
                videosPermitidos.remove(talvezVmax.get()); //1.8.a
                result.add(new Pair<>(talvezVmax.get().getNome(), talvezVmax.get().getCodigo())); //1.9.a
                
            // se talvezVmax estah vazio, quer dizer que a lista nao tinha mais videos
            } else {
                return result;
            }
        }
        return result;
    }

    /**
     * Filtra uma lista de videos para ter apenas os videos que este curador esta autorizado a utilizar
     * @param videos list de videos a filtrar
     * @param bGeral biblioteca geral do sistema
     * @return lista de informacoes sobre os videos que este curador esta autorizado a utilizar
     * @throws NaoHaVideosComTagException se nao existir nenhum video que este curador esta autorizado a utilizar
     */
    public List<Pair<String, String>> getVideosPermitidos(List<Video> videos, BibliotecaGeral bGeral) 
            throws NaoHaVideosComTagException {
        List<Pair<String, String>> result = new ArrayList<>(); //2.1
        String idVideo;
        String nomeVideo;

        for(Video v : videos) {
            idVideo = v.getCodigo(); //2.2
            if(this.bPessoal.temVideo(idVideo) || bGeral.temVideo(idVideo)) { //2.3 e 2.4
                nomeVideo = v.getNome(); //2.5
                result.add(new Pair<>(nomeVideo, idVideo)); //2.6
            }
        }
        
        if(result.isEmpty()) {
            throw new NaoHaVideosComTagException();
        } else {
            return result;
        }
    }

    //METODOS ASSOCIA

    /**
     * Associa um video ah biblioteca pessoal deste curador
     * @param video video a associar a biblioteca
     */
    public void associaVideoBiblioteca(Video video) {
        this.bPessoal.associaVideo(video); //3.b.1
    }
}