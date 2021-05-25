package com.quynhanh.architecturea2;

import com.quynhanh.architecturea2.model.*;
import com.quynhanh.architecturea2.service.StaffService;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest({"spring.main.allow-bean-definition-overriding=true"})//enable override beans in old app config
@Import(TestConfig.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class StaffServiceTest {

    @Autowired
    private StaffService staffService;
    static boolean dataPopulated = false;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("BEFORE THE TEST FOR STAFF SERVICE\n");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("AFTER THE TEST FOR STAFF SERVICE\n");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("BEFORE EACH TEST\n");

        if (!dataPopulated){
            //create sample date for testing
            Staff staff1 = new Staff(1, "Quynh Anh", "157 Nguyen Van Linh", "010010101", "quing@gmail.com");
            Staff staff2 = new Staff(2, "Nha Uyen", "12 Nguyen Van Linh", "1001101010", "nu@gmail.com");
            Staff staff3 = new Staff(3, "Quynh Giang", "434 Nguyen Van Linh", "05005005005", "zhang@gmail.com");
            Staff staff4 = new Staff(4, "Huu tri", "2342 Nguyen Van Linh", "3454534253", "tree@gmail.com");

            //test the addStaff function
            staffService.addStaff(staff1);
            staffService.addStaff(staff2);
            staffService.addStaff(staff3);
            staffService.addStaff(staff4);

            dataPopulated=true;
        }

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("AFTER EACH TEST\n");
    }

    @Test
    public void testAddStaff() {
        //create sample date for testing
        Staff newStaff = new Staff("Quynh Anh", "157 Nguyen Van Linh", "010010101", "quing@gmail.com");

        //get new staff id
        int newStaffId = staffService.addStaff(newStaff);

        //test the addStaff function
        assertEquals(newStaff.getStaff_id(), newStaffId);

        staffService.deleteStaff(newStaff);
    }

    @Test
    public void testDeleteStaff(){
        //create sample data for testing
        Staff newStaff = new Staff("Kudo Shinichi", "134 Nguyen Van Linh", "029090535", "kudo@gmail.com");
        int newStaffId = staffService.addStaff(newStaff);

        //populate to the database
        String deleteMessage = staffService.deleteStaff(newStaff);
        String expectedMessage = "Staff with id "+ newStaffId + " is deleted";

        //test the deleteStaff function
        assertEquals( expectedMessage, deleteMessage);

    }

    @Test
    public void testUpdateStaff(){
        //create sample data for testing
        Staff newStaff = new Staff("Ran Mori", "134 Nguyen Van Linh", "010101", "mori@gmail.com");
        staffService.addStaff(newStaff);

        newStaff.setName("Mori Kogoro");

        //test updating
        assertEquals(newStaff.toString(), staffService.updateStaff(newStaff).toString());

        //delete from database
        staffService.deleteStaff(newStaff);
    }

    @Test
    public void testGetStaff(){
        //create sample data
        Staff staff1 = new Staff(1, "Quynh Anh", "157 Nguyen Van Linh", "010010101", "quing@gmail.com");
        Staff staff2 = new Staff(2, "Nha Uyen", "12 Nguyen Van Linh", "1001101010", "nu@gmail.com");
        Staff staff3 = new Staff(3, "Quynh Giang", "434 Nguyen Van Linh", "05005005005", "zhang@gmail.com");
        Staff staff4 = new Staff(4, "Huu tri", "2342 Nguyen Van Linh", "3454534253", "tree@gmail.com");

        //expected list when getAll
        Staff[] staffList = new Staff[] { staff1, staff2, staff3, staff4};

        List<Staff> expectedList = Arrays.asList(staffList);

        //test get all staffs
        assertEquals(expectedList.toString(), staffService.getAllStaff().toString());
        //test get a staff
        assertEquals(staff1.toString(), staffService.getAStaff(1).toString());

        //delete sample data from the database
        staffService.deleteStaff(staff1);
        staffService.deleteStaff(staff2);
        staffService.deleteStaff(staff3);
        staffService.deleteStaff(staff4);

    }

}
