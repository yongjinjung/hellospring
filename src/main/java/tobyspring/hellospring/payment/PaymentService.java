package tobyspring.hellospring.payment;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class PaymentService {
    private final ExRateProvider provider;
    private final Clock clock;

    public PaymentService(ExRateProvider provider, Clock clock) {
        this.provider = provider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        //환율 가져오기
        BigDecimal exRate = provider.getExRate(currency);
        //원화 환산 금액을 계산한다.
        //BigDecimal convertedAmount = currencyAmount.multiply(exRate);
        //유효 시간 계산
        //LocalDateTime validUntil = LocalDateTime.now(clock).plusMinutes(30);
        //return new Payment(orderId, currency, currencyAmount, exRate, convertedAmount, validUntil);
        return Payment.createPayment(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock) );
    }
}
