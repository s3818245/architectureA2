package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.OrderService;
import com.quynhanh.architecturea2.service.ReceivingNoteService;
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
public class ReceivingNoteServiceTest {
    @Autowired
    private StaffService staffService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReceivingNoteService receivingNoteService;

    private static boolean dataPopulated=false;

    //initialize data to create mock receiving note

    //initialize dependency for receiving note
    static Staff staff1 = new Staff(1,"Quing", "010 Nguyen Van Linh", "010101010","quing@gmail.com");
    static Staff staff2 = new Staff(2,"NU", "123 Nguyen Van Linh", "020202020202","nu@gmail.com");

    static Provider provider1 = new Provider(1,"Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing");
    static Provider provider2 = new Provider(2,"Sample provider 2", "9643 Nguyen Van Linh", "909090909", "958393500","sampleprovider@gmail.com", "NU");

    static Category category1 = new Category(1,"Computer");
    static Category category2 = new Category(2,"Projector");

    static Product product1 = new Product(1,"Macbook M1", "2020","Apple", "Apple",
            "The new model of Macbook", category1, 1300 );
    static Product product2 = new Product(2,"3D Projector", "2019","Pineapple", "Pineapple",
            "New model of projector", category2, 500 );

    //initialize order details
    static OrderDetail orderDetail1 = new OrderDetail(1, 1000, product1);
    static OrderDetail orderDetail2 = new OrderDetail(2, 700, product2);

    //initialize list for 2 orders
    static List<OrderDetail> orderDetailsList1 = new ArrayList<>();
    static List<OrderDetail> orderDetailsList2 = new ArrayList<>();

    //create date for order
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static Date sampleOrderDate1 = null;
    static Date sampleOrderDate2 = null;
    static Date sampleReceivingDate1 = null;
    static Date sampleReceivingDate2 = null;

    static Order order1 = null;
    static Order order2 = null;

    static ReceivingNote sampleReceiving1 = null;
    static ReceivingNote sampleReceiving2 = null;

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
                sampleOrderDate1 = format.parse("2020-10-10");
                sampleOrderDate2 = format.parse("2020-10-05");

                sampleReceivingDate1 = format.parse("2020-10-15");
                sampleReceivingDate2 = format.parse("2020-10-10");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //create new order
            order1 = new Order(1, sampleOrderDate1, staff1, provider1, orderDetailsList1);
            order2 = new Order(2, sampleOrderDate2, staff2, provider2, orderDetailsList2);

            //populate sample order details list
            orderDetailsList1.add(orderDetail1);
            orderDetailsList2.add(orderDetail2);

            //populate staff table for order
            this.staffService.addStaff(staff1);
            this.staffService.addStaff(staff2);

            //populate order table
            this.orderService.addOrder(order1);
            this.orderService.addOrder(order2);

            //create sample receivingNote
            sampleReceiving1 = new ReceivingNote(sampleReceivingDate1, staff1, order1);
            sampleReceiving2 = new ReceivingNote(sampleReceivingDate2, staff2, order2);

            //populate receiving details and receiving note to table
            this.receivingNoteService.addReceivingDetails(receivingNoteService.addReceivingNote(sampleReceiving1));
            this.receivingNoteService.addReceivingDetails(receivingNoteService.addReceivingNote(sampleReceiving2));

            //update the sample data with details generated from order for testing get later on
            ReceivingDetail receivingDetail1 = new ReceivingDetail(new Product(1,"Macbook M1", "2020","Apple", "Apple",
                    "The new model of Macbook", category1, 1300 ), 1);
            ReceivingDetail receivingDetail2 = new ReceivingDetail(new Product(2,"3D Projector", "2019","Pineapple", "Pineapple",
                    "New model of projector", category2, 500 ), 2);


