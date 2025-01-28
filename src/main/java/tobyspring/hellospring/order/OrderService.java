package tobyspring.hellospring.order;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import tobyspring.hellospring.data.JpaOrderRepository;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final PlatformTransactionManager transactionManager;


    public OrderService(OrderRepository repository, PlatformTransactionManager transactionManager) {
        this.repository = repository;
        this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {

        Order order = new Order(no, total);

        return new TransactionTemplate(transactionManager).execute((status) -> {
            this.repository.save(order);
            return order;
        });
    }

    public Order findById(Long id) {
        return repository.findById(id);
    }
}
