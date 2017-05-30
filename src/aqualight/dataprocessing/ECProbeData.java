/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.dataprocessing;

import aqualight.dataprocessing.IProbeData;
import java.sql.Date;

/**
 * @brief Probe data model 
 * @author Thomas Sobieroy
 */
public class ECProbeData implements IProbeData{
   

    //Ph Value 
    private double ec;
    //Temperature probe Value
    private double temperature;
    private Date TimeStamp;
    /**
     * @brief constructor for ph probe data
     * @param TimeStamp Time
     * @param temperature Temperature
     * @param ec Conductivity
     **/
    public ECProbeData(Date TimeStamp, double temperature, double ec){
        this.TimeStamp = TimeStamp;
        this.temperature = temperature;
        this.ec = ec;
    }
    /**
     * @brief never let ec probe data exist without values
     */
    private ECProbeData(){}
    
    

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
        return ec;
    }

    /**
     * Set the value of ec
     *
     * @param ph new value of ec
     */
    @Override
    public void setProbeValue(double ec) {
        this.ec = ec;
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
     * @return string to identify type of probe
     */
    @Override
    public String getProbeType() {
        return "ec";
    }
    
}
