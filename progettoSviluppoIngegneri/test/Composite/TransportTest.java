/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Andrea
 */
public class TransportTest {
    
    public TransportTest() {
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
     * Test of getDurata method, of class Transport.
     */
    
    public void testGetDurata() {
        System.out.println("getDurata");
        Transport instance = null;
        int expResult = 0;
        int result = instance.getDurata();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toList method, of class Transport.
     */
   
    public void testToList() {
        System.out.println("toList");
        ArrayList list = null;
        Transport instance = null;
        instance.toList(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocality method, of class Transport.
     */
    
    public void testGetLocality() {
        System.out.println("getLocality");
        Transport instance = null;
        String expResult = "";
        String result = instance.getLocality();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
