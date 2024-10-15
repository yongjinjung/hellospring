package tobyspring.hellospring.order;

public interface OrderRepository {
    void save(Order order);

    Order findById(Long Id);
}
