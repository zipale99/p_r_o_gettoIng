/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Composite;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Andrea
 */
public class StayTemplateCompositeTest {
    
    public StayTemplateCompositeTest() {
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
     * Test of getDurata method, of class StayTemplateComposite.
     */
    @Test
    public void testGetDurata() {
        System.out.println("getDurata");
        StayTemplateComposite instance = new StayTemplateComposite();
        instance.add(new Accomodation("","","","","",2));
        instance.add(new Transport("","","",0));
        instance.add(new Accomodation("","","","","",2));
        instance.add(new Accomodation("","","","","",4));
        instance.add(new Accomodation("","","","","",5));
        instance.add(new Transport("","","",1));
        StayTemplateComposite instance2 = new StayTemplateComposite();
        instance2.add(new Accomodation("","","","","",2));
        instance2.add(new Transport("","","",1));
        instance2.add(new Accomodation("","","","","",2));
        instance.add(instance2);
        int expResult = 19;
        int result = instance.getDurata();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of consistenza method, of class StayTemplateComposite.
     */
    @Test
    public void testConsistenza() {
        System.out.println("consistenza");
        StayTemplateComposite instance = new StayTemplateComposite();
        StayTemplateComposite instanceA = new StayTemplateComposite("1","Torino","Milano");
        StayTemplateComposite instanceB = new StayTemplateComposite("2","Milano","Genova");
        StayTemplateComposite instanceC = new StayTemplateComposite("3","Genova","Firenze");
        StayTemplateComposite instanceD = new StayTemplateComposite("4","Firenze","Roma");
        instance.add(instanceA);
        instance.add(instanceB);
        instance.add(instanceC);
        instance.add(instanceD);
        boolean expResult = true;
        boolean result = instance.consistenza();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }


    /**
     * Test of toListSt method, of class StayTemplateComposite.
     */
    
    public void testToListSt() {
        System.out.println("toListSt");
        StayTemplateComposite instance = new StayTemplateComposite();
        List expResult = null;
        List result = instance.toListSt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocality method, of class StayTemplateComposite.
     */
    @Test
    public void testGetLocality() {
        System.out.println("getLocality");
        StayTemplateComposite instance = new StayTemplateComposite();
        
        StayTemplateComposite instanceA = new StayTemplateComposite("1","Torino","Milano");
        instanceA.add(new Accomodation("","","","Torino","Torino",2));
        instanceA.add(new Transport("","Torino","Milano",1));
        instanceA.add(new Accomodation("","","","Milano","Milano",2));
        
        StayTemplateComposite instanceB = new StayTemplateComposite("2","Milano","Genova");
        instanceB.add(new Transport("","Milano","Napoli",1));
        instanceB.add(new Transport("","Roma","Pisa",1));
        
        instance.add(instanceA);
        instance.add(instanceB);
        String expResult = "'Torino', 'Torino', 'Milano', 'Milano', 'Milano', 'Napoli', 'Roma', 'Pisa', ";
        String result = instance.getLocality();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
