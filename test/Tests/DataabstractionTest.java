/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import aqualight.databastraction.ECProbe;
import aqualight.databastraction.IProbe;
import aqualight.databastraction.PhProbe;
import aqualight.databastraction.Probes;
import aqualight.databastraction.TemperatureProbe;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tsobieroy
 */
public class DataabstractionTest {
    
    public DataabstractionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * @brief checks if the settings can be read from the sqlite-db, 
     * assumes that settings were add in the database
     */
    @Test
    public void checkProbeSettings() 
    {
        Probes probes = new Probes();
        IProbe[] array = probes.getProbes();        
        assert(array.length > 0);
        
        boolean ecProbe = false;
        boolean phProbe = false;
        boolean tempProbe = false;
        
        for (IProbe array1 : array) {
            if (array1.getClass().equals(TemperatureProbe.class)) {
                tempProbe = true;
            }
            if (array1.getClass().equals(ECProbe.class)) {
                ecProbe = true;
            }
            if (array1.getClass().equals(PhProbe.class)) {
                phProbe = true;
            }            
        }
        assertTrue(tempProbe);
        assertTrue(phProbe);
        //This will fail anyway
        assertTrue(ecProbe);
    
    
    }
}
