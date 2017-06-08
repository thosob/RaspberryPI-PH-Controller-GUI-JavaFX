/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.util.LinkedList;

/**
 * @brief contains all settings
 * @author Thomas Sobieroy
 */
public class Settings {
    
    Probes Probes;
    
    
    public Settings(){
        Probes = new Probes();                     
    }
    
    /**
     * @brief initializes all probe data
     */
    public void initializeProbeData(){
        LinkedList<ProbeData> list = (LinkedList<ProbeData>) ProbeDataReader.ReadProbeData();
        for(ProbeData data : list){
            //copies data to the probes
            if(Probes.getProbe(data.getAddress()) != null){
                Probes.getProbe(data.getAddress()).setValue(new ProbeData(data.getAddress(), data.getTimeStamp(), data.getValue()));
            }
        }
        list.clear();
    }
    /**
     * @brief update the probe data
     * @return true if successfully updated and false if not
     */
    public boolean updateProbeData(){
        // TO DO
    }
    
}
