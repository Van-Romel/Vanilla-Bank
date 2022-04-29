package br.com.akirodou.vanillabank;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.akirodou.vanillabank.api.service.ClienteService;
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
class ClienteServiceTests {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepositoryMock;

    
    private static ClienteEntity clienteEsperado() {
    	
        ClienteEntity clienteEsperado = new ClienteEntity(); 
        clienteEsperado.setId(1L);
        clienteEsperado.setNome("Guilherme");
        clienteEsperado.setCpf("12345678910");
        
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
    	
    	assertThrows(GlobalApplicationException.class, () -> clienteService.findById(1L));

    }
    
    @Test
    void verificaSeEncontraOCpf() {
    	ClienteEntity clienteEsperado = clienteEsperado(); 
    	
    	when(clienteRepositoryMock.findByCpf(any())).thenReturn(Optional.of(clienteEsperado));
    	
    	ClienteEntity clienteRetorno = assertDoesNotThrow(() -> clienteService.findByCpf("12345678910"));
    	
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
    	
    	clienteService.delete(1L);
    
    	verify(clienteRepositoryMock, times(1)).deleteById(1L);

    
    }
  

}