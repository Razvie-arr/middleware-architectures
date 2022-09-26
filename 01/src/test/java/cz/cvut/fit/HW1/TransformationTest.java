package cz.cvut.fit.HW1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;

@SpringBootTest(classes = HW1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransformationTest {
    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getMessage() {
        String message = "springBootTest";
        ResponseEntity<String> response = template.getForEntity(base.toString()+ "test/" + message, String.class);
        assertThat(response.getBody()).isEqualTo("Your message is: " + message);
    }

}
