package com.quynhanh.architecturea2.service;
import com.quynhanh.architecturea2.model.DeliveryDetail;
import com.quynhanh.architecturea2.model.DeliveryNote;
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
}

