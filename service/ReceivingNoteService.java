package com.quynhanh.architecturea2.service;

import com.quynhanh.architecturea2.model.Order;
import com.quynhanh.architecturea2.model.OrderDetail;
import com.quynhanh.architecturea2.model.ReceivingDetail;
import com.quynhanh.architecturea2.model.ReceivingNote;
import org.hibernate.Criteria;
import org.hibernate.QueryException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReceivingNoteService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int addReceivingNote(ReceivingNote receivingNote){
//        receivingNote.getOrder().setReceivingNote(receivingNote);

        this.sessionFactory.getCurrentSession().save(receivingNote);
//        if(receivingNote.getOrder().getOrderDetails()!=null){
//            for (OrderDetail orderDetail : receivingNote.getOrder().getOrderDetails()){
//                ReceivingDetail newDetail = new ReceivingDetail();
//                newDetail.setReceivingNote(receivingNote);
//                newDetail.setProduct(orderDetail.getProduct());
//                newDetail.setQuantity(orderDetail.getQuantity());
//                this.sessionFactory.getCurrentSession().save(newDetail);
//            }
//        }

//        for (ReceivingDetail receivingDetail : receivingNote.getReceivingDetails()){
//            //set the delivery note in the delivery detail to be the current one
//            receivingDetail.setReceivingNote(receivingNote);
//            //save the details of the receiving note
//            this.sessionFactory.getCurrentSession().save(receivingDetail);
//        }

        return receivingNote.getReceiving_note_id();
    }

    //Function to add details for the newly created receiving note
    public int addReceivingDetails(int receivingId){

        ReceivingNote receivingNote = this.sessionFactory.getCurrentSession().get(ReceivingNote.class, receivingId);

//        Query query = this.sessionFactory.getCurrentSession().createQuery("from Order where receivingNote = :noteId");
//        query.setString("noteId", Integer.toString(receivingId));
//        Order order = (Order) query.list().get(0);

        if(receivingNote.getOrder().getOrderDetails()!=null){
            for (OrderDetail orderDetail : receivingNote.getOrder().getOrderDetails()){
                //create new receiving note detail for each order detail
                ReceivingDetail newDetail = new ReceivingDetail();
                //set the receiving note of this detail as the newly created receiving note
                newDetail.setReceivingNote(receivingNote);
                //get product from the order detail
                newDetail.setProduct(orderDetail.getProduct());
                //get quantity from the order detail
                newDetail.setQuantity(orderDetail.getQuantity());
                //save new detail to the database
                this.sessionFactory.getCurrentSession().save(newDetail);
            }
        }

        return receivingId;
    }

    public int updateReceivingNote(ReceivingNote receivingNote){
        this.sessionFactory.getCurrentSession().update(receivingNote);
        return receivingNote.getReceiving_note_id();
    }

    //function to update receiving details with new order
    public ReceivingNote updateReceivingDetails(int receivingId){
        int updatedNoteId = addReceivingDetails(receivingId);//call function to update details with new order details
        //get the updated note
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, updatedNoteId);
    }

    public ReceivingNote getOneReceivingNote(int id){
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
    }

    public String deleteReceivingNote(ReceivingNote receivingNote) {
        sessionFactory.getCurrentSession().delete(receivingNote);
        return "Delivery Note with id: " + receivingNote.getReceiving_note_id() + " is successfully deleted";
    }

    public List<ReceivingNote> getAllReceivingNote() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class);
        return criteria.list();
    }
}
