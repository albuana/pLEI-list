package pleilist.app.checkers.adapters;

import java.net.MalformedURLException;
import java.net.URL;

import net.padroesfactory.AddressVerifier;
import pleilist.app.checkers.AddressChecker;

public class NetCheckerAdapter implements AddressChecker {

	//METODOS
	@Override
	public boolean verificaEndereco(String address) {
		AddressVerifier a = new AddressVerifier();
		try {
			return a.verifyAddress(new URL(address));
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
