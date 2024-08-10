package tobyspring.hellospring.payment;

import tobyspring.hellospring.Payment;
import tobyspring.hellospring.PaymentService;
import tobyspring.hellospring.SimpleExRateProvider;

import java.math.BigDecimal;

public class Client {
    /**
     * 클라이언트 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        PaymentService paymentService = new PaymentService(new SimpleExRateProvider());
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment = " + payment);
    }
}

