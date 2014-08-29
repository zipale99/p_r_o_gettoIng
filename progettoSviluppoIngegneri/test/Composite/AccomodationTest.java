/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
/**
 *
 * @author Andrea
 */
public class AccomodationTest {
    
    public AccomodationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of getDurata method, of class Accomodation.
     */
    @Test
    public void testGetDurata() {
        System.out.println("getDurata");
        Accomodation instance = new Accomodation("Campeggio","Campeggio All Inclusive","**","Torino","Torino",5);
        int expResult = 5;
        int result = instance.getDurata();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toList method, of class Accomodation.
     */
   
    public void testToList() {
        System.out.println("toList");
        ArrayList list = null;
        Accomodation instance = new Accomodation("Campeggio","Campeggio All Inclusive","**","Torino","Torino",5);
        instance.toList(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocality method, of class Accomodation.
     */
    @Test
    public void testGetLocality() {
        System.out.println("getLocality");
        Accomodation instance = new Accomodation("Campeggio","Campeggio All Inclusive","**","Torino","Torino",5);
        String expResult = "'Torino', ";
        String result = instance.getLocality();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
