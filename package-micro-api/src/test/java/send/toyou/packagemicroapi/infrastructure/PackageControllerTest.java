package send.toyou.packagemicroapi.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
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

    @BeforeEach
    void init() {

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

   /* @SneakyThrows
    @Test
    void testfindPackage() {
        var pack = new Package();
        pack.setName("test");

        this.mockMvc.perform(get("/api/package/{id}", "test"))
                .andExpect(status().isOk())
                .andDo(print());
    }*/
}
