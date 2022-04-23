package br.com.akirodou.vanillabank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class ContextTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}

