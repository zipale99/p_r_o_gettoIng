/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andrea
 */
public class HandMadeStayTest {
    
    public HandMadeStayTest() {
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
     * Test of getDurata method, of class HandMadeStay.
     */
    @Test
    public void testGetDurata() {
        System.out.println("getDurata");
        HandMadeStay instance = new HandMadeStay("Torino","Milano",3);
        int expResult = 3;
        int result = instance.getDurata();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}
