package example.micronaut;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;

@MicronautTest
public class InfoTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testGitComitInfoAppearsInJson() {
        HttpRequest request = HttpRequest.GET("/info");

        HttpResponse<Map> rsp = client.toBlocking().exchange(request, Map.class);

        Assertions.assertEquals(200, rsp.status().getCode());

        Map json = rsp.body();

        Assertions.assertNotNull(json.get("git"));
        Assertions.assertNotNull(((Map) json.get("git")).get("commit"));
        Assertions.assertNotNull(((Map) ((Map) json.get("git")).get("commit")).get("message"));
        Assertions.assertNotNull(((Map) ((Map) json.get("git")).get("commit")).get("time"));
        Assertions.assertNotNull(((Map) ((Map) json.get("git")).get("commit")).get("id"));
        Assertions.assertNotNull(((Map) ((Map) json.get("git")).get("commit")).get("user"));
        Assertions.assertNotNull(((Map) json.get("git")).get("branch"));
    }
}