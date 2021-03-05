package pleilist.app.excepcoes;

public class NaoHaVideosComTagException extends Exception {
    //METODOS
    @Override
    public String toString() {
        return "Nao ha videos com o tag especificado.";
    } 
}