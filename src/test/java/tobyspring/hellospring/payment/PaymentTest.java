package tobyspring.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.math.BigDecimal.*;

public class PaymentTest {

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Payment usd = Payment.createPayment(1L, "USD", TEN, valueOf(1_000), LocalDateTime.now(clock));

        Assertions.assertThat(usd.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));
        Assertions.assertThat(usd.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid(){
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Payment payment = Payment.createPayment(1L, "USD", TEN, valueOf(1_000), LocalDateTime.now(clock));


        System.out.println("clock = " + LocalDateTime.now(clock));
        System.out.println("clock1 = " + LocalDateTime.now(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES))));
        Assertions.assertThat(payment.isValid(clock)).isTrue();
        Assertions.assertThat(payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))).isFalse();
    }
}
