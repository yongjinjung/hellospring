package tobyspring.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.CachedExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration
//@ComponentScan(basePackages = {"tobyspring.hellospring"})
public class PaymentConfig {

    @Bean
    public PaymentService paymentService(){
        return new PaymentService(cachedExratePrvider(), clock());
    }

    public ExRateProvider cachedExratePrvider(){
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider(){
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock(){return Clock.systemDefaultZone();}
}
