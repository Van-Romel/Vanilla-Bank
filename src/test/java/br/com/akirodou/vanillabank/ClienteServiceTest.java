package br.com.akirodou.vanillabank;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.api.service.ContaCorrenteService;
import br.com.akirodou.vanillabank.api.service.ContaEspecialService;
import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepositoryMock;
    @Mock
    ContaCorrenteService contaCorrenteServiceMock;
    @Mock
    ContaEspecialService contaEspecialServiceMock;

    
    private static ClienteEntity clienteEsperado() {
    	
        ClienteEntity clienteEsperado = new ClienteEntity(); 
        clienteEsperado.setId(1L);
        clienteEsperado.setNome("Guilherme");
        clienteEsperado.setCpf("36817371007");
        
        return clienteEsperado;
    }
    
    @Test
    void verificaSeEncontraOId() {
        ClienteEntity clienteEsperado = clienteEsperado(); 
     
        when(clienteRepositoryMock.findById(any())).thenReturn(Optional.of(clienteEsperado));

        ClienteEntity clienteRetorno = assertDoesNotThrow(() -> clienteService.findById(1L));

        assertEquals(clienteEsperado.getNome(), clienteRetorno.getNome());
        assertEquals(clienteEsperado.getCpf(), clienteRetorno.getCpf());
        assertEquals(clienteEsperado.getId(), clienteRetorno.getId());
    }
    
    @Test
    void verificaSeNaoEncontraOId() {
    	
    	when(clienteRepositoryMock.findById(any())).thenReturn(Optional.empty());
    	
    	assertThrows(GlobalApplicationException.class, () -> clienteService.findById(5L));

    }
    
    @Test
    void verificaSeEncontraOCpf() {
    	ClienteEntity clienteEsperado = clienteEsperado(); 
    	
    	when(clienteRepositoryMock.findByCpf("36817371007")).thenReturn(Optional.of(clienteEsperado));

    	ClienteEntity clienteRetorno = assertDoesNotThrow(() -> clienteService.findByCpf("36817371007"));
    	
    	assertEquals(clienteEsperado.getCpf(), clienteRetorno.getCpf());
    	assertEquals(clienteEsperado.getNome(), clienteRetorno.getNome());
        assertEquals(clienteEsperado.getId(), clienteRetorno.getId());
    }
    
    @Test
    void verificaSeNaoEncontraPorCpf() {
    	
    	when(clienteRepositoryMock.findByCpf(any())).thenReturn(Optional.empty());
    	
    	assertThrows(GlobalApplicationException.class, () -> clienteService.findByCpf("12345678910"));
    	
    }
    
    @Test
    void verificaSeSalva() { 
    	ClienteEntity clienteEsperado = clienteEsperado(); 
    	
    	when(clienteRepositoryMock.save(any())).thenReturn(clienteEsperado);
    	
    	ClienteEntity clienteRetorno = assertDoesNotThrow(() -> clienteService.save(clienteEsperado));
    	
    	assertEquals(clienteEsperado.getCpf(), clienteRetorno.getCpf());
    	assertEquals(clienteEsperado.getNome(), clienteRetorno.getNome());
        assertEquals(clienteEsperado.getId(), clienteRetorno.getId());
    }
    
    @Test
    void verificaSeDeleta() {

        when(clienteRepositoryMock.findById(any())).thenReturn(Optional.of(clienteEsperado()));
        when(contaCorrenteServiceMock.findByCliente(any())).thenReturn(Optional.empty());
        when(contaEspecialServiceMock.findByCliente(any())).thenReturn(Optional.empty());

    	clienteService.delete(1L);
    
    	verify(clienteRepositoryMock, times(1)).delete(any());

    
    }
  

}