package tobyspring.hellospring.data;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;
import tobyspring.hellospring.order.Order;
import tobyspring.hellospring.order.OrderRepository;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class JdbcOrderRepository implements OrderRepository {

    private final JdbcClient jdbcClient;

    public JdbcOrderRepository(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
    }

    @PostConstruct
    void initDb(){
        jdbcClient.sql("""
            create table orders (id bigint not null, no varchar(255), total numeric(38,2), primary key (id));
            alter table if exists orders drop constraint if exists UK_43egxxciqr9ncgmxbdx2avi8n;
            alter table if exists orders add constraint UK_43egxxciqr9ncgmxbdx2avi8n unique (no);
            create sequence orders_SEQ start with 1 increment by 50;
       """).update();
    }

    @Override
    public void save(Order order) {
        Long id = jdbcClient.sql("select next value for orders_SEQ").query(Long.class).single();
        System.out.println("id = " + id);

        order.setId(id);
        order.setNo("1234111");
        order.setTotal(BigDecimal.TEN);
        jdbcClient.sql("insert into orders (no,total,id) values (?,?,?)")
                .params(order.getNo(), order.getTotal(), order.getId()).update();
    }

    @Override
    public Order findById(Long id) {
        return jdbcClient.sql("select * from orders where id = ?")
                .params(id).query(Order.class).single();
    }
}
