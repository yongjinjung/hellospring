package tobyspring.hellospring.exrate;

import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.ExRateData;
import tobyspring.hellospring.api.ExRateExtractor;

import java.math.BigDecimal;

public class RestTemplateExRateProvider implements ExRateExtractor {
    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "";
        return restTemplate.getForObject(url, ExRateData.class).rates().get("KRW");
    }
}
