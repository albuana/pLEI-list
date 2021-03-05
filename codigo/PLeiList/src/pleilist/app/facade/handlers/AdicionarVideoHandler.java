package pleilist.app.facade.handlers;

import java.time.Duration;

import pleilist.app.checkers.AddressChecker;
import pleilist.app.checkers.adapters.ComCheckerAdapter;
import pleilist.app.dominio.Hashtag;
import pleilist.app.dominio.bibliotecas.BibliotecaGeral;
import pleilist.app.dominio.catalogos.CatalogoHashtags;
import pleilist.app.dominio.utilizadores.Curador;
import pleilist.app.dominio.videos.Clip;
import pleilist.app.dominio.videos.Video;
import pleilist.app.excepcoes.EnderecoInvalidoException;
import utils.Configuration;

public class AdicionarVideoHandler {

    //ATRIBUTOS
    private Curador curador;
    private Video video;
    private BibliotecaGeral bGeral;
    private CatalogoHashtags catHashtags;

    //CONSTRUTOR
    /**
     * Construtor de handler para UC1 adicionar video a biblioteca
     * @param curador Curador que vai adicionar o video
     * @param catHashtags Catalogo de Hashtags 
     * @param bGeral Biblioteca Geral
     */
    public AdicionarVideoHandler(Curador curador, BibliotecaGeral bGeral, CatalogoHashtags catHashtags) {
        this.curador = curador;
        this.bGeral = bGeral;
        this.catHashtags = catHashtags;
    }

    //METODOS
    /**
     * Metodo que nao faz nada
     * @requires Existe c: curador corrente e autenticaado
     */
    public void iniciarAdicionar() {
        //Nao faz "nada"
    }
    
    /**
     * Delega ao curador para criar v: Video
     * @param isClip boolean que indica se o tipo de video a criar eh do tipo Clip
     * @requires Existe um curador corrente, i.e this.curador != null
     * @ensures Se isClip eh criado v: Clip
     *          cc eh criado v: Stream 
     *          v eh o video corrente, i.e. this.video = v
     */
    public void definirComoClip(boolean isClip) {
        this.video = this.curador.criaVideo(isClip); //1.
    }

    /**
     * Atribui valores aos atributos de video
     * @param title nome a atribuir ao video
     * @param address endereco do video
     * @requires existe v: Video corrente, i.e. this.video != null
     * @ensures Seja address um endereco valido, v.nome = titulo e v.endereco = endereco
     * @throws EnderecoInvalidoException se address nao eh um endereco valido
     */
    public void indicaVideo(String title, String address) throws EnderecoInvalidoException {
        AddressChecker c = Configuration.getInstance().createInstanceFromKey("checker", new ComCheckerAdapter()); // PADRAO: adapter, reflection
        if(c.verificaEndereco(address)) {
            this.video.setTitulo(title); //1.
            this.video.setEndereco(address); //2.
        } else {
            throw new EnderecoInvalidoException(address);
        }
    }

    /**
     * Se v: video corrente for do tipo Clip, v.duracao = duration
     * @param duration duracao a atribuir ao clip
     * @requires existe v: Clip corrente, i.e. this.video instanceof Clip
     * @ensures se v: Clip corrente, v.duracao = duration
     */
    public void indicaDuracao(Duration duration) {
        if(this.video != null && this.video instanceof Clip) {
            ((Clip) this.video).setDuracao(duration); //1.
        }
    }

    /**
     * Associa o hashtag, cuja descricao eh tag, ao video corrente
     * @param tag descricao do hashtag a associar ao video corrente
     * @requires existe v: Video corrente, i.e. this.video != null
     * @ensures Seja h: Hashtag tal que h.descricao.equals(tag)
     *          ou eh criado h: Hashtag com h.descricao = tag
     *          h.videos.contains(v)
     *          v.hashtags.contains(h)
     */
    public void indicaTag(String tag) {
        if(this.video != null) {
            Hashtag h = this.catHashtags.associaVideoHashtag(this.video, tag); //1.
            this.video.associaHashtag(h); //2.
        }
    }

    /**
     * Termina a adicao do video
     * @param ePublico indica se o video eh publico ou privado
     * @return codigo do video criado
     * @requires existe v: Video corrente, i.e. this.video != null
     *           existe c: Curador corrente, i.e. this.curador != null
     * @ensures Caso ePublico seja b: BibliotecaGeral
     *          cc seja b: BibliotecaPrivada associada a c
     *          b.contains(v)
     */
    public String defineComoPublico(boolean ePublico) {
        if(this.video != null && this.curador != null) {
            
            if(ePublico) {
                this.bGeral.associaVideo(this.video); //3.a
            } else {
                this.curador.associaVideoBiblioteca(this.video); //3.b
            }
            return this.video.getCodigo(); //4.
        } else {
            return "";
        }
    }
}