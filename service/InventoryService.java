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

        //join product, receiving note and the receiving details table
        //get the total of quantity of each product on the database
        SQLQuery queryForReceiving = sessionFactory.getCurrentSession().createSQLQuery(
                "select P.id, P.name, sum(R.quantity) as receivied "+
                        "from product P, receivingDetail R, receivingNote RN " +
                        "where R.receivingnote_receiving_note_id = RN.receiving_note_id and P.id = R.product_id " +
                        "and RN.date >= :startdate and RN.date <= :enddate " +
                        "group by P.id;"
        );

        //get the total of quantity of each product on the database
        //join product, delivering note and the delivering details table
        SQLQuery queryForDelivered = sessionFactory.getCurrentSession().createSQLQuery(
                "select P.id, P.name, sum(D.quantity) as delivered "+
                        "from product P, deliveryDetail D, deliveryNote DN " +
                        "where P.id = D.product_id and D.deliverynote_delivery_note_id = DN.delivery_note_id " +
                        "and DN.date >= :startdate and DN.date <= :enddate " +
                        "group by P.id;"
        );

        //set the parameter in the query to start and end date specified 
        queryForDelivered.setParameter("startdate", start);
        queryForDelivered.setParameter("enddate", end);
        queryForReceiving.setParameter("startdate", start);
        queryForReceiving.setParameter("enddate", end);
        
        //convert query result to list
        List<Object[]> delivered = queryForDelivered.list();
        List<Object[]> received = queryForReceiving.list();

        //List that will store server response
        List<String> response = new ArrayList<>();

        for (Object[] receivedProducts : received){//go through each result of the receiving query
            boolean productDelivered = false;
            String newLineInResponse = "";

            for (Object[] deliveredProducts : delivered){//go through each result of the delivering query
                if (receivedProducts[0] == deliveredProducts[0]){//if product is delivered
                    // in the response set balance to received - delivered
                     newLineInResponse = "Product: " + receivedProducts[1].toString() + " (id - " + receivedProducts[0].toString()
                            + ") | Received: " + receivedProducts[2].toString() + " | Delivered: " + deliveredProducts[2].toString() +
                            " | Balance: " + ( Integer.parseInt(receivedProducts[2].toString()) - Integer.parseInt(deliveredProducts[2].toString()));
                    productDelivered = true;
                    break;//break inner for loop
                }
            }

            if (!productDelivered){ // if the product is not delivered, set the balance to the amount received
                newLineInResponse = "Product: " + receivedProducts[1].toString() + " (id - " + receivedProducts[0].toString()
                        + ") | Received: " + receivedProducts[2].toString() + " | Delivered: 0"+
                        " | Balance: " + receivedProducts[2].toString();
            }

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
