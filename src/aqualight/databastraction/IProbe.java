/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.util.LinkedList;
import java.util.List;

/**
 * @brief description of a probe
 * @author Thomas Sobieroy
 */
public interface IProbe {
    
    /**
     * @brief Data 
     */
    final List<IProbeData> Data = new LinkedList<>();
    
    /**
     * @brief gets the address of a probe
     * @return 
     */
    public String getAddress();
    /**
     * @brief set the address of 
     * @param address i2c address or path to temperature file
     */
    public void setAddress(String address);
    
    /**
     * @brief gets the name of the label for the probe
     * @return probe name
     */
    public String getName();
    
    /**
     * @brief sets the name of the probes label
     * @param name of the probe
     */
    public void setName(String name);
    /**
     * @brief gets the list with all probe data
     * @return List with all probe data
     **/
    public List<IProbeData> getValues();
    /**
     * @param data of the probe
     * @brief adds Data to the probe
     */
    public void setValue(IProbeData data);
    /**
     * @brief gets the Data record with the last timestamp
     * @return IProbeData 
     */
    public IProbeData getLastValue();
    
}
