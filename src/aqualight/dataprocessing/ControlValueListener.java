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

    private HashMap<String, Label> map;
    private HashMap<String, Label> tempMap;
    
    /**
     * @brief Here you find the control value listener
     * @param labels labels from gui
     * @param tempLabels temp labels from gui
     * @param map map with all labels, to define which label we need
     * @param tempMap tempMap with all labels, to define which, we will need
     */
    public ControlValueListener(HashMap<String, Label> map, HashMap<String, Label> tempMap){
        this.map = map;
        this.tempMap = tempMap;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        HashMap<String,String> resultMap = (HashMap<String,String>) arg;
        
        resultMap.keySet().forEach((s) -> {
            getMap().get(s).setText(resultMap.get(s));
        });
        AqualightPhControllerGui.getGuiStage().show();
    }

    /**
     * @return the map
     */
    public HashMap<String, Label> getMap() {
        return map;
    }

    /**
     * @return the tempMap
     */
    public HashMap<String, Label> getTempMap() {
        return tempMap;
    }
}
