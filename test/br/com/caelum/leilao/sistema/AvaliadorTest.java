package br.com.caelum.leilao.sistema;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	@Test
	public void avaliaLancesEmOrdemCrecente() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(400.00, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.00, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}

	@Test
	public void avaliaLancesEmOrdemDecrecente() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 250.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(400.00, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.00, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}


	@Test
	public void avaliaLancesRandomicos() {
		Usuario joao = new Usuario("Joao");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		Random random = new Random();

		List<Double> lances = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			Double lance = random.nextDouble();
			lances.add(lance);
			leilao.propoe(new Lance(joao, lance));

		}

		lances.sort(Comparator.comparingDouble(Double::doubleValue));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(lances.get(lances.size() - 1), leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(lances.get(0), leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}

	@Test
	public void avaliaLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("Joao");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(250.0, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.0, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}

	@Test
	public void avaliaMediaDosLances() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		Double media = (250.00 + 300.00 + 400.00) / 3;

		assertEquals(media, leiloeiro.getMediaDosLances().getAsDouble(), 0.0001);
	}

	@Test
	public void avaliaOsTresMaioresLances() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 100.0));
		leilao.propoe(new Lance(maria, 50.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(3, tresMaiores.size());
		assertEquals(400.00, tresMaiores.get(0).getValor(), 0.0001);
		assertEquals(300.00, tresMaiores.get(1).getValor(), 0.0001);
		assertEquals(250.00, tresMaiores.get(2).getValor(), 0.0001);

	}
	
	@Test
	public void avaliaLeilaoCom2Lances() {
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(2, tresMaiores.size());
		assertEquals(300.00, tresMaiores.get(0).getValor(), 0.0001);
		assertEquals(250.00, tresMaiores.get(1).getValor(), 0.0001);

	}
	
	@Test
	public void avaliaLeilaoVazio() {

		Leilao leilao = new Leilao("Playstation 3 Novo");

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(0, tresMaiores.size());
		
	}
	

}
