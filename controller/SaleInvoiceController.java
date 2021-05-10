package com.example.sadi_assignment2_s3819293.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
