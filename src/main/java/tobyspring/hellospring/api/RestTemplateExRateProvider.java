package tobyspring.hellospring.api;

import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.api.ExRateExtractor;
import tobyspring.hellospring.exrate.ExRateData;

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
