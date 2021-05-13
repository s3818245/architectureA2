package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.model.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.sadi_assignment2_s3819293.model.SaleInvoice;
import com.example.sadi_assignment2_s3819293.service.SaleInvoiceService;

@RestController
public class SaleInvoiceController {
    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.GET)
    public List<SaleInvoice> getAllSaleInvoice() {
        return saleInvoiceService.getAllSaleInvoice();
    }

    @RequestMapping(path = "/addSaleInvoice", method = RequestMethod.POST)
    public int addSaleInvoice(SaleInvoice saleInvoice) {
        return saleInvoiceService.addSaleInvoice(saleInvoice);
    }

    @RequestMapping(path = "/deleteSaleInvoice/{id}", method = RequestMethod.DELETE)
    public String deleteSaleInvoice(@PathVariable int id) {
        return saleInvoiceService.deleteSaleInvoice(id);
    }

    @RequestMapping(path = "/updateSaleInvoice/{id}", method = RequestMethod.PUT)
    public SaleInvoice updateSaleInvoice(@RequestBody SaleInvoice saleInvoice, @PathVariable int id) {
        SaleInvoice updateSaleInvoice = saleInvoiceService.getSaleInvoiceById(id);
        if (updateSaleInvoice != null) {
            this.saleInvoiceService.updateSaleInvoice(saleInvoice);
        }
        return saleInvoice;
    }

    @RequestMapping(path = "/getSaleInvoice/{id}", method = RequestMethod.GET)
    public SaleInvoice getSaleInvoice(@PathVariable int id) {
        return saleInvoiceService.getSaleInvoiceById(id);
    }
}
