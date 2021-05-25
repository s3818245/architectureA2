package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

@TestConfiguration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableTransactionManagement
@Profile("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestConfig {
    @Bean
    public Customer customer() {
        return new Customer();
    }

    @Bean
    public Provider provider() {
        return new Provider();
    }

    @Bean
    public Category category() {
        return new Category();
    }

    @Bean
    public Product product() {
        return new Product();
    }

    @Bean
    public Staff staff() {
        return new Staff();
    }

    @Bean
    public Order order() {
        return new Order();
    }

    @Bean
    public OrderDetail orderDetail() {
        return new OrderDetail();
    }

    @Bean
    public SaleInvoice saleInvoice() {
        return new SaleInvoice();
    }

    @Bean
    public SaleDetail saleDetail() {
        return new SaleDetail();
    }

    @Bean
    public ReceivingNote receivingNote() {
        return new ReceivingNote();
    }

    @Bean
    public ReceivingDetail receivingDetail() {
        return new ReceivingDetail();
    }

    @Bean
    public DeliveryNote deliveryNote() {
        return new DeliveryNote();
    }

    @Bean
    public DeliveryDetail deliveryDetail() {
        return new DeliveryDetail();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        Properties properties = new Properties();
        //For Postgresql
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //For mysql
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setPackagesToScan("com.quynhanh.architecturea2.model");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/test");
        dataSource.setUsername("postgres");
        dataSource.setPassword("quynhanh551");

        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }
}
