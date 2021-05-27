package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.OrderDetail;
import com.example.sadi_assignment2_s3819293.model.ReceivingDetail;
import com.example.sadi_assignment2_s3819293.model.ReceivingNote;

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
 * This class represent a Receiving Note Service, which contain all the CRUD operation with some additional API like get all the note(s) in a period of time
 */

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
        //save receiving note to the database
        this.sessionFactory.getCurrentSession().save(receivingNote);
        //return the id of the new receiving note
        return receivingNote.getReceiving_note_id();
    }

    //Function to add details for the newly created or updated receiving note
    public int addReceivingDetails(int receivingId){

        ReceivingNote receivingNote = this.sessionFactory.getCurrentSession().get(ReceivingNote.class, receivingId);
        //if the receiving notes have details copied from the old order, delete it
        if (!receivingNote.getReceivingDetails().isEmpty()){
            for (ReceivingDetail oldDetail : receivingNote.getReceivingDetails()){
                this.sessionFactory.getCurrentSession().delete(oldDetail);
            }
        }

        //get products from order to copy to receiving details
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
        //update receiving note to the database
        this.sessionFactory.getCurrentSession().update(receivingNote);
        //return the receiving note id
        return receivingNote.getReceiving_note_id();
    }

    //function to update receiving details with new order
    public ReceivingNote updateReceivingDetails(int receivingId){
        int updatedNoteId = addReceivingDetails(receivingId);//call function to update details with new order details

        //clear data of current session
        this.sessionFactory.getCurrentSession().flush();
        this.sessionFactory.getCurrentSession().clear();

        //get the updated note from the database
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, updatedNoteId);
    }

    public ReceivingNote getOneReceivingNote(int id){
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
    }

    public String deleteReceivingNote(ReceivingNote receivingNote) {
        sessionFactory.getCurrentSession().delete(receivingNote);
        return "Receiving Note with id: " + receivingNote.getReceiving_note_id() + " is successfully deleted";
    }

    public List<ReceivingNote> getAllReceivingNote() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class);
        return criteria.list();
    }

    public List<ReceivingNote> getNoteByDate(Date start, Date end){
        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class)
                .add(Restrictions.between("date", start, end));
        List<ReceivingNote> noteList = criteria.list();
        return noteList;
    }
}
