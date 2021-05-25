package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.*;
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
public class DeliveryNoteServiceTest {
    @Autowired
    private SaleInvoiceService saleInvoiceService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeliveryNoteService deliveryNoteService;

    private static boolean dataPopulated=false;

    //initialize data to create mock delivery note

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

    //initialize list for 2 orders
    static List<SaleDetail> saleDetailList1 = new ArrayList<>();
    static List<SaleDetail> saleDetailList2 = new ArrayList<>();

    //create date for sale invoice
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static Date sampleDate1 = null;
    static Date sampleDate2 = null;
    static Date sampleDeliveryDate1 = null;
    static Date sampleDeliveryDate2 = null;

    static SaleInvoice saleInvoice1 = null;
    static SaleInvoice saleInvoice2 = null;

    static DeliveryNote sampleDelivery1 = null;
    static DeliveryNote sampleDelivery2 = null;

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

        if (!dataPopulated){//make sure only run during the first test
            //create sample date
            try {
                sampleDate1 = format.parse("2020-10-10");
                sampleDate2 = format.parse("2020-10-05");

                sampleDeliveryDate1 = format.parse("2020-10-15");
                sampleDeliveryDate2 = format.parse("2020-10-10");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //create new invoice
            saleInvoice1 = new SaleInvoice(1, sampleDate1, staff1, customer1, saleDetailList1);
            saleInvoice2 = new SaleInvoice(2, sampleDate2, staff2, customer2, saleDetailList2);

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

            //create sample delivery note
            sampleDelivery1 = new DeliveryNote(sampleDeliveryDate1, staff1, saleInvoice1);
            sampleDelivery2 = new DeliveryNote(sampleDeliveryDate2, staff2, saleInvoice2);

            //populate delivery details and delivery note to table
            this.deliveryNoteService.addDeliveryDetails(deliveryNoteService.addDeliveryNote(sampleDelivery1));
            this.deliveryNoteService.addDeliveryDetails(deliveryNoteService.addDeliveryNote(sampleDelivery2));


        }

        //make sure to only populate data once
        dataPopulated = true;
    }

    @Test
    public void testAddNote(){
        //Create sample sale invoice for adding note
        //create sample sale invoice details
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);

