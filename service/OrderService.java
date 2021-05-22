package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.*;

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

    //helper function to check whether a product has existed
    //return the existed product
    public Product checkProductExisted(Product product){
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Product.class);
        List<Product> productList =  criteria.list();

        //check if given product has the same infos as those in the product list
        for (Product existedProduct : productList){
            if (existedProduct.getName().equalsIgnoreCase(product.getName())
            && existedProduct.getBrand().equalsIgnoreCase(product.getBrand())
            && existedProduct.getCompany().equalsIgnoreCase(product.getCompany())
            && existedProduct.getDescription().equalsIgnoreCase(product.getDescription())
            && existedProduct.getModel().equalsIgnoreCase(product.getModel())
            && existedProduct.getSellingPrice() == product.getSellingPrice()){
                return existedProduct;
            }
        }

        //return null if no matching product is found
        return null;
    }

    //helper function to check whether a category has existed
    //return the existed category
    public Category checkCategoryExisted(Category category){
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Category.class);
        List<Category> categories = criteria.list();
        if (category!=null){
            for (Category existedCategories : categories){
                if (existedCategories.getName()!=null && category.getName() != null){
                    if (existedCategories.getName().equalsIgnoreCase(category.getName())){
                        return existedCategories;
                    }
                }
            }
        }
        //return null if no matching category is found
        return null;
    }

    //helper function to check whether a provider has existed
    //return the existed provider
    public Provider checkProviderExisted(Provider provider){
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Provider.class);
        List<Provider> providers = criteria.list();
        if (provider!=null){
            for (Provider existedProviders :providers){
                if (provider.getName().equalsIgnoreCase(existedProviders.getName())
                        && provider.getAddress().equalsIgnoreCase(existedProviders.getAddress())
                        && provider.getEmail().equalsIgnoreCase(existedProviders.getEmail())
                        && provider.getPhone().equalsIgnoreCase(existedProviders.getPhone()) &&
                        provider.getFax().equalsIgnoreCase(existedProviders.getFax())
                        && provider.getContactPerson().equalsIgnoreCase(existedProviders.getContactPerson())){
                    //if provider existed
                    //return the provider
                    return  existedProviders;
                }
            }
        }
        //return null if no matching category is found
        return null;
    }


    public List<Order> getAllOrder() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
        return criteria.list();
    }

    public int addOrder(Order order){
        if (checkProviderExisted(order.getProvider()) != null){
            //if provider existed, set order provider
            order.setProvider(checkProviderExisted(order.getProvider()));
        }else{
            //save new provider
            this.sessionFactory.getCurrentSession().save(order.getProvider());
        }
        for(OrderDetail orderDetails: order.getOrderDetails()){
            //set the order in order details as the current order
            orderDetails.setOrder(order);

            if (checkCategoryExisted(orderDetails.getProduct().getCategory())!=null){
                //if category existed, set the category of product to that category
                orderDetails.getProduct().setCategory(checkCategoryExisted(orderDetails.getProduct().getCategory()));
            }else{
                //add new category to the category table
                this.sessionFactory.getCurrentSession().save(orderDetails.getProduct().getCategory());
            }

            if (checkProductExisted(orderDetails.getProduct())!=null){
                //if product existed, set the product of current detail to the existed product
                orderDetails.setProduct(checkProductExisted(orderDetails.getProduct()));
            }else{
                //add the new product
                this.sessionFactory.getCurrentSession().save(orderDetails.getProduct());
            }

        }
        this.sessionFactory.getCurrentSession().save(order);
        return order.getOrder_id();
    }

    public String deleteOrder(Order order){
        this.sessionFactory.getCurrentSession().delete(order);
        return "Order with id " + order.getOrder_id() + " is deleted";
    }

    public Order updateOrder(Order order){
        if (checkProviderExisted(order.getProvider()) != null){
            //if provider existed, set order provider
            order.setProvider(checkProviderExisted(order.getProvider()));
        }else{
            //save new provider
            this.sessionFactory.getCurrentSession().save(order.getProvider());
        }
        //loop through the order details for updating
        if (order.getOrderDetails() != null){
            for(OrderDetail orderDetail: order.getOrderDetails()){
                orderDetail.setOrder(order);

                //update order detail
                if (orderDetail.getProduct()!=null){

                    if (checkCategoryExisted(orderDetail.getProduct().getCategory())!= null){
                        //if category has existed, set current product category to this category
                        orderDetail.getProduct().setCategory(checkCategoryExisted(orderDetail.getProduct().getCategory()));
                    }else{
                        //update new category
                        this.sessionFactory.getCurrentSession().save(orderDetail.getProduct().getCategory());
                    }
                    this.sessionFactory.getCurrentSession().save(orderDetail.getProduct().getCategory());

                    if (checkProductExisted(orderDetail.getProduct())!=null){
                        //if product existed, set the product of current detail to the existed product
                        orderDetail.setProduct(checkProductExisted(orderDetail.getProduct()));
                    }else {
                        //add the new product
                        this.sessionFactory.getCurrentSession().save(orderDetail.getProduct());
                    }


                }
            }
        }

        this.sessionFactory.getCurrentSession().update(order);

        //flush current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //get updated order from database
        return getAnOrder(order.getOrder_id());
    }

    public Order getAnOrder(int order_id){
        return this.sessionFactory.getCurrentSession().get(Order.class, order_id);
    }

}
