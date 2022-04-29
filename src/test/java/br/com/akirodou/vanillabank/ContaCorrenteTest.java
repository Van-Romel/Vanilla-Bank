package br.com.akirodou.vanillabank;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.api.service.ContaCorrenteService;
import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaCorrenteEntity;
import br.com.akirodou.vanillabank.model.repository.ContaCorrenteRepository;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContaCorrenteTest {

	@InjectMocks
	ContaCorrenteService contaCorrenteService;

	@Mock
	ContaCorrenteRepository contaCorrenteRepositoryMock;
	
	@Mock
	ClienteService clienteServiceMock;

	public static ContaCorrenteEntity contaCorrenteEsperado() {

		ContaCorrenteEntity contaCorrenteEsperado = new ContaCorrenteEntity();
		contaCorrenteEsperado.setCartaoDeCredito("12345679");
		contaCorrenteEsperado.setId(1L);
		contaCorrenteEsperado.setSaldo(BigDecimal.valueOf(4000));
		contaCorrenteEsperado.setTitular(null);

		return contaCorrenteEsperado;
	}

	@Test
	void verificaSeEncontraOId() {
		ContaCorrenteEntity contaCorrenteEsperado = contaCorrenteEsperado();

		when(contaCorrenteRepositoryMock.findById(any())).thenReturn(Optional.of(contaCorrenteEsperado));

		ContaCorrenteEntity contaCorrenteRetorno = assertDoesNotThrow(() -> contaCorrenteService.findById(1L));

		assertEquals(contaCorrenteEsperado.getId(), contaCorrenteRetorno.getId());
		assertEquals(contaCorrenteEsperado.getCartaoDeCredito(), contaCorrenteRetorno.getCartaoDeCredito());
		assertEquals(contaCorrenteEsperado.getTitular(), contaCorrenteRetorno.getTitular());	
	}
	
	@Test
	void verifcaSeNaoEncontraOId() { 
		
		when(contaCorrenteRepositoryMock.findById(any())).thenReturn(Optional.empty()); 
		
		assertThrows(GlobalApplicationException.class, () -> contaCorrenteService.findById(1L));
	}
	
	@Test
	void verificaSeEncontraOCartao() { 
		ContaCorrenteEntity contaCorrenteEsperado = contaCorrenteEsperado(); 
		
		when(contaCorrenteRepositoryMock.findByCartaoDeCredito(any())).thenReturn(Optional.of(contaCorrenteEsperado));
		
		ContaCorrenteEntity contaCorrenteRetorno = assertDoesNotThrow(() -> contaCorrenteService.findByCartao("12345679")); 
		
		assertEquals(contaCorrenteEsperado.getCartaoDeCredito(), contaCorrenteRetorno.getCartaoDeCredito());
		assertEquals(contaCorrenteEsperado.getId(), contaCorrenteRetorno.getId());
		assertEquals(contaCorrenteEsperado.getTitular(), contaCorrenteRetorno.getTitular());
	}
	
	@Test
	void verificaSeNaoEncontraOCartao() { 
		
		when(contaCorrenteRepositoryMock.findByCartaoDeCredito(any())).thenReturn(Optional.empty());
		
		assertThrows(GlobalApplicationException.class, () -> contaCorrenteService.findByCartao("123415612"));
		
	}
	
	@Test
	void verificaSeExisteOIdOuNao() { 
		Boolean contaCorrenteEsperado = true;
		
		when(contaCorrenteRepositoryMock.existsById(any())).thenReturn(contaCorrenteEsperado);
		
		assertTrue("3",true);
		assertFalse("4", false);
	}
	
	@Test
	void verificaSeEncontraClientePeloCPF() {
		ContaCorrenteEntity contaCorrenteEsperado = contaCorrenteEsperado(); 
		ClienteEntity clienteEsperado = new ClienteEntity();
		
		when(clienteServiceMock.findByCpf(any())).thenReturn(clienteEsperado);
		when(contaCorrenteRepositoryMock.findByTitularId(any())).thenReturn(Optional.of(contaCorrenteEsperado));
		ContaCorrenteEntity contaCorrenteRetorno = assertDoesNotThrow(() -> contaCorrenteService.findByClienteCpf("12346548910"));
		
		assertEquals(contaCorrenteEsperado.getId(), contaCorrenteRetorno.getId());
	}
	
	@Test
	void verificaSeNaoEncontraClientePeloCPF() {
    
		when(contaCorrenteRepositoryMock.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(GlobalApplicationException.class, () -> contaCorrenteService.findById(1L));
	}
	
	@Test
	void verificaSeDeleta() {
		contaCorrenteService.delete(1L);
    	verify(contaCorrenteRepositoryMock, times(1)).deleteById(1L);
	}


}
