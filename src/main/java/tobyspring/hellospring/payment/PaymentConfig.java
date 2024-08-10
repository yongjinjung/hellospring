package tobyspring.hellospring.payment;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.ExRateProvider;
import tobyspring.hellospring.PaymentService;
import tobyspring.hellospring.WebApiExRateProvider;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService(){
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider(){
        return new WebApiExRateProvider();
    }
}
