package tobyspring.hellospring.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.hellospring.OrderConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder(){
        var order = orderService.createOrder("0100", BigDecimal.ONE);
        assertThat(order.getId()).isGreaterThan(0);

       // var order2 = orderService.findById(order.getId());
       // System.out.println("order2 = " + order2);
       // assertThat(order2.getId()).isEqualTo(order.getId());
       // assertThat(order2.getNo()).isEqualTo(order.getNo());
    }

    @Test
    void createOrders(){
        List<OrderReq> orderReqs = List.of(new OrderReq("0200", BigDecimal.ONE), new OrderReq("0201", BigDecimal.TWO), new OrderReq("0202", BigDecimal.valueOf(3)));
        List<Order> orders = orderService.createOrders(orderReqs);
        assertThat(orders).hasSize(3);

        orders.forEach(order -> assertThat(order.getId()).isGreaterThan(0));
    }

    @Test
    void createDuplicatedOrders(){
        List<OrderReq> orderReqs = List.of(new OrderReq("0200", BigDecimal.ONE), new OrderReq("0201", BigDecimal.TWO), new OrderReq("0201", BigDecimal.valueOf(3)));
        assertThatThrownBy(() -> orderService.createOrders(orderReqs)).isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        Long count = client.sql("select count(*) from orders").query(Long.class).single();
        assertThat(count).isEqualTo(0);
    }

}
