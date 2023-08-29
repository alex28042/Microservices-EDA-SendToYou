package send.toyou.packagemicroapi.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.persistence.Package;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
@AutoConfigureMockMvc
public class PackageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConnectionFactory connectionFactory;

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript))
                .block();
    }

    @BeforeEach
    void init(@Value("classpath:/schema.sql") Resource schema) {
        this.executeScriptBlocking(schema);
    }

    @AfterEach
    void after(@Value("classpath:/clear.sql") Resource clear) {
        this.executeScriptBlocking(clear);
    }

    @SneakyThrows
    @Test
    void testAddPackage() {
        var pack = new Package();
        pack.setName("test");

        this.mockMvc.perform(post("/api/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pack)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @SneakyThrows
    @Test
    void testAddPackageFails() {
        this.mockMvc.perform(post("/api/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("fail")))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

   @SneakyThrows
    @Test
    void testfindPackage(@Value("classpath:/insert-data.sql") Resource insert) {
        executeScriptBlocking(insert);
        String idToTest = "test";

        this.mockMvc.perform(get("/api/package/{id}", idToTest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
