package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.OrderDetail;
import com.example.sadi_assignment2_s3819293.model.ReceivingDetail;
import com.example.sadi_assignment2_s3819293.model.ReceivingNote;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
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

    public List<ReceivingNote> getAllReceivingNote() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class);
        return criteria.list();
    }

    public ReceivingNote getOneReceivingNote(int id){
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
    }

    public int addReceivingNote(ReceivingNote receivingNote){
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

    public int addReceivingDetails(int receivingId){
        ReceivingNote receivingNote = this.sessionFactory.getCurrentSession().get(ReceivingNote.class, receivingId);
        if(receivingNote.getOrder().getOrderDetails()!=null){
            for (OrderDetail orderDetail : receivingNote.getOrder().getOrderDetails()){
                ReceivingDetail newDetail = new ReceivingDetail();
                newDetail.setReceivingNote(receivingNote);
                newDetail.setProduct(orderDetail.getProduct());
                newDetail.setQuantity(orderDetail.getQuantity());
                this.sessionFactory.getCurrentSession().save(newDetail);
            }
        }

        return receivingId;
    }

    public String deleteReceivingNote(int id) {
        ReceivingNote receivingNote = sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
        if (receivingNote != null) {
            sessionFactory.getCurrentSession().delete(receivingNote);
        }
        return "Delivery Note with id: " + receivingNote.getReceiving_note_id() + " is successfully deleted";
    }

    public ReceivingNote updateReceivingNote(ReceivingNote receivingNote) {
        if (receivingNote.getReceivingDetails() != null) {
            for (ReceivingDetail receivingDetail: receivingNote.getReceivingDetails()) {
                receivingDetail.setReceivingNote(receivingNote);
            }
        }
        sessionFactory.getCurrentSession().update(receivingNote);
        return receivingNote;
    }
}
