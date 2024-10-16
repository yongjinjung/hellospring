package tobyspring.hellospring.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl {

    private final OrderRepository repository;
    //private final PlatformTransactionManager transactionManager;


    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
       // this.transactionManager = transactionManager;
    }

    public Order createOrder(String no, BigDecimal total) {

        Order order = new Order(no, total);
        this.repository.save(order);
        return order;
//        return new TransactionTemplate(transactionManager).execute((status) -> {
//            this.repository.save(order);
//            return order;
//        });
    }

    public Order findById(Long id) {
        return repository.findById(id);
    }

    public List<Order> createOrders(List<OrderReq> reqs){
        //reqs.forEach(req -> createOrder(req.no(), req.total()));
        //return new TransactionTemplate(transactionManager).execute(status -> reqs.stream().map(req -> createOrder(req.no(), req.total())).toList());
        return reqs.stream().map(req -> createOrder(req.no(), req.total())).toList();
    }
}