            sampleReceiving1.setReceivingDetails(Arrays.asList(receivingDetail1));
            sampleReceiving2.setReceivingDetails(Arrays.asList(receivingDetail2));
        }

        //make sure to only populate data once
        dataPopulated = true;
    }

    @Test
    public void testAddNote(){
        //Create sample order for adding note
        //create sample order details
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(15, 1000, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        //create order and receiving date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        Date sampleReceivingDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
            sampleReceivingDate = format.parse("2021-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //sample staff for order
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);
        //sample staff for receiving
        Staff newReceive = new Staff();
        this.staffService.addStaff(newReceive);

        //create sample order
        Order sampleOrder = new Order(sampleDate, newStaff, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);
        //add sampleOrder to the list
        this.orderService.addOrder(sampleOrder);

        //create sample receiving note for adding
        ReceivingNote sampleReceiving = new ReceivingNote(sampleReceivingDate, newReceive, sampleOrder);
        //get id when added
        int actualId = this.receivingNoteService.addReceivingNote(sampleReceiving);

        //test if id is as expected
        assertEquals(sampleReceiving.getReceiving_note_id(), actualId);

        //delete sample data from database
        this.orderService.deleteOrder(sampleOrder);
        this.staffService.deleteStaff(newStaff);
        this.staffService.deleteStaff(newReceive);
    }

    @Test
    public void testDeleteNote(){
        //Create sample order for adding note
        //create sample order details
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(15, 1000, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        //create order and receiving date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        Date sampleReceivingDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
            sampleReceivingDate = format.parse("2021-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //sample staff for order
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);
        //sample staff for receiving
        Staff newReceive = new Staff();
        this.staffService.addStaff(newReceive);

        //create sample order
        Order sampleOrder = new Order(sampleDate, newStaff, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);
        //add sampleOrder to the list
        this.orderService.addOrder(sampleOrder);

        //create sample receiving note for adding
        ReceivingNote sampleReceiving = new ReceivingNote(sampleReceivingDate, newReceive, sampleOrder);
        //populate receiving table for delete
        this.receivingNoteService.addReceivingNote(sampleReceiving);

        //get expected delete message
        String expectedMessage = "Receiving Note with id: " + sampleReceiving.getReceiving_note_id() + " is successfully deleted";
        String message = this.receivingNoteService.deleteReceivingNote(sampleReceiving);

        assertEquals(expectedMessage, message);

        //delete sample data from database
        this.orderService.deleteOrder(sampleOrder);
        this.staffService.deleteStaff(newStaff);
        this.staffService.deleteStaff(newReceive);
    }

    @Test
    public void testUpdateNote(){
        //Create sample orders for updating note
        //create sample orders details
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(15, 1000, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        List<OrderDetail> orderDetailsList2 = new ArrayList<>();
        OrderDetail orderDetail2 = new OrderDetail(15, 1000, new Product("Updated product", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList2.add(orderDetail2);

        //create order and receiving date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate1 = null;
        Date sampleReceivingDate1 = null;
        Date sampleReceivingDate2 = null;
        try {
            orderDate1 = format.parse("2020-12-12");
            sampleReceivingDate1 = format.parse("2021-01-01");
            sampleReceivingDate2 = format.parse("2021-02-02");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //sample staff for order
        Staff newStaff1 = new Staff();
        this.staffService.addStaff(newStaff1);
        //sample staff for receiving
        Staff newReceive = new Staff();
        this.staffService.addStaff(newReceive);
        Staff otherReceiveStaff = new Staff();
        this.staffService.addStaff(otherReceiveStaff);

        //create sample order
        Order sampleOrder1 = new Order(orderDate1, newStaff1, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);
        //create other sample order for testing
        Order sampleOrder2 = new Order(orderDate1, newStaff1, new Provider("Updated provider", "12 Nguyen Van Linh", "01010011001", "010100110","updated@gmail.com", "Quing"), orderDetailsList2);

        //add 2 orders to the list
        this.orderService.addOrder(sampleOrder1);
        this.orderService.addOrder(sampleOrder2);

        //create sample receiving note
        ReceivingNote sampleReceiving = new ReceivingNote(sampleReceivingDate1, newReceive, sampleOrder1);

        //add receiving note & details
        this.receivingNoteService.addReceivingDetails(receivingNoteService.addReceivingNote(sampleReceiving));

        //update the receiving note with sampleReceivingDate2, otherReceiveStaff and sampleOrder2
        sampleReceiving.setDate(sampleReceivingDate2);
        sampleReceiving.setStaff(otherReceiveStaff);
        sampleReceiving.setOrder(sampleOrder2);

        //get return value when update
        ReceivingNote returnValue = this.receivingNoteService
                .updateReceivingDetails(receivingNoteService.updateReceivingNote(sampleReceiving));

        //expected to return receiving note with details copied from order
        ReceivingDetail expectedDetail = new ReceivingDetail(new Product("Updated product", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ), 15);
        sampleReceiving.setReceivingDetails(Arrays.asList(expectedDetail));

        System.out.println(returnValue.toString());
        assertEquals(sampleReceiving.toString(), returnValue.toString());

        //delete all sample data
        this.receivingNoteService.deleteReceivingNote(sampleReceiving);

        this.orderService.deleteOrder(sampleOrder1);
        this.orderService.deleteOrder(sampleOrder2);

        this.staffService.deleteStaff(newStaff1);
        this.staffService.deleteStaff(newReceive);
        this.staffService.deleteStaff(otherReceiveStaff);

    }

    @Test
    public void testGetNote(){
        //test get one
        assertEquals(sampleReceiving1.toString(), this.receivingNoteService.getOneReceivingNote(1).toString());

        //test get all
        assertEquals(Arrays.asList(sampleReceiving1, sampleReceiving2).toString(),
                this.receivingNoteService.getAllReceivingNote().toString());
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


        //expected result for filter by a date
        assertEquals(Arrays.asList(sampleReceiving2).toString(),
                this.receivingNoteService.getNoteByDate(sampleDateStart, sampleDateEnd).toString());
        //expected result for filter by start and end date
        assertEquals(Arrays.asList(sampleReceiving1, sampleReceiving2).toString(),
                this.receivingNoteService.getNoteByDate(sampleStartDate, sampleEndDate).toString());

    }
}


