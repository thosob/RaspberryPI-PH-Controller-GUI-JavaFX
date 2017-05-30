package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import aqualight.phcontroller.gui.*;
import javafx.scene.control.Label;

/**
 * @brief small test class to make sure that all works fine
 * @author Thomas Sovbieroy
 */
public class AqualightPhControllerGuiTest {
    
    public AqualightPhControllerGuiTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //Set class fully up
        AqualightPhControllerGui.main(null);
    }
    
    @After
    public void tearDown() {
    }

    /**
     *  @brief test to make sure, that global fields be o.k.
     */
     @Test
     public void checkGlobalFields() {
         Label[] l1 = AqualightPhControllerGui.GetAllTemperatureLabels();
         Label[] l2 = AqualightPhControllerGui.GetAllProbeLabels();
         Label[] l3 = AqualightPhControllerGui.getTempLabels();
         Label[] l4 = AqualightPhControllerGui.getTempLabelsName();
         
        //Check if all labels are in the maps
         assert(l1.length == 2);
         assert(l2.length == 8);
         assert(l3.length == 2);
         assert(l4.length == 2);
         
         //Check if maps are load correctly
         Label l5 = AqualightPhControllerGui.GetLabel("77");
         Label l6 = AqualightPhControllerGui.GetTempLabel("77");
         assert(l5 != null);
         assert(l6 != null);
         
         
     }
}
