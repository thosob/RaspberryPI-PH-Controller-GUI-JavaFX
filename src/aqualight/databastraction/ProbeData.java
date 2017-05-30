/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.sql.Date;


/**
 * @brief That's a data object
 * @author Thomas Sobieroy
 */
public class ProbeData implements IProbeData {
    
    private String Address;
    private Date Time;
    private double Value;
    
    
    /**
     * @param address Adds the address 
     * @param time adds the timestamp of the data
     * @param value adds the value of the data
     * @brief Make DataHandler Object - needed for each probe 
     */
    public ProbeData(String address, Date time, double value){
        Address = address;
        Time = time;
        Value = value;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * @return the Time
     */
    public Date getTime() {
        return Time;
    }

    /**
     * @param Time the Time to set
     */
    public void setTime(Date Time) {
        this.Time = Time;
    }

    /**
     * @return the Value
     */
    public double getValue() {
        return Value;
    }

    /**
     * @param Value the Value to set
     */
    public void setValue(double Value) {
        this.Value = Value;
    }

    @Override
    public Date getTimeStamp() {
        return getTime();
    }

    @Override
    public void setTimeStamp(Date timeStamp) {
        setTime(timeStamp);
    }

    @Override
    public double getProbeValue() {
        return getValue();
    }

    @Override
    public void setProbeValue(double value) {
        setValue(value);
    }        
}
