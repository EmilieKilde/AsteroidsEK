import dk.sdu.cbse.score.Score;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ScoreTest {
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void contextLoads() {
    }

    @Test
    void testGetInitialScore() {
        String url = "http://localhost:" + port + "/getScore";
        Integer score = restTemplate.getForObject(url, Integer.class);
        assertThat(score).isNotNull();
    }

    @Test
    void testHealthEndpoint() {
        String url = "http://localhost:" + port + "/health";
        Score.HealthResponse response =
                restTemplate.getForObject(url, Score.HealthResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo("UP");
    }
}
