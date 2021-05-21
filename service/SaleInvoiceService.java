package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.Customer;
import com.example.sadi_assignment2_s3819293.model.DeliveryNote;
import com.example.sadi_assignment2_s3819293.model.SaleDetail;
import com.example.sadi_assignment2_s3819293.model.SaleInvoice;
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

    public SaleInvoice getSaleInvoiceById(int id) {
        return sessionFactory.getCurrentSession().get(SaleInvoice.class, id);
    }

    public int addSaleInvoice(SaleInvoice saleInvoice) {
        if (saleInvoice != null) {
            for (SaleDetail saleDetail: saleInvoice.getSaleDetails()) {
                saleDetail.setSalesInvoice(saleInvoice);

                this.sessionFactory.getCurrentSession().save(saleDetail.getProduct());

                this.sessionFactory.getCurrentSession().save(saleDetail.getSalesInvoice());
            }
        }
        this.sessionFactory.getCurrentSession().saveOrUpdate(saleInvoice);
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

                this.sessionFactory.getCurrentSession().update(saleDetail.getProduct());

                this.sessionFactory.getCurrentSession().update(saleDetail.getSalesInvoice());
            }
        }
        this.sessionFactory.getCurrentSession().update(saleInvoice);
        return saleInvoice;
    }

    public List<SaleInvoice> getInvoiceByDate(Date start, Date end) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(SaleInvoice.class)
                .add(Restrictions.between("date", start, end));
        List<SaleInvoice> noteList = criteria.list();
        return noteList;
    }

    public List<SaleInvoice> getInvoiceByCustomer(Date start, Date end) {

    }

    public List<SaleInvoice> getInvoiceByStaff(Date start, Date end) {

    }
}
