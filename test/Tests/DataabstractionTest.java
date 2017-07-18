/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import aqualight.databastraction.ECProbe;
import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.IProbe;
import aqualight.databastraction.PhProbe;
import aqualight.databastraction.Probes;
import aqualight.databastraction.TemperatureProbe;
import aqualight.visualisation.CalibrationController;
import java.sql.SQLException;
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
        //Make sure, that we deal with a file
        assert(GlobalObjects.getDatabaseFile().isFile());
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
        assertTrue(ecProbe);        
    }
    /**
     * @brief tests saving probe settings 
     */
    @Test
    public void testSaveProbeSettings(){                
        Probes probes = new Probes();
        IProbe[] array = probes.getProbes();
        TemperatureProbe temp = null;
        ECProbe ec = null;
        PhProbe ph = null;
        
        
        //find at least one probe of each type to test settings out
        for (IProbe item : array) {
            if (item.getClass().equals(TemperatureProbe.class)) {
                temp = (TemperatureProbe)item;
                
            }
            if (item.getClass().equals(ECProbe.class)) {
                ec = (ECProbe)item;
            }
            if (item.getClass().equals(PhProbe.class)) {
                ph = (PhProbe)item;                                
            }            
        }
        assert(temp != null);
        assert(ec != null);
        assert(ph != null);
        String ecName = ec.getName();
        String phName = ph.getName();
        String tempName = temp.getName();
        String ecAddress = ec.getAddress();
        String phAddress = ph.getAddress();
        String tempAddress = temp.getAddress();
                       
        ec.setName("TestUnit");
        ph.setName("TestUnit");
        temp.setName("TestUnit");
        
        assertTrue(ec.writeChanges());
        assertTrue(ph.writeChanges());
        assertTrue(temp.writeChanges());
        
        
        //Works: Checked every time data is loaded new
        probes = new Probes();        
        //Checks if everything worked fine
        assertTrue(probes.getProbe(ecAddress).getName().equals("TestUnit"));
        assertTrue(probes.getProbe(phAddress).getName().equals("TestUnit"));
        assertTrue(probes.getProbe(tempAddress).getName().equals("TestUnit"));
        //Set back data
        ec.setName(ecName);
        ph.setName(phName);
        temp.setName(tempName);        
        assertTrue(ec.writeChanges());
        
        
        
    }
    
    @Test
    public void testSaveCalibration() throws SQLException{
        Probes probes = new Probes();
        IProbe[] array = probes.getProbes();        
        assert(array.length > 0);
        
        ECProbe ecProbe = null;
        PhProbe phProbe = null;
        TemperatureProbe tempProbe = null;
        
        for (IProbe array1 : array) {
            if (array1.getClass().equals(TemperatureProbe.class)) {
                tempProbe = (TemperatureProbe)array1;
            }
            if (array1.getClass().equals(ECProbe.class)) {
                ecProbe = (ECProbe)array1;
            }
            if (array1.getClass().equals(PhProbe.class)) {
                phProbe = (PhProbe)array1;
            }            
        }
        assertTrue(phProbe.saveCalibration("ph4", "1798"));
        assertTrue(ecProbe.saveCalibration("lowCal","77"));                                
    }        
}
