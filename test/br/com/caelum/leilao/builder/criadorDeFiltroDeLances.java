package br.com.caelum.leilao.builder;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.sistema.FiltroDeLances;

public class criadorDeFiltroDeLances {

	private FiltroDeLances filtroDeLances;
	private ArrayList<Lance> lances;

	public criadorDeFiltroDeLances cria() {
		filtroDeLances = new FiltroDeLances();
		lances = new ArrayList<Lance>();
		return this;
	}

	public criadorDeFiltroDeLances lance(Usuario usuario, double valor) {
		lances.add(new Lance(usuario, valor));
		return this;
	}

	public List<Lance> controi() {
		return filtroDeLances.filtra(lances);
	}

	
	

}
