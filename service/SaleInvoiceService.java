package com.quynhanh.architecturea2.service;

import com.quynhanh.architecturea2.model.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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


    public Product getAProduct(int id){
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }

    public double totalValueOfInvoice(int id) {
        double sumOfInvoice = 0;
        SaleInvoice savedSaleInvoice = this.sessionFactory.getCurrentSession().get(SaleInvoice.class, id);

        for (SaleDetail saleDetail: savedSaleInvoice.getSaleDetails()) {
            if (getAProduct(saleDetail.getProduct().getId()) != null) {
                sumOfInvoice += saleDetail.getTotalValue();
            }
        }

        return sumOfInvoice;
    }

    public int addSaleInvoice(SaleInvoice saleInvoice) {
        for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
            saleDetail.setSalesInvoice(saleInvoice);

            if(saleDetail.getProduct() != null) {
                int id = saleDetail.getProduct().getId();
                Product product = getAProduct(id);

                saleDetail.setPrice(product.getSellingPrice());

                saleDetail.setProduct(product);

                saleDetail.setTotalValue(product.getSellingPrice() * saleDetail.getQuantity());
            }
            this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
        }

        saleInvoice.setTotalPrice(totalValueOfInvoice(saleInvoice.getSale_invoice_id()));

        this.sessionFactory.getCurrentSession().save(saleInvoice);

        return saleInvoice.getSale_invoice_id();
    }

    public String deleteSaleInvoice(SaleInvoice saleInvoice) {
        this.sessionFactory.getCurrentSession().delete(saleInvoice);
        return "Successfully deleted sale invoice_" + saleInvoice.getSale_invoice_id();
    }

    public SaleInvoice updateSaleInvoice(SaleInvoice saleInvoice) {
        //List<SaleDetail> newList = new ArrayList<>();

        if (saleInvoice.getSaleDetails() != null) {
            for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
                saleDetail.setSalesInvoice(saleInvoice);

                if (saleDetail.getProduct() != null) {
                    int id = saleDetail.getProduct().getId();
                    Product product = getAProduct(id);

                    if (product.getSellingPrice() != 0) {
                        saleDetail.setPrice(product.getSellingPrice());
                    }
                    else {
                        this.sessionFactory.getCurrentSession().save(saleDetail.getProduct().getSellingPrice());
                    }

                    if (getAProduct(saleDetail.getProduct().getId()) != null) {
                        saleDetail.setProduct(product);
                    }

                    saleDetail.setTotalValue(product.getSellingPrice() * saleDetail.getQuantity());

                    //this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
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
        if (objType.equalsIgnoreCase("customer")) {
            Customer customer = this.sessionFactory.getCurrentSession().get(Customer.class, id);
            if(customer != null) {
                Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                        .add(Restrictions.eq("customer", customer))
                        .add(Restrictions.between("date", start, end));
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
