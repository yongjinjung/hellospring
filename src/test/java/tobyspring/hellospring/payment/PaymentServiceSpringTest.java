package tobyspring.hellospring.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.TestObjectFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
public class PaymentServiceSpringTest {

    //@Autowired BeanFactory beanFactory;
    @Autowired PaymentService paymentService;
    @Autowired Clock clock;
    @Autowired ExRateProviderStub exRateProviderStub;

    @Test
    void prepareStub() {
        // BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        //PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment usd = paymentService.prepare(1L, "USD", TEN);

        assertThat(usd.getExRate()).isEqualByComparingTo(valueOf(1_000));
        assertThat(usd.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        exRateProviderStub.setExRate(valueOf(500));
        Payment usd2 = paymentService.prepare(1L, "USD", TEN);

        assertThat(usd2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(usd2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));
    }

    @Test
    void validUntil() {
        Payment usd = paymentService.prepare(1L, "USD", TEN);

        //valid until이 prepare() 30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        assertThat(usd.getValidUntil()).isEqualTo(expectedValidUntil);
    }
}
