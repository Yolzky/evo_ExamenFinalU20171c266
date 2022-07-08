package integration_tests;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import betrip.services.betrip_backend_services.BetripBackendServicesApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetripBackendServicesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverControlerIntegrationTest {
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void TestGetDriverbyId() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/drivers/2"),
				HttpMethod.GET, entity, String.class);

		String expected = 
				"{\"id\":2,\"name\":\"Ruben\",\"last_name\":\"Perez\",\"age\":30,\"dni\":\"75643125\",\"district\":\"San Isidro\",\"email\":\"driver1@mail.com\",\"password\":\"qwerty\",\"type\":\"auto\",\"model\":\"yaris\",\"brand\":\"toyota\",\"licence_plate\":\"UPC-123\",\"licence_code\":\"Q75643125\",\"number_seats\":4,\"phoneNumber\":\"94513154\",\"puntuacion\":5,\"pfp\":\"fotito.png\"}";
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}	
}