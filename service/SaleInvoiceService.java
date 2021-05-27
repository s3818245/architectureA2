package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.*;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Sale Invoice Service, which contain all the CRUD operations
 * with some additional API like get all Invoice(s) in a period of time or all Invoice(s) made by a Staff or Customer in a period of time
 * as well as the Revenue made from the Invoice(s) in a period of time
 */

@Service
@Transactional
public class SaleInvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<SaleInvoice> getAllSaleInvoice() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class);
        return criteria.list();
    }

    public SaleInvoice getASaleInvoice(int id) {
        return sessionFactory.getCurrentSession().get(SaleInvoice.class, id);
    }

    //Helper function to get a product from the database using the product's id
    public Product getAProduct(int id){
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    //helper function to calculate the total amount of an invoice
    public double totalValueOfInvoice(int id) {
        double sumOfInvoice = 0;
        SaleInvoice savedSaleInvoice = this.sessionFactory.getCurrentSession().get(SaleInvoice.class, id);

        //get the total value of a sale detail using the selling price and quantity of the product
        //add all the value from each sale detail to get the sum of an invoice
        for (SaleDetail saleDetail: savedSaleInvoice.getSaleDetails()) {
            if (getAProduct(saleDetail.getProduct().getId()) != null) {
                sumOfInvoice += saleDetail.getTotalValue();
            }
        }

        //return the sum amount after adding all the value
        return sumOfInvoice;
    }

    public int addSaleInvoice(SaleInvoice saleInvoice) {
        for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
            //set the invoice in sale details as the current order
            saleDetail.setSalesInvoice(saleInvoice);

            if(saleDetail.getProduct() != null) {//check if the product exist
                //If exist, get the product through the id
                int id = saleDetail.getProduct().getId();
                Product product = getAProduct(id);
                //set the sale detail's price as the existed product's selling price
                saleDetail.setPrice(product.getSellingPrice());
                //set the sale detail's product as the existed product
                saleDetail.setProduct(product);
                //set the sale detail's total as the total amount of all product in the detail
                saleDetail.setTotalValue(product.getSellingPrice() * saleDetail.getQuantity());
            }
            this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
        }
        //set the total price of the invoice using the helper function
        saleInvoice.setTotalPrice(totalValueOfInvoice(saleInvoice.getSale_invoice_id()));

        this.sessionFactory.getCurrentSession().save(saleInvoice);

        return saleInvoice.getSale_invoice_id();
    }

    public String deleteSaleInvoice(SaleInvoice saleInvoice) {
        this.sessionFactory.getCurrentSession().delete(saleInvoice);
        return "Successfully deleted sale invoice_" + saleInvoice.getSale_invoice_id();
    }

    public SaleInvoice updateSaleInvoice(SaleInvoice saleInvoice) {
        //check if the sale invoice have sale details
        if (saleInvoice.getSaleDetails() != null) {
            for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
                saleDetail.setSalesInvoice(saleInvoice);
                //check if the sale invoice contain product
                if (saleDetail.getProduct() != null) {
                    //if contained, get the product information
                    int id = saleDetail.getProduct().getId();
                    Product product = getAProduct(id);

                    if (product.getSellingPrice() != 0) {
                        //if the detail's price already existed, update the price to the product's selling price
                        saleDetail.setPrice(product.getSellingPrice());
                    }
                    else {
                        //if there is no price exist in the detail, save the price as the product's selling price
                        this.sessionFactory.getCurrentSession().save(saleDetail.getProduct().getSellingPrice());
                    }

                    if (getAProduct(saleDetail.getProduct().getId()) != null) {
                        saleDetail.setProduct(product);
                    }
                    //set the total price of a details
                    saleDetail.setTotalValue(product.getSellingPrice() * saleDetail.getQuantity());
                }
            }
        }

        int idOfNewInvoice = saleInvoice.getSale_invoice_id();

        this.sessionFactory.getCurrentSession().update(saleInvoice);
        //flush current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //get new total price
        double sumOfInvoice = 0;
        //get updated sale invoice
        SaleInvoice savedSaleInvoice = this.sessionFactory.getCurrentSession().get(SaleInvoice.class, idOfNewInvoice);

        //calculate the total price
        for (SaleDetail saleDetail: savedSaleInvoice.getSaleDetails()) {
            if (getAProduct(saleDetail.getProduct().getId()) != null) {
                sumOfInvoice += saleDetail.getTotalValue();
            }
        }

        //set total price
        savedSaleInvoice.setTotalPrice(sumOfInvoice);

        //update sale invoice with total price
        this.sessionFactory.getCurrentSession().update(savedSaleInvoice);

        //flush current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //get updated sale invoice
        return getASaleInvoice(idOfNewInvoice);
    }

    public List<SaleInvoice> getInvoiceByDate(Date start, Date end) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                .add(Restrictions.between("date", start, end));
        List<SaleInvoice> noteList = criteria.list();
        return noteList;
    }

    public List<SaleInvoice> getInvoiceByCustomerOrStaff(Date start, Date end, String objType, int id) {
        //check if the invoice user want to see is by the customer who ordered or the staff who make the invoice
        if (objType.equalsIgnoreCase("customer")) { //if the type is by customer
            Customer customer = this.sessionFactory.getCurrentSession().get(Customer.class, id);
            if(customer != null) {
                Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                        .add(Restrictions.eq("customer", customer))
                        .add(Restrictions.between("date", start, end))
                        .setFirstResult(0)
                        .setMaxResults(5);
                return criteria.list();
            }
            else {
                return null;
            }
        }
        else if (objType.equalsIgnoreCase("staff")) {
            Staff staff = this.sessionFactory.getCurrentSession().get(Staff.class, id);
            if (staff != null) {
                Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                        .add(Restrictions.eq("staff", staff))
                        .add(Restrictions.between("date", start, end));
                return criteria.list();
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public double getRevenueByDate(Date start, Date end) {
        double revenue = 0;

        for (SaleInvoice saleInvoice: getInvoiceByDate(start, end)) {
            revenue += totalValueOfInvoice(saleInvoice.getSale_invoice_id());
        }

        return revenue;
    }

    public double getRevenueByCustomerOrStaff(Date start, Date end, String objType, int id) {
        double revenue = 0;

        for (SaleInvoice saleInvoice: getInvoiceByCustomerOrStaff(start, end, objType, id)) {
            if (saleInvoice != null) {
                revenue += totalValueOfInvoice(saleInvoice.getSale_invoice_id());
            }
        }

        return revenue;
    }

    //Additional function for adding products to database
    //FOR TESTING PURPOSE ONLY, product is only created via OrderService
    //used in SaleInvoiceServiceTest
    public int addProduct(Product product){
        this.sessionFactory.getCurrentSession().save(product.getCategory());
        this.sessionFactory.getCurrentSession().save(product);

        return product.getId();
    }

    //Additional function for deleting products from database
    //FOR TESTING PURPOSE ONLY, product is only created via OrderService
    //used in SaleInvoiceServiceTest
    public void deleteProduct(Product product){
        this.sessionFactory.getCurrentSession().delete(product.getCategory());
        this.sessionFactory.getCurrentSession().delete(product);
    }

}
