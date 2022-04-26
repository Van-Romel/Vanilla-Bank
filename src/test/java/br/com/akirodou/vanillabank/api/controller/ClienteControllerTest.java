//package br.com.akirodou.vanillabank.api.controller;
//
//import br.com.akirodou.vanillabank.api.service.ClienteService;
//import br.com.akirodou.vanillabank.config.ContextTest;
//import br.com.akirodou.vanillabank.model.dto.ClientePostDTO;
//import br.com.akirodou.vanillabank.model.dto.ClienteRespDTO;
//import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
//import br.com.akirodou.vanillabank.model.repository.ClienteRepository;
//import org.hamcrest.Matchers;
//import org.junit.Before;
//import org.junit.jupiter.api.*;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.stubbing.OngoingStubbing;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import static org.mockito.AdditionalAnswers.returnsFirstArg;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Order(1)
//class ClienteControllerTest extends ContextTest {
//
//    private ClienteController clienteController;
//    private ClienteRepository clienteRepository;
//    private ClienteService clienteService;
//
//    @BeforeEach
//    public void setup() {
//        clienteRepository = mock(ClienteRepository.class);
//        clienteService = new ClienteService(clienteRepository);
//        clienteController = new ClienteController(clienteService);
//    }
//
//    ClienteEntity CLIENTE_1 = new ClienteEntity() {{
//        setId(1L);
//        setCpf("499.647.600-19");
//        setNome("Kaguya Ōtsutsuki");
//    }};
//    ClienteEntity CLIENTE_2 = new ClienteEntity() {{
//        setId(2L);
//        setCpf("046.699.390-09");
//        setNome("kuru'pir");
//    }};
//
//    @Test
//    void deveRetornarStatus201_aoCadastrarCliente() throws Exception {
//
//        var clientePostDTO = new ClientePostDTO();
//        clientePostDTO.setNome("Cras justo odio");
//        clientePostDTO.setCpf("046.699.390-09");
////        clientePostDTO.setCpf("559.351.560-32");
//
//        Mockito.when(clienteController.save(any(ClientePostDTO.class))).thenReturn(returnsFirstArg());
//
//        this.getMockMvc()
//                .perform(
//                        post("/cliente")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(getMapper().writeValueAsString(clientePostDTO))
//                ).andDo(print())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()))
//                .andExpect(jsonPath("$.nome").value("Cras justo odio"))
//                .andExpect(jsonPath("$.cpf").value("046.699.390-09"));
//    }
//
//    @Test
//    void findAll() {
//    }
//
//    @Test
//    void getClientById() {
//    }
//
//    @Test
//    void updateClient() {
//    }
//
//    @Test
//    void deleteClient() {
//    }
//}