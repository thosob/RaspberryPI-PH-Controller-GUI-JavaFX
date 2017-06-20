/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.Probes;
import aqualight.dataprocessing.ControlValueDispatcher;
import aqualight.dataprocessing.ControlValueListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import javafx.scene.control.Label;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @brief this test is intended to make sure, that displaying data on the screen
 * works as expected
 * @author tsobieroy
 */
public class DatavisualisationTest {
    
    private Process process;
    private HashMap<String, Label> map = new HashMap<String, Label>();
    private HashMap<String, Label> tempMap = new HashMap<String, Label>();
    private Probes probes;
    
     @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {    
        try {
            //make sure our mockfile for processing works
            process = new ProcessBuilder("/aqualight-phcontroller-gui-mockup").start();               
            map.put("1", label);
            map.put("2", label);
            tempMap.put("3", label);
            tempMap.put("4", label);
            probes = GlobalObjects.getProbes();
        } catch (IOException ex) {
            assert(ex == null);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * @brief checks if the settings can be read from the sqlite-db, 
     * assumes that settings were add in the database
     */
    @Test
    public void checkObserver() throws InterruptedException {
        
        ControlValueDispatcher dis = new ControlValueDispatcher();
        //Start another thread, so the getting control values does not block the gui
        Thread t = new Thread(dis);                
        t.start();        
        //Use control value listener to listen to changes in the probe data
        ControlValueListener listener = new ControlValueListener(map, tempMap);        
        dis.addObserver(listener);
        for(int i = 0; i < 5; i++){
            if(dis.hasChanged()){
                assert(dis.countObservers() == 1);
                assert(listener.getMap().size() > 0);
                assert(listener.getTempMap().size() > 0);
                assert(listener.getMap().get("1").getText().equals("12.75"));
                assert(listener.getMap().get("2").getText().equals("12.75"));
                assert(listener.getTempMap().get("3").getText().equals("12.75"));
                assert(listener.getTempMap().get("4").getText().equals("12.75"));
                break;
            }
            sleep(2000);
        }
       
    }
    
    
    
}
