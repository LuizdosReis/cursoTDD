package br.com.caelum.leilao.sistema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private OptionalDouble maiorDeTodos;
	private OptionalDouble menorDeTodos;
	private OptionalDouble mediaDosLances;
	private List<Lance> tresMaiores;

	public void avalia(Leilao leilao) {
		if(leilao.getLances().size() == 0)
			throw new RuntimeException("Não é possivel avaliar um leilão sem lances");
			
		maiorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).max();
		menorDeTodos = leilao.getLances().stream().mapToDouble(Lance::getValor).min();
		mediaDosLances = leilao.getLances().stream().mapToDouble(Lance::getValor).average();

		pegaOsTresMaioresNo(leilao);

	}

	private void pegaOsTresMaioresNo(Leilao leilao) {
		List<Lance> lances = new ArrayList<>(leilao.getLances());
		lances.sort(new Comparator<Lance>() {

			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() > o2.getValor())
					return -1;
				if (o1.getValor() < o2.getValor())
					return 1;
				return 0;
			}

		});
		tresMaiores = lances.subList(0, leilao.getLances().size() > 3 ? 3 : leilao.getLances().size());
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

	public List<Lance> getTresMaiores() {
		return tresMaiores;
	}
}
