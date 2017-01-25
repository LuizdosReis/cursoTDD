package br.com.caelum.leilao.sistema;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.criadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario maria;
	private Usuario jose;
	private Usuario joao;
	
	@Before
	public void setUp() {
		leiloeiro = new Avaliador();
		joao = new Usuario("Joao");
		jose = new Usuario("Jos�");
		maria = new Usuario("Maria");
	}
	
	@Test
	public void avaliaLancesEmOrdemCrecente() {

		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao,250)
				.lance(jose,300)
				.lance(maria,400)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertEquals(400.00, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.00, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}


	@Test
	public void avaliaLancesEmOrdemDecrecente() {
		
		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 400.0)
				.lance(jose, 300.0)
				.lance(maria, 250.0)
				.constroi();


		leiloeiro.avalia(leilao);

		assertEquals(400.00, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.00, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}


	@Test
	public void avaliaLancesRandomicos() {

		criadorDeLeilao criadorDeleilao = new criadorDeLeilao().para("Playstation 3 Novo");

		Random random = new Random();

		List<Double> lances = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Double lance = random.nextDouble();
			lances.add(lance);
			if(i%2==0)
				criadorDeleilao.lance(joao, lance);
			else
				criadorDeleilao.lance(jose, lance);
		}
		
		Leilao leilao = criadorDeleilao.constroi();

		lances.sort(Comparator.comparingDouble(Double::doubleValue));

		leiloeiro.avalia(leilao);

		assertEquals(lances.get(lances.size() - 1), leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(lances.get(0), leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}

	@Test
	public void avaliaLeilaoComApenasUmLance() {

		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(maria, 250.0)
				.constroi();

		leiloeiro.avalia(leilao);

		assertEquals(250.0, leiloeiro.getMaiorLance().getAsDouble(), 0.0001);
		assertEquals(250.0, leiloeiro.getMenorLance().getAsDouble(), 0.0001);

	}

	@Test
	public void avaliaMediaDosLances() {

		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 400.0)
				.lance(jose, 300.0)
				.lance(maria, 250.0)
				.constroi();


		leiloeiro.avalia(leilao);

		Double media = (250.00 + 300.00 + 400.00) / 3;

		assertEquals(media, leiloeiro.getMediaDosLances().getAsDouble(), 0.0001);
	}

	@Test
	public void avaliaOsTresMaioresLances() {
		
		
		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.lance(maria, 100.0)
				.lance(jose, 50.0)
				.lance(maria, 400.0)
				.constroi();

		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(3, tresMaiores.size());
		assertEquals(400.00, tresMaiores.get(0).getValor(), 0.0001);
		assertEquals(300.00, tresMaiores.get(1).getValor(), 0.0001);
		assertEquals(250.00, tresMaiores.get(2).getValor(), 0.0001);

	}
	
	@Test
	public void avaliaLeilaoCom2Lances() {
		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.constroi();
		
		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(2, tresMaiores.size());
		assertEquals(300.00, tresMaiores.get(0).getValor(), 0.0001);
		assertEquals(250.00, tresMaiores.get(1).getValor(), 0.0001);

	}
	
	@Test
	public void avaliaLeilaoVazio() {

		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo").constroi();

		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();

		assertEquals(0, tresMaiores.size());
		
	}
	

}
