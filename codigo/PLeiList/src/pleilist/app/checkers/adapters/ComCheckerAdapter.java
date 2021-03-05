package pleilist.app.checkers.adapters;

import com.chavetasfechadas.UrlChecker;

import pleilist.app.checkers.AddressChecker;

public class ComCheckerAdapter implements AddressChecker {

	//METODOS
    @Override
    public boolean verificaEndereco(String address) {
        UrlChecker u = new UrlChecker();
        u.setUrl(address);
        return u.validate();
    }
    
}
