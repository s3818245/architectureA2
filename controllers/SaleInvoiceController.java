package com.example.sadi_assignment2_s3819293.controllers;

import com.example.sadi_assignment2_s3819293.model.SaleInvoice;
import com.example.sadi_assignment2_s3819293.service.SaleInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nguyen Thi Nha Uyen (s3819293) and Phan Truong Quynh Anh (s3818245)
 * @version 1.0
 * @since May 10, 2021
 *
 * This class represent a Controller for Sale Invoice in a REST API, it contains all the paths for each operations in the Service
 */

@RestController
public class SaleInvoiceController {
    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.GET)
    public List<SaleInvoice> getAllSaleInvoice() {
        return this.saleInvoiceService.getAllSaleInvoice();
    }

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.POST)
    public int addSaleInvoice(@RequestBody SaleInvoice saleInvoice) {
        return this.saleInvoiceService.addSaleInvoice(saleInvoice);
    }

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.DELETE)
    public String deleteSaleInvoice(@RequestBody SaleInvoice saleInvoice) {
        return this.saleInvoiceService.deleteSaleInvoice(saleInvoice);
    }

    @RequestMapping(path = "/saleInvoices", method = RequestMethod.PUT)
    public SaleInvoice updateSaleInvoice(@RequestBody SaleInvoice saleInvoice) {
        return this.saleInvoiceService.updateSaleInvoice(saleInvoice);
    }

    @RequestMapping(path = "/saleInvoices/{id:[\\d]+}", method = RequestMethod.GET)
    public SaleInvoice getSaleInvoice(@PathVariable int id) {
        return saleInvoiceService.getASaleInvoice(id);
    }

    @RequestMapping(path = "/saleInvoices/filter", method = RequestMethod.GET)
    public List<SaleInvoice> getInvoiceByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date != null && start == null && end == null) { //filter by a date
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
        else if(start != null && end != null) {//filter between start and end date
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

    @RequestMapping(path = "/saleInvoices/filter", params = {"start", "end", "type", "id"}, method = RequestMethod.GET)
    public List<SaleInvoice> getInvoiceByCustomerOrStaff(@RequestParam(value = "start") String start, @RequestParam(value = "end") String end, @RequestParam(value = "type") String type, @RequestParam(value = "id") int id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);

            return this.saleInvoiceService.getInvoiceByCustomerOrStaff(startDate, endDate, type, id);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(path = "/saleInvoices/revenue", method = RequestMethod.GET)
    public double getRevenueByDate(@RequestParam(required = false) String date, @RequestParam(required = false) String start, @RequestParam(required = false) String end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date != null && start == null && end == null) {//find revenue at a specific date
            String startDate = date + " 00:00:00";
            String endDate = date + " 23:59:59";

            try {
                Date atStart = format.parse(startDate);
                Date atEnd = format.parse(endDate);
                return this.saleInvoiceService.getRevenueByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
        else if(start != null && end != null) {//find revenue between start and end date
            start += " 00:00:00";
            end += " 23:59:59";

            try {
                Date atStart = format.parse(start);
                Date atEnd = format.parse(end);
                return this.saleInvoiceService.getRevenueByDate(atStart, atEnd);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
        else {
            return 0;
        }
    }

    @RequestMapping(path = "/saleInvoices/revenue", params = {"start", "end", "type", "id"}, method = RequestMethod.GET)
    public double getRevenueByCustomerOrStaff(@RequestParam(value = "start") String start, @RequestParam(value = "end") String end, @RequestParam(value = "type") String type, @RequestParam(value = "id") int id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);

            return this.saleInvoiceService.getRevenueByCustomerOrStaff(startDate, endDate, type, id);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @RequestMapping("/saleInvoices/*")
    public String pageNotFound() {
        return "Page not found";
    }
}

