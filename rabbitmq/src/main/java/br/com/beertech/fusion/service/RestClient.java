package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operacao;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

    private RestTemplate restTemplate;
    private Operacao RestObject;
    public final String APIURL = "http://localhost:8081/bankbeer/operacao";

    public RestClient(Operacao RestObjectParamenter)
    {
        RestObject = RestObjectParamenter;
    }
    public void sendPostAPI()
    {
        try
        {
            this.restTemplate = new RestTemplateBuilder().build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("tipoOperacao", RestObject.getTipoOperacao());
            map.put("valorOperacao", RestObject.getValorOperacao());

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<Operacao> response = this.restTemplate.postForEntity(APIURL, entity,Operacao.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println(response.getBody().toString());
            } else {
                System.out.println("Sem retorno");
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
