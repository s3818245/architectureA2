package com.example.sadi_assignment2_s3819293.controller;

import com.example.sadi_assignment2_s3819293.model.SaleDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.POST)
    public int addSaleInvoice(SaleInvoice saleInvoice) {
        return saleInvoiceService.addSaleInvoice(saleInvoice);
    }

    @RequestMapping(path = "/saleInvoices/{id}", method = RequestMethod.DELETE)
    public String deleteSaleInvoice(@PathVariable int id) {
        return saleInvoiceService.deleteSaleInvoice(id);
    }

    @RequestMapping(path = "/saleInvoices/{id}", method = RequestMethod.PUT)
    public SaleInvoice updateSaleInvoice(@RequestBody SaleInvoice saleInvoice, @PathVariable int id) {
        SaleInvoice updateSaleInvoice = saleInvoiceService.getSaleInvoiceById(id);
        if (updateSaleInvoice != null) {
            this.saleInvoiceService.updateSaleInvoice(saleInvoice);
        }
        return saleInvoice;
    }

    @RequestMapping(path = "/saleInvoices/{id}", method = RequestMethod.GET)
    public SaleInvoice getSaleInvoice(@PathVariable int id) {
        return saleInvoiceService.getSaleInvoiceById(id);
    }

    @RequestMapping(path = "/saleInvoices/filter", method = RequestMethod.GET)
    public List<SaleInvoice> getInvoiceByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date != null && start == null && end == null) {
            String startDate = date + " 00:00:00";
            String endDate = date + " 23:59:59";

            try {
                Date atStart = format.parse(startDate);
                Date atEnd = format.parse(endDate);
                return this.saleInvoiceService.getInvoiceByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(start != null && end != null) {
            start += " 00:00:00";
            end += " 23:59:59";

            try {
                Date atStart = format.parse(start);
                Date atEnd = format.parse(end);
                return this.saleInvoiceService.getInvoiceByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        else {
            return null;
        }
    }

    @RequestMapping(path = "/saleInvoices/filter", params = {"start", "end"}, method = RequestMethod.GET)
    public List<SaleInvoice> getInvoiceBetweenDate(@RequestParam(value = "start") String start, @RequestParam(value = "end") String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);

            return this.saleInvoiceService.getInvoiceByDate(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
