package br.com.caelum.leilao.dominio;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeilaoTest {
	@Test
	public void deveReceberUmLance(){
		Leilao leilao = new Leilao("Macbook");
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.00));
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
	}
	
	@Test
	public void deveReceberVariosLances(){
		Leilao leilao = new Leilao("Macbook");
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.00));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000.00));
		
		assertEquals(2,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
		assertEquals(3000.00, leilao.getLances().get(1).getValor(),0.0001);
		
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario(){
		Leilao leilao = new Leilao("Macbook");
		Usuario usuario = new Usuario("Steve Jobs");
		
		
		leilao.propoe(new Lance(usuario, 2000.00));
		leilao.propoe(new Lance(usuario, 3000.00));
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(2000.00, leilao.getLances().get(0).getValor(),0.0001);
		
	}
	
	@Test
	public void naoDeveAceitarMaisDeCincoLancesPorUsuario(){
		Leilao leilao = new Leilao("Macbook");
		Usuario jobs = new Usuario("Steve Jobs");
		Usuario wozniak = new Usuario("Steve Wozniak");
		
		
		leilao.propoe(new Lance(jobs, 2000.00));
		leilao.propoe(new Lance(wozniak, 3000.00));
		
		leilao.propoe(new Lance(jobs, 4000.00));
		leilao.propoe(new Lance(wozniak, 5000.00));
		
		leilao.propoe(new Lance(jobs, 6000.00));
		leilao.propoe(new Lance(wozniak, 7000.00));
		
		leilao.propoe(new Lance(jobs, 8000.00));
		leilao.propoe(new Lance(wozniak, 9000.00));
		
		leilao.propoe(new Lance(jobs, 10000.00));
		leilao.propoe(new Lance(wozniak, 11000.00));
	
		leilao.propoe(new Lance(jobs, 12000.00));
		
		assertEquals(10,leilao.getLances().size());
		assertEquals(11000.00, leilao.getLances().get(9).getValor(),0.0001);
		
	}
	
	@Test
	public void deveDobrarOUltimoLanceDoUsuario(){
		Leilao leilao = new Leilao("Macbook");
		
		Usuario jobs = new Usuario("Steve Jobs");
		Usuario wozniak = new Usuario("Steve Wozniak");
		
		leilao.propoe(new Lance(jobs, 2000.00));
		leilao.propoe(new Lance(wozniak, 3000.00));
		
		leilao.dobraLance(jobs);
		
		assertEquals(3,leilao.getLances().size());
		assertEquals(4000.00, leilao.getLances().get(2).getValor(),0.0001);		
	}
	
	
	@Test
	public void naoDeveDobrarOLanceDoUsuarioQueNaoDeveLance(){
		Leilao leilao = new Leilao("Macbook");
		
		Usuario jobs = new Usuario("Steve Jobs");
		Usuario wozniak = new Usuario("Steve Wozniak");
		
		leilao.propoe(new Lance(wozniak, 3000.00));
		
		leilao.dobraLance(jobs);
		
		assertEquals(1,leilao.getLances().size());
		assertEquals(3000.00, leilao.getLances().get(0).getValor(),0.0001);		
	}
	
	
	@Test
	public void devePegarOUltimoLanceDoUsuarioParaDobrar(){
		Leilao leilao = new Leilao("Macbook");
		
		Usuario jobs = new Usuario("Steve Jobs");
		Usuario wozniak = new Usuario("Steve Wozniak");
		
		leilao.propoe(new Lance(jobs, 2000.00));
		leilao.propoe(new Lance(wozniak, 3000.00));
		
		leilao.propoe(new Lance(jobs, 4000.00));
		leilao.propoe(new Lance(wozniak, 5000.00));
		
		leilao.propoe(new Lance(jobs, 6000.00));
		leilao.propoe(new Lance(wozniak, 7000.00));
		
		leilao.propoe(new Lance(jobs, 8000.00));
		leilao.propoe(new Lance(wozniak, 9000.00));
		
		leilao.dobraLance(jobs);
		
		assertEquals(9,leilao.getLances().size());
		assertEquals(16000.00, leilao.getLances().get(8).getValor(),0.0001);		
	}
	
}
