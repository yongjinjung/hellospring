package tobyspring.hellospring.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;


class PaymentServiceTest {

    private Clock clock;

    @BeforeEach
    void beforeEach(){
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족 했는지 검증")
    void prepare()  {

        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(new ApiTemplate()), Clock.systemDefaultZone());
        Payment payment = paymentService.prepare(1L, "USD", TEN);

        //환율정보 가져온다
        assertThat(payment.getExRate()).isNotNull();

        //원화환산금액 계산
        assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExRate().multiply(payment.getCurrencyAmount()));

        //원화환산금액의 유효시간 계산
        System.out.println(payment.getValidUntil());
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now().plusMinutes(30));
        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));

    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족 했는지 검증")
    void prepare2() {
        //준비
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(new ApiTemplate()), Clock.systemDefaultZone());

        /* 실행 */
        Payment usd = paymentService.prepare(2L,
                "USD",
                new BigDecimal("20"));

        //검증
        assertThat(usd.getExRate()).isNotNull(); //적용할 환율 정보가 있는지 검증한다.
        assertThat(usd.getConvertedAmount()).isEqualTo(usd.getExRate().multiply(usd.getCurrencyAmount())); //원화 환산 금액이 환율정보를 가지고 정상적으로 계산이 되었는지 검증한다.
        assertThat(usd.getValidUntil()).isAfter(LocalDateTime.now());    //원화 환산 금액 유효시간이 현재 시간보다 이후인지 검증한다.
        assertThat(usd.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30)); //원화 환산 금액 유효시간이 현재 시간에서 plus 30분 한 시간보다 이전인지 검증한다.

    }

    @Test
    void prepareStub() {

        getPayment(valueOf(500), valueOf(5_000), this.clock);
        getPayment(valueOf(600), valueOf(6_000), this.clock);
        getPayment(valueOf(1_000_000), valueOf(10_000_000), this.clock);

    }

    @Test
    void vlidUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);
        Payment usd = paymentService.prepare(1L, "USD", TEN);
        //valid until이 prepare()30분 뒤로 설정됐는가?
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        assertThat(usd.getValidUntil()).isEqualTo(expectedValidUntil);

    }

    private static void getPayment(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) {
        //준비
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        //실행
        Payment usd = paymentService.prepare(3L, "USD", TEN);

        //검증
        //환율정보를 가져온다.
        assertThat(usd.getExRate()).isEqualByComparingTo(exRate);
        assertThat(usd.getConvertedAmount()).isEqualTo(convertedAmount);

    }




}