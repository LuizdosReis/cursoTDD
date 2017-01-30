package br.com.caelum.leilao.dominio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import org.hamcrest.Matchers;

import static br.com.caelum.leilao.matcher.LeilaoMatcher.temUm;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.criadorDeLeilao;

public class LeilaoTest {
	
	private Usuario wozniak;
	private Usuario jobs;


	@Before
	public void criaUsuarios(){
		jobs = new Usuario("Steve Jobs");
		wozniak = new Usuario("Steve Wozniak");
	}
	
	@Test
	public void deveReceberUmLance(){
		 criadorDeLeilao criadorDeLeilao = new criadorDeLeilao();
		 Leilao leilao = criadorDeLeilao.para("Macbook").constroi();
		 
		 assertThat(leilao.getLances().size(),equalTo(0));
		
		leilao = criadorDeLeilao.lance(jobs, 2000.00).constroi();
		
		assertThat(leilao, temUm(new Lance(jobs, 2000.00)));
	}
	
	@Test
	public void deveReceberVariosLances(){
		
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.constroi();
		
		assertThat(leilao.getLances(), hasItems(
				new Lance(jobs, 2000.00),
				new Lance(wozniak, 3000.00)
			));
		
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(jobs, 3000.00)
				.constroi();
		
		assertThat(leilao.getLances(), hasItems(
				new Lance(jobs, 2000.00)
			));		
	}
	
	@Test
	public void naoDeveAceitarMaisDeCincoLancesPorUsuario(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.lance(jobs, 4000.00)
				.lance(wozniak, 5000.00)
				.lance(jobs, 6000.00)
				.lance(wozniak, 7000.00)
				.lance(jobs, 8000.00)
				.lance(wozniak, 9000.00)
				.lance(jobs, 10000.00)
				.lance(wozniak, 11000.00)
				.lance(jobs, 12000.00)				
				.constroi();
		
		
		assertThat(leilao.getLances(), hasItems(
				new Lance(jobs, 2000.00),
				new Lance(wozniak, 3000.00),
				new Lance(jobs, 4000.00),
				new Lance(wozniak, 5000.00),
				new Lance(jobs, 6000.00),
				new Lance(wozniak, 7000.00),
				new Lance(jobs, 8000.00),
				new Lance(wozniak, 9000.00),
				new Lance(jobs, 10000.00),
				new Lance(wozniak, 11000.00)			
			));	
		
	}
	
	@Test
	public void deveDobrarOUltimoLanceDoUsuario(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.constroi();
		
		leilao.dobraLance(jobs);
		
		assertThat(leilao, temUm(new Lance(jobs, 4000.00)));
	}
	
	
	@Test
	public void naoDeveDobrarOLanceDoUsuarioQueNaoDeveLance(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(wozniak, 3000.00)
				.constroi();
		
		leilao.dobraLance(jobs);
		
		assertThat(leilao, not(temUm(new Lance(jobs, 4000.00))));	
	}
	
	@Test
	public void devePegarOUltimoLanceDoUsuarioParaDobrar(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.lance(jobs, 4000.00)
				.lance(wozniak, 5000.00)
				.lance(jobs, 6000.00)
				.lance(wozniak, 7000.00)
				.lance(jobs, 8000.00)
				.lance(wozniak, 9000.00)
				.constroi();
		
		leilao.dobraLance(jobs);
		
		assertThat(leilao, temUm(new Lance(jobs, 16000.00)));	
	}
	
}
