package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aqualight.databastraction.*;
import aqualight.visualisation.AqualightPhControllerGui;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javafx.scene.control.Label;

/**
 * @brief small test class to make sure that all works fine
 * @author Thomas Sovbieroy
 */
public class AqualightPhControllerGuiTest {
        
    
    public AqualightPhControllerGuiTest() {
    }
    /**
     * @brief set up class
     */
    @BeforeClass
    public static void setUpClass() {   
        //Set class fully up
        AqualightPhControllerGui gui = new AqualightPhControllerGui(); 
        HashMap<Integer,String> labelAddress = gui.getLabelAddress();
        HashMap<String, String> nameStrings = gui.getLabelNames();   
        //Mock-Data if no data is there
        if(labelAddress.size() == 0){
            labelAddress.put(1, "77");
            labelAddress.put(2, "78");
            labelAddress.put(3, "79");
            labelAddress.put(4, "80");
            labelAddress.put(5, "81");
            labelAddress.put(6, "82");
            //Temperature
            labelAddress.put(7, "83");
            labelAddress.put(8, "84");            
            AqualightPhControllerGui.WriteMapToDisk("LabelAddress", labelAddress);
        }
        if(nameStrings.size() == 0){
            nameStrings.put("77", "P1");
            nameStrings.put("78", "P2");
            nameStrings.put("79", "P3");
            nameStrings.put("80", "P4");
            nameStrings.put("81", "P5");
            nameStrings.put("82", "P6");
            //Temperature
            nameStrings.put("83", "Temp1");
            nameStrings.put("84", "Temp2");
            AqualightPhControllerGui.WriteMapToDisk("LabelNames", nameStrings);
        }
        
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
     *  @brief test to make sure, that global fields be o.k.
     */
     @Test
     public void checkGlobalFields() {
           
     }
     /**
      * @brief checks the probe data
      */
     @Test
     public void checkProbeData(){
         Probes probes = GlobalObjects.getProbes();
         
         //assert at least one probe is connected
         assert( probes.getProbes().length > 1);
         
        //assert probe data exists for each probe
        for(IProbe probe : probes.getProbes()){            
            assert(probe.hasValues());
        }
         
     }
}
