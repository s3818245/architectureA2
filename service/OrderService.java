package com.quynhanh.architecturea2.service;
import com.quynhanh.architecturea2.model.Order;
import com.quynhanh.architecturea2.model.OrderDetail;
import com.quynhanh.architecturea2.model.Product;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Order> getAllOrder() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
        return criteria.list();
    }

    public int addOrder(Order order){
        this.sessionFactory.getCurrentSession().save(order.getProvider());
        for(OrderDetail orderDetails: order.getOrderDetails()){
            //set the order in order details as the current order
            orderDetails.setOrder(order);
            //add new category to the category table
            this.sessionFactory.getCurrentSession().save(orderDetails.getProduct().getCategory());
            //add the new product
            this.sessionFactory.getCurrentSession().save(orderDetails.getProduct());
        }
        this.sessionFactory.getCurrentSession().saveOrUpdate(order);
        return order.getOrder_id();
    }

    public String deleteOrder(Order order){
        this.sessionFactory.getCurrentSession().delete(order);
        return "Order with id " + order.getOrder_id() + " is deleted";
    }

    public Order updateOrder(Order order){
        this.sessionFactory.getCurrentSession().update(order.getProvider());
        //loop through the order details for updating
        for(OrderDetail orderDetail: order.getOrderDetails()){
            orderDetail.setOrder(order);
            //update category
            this.sessionFactory.getCurrentSession().update(orderDetail.getProduct().getCategory());
            //update order detail
            this.sessionFactory.getCurrentSession().update(orderDetail.getProduct());
        }
        this.sessionFactory.getCurrentSession().update(order);
        return order;
    }

    public Order getAnOrder(int order_id){
        return this.sessionFactory.getCurrentSession().get(Order.class, order_id);
    }

}
