package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.CustomerService;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest({"spring.main.allow-bean-definition-overriding=true"})//enable override beans in old app config
@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    static boolean dataPopulated = false;

    //create sample date for testing
    static Customer customer1 = new Customer(1,"Quynh Anh", "157 Nguyen Van Linh", "010010101", "010010101", "quing@gmail.com", "quing");
    static Customer customer2 = new Customer(2,"Nha Uyen", "12 Nguyen Van Linh", "1001101010", "1001101010", "nu@gmail.com", "nu");
    static Customer customer3 = new Customer(3,"Quynh Giang", "434 Nguyen Van Linh", "05005005005", "05005005005","zhang@gmail.com", "zhang");
    static Customer customer4 = new Customer(4,"Huu tri", "2342 Nguyen Van Linh", "3454534253", "3454534253", "tree@gmail.com", "tree");


    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("BEFORE THE TEST FOR CUSTOMER SERVICE\n");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("AFTER THE TEST FOR CUSTOMER SERVICE\n");
    }

    @Before
    public void setUp(){
        System.out.println("BEFORE EACH TEST\n");

        if (!dataPopulated){
            //test the addStaff function
            customerService.addCustomer(customer1);
            customerService.addCustomer(customer2);
            customerService.addCustomer(customer3);
            customerService.addCustomer(customer4);

            dataPopulated=true;
        }

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("AFTER EACH TEST\n");
    }

    @Test
    public void testAddCustomer() {
        //create sample date for testing
        Customer newCustomer = new Customer("Quynh Anh", "157 Nguyen Van Linh", "010010101", "010010101", "quing@gmail.com", "contact");

        //get new staff id
        int newId = customerService.addCustomer(newCustomer);

        //test the addStaff function
        assertEquals(newCustomer.getCustomer_id(), newId);

        customerService.deleteCustomer(newCustomer);
    }

    @Test
    public void testDeleteCustomer(){
        //create sample data for testing
        Customer newCustomer = new Customer("Kudo Shinichi", "134 Nguyen Van Linh", "029090535", "029090535","kudo@gmail.com", "kudo");
        int newStaffId = customerService.addCustomer(newCustomer);

        //populate to the database
        String deleteMessage = customerService.deleteCustomer(newCustomer);
        String expectedMessage = "Customer with id: " + newCustomer.getCustomer_id() + " had been successfully deleted";

        //test the deleteStaff function
        assertEquals( expectedMessage, deleteMessage);

    }

    @Test
    public void testUpdateCustomer(){
        //create sample data for testing
        Customer newCustomer = new Customer("Ran Mori", "134 Nguyen Van Linh", "010101", "010101","mori@gmail.com", "ran");
        customerService.addCustomer(newCustomer);

        newCustomer.setName("Mori Kogoro");

        //test updating
        assertEquals(newCustomer.toString(), customerService.updateCustomer(newCustomer).toString());

        //delete from database
        customerService.deleteCustomer(newCustomer);
    }

    @Test
    public void testGetCustomer(){
        List<Customer> expectedList = Arrays.asList(customer1,customer2,customer3,customer4);

        //test get all staffs
        assertEquals(expectedList.toString(), customerService.getAllCustomer().toString());
        //test get a staff
        assertEquals(customer1.toString(), customerService.getCustomerById(1).toString());

        //test search customer by name
        assertEquals(Arrays.asList(customer2).toString(), customerService.getCustomerByName("Nha Uyen").toString());
        //test search customer by address
        assertEquals(Arrays.asList(customer3).toString(), customerService.getCustomerByAddress("434 Nguyen Van Linh").toString());
        //test search customer by phone
        assertEquals(Arrays.asList(customer4).toString(), customerService.getCustomerByPhone("3454534253").toString());

        //delete sample data from the database
        customerService.deleteCustomer(customer1);
        customerService.deleteCustomer(customer2);
        customerService.deleteCustomer(customer3);
        customerService.deleteCustomer(customer4);

    }
}
