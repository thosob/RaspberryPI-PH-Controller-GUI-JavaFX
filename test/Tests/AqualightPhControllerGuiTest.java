package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import aqualight.databastraction.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
