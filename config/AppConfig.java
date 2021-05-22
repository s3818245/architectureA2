package com.example.sadi_assignment2_s3819293.config;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

import com.example.sadi_assignment2_s3819293.model.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@Configuration
@EnableTransactionManagement
@EnableWebMvc
public class AppConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory(){

        Properties properties = new Properties();
        //For Postgresql
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        //For mysql
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setPackagesToScan("com.example.sadi_assignment2_s3819293.model");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/assignment2");
        dataSource.setUsername("postgres");
        dataSource.setPassword("C@thyNguy3n%9$8");

        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(properties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory);

        return tx;
    }

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
}
