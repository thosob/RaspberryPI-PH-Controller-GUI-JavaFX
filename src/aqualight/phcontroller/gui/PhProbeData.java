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
public class PhProbeData implements IProbeData{
   

    //Ph Value 
    private double ph;
    //Temperature probe Value
    private double temperature;
    private Date TimeStamp;
    /**
     * @brief constructor for ph probe data
     * @param TimeStamp Time
     * @param temperature Temperature
     * @param ph PH
     */
    public PhProbeData(Date TimeStamp, double temperature, double ph){
        this.TimeStamp = TimeStamp;
        this.temperature = temperature;
        this.ph = ph;
    }
    /**
     * @brief never let ph probe data exist without values
     */
    private PhProbeData(){}
    
    

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
    @Override
    public double getProbeValue() {
        return ph;
    }

    /**
     * Set the value of ph
     *
     * @param ph new value of ph
     */
    @Override
    public void setProbeValue(double ph) {
        this.ph = ph;
    }

                

    /**
     * Get the value of timeStamp
     *
     * @return the value of timeStamp
     */
    @Override
    public Date getTimeStamp() {      
        return TimeStamp;
    }

    /**
     * Set the value of timeStamp
     *
     * @param timeStamp new value of timeStamp
     */
    @Override
    public void setTimeStamp(Date timeStamp) {
        this.TimeStamp = timeStamp;
    }        
    /**
     * @brief returns the type of the probe
     * @return 
     */
    @Override
    public String getProbeType() {
        return "ph";
    }
    
}
