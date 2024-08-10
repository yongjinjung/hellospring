package tobyspring.hellospring.api;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal getExRate(String currency);
}
