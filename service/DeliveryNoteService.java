package com.example.sadi_assignment2_s3819293.service;
import com.example.sadi_assignment2_s3819293.model.DeliveryDetail;
import com.example.sadi_assignment2_s3819293.model.DeliveryNote;
import com.example.sadi_assignment2_s3819293.model.SaleDetail;
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
        //
        this.sessionFactory.getCurrentSession().save(deliveryNote);
        //
        return deliveryNote.getDelivery_note_id();
    }

    public int addDeliveryDetails(int deliveryID) {

        DeliveryNote deliveryNote = this.sessionFactory.getCurrentSession().get(DeliveryNote.class, deliveryID);

        if(!deliveryNote.getDeliveryDetails().isEmpty()) {
            for (DeliveryDetail oldDetail: deliveryNote.getDeliveryDetails()) {
                this.sessionFactory.getCurrentSession().delete(oldDetail);
            }
        }

        if (deliveryNote.getSaleInvoice().getSaleDetails() != null) {
            for (SaleDetail saleDetail: deliveryNote.getSaleInvoice().getSaleDetails()) {
                //create a new delivery detail note for each sale detail
                DeliveryDetail newDetail = new DeliveryDetail();
                //set the delivery note with the new detail created
                newDetail.setDeliveryNote(deliveryNote);
                //get the product quantity from the sale detail
                newDetail.setQuantity(saleDetail.getQuantity());
                //save the new detail into the database
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

    public int updateDeliveryNote(DeliveryNote deliveryNote) {
        //
        this.sessionFactory.getCurrentSession().update(deliveryNote);
        //
        return deliveryNote.getDelivery_note_id();
    }

    public DeliveryNote updateDeliveryDetails(int deliveryId) {
        int updateNoteId = addDeliveryDetails(deliveryId);

        //clear data of current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //
        return this.sessionFactory.getCurrentSession().get(DeliveryNote.class, updateNoteId);
    }

    public List<DeliveryNote> getNoteByDate(Date start, Date end) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class)
                .add(Restrictions.between("date", start, end));
        List<DeliveryNote> noteList = criteria.list();
        return noteList;
    }
}

