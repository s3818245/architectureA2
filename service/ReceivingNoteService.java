package com.quynhanh.architecturea2.service;

import com.quynhanh.architecturea2.model.ReceivingDetail;
import com.quynhanh.architecturea2.model.ReceivingNote;
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

    public int addReceivingNote(ReceivingNote receivingNote){
        for (ReceivingDetail receivingDetail : receivingNote.getReceivingDetails()){
            //set the delivery note in the delivery detail to be the current one
            receivingDetail.setReceivingNote(receivingNote);
            //save the details of the receiving note
            this.sessionFactory.getCurrentSession().save(receivingDetail);
        }

        return receivingNote.getReceiving_note_id();
    }

    public ReceivingNote getOneReceivingNote(int id){
        return this.sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
    }

    public List<ReceivingNote> getAllReceivingNote() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class);
        return criteria.list();
    }
}
