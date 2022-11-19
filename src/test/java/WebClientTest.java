
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WebClientTest {

    @Test
    public void getRequest() {
        WebClient client = WebClient
                .builder()
                .baseUrl("http://www.swime.kro.kr/")
                .build();

        String json = client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("getPopularGroupList.JSON")
                        .queryParam("pageNum", "1")
                        .queryParam("amount", "6")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();

        List<Map<String, String>> list = Collections.emptyList();

        try {
            ObjectMapper mapper = new ObjectMapper();

            list = mapper.readValue(
                    json,
                    new TypeReference<List<Map<String, String>>>() {
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert list.size() > 0;

        System.out.println("\n----- result -----\n");

        list.forEach(System.out::println);
    }
}
