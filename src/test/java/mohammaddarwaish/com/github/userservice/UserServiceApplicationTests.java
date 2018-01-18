package mohammaddarwaish.com.github.userservice;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"management.port="})
@ActiveProfiles("test")
@WebAppConfiguration
@AutoConfigureMockMvc
@Ignore
public class UserServiceApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void prepareMockMvc() {
        mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void healthAndMetricsCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/env")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/configprops")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/info")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/loggers")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/trace")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/beans")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/autoconfig")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/mappings")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/metrics")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/auditevents")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/dump")).andExpect(status().isOk());
    }

}
