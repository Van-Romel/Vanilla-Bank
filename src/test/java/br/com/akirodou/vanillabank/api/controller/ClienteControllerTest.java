package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.api.service.ClienteService;
import br.com.akirodou.vanillabank.model.dto.ClientePostDTO;
import br.com.akirodou.vanillabank.model.dto.ClienteRespDTO;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

////@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
////@Order(1)
////@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ClienteController clienteController;

    private ClienteService clienteService;


    @BeforeEach
    public void setup() {
        clienteService = mock(ClienteService.class);
        clienteController = new ClienteController(clienteService);
    }

    ClienteEntity CLIENTE_1 = new ClienteEntity() {{
        setId(1L);
        setCpf("499.647.600-19");
        setNome("Kaguya Ōtsutsuki");
    }};
    ClienteEntity CLIENTE_2 = new ClienteEntity() {{
        setId(2L);
        setCpf("046.699.390-09");
        setNome("kuru'pir");
    }};

    @Test
    void deveRetornarStatus201_aoCadastrarCliente() throws Exception {

        var clientePostDTO = new ClientePostDTO();
        clientePostDTO.setNome("Cras justo odio");
        clientePostDTO.setCpf("046.699.390-09");
//        clientePostDTO.setCpf("559.351.560-32");

        mockMvc
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(clientePostDTO))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.nome").value("Cras justo odio"))
                .andExpect(jsonPath("$.cpf").value("046.699.390-09"));
    }

    @Test
    void deveRetornarStatus200_aoListarTodosOsClientes() {

        ClienteEntity clisnte1 = new ClienteEntity() {{
            setId(1L);
            setCpf("499.647.600-19");
            setNome("Kaguya Ōtsutsuki");
        }};
        ClienteEntity cliente2 = new ClienteEntity() {{
            setId(2L);
            setCpf("046.699.390-09");
            setNome("kuru'pir");
        }};

        when(this.clienteService.findAll()).thenReturn(
                new ArrayList<ClienteEntity>() {{
                    add(clisnte1);
                    add(cliente2);
                }}
        );

        given()
                .accept(MediaType.APPLICATION_JSON)
                .when()
                .get("/cliente")
                .then()
                .statusCode(HttpStatus.OK.value()).and().log().everything();
    }

    @Test
    void getClientById() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
    }
}