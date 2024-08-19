package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PaymentService {
    private final ExRateProvider provider;

    public PaymentService(ExRateProvider provider) {
        this.provider = provider;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal currencyAmount) throws IOException {
        //환율 가져오기
        BigDecimal exRate = provider.getExRate(currency);
        //금액 계산
        BigDecimal convertedAmount = currencyAmount.multiply(exRate);
        //유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);
        return new Payment(orderId, currency, currencyAmount, exRate, convertedAmount, validUntil);
    }
}
