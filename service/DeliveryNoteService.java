package com.quynhanh.architecturea2.service;
import com.quynhanh.architecturea2.model.DeliveryDetail;
import com.quynhanh.architecturea2.model.DeliveryNote;
import com.quynhanh.architecturea2.model.SaleDetail;
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
        //save the delivery note to database
        this.sessionFactory.getCurrentSession().save(deliveryNote);
        //return the id of the delivery note
        return deliveryNote.getDelivery_note_id();
    }

    //function to add delivery detail to a new or updated delivery note
    public int addDeliveryDetails(int deliveryID) {

        DeliveryNote deliveryNote = this.sessionFactory.getCurrentSession().get(DeliveryNote.class, deliveryID);
        //delete the old details from the sale invoice if content
        if(!deliveryNote.getDeliveryDetails().isEmpty()) {
            for (DeliveryDetail oldDetail: deliveryNote.getDeliveryDetails()) {
                this.sessionFactory.getCurrentSession().delete(oldDetail);
            }
        }

        //get products from sale invoice to copy to delivery details
        if (deliveryNote.getSaleInvoice().getSaleDetails() != null) {
            for (SaleDetail saleDetail: deliveryNote.getSaleInvoice().getSaleDetails()) {
                //create a new delivery detail note for each sale detail
                DeliveryDetail newDetail = new DeliveryDetail();
                //set the delivery note with the new detail created
                newDetail.setDeliveryNote(deliveryNote);
                //get product from sale invoice
                newDetail.setProduct(saleDetail.getProduct());
                //get the product quantity from the sale detail
                newDetail.setQuantity(saleDetail.getQuantity());
                //save the new detail into the database
                this.sessionFactory.getCurrentSession().save(newDetail);
            }
        }

        return deliveryID;
    }

    public int updateDeliveryNote(DeliveryNote deliveryNote) {
        //update delivery note to database
        this.sessionFactory.getCurrentSession().update(deliveryNote);
        //return the id of delivery note
        return deliveryNote.getDelivery_note_id();
    }

    //function to update delivery details with changed sale invoice
    public DeliveryNote updateDeliveryDetails(int deliveryId) {
        int updateNoteId = addDeliveryDetails(deliveryId);

        //clear data of current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //
        return this.sessionFactory.getCurrentSession().get(DeliveryNote.class, updateNoteId);
    }

    public String deleteDeliveryNote(DeliveryNote deliveryNote) {
        this.sessionFactory.getCurrentSession().delete(deliveryNote);
        return "Delivery Note with id: " + deliveryNote.getDelivery_note_id() + " is successfully deleted";
    }


    public List<DeliveryNote> getNoteByDate(Date start, Date end) {
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class)
                .add(Restrictions.between("date", start, end));
        List<DeliveryNote> noteList = criteria.list();
        return noteList;
    }
}

