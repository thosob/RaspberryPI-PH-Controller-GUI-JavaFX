/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.dataprocessing;

import java.sql.Date;


/**
 * @brief general interface for describing a probe
 * @author Thomas Sobieroy
 */
public interface IProbeData {
    
   
    
    /**
     * @brief Get the value of timeStamp    
     * @return the value of timeStamp
     */
    public Date getTimeStamp();

    /**
     * @brief Set the value of timeStamp     
     * @param timeStamp new value of timeStamp
     */
    public void setTimeStamp(Date timeStamp);  
    /**
     * @brief Get the value of the probe
     *
     * @return the value of probe
     */
    public double getProbeValue();

    /**
     * @brief Set the value of probe
     *
     * @param value new value of probe
     */
    public void setProbeValue(double value);
    
    /**
     * @brief gets type of the probe
     * @return type of the probe e.g. ph or conductivity
     */
    public String getProbeType();
    
    /**
     * @brief Force overriding of the equals method
     * @param obj that is to be compared to the class
     * @return true if object is equal or else false meaning not equal
     */
    @Override
    public boolean equals(Object obj);
}
