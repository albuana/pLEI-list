package pleilist.app.dominio.videos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import pleilist.app.dominio.Classificacao;
import pleilist.app.dominio.Hashtag;
import pleilist.app.dominio.utilizadores.Utilizador;

public abstract class Video extends Observable {

    //ATRIBUTOS
    protected String codigo; // chave (id unico) do video
    protected String nome;
    protected String endereco;
    protected int nVisualizacoes;
    protected double classificacaoMedia;
    protected Set<Hashtag> hashtags; // para nao haver problema de duplicacao
    protected List<Classificacao> classificacoes;


    //CONSTRUTOR
    /**
     * Construtor do video
     */
    public Video() {
        this.codigo = IdentificationVideo.getInstance().nextId();
        this.nome = ""; // dummy values
        this.endereco = ""; // dummy values
        this.nVisualizacoes = 0;
        this.classificacaoMedia = 0;
        this.hashtags = new HashSet<>(); //1.1.1
        this.classificacoes = new ArrayList<>(); //1.1.2
    }

    //METODOS
    @Override
    public boolean equals(Object v) {
        return v instanceof Video 
                && this.codigo.equals(((Video) v).getCodigo());
    }

    //GETTERS

    /**
     * Devolve o codigo do video
     * @return codigo do video
     */
    public String getCodigo() {
        return this.codigo; 
    }

    /**
     * Devolve o nome deste video, se este ja tiver sido atribuido
     * cc devolve uma string vazia
     * @return o nome deste video se ja tiver sido atribuido, cc uma string vazia
     */
    public String getNome() {
        return this.nome;	        
    }

    /**
     * Devolve a media de todas as classificacoes atribuidas a este video
     * @return classificao media deste video
     */
    public double getClassificacao() {
        return this.classificacaoMedia;
    }

    /**
     * Devolve o endereco deste video, se este ja tiver sido atribuido
     * cc devolve uma string vazia
     * @return o endereco deste video se ja tiver sido atribuido, cc uma string vazia
     */
    public String getEndereco() {
        return this.endereco;
    }
    
    /** 
     * Devolve uma lista de todos os Hashtags associados a este video
     * @return lista com todos os Hashtags associados a este video
     */
    public List<Hashtag> getHashtags() {
        return new ArrayList<>(this.hashtags);
    }

    /**
     * Devolve o numero de vezes que este video foi visto
     * @return numero de vezes que este video foi visto
     */
    public int getNVisualizacoes() {
        return this.nVisualizacoes;
    }

    //SETTERS
    
    /**
     * Coloca title como nome do video 
     * @param title nome a dar ao video
     */
    public void setTitulo(String title) {
        this.nome = title;		
    }

    /**
     * Coloca address como endereco do video
     * @param address endereco a atribuir ao video
     */
    public void setEndereco(String address) {
        this.endereco = address;
    }

    //METODOS ASSOCIA OU ADICIONA
    
    /**
     * Associa hashtag a este video, i.e. adiciona-o aos hastags deste video
     * @param hashtag hashtag a associar ao video
     */
    public void associaHashtag(Hashtag hashtag) {
        this.hashtags.add(hashtag); //2.1
    }

    /**
     * Adiciona uma nova classificacao a este video, ou altera a classificacao 
     * que utilizador jah atribuiu a este video
     * @param classificacao
     * @param utilizador
     * @ensures Seja c: Classificacao tal que c.utilizador.equals(utilizador) e c.video.equals(this), 
     *              c.estrelas = classificacao
     *          cc 
     *              eh criado c: Classficacao com c.estrelas = classificacao
     *              associa-se c a this
     *              associa-se c a utilizador
     */
    public void adicionaClassificacao(int classificacao, Utilizador utilizador) {
        boolean jaClassificou = false;
        for(Classificacao c : this.classificacoes) {
            jaClassificou = c.temUtilizador(utilizador);
            if(jaClassificou) { //1.1.1
                c.setClassificacao(classificacao); //1.1.2
                break; //1.1.3
            }
        }
        if(!jaClassificou) {
            Classificacao cl = new Classificacao(classificacao, utilizador, this); //1.1.4
            this.classificacoes.add(cl); //1.1.5
        }
        atualizaNVisualizacoes(); //1.1.6
        atualizaClassificacao(); //1.1.7
    }

    //METODOS PRIVADOS ATUALIZA
    
    /**
     * Atualiza a classificacaoMedia deste video, percorrendo todas as 
     * classificacoes atribuidas a este video
     */
    private void atualizaClassificacao() {
        double somatorio = 0;

        for (Classificacao c : this.classificacoes) {
            somatorio += c.getEstrelas();
        }
        this.classificacaoMedia = somatorio / this.classificacoes.size();

        // PADRAO: Observer - atualiza classificacao das playlists que contem este video 
        this.setChanged();
        this.notifyObservers();
    }

    /** 
     * Adiciona 1 ao numero de visualizacoes deste video
     */
    private void atualizaNVisualizacoes() {
        this.nVisualizacoes++;
    }
}