package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.hellospring.payment.Payment;
import tobyspring.hellospring.payment.PaymentService;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {
    /**
     * 클라이언트 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws InterruptedException {

        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        PaymentService paymentService2 = beanFactory.getBean(PaymentService.class);

        System.out.println(  paymentService2 == paymentService);
        //PaymentService paymentService = new PaymentService(new SimpleExRateProvider());
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(75.7));
        System.out.println("payment1 = " + payment);
        System.out.println("===================================================");
        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(75.7));
        System.out.println("payment2 = " + payment2);
        TimeUnit.SECONDS.sleep(3);
        System.out.println("===================================================");
        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(75.7));
        System.out.println("payment3 = " + payment3);
    }
}

