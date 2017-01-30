package br.com.caelum.leilao.sistema;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.criadorDeFiltroDeLances;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;

public class FiltroDeLancesTest {
	
	private Usuario joao;

	@Before
	public void setUp(){
		joao = new Usuario("Joao");
	}
	
    @Test
    public void deveSelecionarLancesEntre1000E3000() {
    	List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,2000).
    			lance(joao,1000).
    			lance(joao,3000).
    			lance(joao, 800).
    			controi();
      	
        assertThat(resultado.get(0).getValor(), equalTo(2000.0));
    }



	@Test
    public void deveSelecionarLancesEntre500E700() {
		List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,600).
    			lance(joao,500).
    			lance(joao,700).
    			lance(joao, 800).
    			controi();
		
        assertThat(resultado.get(0).getValor(), equalTo(600.0));
    }
    
    @Test
    public void deveSelecionarLancesMaioresQue5000() {
    	List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,300).
    			lance(joao,800).
    			lance(joao,4000).
    			lance(joao,6000).
    			controi();
    	
        assertThat(resultado.get(0).getValor(), equalTo(6000.0));
    }
    
    @Test
    public void deveEliminarLancesMenoresQues500() {
    	List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,300).
    			lance(joao,200).
    			controi();
    	
        assertThat(resultado.size(),equalTo(0));
    }
    
    @Test
    public void deveEliminarLancesEntre700e1000() {
    	List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,750).
    			lance(joao,900).
    			controi();
        
        assertThat(resultado.size(),equalTo(0));
    }
    
    @Test
    public void deveEliminarLancesEntre3000e5000() {
    	List<Lance> resultado = new criadorDeFiltroDeLances().cria().
    			lance(joao,3001).
    			lance(joao,4999).
    			lance(joao,3000).
    			lance(joao, 800).
    			controi();
        
        assertThat(resultado.size(),equalTo(0));
    }
}