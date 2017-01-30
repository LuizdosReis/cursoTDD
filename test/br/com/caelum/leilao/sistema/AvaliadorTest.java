package br.com.caelum.leilao.sistema;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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
		jose = new Usuario("José");
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
		
		assertThat(leiloeiro.getMaiorLance().getAsDouble(),equalTo(400.00));
		assertThat(leiloeiro.getMenorLance().getAsDouble(),equalTo(250.00));
		
	}

	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeilaoSemNenhumLanceDado(){
		Leilao leilao = new criadorDeLeilao().para("Notebook Dell").constroi();
		
		leiloeiro.avalia(leilao);
		
	}

	@Test
	public void avaliaLancesEmOrdemDecrecente() {
		
		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 400.0)
				.lance(jose, 300.0)
				.lance(maria, 250.0)
				.constroi();


		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance().getAsDouble(), equalTo(400.00));
		assertThat(leiloeiro.getMenorLance().getAsDouble(), equalTo(250.00));

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

		assertThat(leiloeiro.getMaiorLance().getAsDouble(), equalTo(lances.get(lances.size() - 1)));
		assertThat(leiloeiro.getMenorLance().getAsDouble(), equalTo(lances.get(0)));
		
	}

	@Test
	public void avaliaLeilaoComApenasUmLance() {

		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(maria, 250.0)
				.constroi();

		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMaiorLance().getAsDouble(), equalTo(250.0));
		assertThat(leiloeiro.getMenorLance().getAsDouble(), equalTo(250.0));

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
		
		assertThat(leiloeiro.getMediaDosLances().getAsDouble(), equalTo(media));

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

		assertThat(tresMaiores, hasItems(
				new Lance(maria, 400.0),
				new Lance(jose, 300.0),
				new Lance(joao, 250.0)
			));

	}
	
	@Test
	public void avaliaLeilaoCom2Lances() {
		Leilao leilao = new criadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.constroi();
		
		leiloeiro.avalia(leilao);

		List<Lance> tresMaiores = leiloeiro.getTresMaiores();
		
		
		assertThat(tresMaiores, hasItems(
				new Lance(joao, 250.0),
				new Lance(jose, 300.0)
			));
		
	}
	
	

}
