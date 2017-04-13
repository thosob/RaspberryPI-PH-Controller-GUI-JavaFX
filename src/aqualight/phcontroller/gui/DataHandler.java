/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @brief Contains all Datahandlers
 * @author Thomas Sobieroy
 */
public class DataHandler {
    
    private final String ProbeType;
    /**
     * @brief ProbeAddress o.sth.
     */
    public String ID;
    /**
     * @brief Containing values
     */
    public LinkedList<IProbeData> Values;
    /**
     * @brief Map with all DataHandlers - important for access in foreach iteration
     */
    public static HashMap<String, DataHandler> DataHandlerList;
    
    /**
     * @param ProbeType Probetyp e.g. ph or conductivity
     * @brief Make DataHandler Object - needed for each probe
     * @param ID
     * @param Values 
     */
    public DataHandler(String ID, LinkedList<IProbeData> Values, String ProbeType){
        this.ID = ID;
        this.Values = Values;  
        this.ProbeType = ProbeType;
    }
    /**
     * @brief data handler should never be accessed by default constructor
     */
    private DataHandler(){
        this.ProbeType = null;
    }
    /**
     * @brief gives back probe type
     * @return string with probetype e.g. "ph" or "conductivity"
     */
    public String getProbeType(){
        return ProbeType;
    }        
    
    
}
