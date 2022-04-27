package br.com.akirodou.vanillabank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class VanillaBankApplicationTests {

    @Test
    @DisplayName("Testa Estado da aplicação")
    public void givenTest_whenGetStatus_thenStatus200() {


    }
}
