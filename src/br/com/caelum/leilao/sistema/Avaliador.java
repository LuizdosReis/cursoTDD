package br.com.caelum.leilao.sistema;

import java.util.OptionalDouble;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private OptionalDouble maiorDeTodos;
	private OptionalDouble menorDeTodos;
	private OptionalDouble mediaDosLances;

	public void avalia(Leilao leilao) {
		maiorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).max();
		menorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).min();
		mediaDosLances = leilao.getLances().stream().mapToDouble(Lance::getValor).average();

	}

	public OptionalDouble getMaiorLance() {
		return maiorDeTodos;
	}

	public OptionalDouble getMenorLance() {
		return menorDeTodos;
	}

	public OptionalDouble getMediaDosLances() {
		return mediaDosLances;
	}
}
