package cz.cvut.fit.HW1;

import cz.cvut.fit.HW1.model.Person;
import cz.cvut.fit.HW1.model.SubmitDTO;
import cz.cvut.fit.HW1.model.Transformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;

@SpringBootTest(classes = HW1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransformationTest {
    @LocalServerPort
    private int port;

    private URL base;

    private String message;

    private String expectedMessage;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        this.message = "Dear Sir or Madam,\n" +
                "\n" +
                "please find the details about my booking below:\n" +
                "\n" +
                "===\n" +
                "Tour id: \"1\"\n" +
                "Location: \"Bohemian Switzerland\"\n" +
                "Person: \"Jan Novak\"\u0001\n" +
                "===\n" +
                "\n" +
                "Regards,\n" +
                "Jan Novak";
        this.expectedMessage = "{\"id\":\"1\",\"location\":\"Bohemian Switzerland\",\"person\":{\"name\":\"Jan\",\"surname\":\"Novak\"}}";
    }

    @Test
    public void transformationSubmitDto() {
        Person fakePerson = new Person("Jan", "Novak");
        SubmitDTO fakeSubmit = new SubmitDTO("1", "Bohemian Switzerland", fakePerson);
        Transformation transformation = new Transformation(message);
        transformation.transform();
        assertThat(transformation.getTransformedSubmit()).isEqualTo(fakeSubmit);
    }

    @Test
    public void postTransformation() {
        HttpEntity<String> request = new HttpEntity<>(message);
        ResponseEntity<String> response = template.postForEntity(base.toString() + "transformation/", request, String.class);
        assertThat(response.getBody()).isEqualTo(expectedMessage);
    }

}
