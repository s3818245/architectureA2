package com.quynhanh.architecturea2.model;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class SaleInvoice {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private Customer customer;


    @OneToMany(mappedBy = "salesInvoice")
    private List<SaleDetail> saleDetails;

    public SaleInvoice() {
    }

}
