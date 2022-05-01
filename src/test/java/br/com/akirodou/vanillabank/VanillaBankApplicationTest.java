package br.com.akirodou.vanillabank;

import br.com.akirodou.vanillabank.config.ContextTest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VanillaBankApplicationTest extends ContextTest {

    @Order(0)
    @Test
    @DisplayName("Testa Estado da aplicação")
    public void givenTest_whenGetStatus_thenStatus200() {


    }
}