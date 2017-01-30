package br.com.caelum.leilao.dominio;

import org.junit.Before;
import org.junit.Test;

public class LanceTest {
	
	private Usuario jorge;
	
	@Before
	public void setUp(){
		jorge = new Usuario("jorge");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deveLancarExcecaoParaValorDoLanceIgualAZero(){
		Lance lance = new Lance(jorge, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deveLancarExcecaoParaValorDoLanceMenorQueZero(){
		Lance lance = new Lance(jorge, -1);
	}

}
