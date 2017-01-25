package br.com.caelum.leilao.dominio;

import static org.junit.Assert.*;

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
		
		assertEquals(0, leilao.getLances().size());
		
		leilao = criadorDeLeilao.lance(jobs, 2000.00).constroi();
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
	}
	
	@Test
	public void deveReceberVariosLances(){
		
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.constroi();
		
		
		assertEquals(2,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
		assertEquals(3000.00, leilao.getLances().get(1).getValor(),0.0001);
		
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(jobs, 3000.00)
				.constroi();
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
		
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
				
		assertEquals(10,leilao.getLances().size());
		assertEquals(11000.00, leilao.getLances().get(9).getValor(),0.0001);
		
	}
	
	@Test
	public void deveDobrarOUltimoLanceDoUsuario(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(jobs, 2000.00)
				.lance(wozniak, 3000.00)
				.constroi();
		
		leilao.dobraLance(jobs);
		
		assertEquals(3,leilao.getLances().size());
		assertEquals(4000.00, leilao.getLances().get(2).getValor(),0.0001);		
	}
	
	
	@Test
	public void naoDeveDobrarOLanceDoUsuarioQueNaoDeveLance(){
		Leilao leilao = new criadorDeLeilao().para("Macbook")
				.lance(wozniak, 3000.00)
				.constroi();
		
		leilao.dobraLance(jobs);
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(3000.00, leilao.getLances().get(0).getValor(),0.0001);		
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
		
		assertEquals(9,leilao.getLances().size());
		assertEquals(16000.00, leilao.getLances().get(8).getValor(),0.0001);		
	}
	
}
