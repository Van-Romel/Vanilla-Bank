package br.com.akirodou.vanillabank.api.service;

import br.com.akirodou.vanillabank.exception.GlobalApplicationException;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;
    @MockBean
    ClienteRepository clienteRepository;

    @BeforeAll
    void setUp() {
//        var cliente1 = new ClienteEntity();
//        cliente1.setNome("Cras justo odio");
//        cliente1.setCpf("37931265017");
//        clienteService.save(cliente1);
//        var cliente2 = new ClienteEntity();
//        cliente2.setNome("kuru'pir");
//        cliente2.setCpf("04669939009");
//        clienteService.save(cliente2);
    }

    @Test
    @DisplayName("Cadastra cliente")
    void deveRetornarCliente_aoCadastrarCliente() {
        var cliente = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.save(ArgumentMatchers.any(ClienteEntity.class))).thenReturn(cliente);

        clienteService.save(cliente);

        Mockito.verify(clienteRepository, Mockito.times(1)).save(ArgumentMatchers.any(ClienteEntity.class));
    }

    @Test
    @DisplayName("Tenta cadastrar cliente com cpf já cadastrado")
    void deveCairEmException_aoCadastrarClienteComCpfExistente() {
        var cliente = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.save(ArgumentMatchers.any(ClienteEntity.class))).thenThrow(new DataIntegrityViolationException(""));
        GlobalApplicationException exception = Assert.assertThrows(GlobalApplicationException.class, () -> {
            clienteService.save(cliente);
        });
        String expectedMessage = "Este CPF já é cadastrado.";
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Retorna Listagem de clientes")
    void deveRetornarListaDeClientes_aoListar() {

        var cliente = Mockito.mock(ClienteEntity.class);
        var cliente2 = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.findAll()).thenReturn(List.of(cliente, cliente2));

        clienteService.findAll();

        Mockito.verify(clienteRepository, Mockito.times(1)).findAll();
    }


    @Test
    @DisplayName("Busca cliente por id")
    void deveRetornarCliente_aoBuscarPeloId() {
        var id = 1L;
        var cliente = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.findById(id)).thenReturn(java.util.Optional.of(cliente));

        clienteService.findById(id);

        Mockito.verify(clienteRepository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Busca cliente por cpf")
    void deveRetornarCliente_aoBuscarPeloCpf() {
        var cpf = "37931265017";
        var cliente = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.findByCpf(cpf)).thenReturn(java.util.Optional.of(cliente));

        clienteService.findByCpf(cpf);

        Mockito.verify(clienteRepository, Mockito.times(1)).findByCpf(cpf);
    }

    @Test
    @DisplayName("Altera cliente")
    void naoDeveTerRetorno_aoAlterarCliente() {
        var clienteId = 2L;
        var cliente = Mockito.mock(ClienteEntity.class);

        Mockito.when(clienteRepository.findById(clienteId)).thenReturn(java.util.Optional.of(cliente));
        Mockito.when(clienteRepository.save(ArgumentMatchers.any(ClienteEntity.class))).thenReturn(cliente);

        clienteService.update(clienteId, cliente);

        Mockito.verify(clienteRepository, Mockito.times(1)).findById(clienteId);
        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    @DisplayName("Remove cliente")
    void naoDeveTerRetorno_aoRemoverCliente() {
        var clienteId = 2L;

        clienteService.delete(clienteId);

        Mockito.verify(clienteRepository, Mockito.times(1)).deleteById(ArgumentMatchers.eq(clienteId));
    }
}
//    @Test
//    void createClienteException() throws GlobalApplicationException {
//        ClienteEntity clienteDuplicate = new ClienteEntity();
//        clienteDuplicate.setNome("Nome");
//        clienteDuplicate.setCpf("123456555");
//        assertThrows(BusinessException.class, () -> {
//            clienteService.save(clienteDuplicate);
//        });
//    }
//
//    @Test
//    void findClienteIdEmpty() throws BusinessException {
//        assertThrows(BusinessException.class, () -> {
//            clienteService.findClienteById(10);
//        });
//    }
