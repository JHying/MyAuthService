
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author H-yin on 2021.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestTest extends AbstractRestTest {

    @Override
    @BeforeAll
    public void setUp() {
        super.setUp();
    }

    @Test
    public void login() {
        try {
            String uri = "/test";
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            assertNotNull("response successfully", content);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
