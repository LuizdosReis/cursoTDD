package br.com.caelum.leilao.builder;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class criadorDeLeilao {

	private Leilao leilao;

	public criadorDeLeilao para(String descricao) {
		leilao = new Leilao(descricao);
		return this;
	}

	public criadorDeLeilao lance(Usuario usuario, double valor) {
		leilao.propoe(new Lance(usuario, valor));
		return this;
	}

	public Leilao constroi() {
		return leilao;
	}

}