        //create invoice and delivery date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        Date sampleDeliveryDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
            sampleDeliveryDate = format.parse("2021-01-01");
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
        //add sample sale invoice to the list
        this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);

        //create sample receiving note for adding
        DeliveryNote sampleDelivery = new DeliveryNote(sampleDeliveryDate, newStaff, sampleSaleInvoice);
        //get id when added
        int actualId = this.deliveryNoteService.addDeliveryDetails(this.deliveryNoteService.addDeliveryNote(sampleDelivery));

        //test if id is as expected
        assertEquals(sampleDelivery.getDelivery_note_id(), actualId);

        //delete sample data from database
        this.deliveryNoteService.deleteDeliveryNote(sampleDelivery);
        this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice);
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.customerService.deleteCustomer(newCustomer);
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testDeleteNote(){
        //Create sample sale invoice for adding note
        //create sample sale invoice details
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);

        //create invoice and delivery date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        Date sampleDeliveryDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
            sampleDeliveryDate = format.parse("2021-01-01");
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
        //add sample sale invoice to the list
        this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);

        //create sample receiving note for adding
        DeliveryNote sampleDelivery = new DeliveryNote(sampleDeliveryDate, newStaff, sampleSaleInvoice);
        //get id when added
        int actualId = this.deliveryNoteService.addDeliveryDetails(this.deliveryNoteService.addDeliveryNote(sampleDelivery));

        //get expected delete message
        String expectedMessage = "Delivery Note with id: " + actualId + " is successfully deleted";
        String message = this.deliveryNoteService.deleteDeliveryNote(sampleDelivery);

        assertEquals(expectedMessage, message);

        //delete sample data from database
        //delete sample data from database
        this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice);
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.customerService.deleteCustomer(newCustomer);
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testUpdateNote(){
        //Create sample sale invoice for adding note
        //create sample sale invoice details
        List<SaleDetail> saleDetailList = new ArrayList<>();
        Product sampleProduct = new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail saleDetail = new SaleDetail(12, 53, sampleProduct);
        saleDetailList.add(saleDetail);

        //create sample updated detail for updated invoice
        List<SaleDetail> updatedSaleDetailList = new ArrayList<>();
        Product updatedProduct = new Product("Updated Product M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 );
        SaleDetail updatedSaleDetail = new SaleDetail(12, 53, updatedProduct);
        updatedSaleDetailList.add(updatedSaleDetail);

        //create invoice and delivery date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        Date sampleDeliveryDate = null;
        Date sampleUpdatedDeliveryDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
            sampleDeliveryDate = format.parse("2021-01-01");
            sampleUpdatedDeliveryDate = format.parse("2021-01-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create and populate staff
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);
        //create and populate staff
        Staff updatedStaff = new Staff("Nha Uyen", "12 Nguyen Van Linh", "1001101010", "nu@gmail.com");
        this.staffService.addStaff(updatedStaff);

        //create and populate customer
        Customer newCustomer = new Customer("Sample customer", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing");
        this.customerService.addCustomer(newCustomer);

        //populate product for sale invoice
        this.saleInvoiceService.addProduct(sampleProduct);
        this.saleInvoiceService.addProduct(updatedProduct);

        //create sample sale invoice
        SaleInvoice sampleSaleInvoice = new SaleInvoice(sampleDate, newStaff, newCustomer, saleDetailList);
        //create updated sale invoice
        SaleInvoice updatedSaleInvoice = new SaleInvoice(sampleDate, newStaff, newCustomer, updatedSaleDetailList);
        //add sample sale invoice to the list
        this.saleInvoiceService.addSaleInvoice(sampleSaleInvoice);
        //add sample updated sale invoice to the list
        this.saleInvoiceService.addSaleInvoice(updatedSaleInvoice);

        //create sample receiving note for adding
        DeliveryNote sampleDelivery = new DeliveryNote(sampleDeliveryDate, newStaff, sampleSaleInvoice);
        //get id when added
        int actualId = this.deliveryNoteService.addDeliveryDetails(this.deliveryNoteService.addDeliveryNote(sampleDelivery));

        //create delivery note updated with new details
        sampleDelivery.setSaleInvoice(updatedSaleInvoice);
        sampleDelivery.setDate(sampleUpdatedDeliveryDate);
        sampleDelivery.setStaff(updatedStaff);


        //get return value when update
        DeliveryNote returnValue = this.deliveryNoteService
                .updateDeliveryDetails(deliveryNoteService.updateDeliveryNote(sampleDelivery));

        //expected to return receiving note with details copied from order
        DeliveryDetail expectedDetail = new DeliveryDetail(updatedProduct, 12);
        sampleDelivery.setDeliveryDetails(Arrays.asList(expectedDetail));

        System.out.println(returnValue.toString());
        assertEquals(sampleDelivery.toString(), returnValue.toString());

        //delete all sample data
        this.deliveryNoteService.deleteDeliveryNote(sampleDelivery);
        this.saleInvoiceService.deleteSaleInvoice(sampleSaleInvoice);
        this.saleInvoiceService.deleteSaleInvoice(updatedSaleInvoice);
        this.saleInvoiceService.deleteProduct(sampleProduct);
        this.saleInvoiceService.deleteProduct(updatedProduct);
        this.customerService.deleteCustomer(newCustomer);
        this.staffService.deleteStaff(newStaff);
        this.staffService.deleteStaff(updatedStaff);


    }

    @Test
    public void testGetNote(){
        //update the sample data with details generated from sale invoice for testing get
        DeliveryDetail deliveryDetail1 = new DeliveryDetail(1, product1, 1);
        DeliveryDetail deliveryDetail2 = new DeliveryDetail(2, product2, 2);
        DeliveryDetail deliveryDetail3 = new DeliveryDetail(3, product1, 2);


        sampleDelivery1.setDeliveryDetails(Arrays.asList(deliveryDetail1, deliveryDetail2));
        sampleDelivery2.setDeliveryDetails(Arrays.asList(deliveryDetail3));

        //test get one
        assertEquals(sampleDelivery1.toString(), this.deliveryNoteService.getOneDeliveryNote(1).toString());

        //test get all
        assertEquals(Arrays.asList(sampleDelivery1, sampleDelivery2).toString(),
                this.deliveryNoteService.getAllDeliveryNote().toString());
    }

    @Test
    public void testFilterNote(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date sampleDateStart = null;
        Date sampleDateEnd = null;
        Date sampleStartDate = null;
        Date sampleEndDate = null;
        try {
            sampleDateStart = dateFormat.parse("2020-10-10 00:00:00.000");
            sampleDateEnd = dateFormat.parse("2020-10-10 23:59:59.000");
            sampleStartDate = dateFormat.parse("2020-10-01 00:00:00.000");
            sampleEndDate = dateFormat.parse("2020-10-20 00:00:00.000");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //update the sample data with details generated from sale invoice for testing get
        DeliveryDetail deliveryDetail1 = new DeliveryDetail(1, product1, 1);
        DeliveryDetail deliveryDetail2 = new DeliveryDetail(2, product2, 2);
        DeliveryDetail deliveryDetail3 = new DeliveryDetail(3, product1, 2);


        sampleDelivery1.setDeliveryDetails(Arrays.asList(deliveryDetail1, deliveryDetail2));
        sampleDelivery2.setDeliveryDetails(Arrays.asList(deliveryDetail3));


        //expected result for filter by a date
        assertEquals(Arrays.asList(sampleDelivery2).toString(),
                this.deliveryNoteService.getNoteByDate(sampleDateStart, sampleDateEnd).toString());
        //expected result for filter by start and end date
        assertEquals(Arrays.asList(sampleDelivery1, sampleDelivery2).toString(),
                this.deliveryNoteService.getNoteByDate(sampleStartDate, sampleEndDate).toString());

    }

}
