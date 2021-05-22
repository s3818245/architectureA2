package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
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

    //helper function to check whether a product has existed
    //return the existed product
    public Product checkProductExisted(Product product){
        Criteria productCriteria = this.sessionFactory.getCurrentSession().createCriteria(Product.class);
        List<Product> productList =  productCriteria.list();

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

    public int addSaleInvoice(SaleInvoice saleInvoice) {
        for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
            saleDetail.setSalesInvoice(saleInvoice);

            if(checkProductExisted(saleDetail.getProduct())!= null) {
                saleDetail.setPrice(checkProductExisted(saleDetail.getProduct()).getSellingPrice());

                saleDetail.setProduct(checkProductExisted(saleDetail.getProduct()));
            }

            this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
        }
        //this.sessionFactory.getCurrentSession().save(saleInvoice);

        return saleInvoice.getSale_invoice_id();
    }

    public String deleteSaleInvoice(int id) {
        SaleDetail saleDetail = this.sessionFactory.getCurrentSession().get(SaleDetail.class, id);
        if (saleDetail != null) {
            this.sessionFactory.getCurrentSession().delete(saleDetail);
        }
        return "Successfully deleted sale invoice_" + id;
    }

    public SaleInvoice updateSaleInvoice(SaleInvoice saleInvoice) {
        if (saleInvoice.getSaleDetails() != null) {
            for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
                saleDetail.setSalesInvoice(saleInvoice);

                if (saleDetail.getProduct() != null) {
                    if(checkProductExisted(saleDetail.getProduct())!= null) {
                        saleDetail.setPrice(checkProductExisted(saleDetail.getProduct()).getSellingPrice());

                        saleDetail.setProduct(checkProductExisted(saleDetail.getProduct()));
                    }
                    //this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
                }
            }
        }

        this.sessionFactory.getCurrentSession().update(saleInvoice);

        //flush current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        return getASaleInvoice(saleInvoice.getSale_invoice_id());
    }

    public List<SaleInvoice> getInvoiceByDate(Date start, Date end) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                .add(Restrictions.between("date", start, end));
        List<SaleInvoice> noteList = criteria.list();
        return noteList;
    }

//    public List<SaleInvoice> getInvoiceByCustomer(int customer_id, Date start, Date end) {
//        Customer customer = this.sessionFactory.getCurrentSession().get(Customer.class, customer_id);
//        if(customer != null) {
//            Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
//                    .add(Restrictions.between("date", start, end));
//            List<SaleInvoice> noteList = criteria.list();
//            return noteList;
//        }
//        else {
//            return null;
//        }
//    }
//
//    public List<SaleInvoice> getInvoiceByStaff(int staff_id, Date start, Date end) {
//        Staff staff = this.sessionFactory.getCurrentSession().get(Staff.class, staff_id);
//        if (staff != null) {
//            Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
//                    .add(Restrictions.between("date", start, end));
//            List<SaleInvoice> noteList = criteria.list();
//            return noteList;
//        }
//        else {
//            return null;
//        }
//    }

    public List<SaleInvoice> getInvoiceByCustomerOrStaff(Date start, Date end, String objType, int id) {
        if (objType.equalsIgnoreCase("customer")) {
            Customer customer = this.sessionFactory.getCurrentSession().get(Customer.class, id);
            if(customer != null) {
                Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                        .add(Restrictions.like("name", customer.getName(), MatchMode.ANYWHERE))
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
                        .add(Restrictions.like("name", staff.getName(), MatchMode.ANYWHERE))
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

    public double totalValueOfInvoice(int id) {
        double sumOfInvoice = 0;

        for (SaleDetail saleDetail: getASaleInvoice(id).getSaleDetails()) {
            if (checkProductExisted(saleDetail.getProduct()) != null) {
                double sumOfProduct = saleDetail.getQuantity() * saleDetail.getProduct().getSellingPrice();
                sumOfInvoice += sumOfProduct;
            }
        }

        return sumOfInvoice;
    }

    public double revenueByDate(Date start, Date end) {
        double revenue = 0;

        for (SaleInvoice saleInvoice: getInvoiceByDate(start, end)) {
            revenue += totalValueOfInvoice(saleInvoice.getSale_invoice_id());
        }

        return revenue;
    }

    public double revenueByCustomerOrStaff(Date start, Date end, String objType, int id) {
        double revenue = 0;

        for (SaleInvoice saleInvoice: getInvoiceByCustomerOrStaff(start, end, objType, id)) {
            if (saleInvoice != null) {
                revenue += totalValueOfInvoice(saleInvoice.getSale_invoice_id());
            }
        }

        return revenue;
    }
}
