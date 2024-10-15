package tobyspring.hellospring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.JpaOrderRepository;
import tobyspring.hellospring.order.Order;

import java.math.BigDecimal;

public class OrderClient {
    public static void main(String[] args) {
        BeanFactory beanFactory =  new AnnotationConfigApplicationContext(DataConfig.class);
        JpaOrderRepository repository = beanFactory.getBean(JpaOrderRepository.class);
        JpaTransactionManager jtm = beanFactory.getBean(JpaTransactionManager.class);

        try {
            new TransactionTemplate(jtm).execute(status -> {

                Order order = new Order("100", BigDecimal.valueOf(10));
                repository.save(order);
                System.out.println("order = " + order);

//                Order order2 = new Order("100", BigDecimal.valueOf(1));
//                repository.save(order2);
                return null;
            });
        }
        catch (DataIntegrityViolationException e) {
            System.out.println("주문번호 중복 복구 작업");
            throw e;
        }

    }
}
