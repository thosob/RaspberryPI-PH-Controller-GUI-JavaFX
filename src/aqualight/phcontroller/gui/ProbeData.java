/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;

import java.sql.Date;

/**
 * @brief Probe data model 
 * @author Thomas Sobieroy
 */
public class ProbeData {
    //Singleton pattern
    private static ProbeData Data;
    //Timestamp in UTC
    private Date TimeStamp;
    //Ph Value 
    private double ph;
    //Temperature probe Value
    private double temperature;
    

    /**
     * Get the value of temperature
     *
     * @return the value of temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Set the value of temperature
     *
     * @param temperature new value of temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    
    
    /**
     * Get the value of ph
     *
     * @return the value of ph
     */
    public double getPh() {
        return ph;
    }

    /**
     * Set the value of ph
     *
     * @param ph new value of ph
     */
    public void setPh(double ph) {
        this.ph = ph;
    }

                

    /**
     * Get the value of timeStamp
     *
     * @return the value of timeStamp
     */
    public Date getTimeStamp() {
        return TimeStamp;
    }

    /**
     * Set the value of timeStamp
     *
     * @param timeStamp new value of timeStamp
     */
    public void setTimeStamp(Date timeStamp) {
        this.TimeStamp = timeStamp;
    }

    
    /**
     * @brief gets the probe data from queue table
     */
    public void GetProbeData(){
        if(Data == null){
            
        }
        
    }
    
    
}
