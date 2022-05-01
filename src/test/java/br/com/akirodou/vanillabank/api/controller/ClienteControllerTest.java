package br.com.akirodou.vanillabank.api.controller;

import br.com.akirodou.vanillabank.config.ContextTest;
import br.com.akirodou.vanillabank.model.dto.ClientePostDTO;
import br.com.akirodou.vanillabank.model.dto.ClientePutDTO;
import br.com.akirodou.vanillabank.model.dto.ClienteRespDTO;
import br.com.akirodou.vanillabank.model.dto.ContaCorrentPostDTO;
import br.com.akirodou.vanillabank.model.entity.ClienteEntity;
import br.com.akirodou.vanillabank.model.entity.ContaCorrenteEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
class ClienteControllerTest extends ContextTest {

    ResultActions resultActions;

    @BeforeEach
    void setUp() throws Exception {
        resultActions = getMockMvc().perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                    setCpf("499.647.600-19");
                    setNome("Kaguya Ōtsutsuki");
                }})));
        resultActions = getMockMvc().perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                    setCpf("046.699.390-09");
                    setNome("kuru'pir");
                }})));
        getMockMvc().perform(post("/conta/corrente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMapper().writeValueAsString(new ContaCorrentPostDTO() {{
                    setCpf("499.647.600-19");
                }})));

    }

    @Test
    @Order(1)
    @DisplayName("Cadastra cliente")
    void deveRetornarStatus201_aoCadastrarCliente() throws Exception {


        resultActions = getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                    setCpf("562.900.320-89");
                                    setNome("Cras justo odio");
                                }}))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.notNullValue()))
                .andExpect(jsonPath("$.nome").value("Cras justo odio"))
                .andExpect(jsonPath("$.cpf").value("562.900.320-89"));
    }

    @Test
    @Order(2)
    @DisplayName("Tenta cadastrar cliente com cpf já cadastrado")
    void deveRetornarStatus400_aoCadastrarClienteJaCadastrado() throws Exception {

        getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                    setCpf("499.647.600-19");
                                    setNome("Cras justo odio");
                                }}))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Este CPF já é cadastrado."));
    }

    @Test
    @Order(3)
    @DisplayName("Tenta cadastrar cliente com cpf invalido")
    void deveRetornarStatus400_aoCadastrarClienteComCpfInvalido() throws Exception {

        getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                                                            setNome("Cras justo odio");
                                                                            setCpf("0466993900");
                                                                        }}
                                ))).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF inválido"));
    }

    @Test
    @Order(4)
    @DisplayName("Tenta cadastrar cliente com nome nulo")
    void deveRetornarStatus400_aoCadastrarClienteComNomeNulo() throws Exception {

        getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                    setCpf("562.900.320-89");
                                    setNome(null);
                                }}))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("São nescessarios nome e cpf para o cadastro de um cliente"));
    }

    @Test
    @Order(5)
    @DisplayName("Tenta cadastrar cliente com nome vazio")
    void deveRetornarStatus400_aoCadastrarClienteComNomeVazio() throws Exception {

        getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                    setCpf("562.900.320-89");
                                    setNome("");
                                }}))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O nome deve conter no minimo 3 caracteres e no maximo 255"));
    }

    @Test
    @Order(6)
    @DisplayName("Tenta cadastrar cliente com nome com mais de 255 caracteres")
    void deveRetornarStatus400_aoCadastrarClienteComNomeComMaisDe255Caracteres() throws Exception {

        var clientePostDTO = new ClientePostDTO();
        clientePostDTO.setNome("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, erat nec euismod aliquam, mauris ipsum ultrices erat, eget consectetur eros tortor eu nisl. In quis consectetur erat, id euismod ipsum. Donec eget nibh eget erat suscipit aliquam. Mauris euismod, ante sed euismod aliquam, mauris ipsum ultrices erat.");
        clientePostDTO.setCpf("969.936.050-07");

        getMockMvc()
                .perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(clientePostDTO))
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O nome deve conter no minimo 3 caracteres e no maximo 255"));
    }

    @Test
    @Order(7)
    @DisplayName("Busca clientes")
    void deveRetornarStatus200_aoListarTodosOsClientes() throws Exception {

        getMockMvc()
                .perform(
                        get("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());

    }

    @Test
    @Order(8)
    @DisplayName("Busca cliente por id")
    void deveRetornarStatus200_aoBuscarClientePorId() throws Exception {

        getMockMvc()
                .perform(
                        get("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cpf").value("499.647.600-19"))
                .andExpect(jsonPath("$.nome").value("Kaguya Ōtsutsuki"));
    }

    @Test
    @Order(9)
    @DisplayName("Tenta buscar cliente por id inexistente")
    void deveRetornarStatus404_aoBuscarClientePorIdInexistente() throws Exception {
        getMockMvc()
                .perform(
                        get("/cliente/{id}", -1)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"));
    }

    @Test
    @Order(10)
    @DisplayName("Tenta buscar cliente por id não numérico")
    void deveRetornarStatus404_aoBuscarClientePorIdNaoNumerico() throws Exception {
        getMockMvc()
                .perform(
                        get("/cliente/{id}", "teste")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"));
    }

    @Test
    @Order(11)
    @DisplayName("Altera cpf do cliente")
    void deveRetornarStatus204_aoAlterarCpfDoCliente() throws Exception {

        getMockMvc()
                .perform(
                        put("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePutDTO() {{
                                    setCpf("562.900.320-89");
                                }}))
                ).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(11)
    @DisplayName("Tenta alterar cpf do cliente para um já existente ou Nullo")
    void deveRetornarStatus400_aoAlterarCpfDoCliente() throws Exception {

        getMockMvc()
                .perform(
                        put("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePutDTO() {{
                                    setCpf("046.699.390-09");
                                }}))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Este CPF já é cadastrado"));

        getMockMvc()
                .perform(
                        put("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePutDTO() {{
                                                                            setCpf(null);
                                                                        }}
                                ))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("CPF não pode ser nullo"));
    }

    @Test
    @Order(12)
    @DisplayName("Altera nome do cliente")
    void deveRetornarStatus204_aoAlterarNomeDoCliente() throws Exception {

        getMockMvc()
                .perform(
                        put("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePutDTO() {{
                                                                            setNome("João");
                                                                        }}
                                ))).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(13)
    @DisplayName("Altera nome e cpf do cliente")
    void deveRetornarStatus204_aoAlterarNomeECpfDoCliente() throws Exception {

        getMockMvc()
                .perform(
                        put("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePutDTO() {{
                                                                            setNome("Cras justo odio");
                                                                            setCpf("562.900.320-89");
                                                                        }}
                                ))).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(14)
    @DisplayName("Deleta cliente")
    void deveRetornarStatus204_aoDeletarCliente() throws Exception {

        var id = getMapper().readValue(
                getMockMvc().perform(post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getMapper().writeValueAsString(new ClientePostDTO() {{
                                    setNome("Cras justo odio");
                                    setCpf("562.900.320-89");
                                }})))
                        .andReturn().getResponse().getContentAsString()
                , ClienteRespDTO.class).getId();
        getMockMvc()
                .perform(
                        delete("/cliente/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(15)
    @DisplayName("Tenta deletar cliente com conta associada")
    void deveRetornarStatus400_aoDeletarClienteComContaAssociada() throws Exception {
        getMockMvc()
                .perform(
                        delete("/cliente/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cliente possui contas vinculadas, não pode ser excluído"));
    }
}