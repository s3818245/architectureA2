package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<String> getInventory(Date start, Date end) {

        //steps in the query
        //- join receiving note table with receiving details
        //- join delivery note table with delivery details
        //- filter out the receiving note after start date and delivery note before start date
        //- join product table with receiving details and delivery details => only products in both tables would be in the 
        //query result
        
        SQLQuery queryForInventory = sessionFactory.getCurrentSession().createSQLQuery(
                "select P.id, P.name, sum(R.quantity) as receivied , sum(D.quantity) as delivered, " +
                        "sum(R.quantity) - sum(D.quantity) as balance " +
                    "from product P, receivingDetail R, deliveryDetail D, receivingNote RN, deliveryNote DN " +
                    "where R.receivingnote_receiving_note_id = RN.receiving_note_id and P.id = R.product_id " +
                        "and P.id = D.product_id and D.deliverynote_delivery_note_id = DN.delivery_note_id " +
                        "and RN.date >= :startdate and DN.date <= :enddate " +
                        "group by P.id;"
        );

        queryForInventory.setParameter("startdate", start);
        queryForInventory.setParameter("enddate", end);

        //change format of each result to array
        List<Object[]> result = queryForInventory.list();

        //List that will store server response
        List<String> response = new ArrayList<>();

        for (Object[] line : result){
            String newLineInResponse = "Product: " + line[1].toString() + " (id - " + line[0].toString()
                                        + ") | Received: " + line[2].toString() + " | Delivered: " + line[3].toString() +
                                        " | Balance: " + line[4].toString();

            response.add(newLineInResponse);
        }

        return response;
    }

    public List<ReceivingNote> getInventoryQuery(Date start, Date end) {
        Query receivingQuery = sessionFactory.getCurrentSession().createSQLQuery("select P.name, sum(D.quantity)" +
                "from product P, receivingNote N, receivingDetail D" +
                "where P.id = D.product_id and D.receiving_note_id = N.receiving_note_id" +
                "and N.date >= :start and N.date <= end" +
                "group by P.id");
        receivingQuery.setParameter("start", start);
        receivingQuery.setParameter("end", end);

        Query deliveryQuery = sessionFactory.getCurrentSession().createSQLQuery("select P.name, sum(D.quantity)" +
                "from product P, deliveryNote N, deliveryDetail D" +
                "where P.id = D.product_id and D.delivery_note_id = N.delivery_note_id" +
                "and N.date >= :start and N.date <= end" +
                "group by P.id");
        receivingQuery.setParameter("start", start);
        receivingQuery.setParameter("end", end);

        List<ReceivingNote> receivingNotes = receivingQuery.getResultList();
        List<DeliveryNote> deliveryNotes = deliveryQuery.getResultList();

        HashMap<String, ArrayList<String>> productKey = new HashMap<>();
        ArrayList<String> productInventoryDetail = new ArrayList<>();

        return receivingNotes;
    }
}
