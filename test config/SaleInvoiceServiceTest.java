package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.CustomerService;
import com.quynhanh.architecturea2.service.SaleInvoiceService;
import com.quynhanh.architecturea2.service.StaffService;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest({"spring.main.allow-bean-definition-overriding=true"})//enable override beans in old app config
@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SaleInvoiceServiceTest {
    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerService customerService;

    private static boolean dataPopulated = false;

    //initialize dependency for sale invoice
    static Staff staff1 = new Staff(1,"Quing", "010 Nguyen Van Linh", "010101010","quing@gmail.com");
    static Staff staff2 = new Staff(2,"NU", "123 Nguyen Van Linh", "020202020202","nu@gmail.com");

    static Customer customer1 = new Customer(1,"Sample customer", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing");
    static Customer customer2 = new Customer(2,"Sample customer 2", "9643 Nguyen Van Linh", "909090909", "958393500","sampleprovider@gmail.com", "NU");

    static Category category1 = new Category(1,"Computer");
    static Category category2 = new Category(2,"Projector");

    static Product product1 = new Product(1,"Macbook M1", "2020","Apple", "Apple",
            "The new model of Macbook", category1, 1300 );
    static Product product2 = new Product(2,"3D Projector", "2019","Pineapple", "Pineapple",
            "New model of projector", category2, 500 );

    //initialize sale details
    static SaleDetail saleDetail1 = new SaleDetail(1, 1000, product1);
    static SaleDetail saleDetail2 = new SaleDetail(2, 700, product2);
    static SaleDetail saleDetail3 = new SaleDetail(2, 2000, product1);

    //initialize list for 2 sale details
    static List<SaleDetail> saleDetailList1 = new ArrayList<>();
    static List<SaleDetail> saleDetailList2 = new ArrayList<>();

    //create date for sale invoices
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static Date sampleDate1 = null;
    static Date sampleDate2 = null;

    static SaleInvoice saleInvoice1 = null;
    static SaleInvoice saleInvoice2 = null;

    @BeforeClass
    public static void beforeAll() {
        System.out.println("\nSTART OF THE TESTING");
    }

    @AfterClass
    public static void afterAll() {
        System.out.println("\nEND OF ALL THE TESTS");
    }

    @Before
    public void setUp() {
        //Initialize mock data for the database
        //create sample date
        try {
            sampleDate1 = format.parse("2020-10-10");
            sampleDate2 = format.parse("2020-10-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create new sale invoice
        saleInvoice1 = new SaleInvoice(1, sampleDate1, staff1, customer1, saleDetailList1);
        saleInvoice2 = new SaleInvoice(2, sampleDate2, staff2, customer2, saleDetailList2);

        if (!dataPopulated){
            //populate sample sale details list
            saleDetailList1.add(saleDetail1);
            saleDetailList1.add(saleDetail2);
            saleDetailList2.add(saleDetail3);

            //populate staff table
            this.staffService.addStaff(staff1);
            this.staffService.addStaff(staff2);

            //populate customer table
            this.customerService.addCustomer(customer1);
            this.customerService.addCustomer(customer2);

            //populate product database
            this.saleInvoiceService.addProduct(product1);
            this.saleInvoiceService.addProduct(product2);


            //populate database
            this.saleInvoiceService.addSaleInvoice(saleInvoice1);
            this.saleInvoiceService.addSaleInvoice(saleInvoice2);

        }

        //make sure to only populate data once
        dataPopulated = true;
    }

    @Test
    public void testAddSaleInvoice() {
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);

        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create and populate staff
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);

        //create and populate customer
        Customer newCustomer = new Customer("Sample customer", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing");
        this.customerService.addCustomer(newCustomer);

        //populate product for sale invoice
        this.saleInvoiceService.addProduct(sampleProduct);

        //create sample sale invoice
        SaleInvoice sampleSaleInvoice = new SaleInvoice(sampleDate, newStaff, newCustomer, saleDetailList);

        //get return value after adding
        int newSaleInvoiceId = this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);

        System.out.println("--TEST FOR ADDING SALE INVOICE--");
        //expect return the new sale invoice id
        assertEquals(sampleSaleInvoice.getSale_invoice_id(), newSaleInvoiceId);

        //remove sample data from database
        this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice);
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.customerService.deleteCustomer(newCustomer);
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testDeleteSaleInvoice(){
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);

        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create and populate staff
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);

        //create and populate customer
        Customer newCustomer = new Customer("Sample customer", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing");
        this.customerService.addCustomer(newCustomer);

        //populate product for sale invoice
        this.saleInvoiceService.addProduct(sampleProduct);

        //create sample sale invoice
        SaleInvoice sampleSaleInvoice = new SaleInvoice(sampleDate, newStaff, newCustomer, saleDetailList);

        //get id of the new sample sale invoice
        int newSaleInvoiceId = this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);

        //expected delete message for delete sample sale invoice
        String expectedMessage = "Successfully deleted sale invoice_" + newSaleInvoiceId;

        //delete sample dependecies

        System.out.println("--TEST FOR DELETE SALE INVOICE--");
        //expect return the new sale invoice id
        assertEquals(expectedMessage, this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice));
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.customerService.deleteCustomer(newCustomer);
        this.staffService.deleteStaff(newStaff);

    }

    @Test
    public void testUpdateSaleInvoice(){
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);
        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create and populate staff
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);
        //create updated new staff
        Staff updatedStaff = new Staff("Nha Uyen", "12 Nguyen Van Linh", "1001101010", "nu@gmail.com");
        this.staffService.addStaff(newStaff);

        //create and populate customer
        Customer newCustomer = new Customer("Sample customer", "12 Nguyen Van Linh", "01010011001", "010100110","samplecustomer@gmail.com", "Quing");
        this.customerService.addCustomer(newCustomer);
        //create new customer for updating
        Customer updatedCustomer = new Customer("updated provider", "9643 Nguyen Van Linh", "909090909", "958393500","samplecustomer@gmail.com", "Quing");
        int newCustomerId = this.customerService.addCustomer(updatedCustomer);
        updatedCustomer.setCustomer_id(newCustomerId);

        //populate product for sale invoice
        this.saleInvoiceService.addProduct(sampleProduct);
        //create and populate new product for testing update
        Product updatedProduct = new Product("udpated product", "2018", "RMIT", "RMIT", "updated prpduct",
                new Category(3,"sample"), 200);
        int newProductId = this.saleInvoiceService.addProduct(updatedProduct);
        updatedProduct.setId(newProductId);//update the id on db

        //create sample sale invoice
        SaleInvoice sampleSaleInvoice = new SaleInvoice(sampleDate, newStaff, newCustomer, saleDetailList);
        //populate sale invoice with sample
        this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);


        //create new date for update
        Date updatedDate = null;
        try {
            updatedDate = format.parse("2021-01-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //update the fields using setters
        //set new date
        sampleSaleInvoice.setDate(updatedDate);
        //Change product in details
        sampleSaleInvoice.getSaleDetails().get(0).setProduct(updatedProduct);
        //update sample object with new price
        sampleSaleInvoice.getSaleDetails().get(0).setPrice(200);
        sampleSaleInvoice.getSaleDetails().get(0).setTotalValue(2400);
        //update customer
        sampleSaleInvoice.setCustomer(updatedCustomer);
        //update staff

        System.out.println("TEST UPDATE SALE INVOICE\n");
        //expected to return the updated sale invoice
        assertEquals(sampleSaleInvoice.toString(), this.saleInvoiceService.updateSaleInvoice(sampleSaleInvoice).toString());

        //delete sample data
        this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice);
        this.staffService.deleteStaff(updatedStaff);
        this.staffService.deleteStaff(newStaff);
        this.customerService.deleteCustomer(newCustomer);
        this.customerService.deleteCustomer(updatedCustomer);
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.saleInvoiceService.deleteProduct(updatedProduct);
    }

    @Test
    public void testGetSaleInvoice(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


        Date sampleStartDate = null;
        Date sampleEndDate = null;
        try {

            sampleStartDate = dateFormat.parse("2020-10-01 00:00:00.000");
            sampleEndDate = dateFormat.parse("2020-10-20 00:00:00.000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //create expected data
        //define expected saleInvoice list
        List<SaleInvoice> expectedSaleList = Arrays.asList(saleInvoice2,saleInvoice1);
        saleInvoice1.setTotalPrice(2300);
        saleInvoice2.setTotalPrice(2600);

        System.out.println("TEST GET ONE SALE INVOICE\n");
        assertEquals(saleInvoice1.toString(), this.saleInvoiceService.getASaleInvoice(1).toString());

        System.out.println("TEST GET ALL SALE INVOICES\n");
        assertEquals(expectedSaleList.toString(), this.saleInvoiceService.getAllSaleInvoice().toString());

        System.out.println("TEST GET ALL SALE INVOICES FROM START TO END DATE\n");
        assertEquals(Arrays.asList(saleInvoice2,saleInvoice1).toString(),
                this.saleInvoiceService.getInvoiceByDate(sampleStartDate, sampleEndDate).toString());

        System.out.println("TEST GET ALL SALE INVOICES BY CUSTOMER FROM START TO END DATE\n");
        assertEquals(Arrays.asList(saleInvoice1).toString(), this.saleInvoiceService.
                getInvoiceByCustomerOrStaff(sampleStartDate, sampleEndDate, "customer", 1).toString());
        System.out.println("TEST GET ALL SALE INVOICES BY STAFF FROM START TO END DATE\n");
        assertEquals(Arrays.asList(saleInvoice2).toString(), this.saleInvoiceService.
                getInvoiceByCustomerOrStaff(sampleStartDate, sampleEndDate, "staff", 2).toString());
    }


    @Test
    public void testStatisticsAPI(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


        Date sampleStartDate = null;
        Date sampleEndDate = null;
        try {

            sampleStartDate = dateFormat.parse("2020-10-01 00:00:00.000");
            sampleEndDate = dateFormat.parse("2020-10-20 00:00:00.000");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("TEST GET REVENUE FROM START TO END DATE\n");
        assertEquals(4900, this.saleInvoiceService.getRevenueByDate(sampleStartDate, sampleEndDate), 0.0);

        System.out.println("TEST GET REVENUE BY STAFF FROM START TO END DATE\n");
        assertEquals(2300, this.saleInvoiceService.getRevenueByCustomerOrStaff(sampleStartDate, sampleEndDate, "staff", 1), 0.0);

        System.out.println("TEST GET REVENUE BY CUSTOMER FROM START TO END DATE\n");
        assertEquals(2600, this.saleInvoiceService.getRevenueByCustomerOrStaff(sampleStartDate, sampleEndDate, "customer", 2), 0.0);
    }
}
