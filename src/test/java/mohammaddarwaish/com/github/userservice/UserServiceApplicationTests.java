package mohammaddarwaish.com.github.userservice;

import org.junit.Before;
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
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator//health")).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/info")).andExpect(status().isOk());
    }

}
