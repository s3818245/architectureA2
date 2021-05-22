package com.example.sadi_assignment2_s3819293.service;

import com.example.sadi_assignment2_s3819293.model.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    public List<Integer> getInventory(Date start, Date end) {
//        Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Product.class);
//        List<Product> products = criteria.list();
//        List<Integer> receivedAmount = new ArrayList<>();
//        List<Integer> deliveredAmount = new ArrayList<>();
//        List<Integer> balanceAmount = new ArrayList<>();
//
//        List<Integer> productList = new ArrayList<>();
//
//        for (Product product: products) {
//            int productId = product.getId();
//            productList.add(productId);
//            List<ReceivingDetail> receivingDetails = this.sessionFactory.getCurrentSession().createCriteria(ReceivingDetail.class).list();
//            List<DeliveryDetail> deliveryDetails = this.sessionFactory.getCurrentSession().createCriteria(DeliveryDetail.class).list();
//
//            int receiveProductAmount = 0;
//            int deliveryProductAmount = 0;
//            int balance;
//
//            for (ReceivingDetail receivingDetail: receivingDetails) {
//                int receivingDetailId = receivingDetail.getReceiving_detail_id();
//                System.out.println(receivingDetailId);
//                if (receivingDetailId == productId) {
//                    receiveProductAmount += receivingDetail.getQuantity();
//                    System.out.println(receiveProductAmount);
//                }
//                receivedAmount.add(receiveProductAmount);
//            }
//
//            for (DeliveryDetail deliveryDetail: deliveryDetails) {
//                int deliveryDetailId = deliveryDetail.getDelivery_detail_id();
//                System.out.println(deliveryDetailId);
//                if (deliveryDetailId == productId) {
//                    deliveryProductAmount += deliveryDetail.getQuantity();
//                    System.out.println(deliveryProductAmount);
//                }
//                deliveredAmount.add(deliveryProductAmount);
//            }
//            balance = receiveProductAmount - deliveryProductAmount;
//            balanceAmount.add(balance);
//        }
//        return productList;

        Criteria receivingNoteCriteria = this.sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class)
                .add(Restrictions.between("date", start, end));
        Criteria deliveryNoteCriteria = this.sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class)
                .add(Restrictions.between("date", start, end));

        List<ReceivingNote> receivingNoteList = receivingNoteCriteria.list();
        List<DeliveryNote> deliveryNoteList = deliveryNoteCriteria.list();

        Iterator<ReceivingNote> receivingIterator = receivingNoteList.iterator();
        Iterator<DeliveryNote> deliveryIterator = deliveryNoteList.iterator();

        ArrayList<Integer> productList = new ArrayList<>();

        List<Integer> receivingAmountList = new ArrayList<>();
        List<Integer> deliveryAmountList = new ArrayList<>();

        while (receivingIterator.hasNext() || deliveryIterator.hasNext()) {
            int receiveProductAmount = 0;
            int deliveryProductAmount = 0;
            int balance;

            if (receivingIterator.hasNext()) {
                for (ReceivingDetail receivingDetail: receivingIterator.next().getReceivingDetails()) {
                    if (!productList.contains(receivingDetail.getProduct().getId())) {
                        productList.add(receivingDetail.getProduct().getId());
                        receiveProductAmount = receivingDetail.getQuantity();
                    }
                    else {
                        receiveProductAmount += receivingDetail.getQuantity();
                    }
                    receivingAmountList.add(receiveProductAmount);
                }
            }

            if (deliveryIterator.hasNext()) {
                for (DeliveryDetail deliveryDetail: deliveryIterator.next().getDeliveryDetails()) {
                    if (!productList.contains(deliveryDetail.getProduct())) {
                        //productList.add(deliveryDetail.getProduct());
                        deliveryProductAmount = deliveryDetail.getQuantity();
                    }
                    else {
                        deliveryProductAmount += deliveryDetail.getQuantity();
                    }
                    deliveryAmountList.add(deliveryProductAmount);
                }
            }
        }
        return deliveryAmountList;
    }
}
