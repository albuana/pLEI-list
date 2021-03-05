package pleilist.app.facade.dto;

import java.time.Duration;

/**
 * Esta classe existe apenas para a camada dos handlers falar com os clientes. N�o � a classe item do modelo de dominio!!!
 *
 */

public class Entrada {

	//ATRIBUTOS
	private String nome;
	private Duration horaInicio;

	//CONSTRUTOR
	public Entrada(String nome, Duration duracao) {
		this.nome = nome;
		this.horaInicio = duracao;
	}
	
	//METODOS
	@Override
	public String toString() {
	    return "Nome: " + this.nome + "\nHora de Início: " + this.horaInicio;
	}
	
	//GETTERS
	public String getNome() {
	    return this.nome;
	}
	
	public Duration getHoraInicio() {
	    return this.horaInicio;
	}
}