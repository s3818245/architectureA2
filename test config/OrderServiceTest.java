package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.OrderService;
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
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private StaffService staffService;
    private static boolean dataPopulated = false;

    //initialize dependency for order
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
    static OrderDetail orderDetail3 = new OrderDetail(2, 2000, product1);

    //initialize list for 2 orders
    static List<OrderDetail> orderDetailsList1 = new ArrayList<>();
    static List<OrderDetail> orderDetailsList2 = new ArrayList<>();

    //create date for order
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    static Date sampleDate1 = null;
    static Date sampleDate2 = null;

    static Order order1 = null;
    static Order order2 = null;

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

        //create new order
        order1 = new Order(1, sampleDate1, staff1, provider1, orderDetailsList1);
        order2 = new Order(2, sampleDate2, staff2, provider2, orderDetailsList2);

        if (!dataPopulated){
            //populate sample order details list
            orderDetailsList1.add(orderDetail1);
            orderDetailsList1.add(orderDetail2);
            orderDetailsList2.add(orderDetail3);

            //populate table
            this.staffService.addStaff(staff1);
            this.staffService.addStaff(staff2);

            //populate database
            this.orderService.addOrder(order1);
            this.orderService.addOrder(order2);
        }

        //make sure to only populate data once
        dataPopulated = true;
    }

    @Test
    public void testAddOrder() {
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(12, 53, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);

        //create sample order
        Order sampleOrder = new Order(sampleDate, newStaff, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);

        //get return value after adding
        int newOrderId = this.orderService.addOrder(sampleOrder);

        System.out.println("--TEST FOR ADDING ORDER--");
        //expect return the new order id
        assertEquals(sampleOrder.getOrder_id(), newOrderId);

        //remove sample data from database
        this.orderService.deleteOrder(sampleOrder);
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testDeleteOrder(){
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(12, 53, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create sample order
        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);
        Order sampleOrder = new Order(sampleDate, newStaff, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);

        //get id of the new sample order
        int newOrderId = this.orderService.addOrder(sampleOrder);

        //expected delete message for delete sample order
        String expectedMessage = "Order with id " + newOrderId + " is deleted";

        System.out.println("--TEST FOR DELETE ORDER--");
        //expect return the new order id
        assertEquals(expectedMessage, this.orderService.deleteOrder(sampleOrder));

        //delete sample data
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testUpdateOrder(){
        List<OrderDetail> orderDetailsList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail(15, 1000, new Product("Macbook M1", "2020","Apple", "Apple",
                "The new model of Macbook", new Category("Computer"), 1300 ));
        orderDetailsList.add(orderDetail);

        //create sample date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date sampleDate = null;
        try {
            sampleDate = format.parse("2020-12-12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Staff newStaff = new Staff();
        this.staffService.addStaff(newStaff);

        //create sample order
        Order sampleOrder = new Order(sampleDate, newStaff, new Provider("Sample provider", "12 Nguyen Van Linh", "01010011001", "010100110","testprovider@gmail.com", "Quing"), orderDetailsList);
        //add sampleOrder to the list
        orderService.addOrder(sampleOrder);

        //create new date for update
        Date updatedDate = null;
        try {
            updatedDate = format.parse("2021-01-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //update the fields using setters
        //set new date
        sampleOrder.setDate(updatedDate);
        //Change product in details
        sampleOrder.getOrderDetails().get(0).setProduct(new Product(3,"udpated product", "2018", "RMIT", "RMIT", "updated prpduct",
                            new Category(3,"sample"), 200));
        //update provider
        sampleOrder.setProvider(new Provider(3,"updated provider", "9643 Nguyen Van Linh", "909090909", "958393500","sampleprovider@gmail.com", "Quing"));

        System.out.println("TEST UPDATE ORDER\n");
        //expected to return the updated Order
        assertEquals(sampleOrder.toString(), this.orderService.updateOrder(sampleOrder).toString());

        //delete sample data
        this.orderService.deleteOrder(sampleOrder);
        this.staffService.deleteStaff(newStaff);
    }

    @Test
    public void testGetOrder(){
        //create expected data
        //define expected order list
        List<Order> expectedOrderList = Arrays.asList(order2, order1);

        System.out.println("TEST GET ONE ORDER\n");
        assertEquals(order1.toString(), this.orderService.getAnOrder(1).toString());

        System.out.println("TEST GET ALL ORDERS\n");
        assertEquals(expectedOrderList.toString(), this.orderService.getAllOrder().toString());


    }

}
