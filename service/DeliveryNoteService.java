package com.example.sadi_assignment2_s3819293.service;
import com.example.sadi_assignment2_s3819293.model.DeliveryDetail;
import com.example.sadi_assignment2_s3819293.model.DeliveryNote;
import com.example.sadi_assignment2_s3819293.model.SaleDetail;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryNoteService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<DeliveryNote> getAllDeliveryNote() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class);
        return criteria.list();
    }

    public DeliveryNote getOneDeliveryNote(int id) {
        return sessionFactory.getCurrentSession().get(DeliveryNote.class, id);
    }

    public int addDeliveryNote(DeliveryNote deliveryNote) {
//        for (DeliveryDetail deliveryDetail: deliveryNote.getDeliveryDetails()) {
//            //set the delivery note in the delivery detail to be the current one
//            deliveryDetail.setDeliveryNote(deliveryNote);
//            //save the details of the receiving note
//            this.sessionFactory.getCurrentSession().save(deliveryDetail);
//
//            this.sessionFactory.getCurrentSession().save(deliveryDetail.getProduct());
//        }
        this.sessionFactory.getCurrentSession().save(deliveryNote);
        return deliveryNote.getDelivery_note_id();
    }

    public int addDeliveryDetails(int deliveryID) {
        DeliveryNote deliveryNote = this.sessionFactory.getCurrentSession().get(DeliveryNote.class, deliveryID);
        if (deliveryNote.getSaleInvoice().getSaleDetails() != null) {
            for (SaleDetail saleDetail: deliveryNote.getSaleInvoice().getSaleDetails()) {
                DeliveryDetail newDetail = new DeliveryDetail();
                newDetail.setDeliveryNote(deliveryNote);
                newDetail.setProduct(saleDetail.getProduct());
                newDetail.setQuantity(saleDetail.getQuantity());
                this.sessionFactory.getCurrentSession().save(newDetail);
            }
        }
        return deliveryID;
    }

    public String deleteDeliveryNote(int id) {
        DeliveryNote deliveryNote = sessionFactory.getCurrentSession().get(DeliveryNote.class, id);
        if (deliveryNote != null) {
            sessionFactory.getCurrentSession().delete(deliveryNote);
        }
        return "Delivery Note with id: " + deliveryNote.getDelivery_note_id() + " is successfully deleted";
    }

    public DeliveryNote updateDeliveryNote(DeliveryNote deliveryNote) {
        if (deliveryNote.getDeliveryDetails() != null) {
            for (DeliveryDetail deliveryDetail: deliveryNote.getDeliveryDetails()) {
                //set the delivery note in the delivery detail to be the current one
                deliveryDetail.setDeliveryNote(deliveryNote);
                //save the details of the receiving note
                this.sessionFactory.getCurrentSession().update(deliveryDetail);

                if (deliveryDetail.getProduct() != null) {
                    this.sessionFactory.getCurrentSession().update(deliveryDetail.getProduct());
                }
            }
        }
        sessionFactory.getCurrentSession().update(deliveryNote);
        return deliveryNote;
    }
}

