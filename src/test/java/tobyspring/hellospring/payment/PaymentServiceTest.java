package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;


class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족 했는지 검증")
    void prepare() throws IOException {

        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(), Clock.systemDefaultZone());
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
    void prepare2() throws IOException {
        //준비
        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(), Clock.systemDefaultZone());

        //실행
        Payment usd = paymentService.prepare(2L, "USD", new BigDecimal("20"));

        //검증
        assertThat(usd.getExRate()).isNotNull(); //적용할 환율 정보가 있는지 검증한다.
        assertThat(usd.getConvertedAmount()).isEqualTo(usd.getExRate().multiply(usd.getCurrencyAmount())); //원화 환산 금액이 환율정보를 가지고 정상적으로 계산이 되었는지 검증한다.
        assertThat(usd.getValidUntil()).isAfter(LocalDateTime.now());    //원화 환산 금액 유효시간이 현재 시간보다 이후인지 검증한다.
        assertThat(usd.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30)); //원화 환산 금액 유효시간이 현재 시간에서 plus 30분 한 시간보다 이전인지 검증한다.

    }

    @Test
    void prepareStub() throws IOException {
        getPayment(valueOf(500), valueOf(5_000));
        getPayment(valueOf(600), valueOf(6_000));
        getPayment(valueOf(1_000_000), valueOf(10_000_000));
        //원화환산금액의 유효시간 계산
        //assertThat(usd.getValidUntil()).isAfter(LocalDateTime.now());
        //assertThat(usd.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static void getPayment(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        //준비
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), Clock.systemDefaultZone());

        //실행
        Payment usd = paymentService.prepare(3L, "USD", TEN);

        //검증
        //환율정보를 가져온다.
        assertThat(usd.getExRate()).isEqualByComparingTo(exRate);
        assertThat(usd.getConvertedAmount()).isEqualTo(convertedAmount);

    }

}