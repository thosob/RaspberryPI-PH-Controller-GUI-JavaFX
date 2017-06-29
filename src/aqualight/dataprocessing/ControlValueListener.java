/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.dataprocessing;

import aqualight.visualisation.AqualightPhControllerGui;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.control.Label;

/**
 * @brief Listener to the Control Value Dispatcher
 * @author tsobieroy
 */
public class ControlValueListener implements Observer {
  
    
    /**
     * @brief Here you find the control value listener
     */
    public ControlValueListener(){        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //First string is address, second string is value
        HashMap<String,String> resultMap = (HashMap<String,String>) arg;
                
        resultMap.keySet().forEach((address) -> {     
            //Get label by hardware address
            AqualightPhControllerGui.GUI.SetLabelValue(address, resultMap.get(address));                        
        });        
    }
}
