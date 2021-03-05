package pleilist.app.dominio.videos;

public class IdentificationVideo {
	
	//ATRIBUTOS
    int id;
    
    // RECEITA PARA SINGLETON 2. instancia static
    private static final IdentificationVideo INSTANCE = new IdentificationVideo();
    
    //METODOS
    
    // RECEITA PARA SINGLETON 1. construtor private (ou protected)
    /**
     * Construtor privado (PADRAO: singleton) para Identification
     */
    private IdentificationVideo() {
        id = -1;
    }
    
    // RECEITA PARA SINGLETON 3. getter static public da instancia 
    /**
     * Devolve a instancia unica do construtor de Identification
     * @return instancia unica do construtor de Identification
     */
    public static IdentificationVideo getInstance() {
        return INSTANCE;
    }
    
    /**
     * Devolve o codigo unico do proximo video
     * @return codigo unico do proximo video
     */
    public String nextId() {
        id++;
        return String.valueOf(id);
    }
}
