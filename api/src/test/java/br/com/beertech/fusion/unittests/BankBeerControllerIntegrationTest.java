package br.com.beertech.fusion.unittests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.beertech.fusion.FusionApplication;

@SpringBootTest(classes = FusionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BankBeerControllerIntegrationTest {
	@Autowired
    private TestRestTemplate restTemplate;
	
	@LocalServerPort
    private int port =8081;
	
	private String getRootUrl() {
        return "http://localhost:" + port + "/bankbeer";
    }
	
	@Test
    public void testGetAllTransacoes() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/transacoes/",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }
	
	@Test
    public void testGetSaldo() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/saldo/",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }
	
	

}
