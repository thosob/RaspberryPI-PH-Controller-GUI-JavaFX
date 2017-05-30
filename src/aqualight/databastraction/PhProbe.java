/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import aqualight.dataprocessing.IProbeData;
import java.util.List;

/**
 * @brief describes a ph-probe
 * @author Thomas Sobieroy
 */
public class PhProbe implements IProbe {

    private String Address;
    private String Name;
    private int Ph4;
    private int Ph7;
    private int Ph9;
    private int TemperatureID;
    
    
    
    @Override
    public String getAddress() {
        return Address;
    }

    @Override
    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        Name = name;
    }

    @Override
    public List<IProbeData> getValues() {
        return Data;
    }

    @Override
    public void setValue(IProbeData data) {
        if(!Data.contains(data)){
            Data.add(data);
        }
    }

    public int getPh4() {
        return Ph4;
    }

    public void setPh4(int Ph4) {
        this.Ph4 = Ph4;
    }

    public int getPh7() {
        return Ph7;
    }

    public void setPh7(int Ph7) {
        this.Ph7 = Ph7;
    }

    public int getPh9() {
        return Ph9;
    }

    public void setPh9(int Ph9) {
        this.Ph9 = Ph9;
    }

    public int getTemperatureID() {
        return TemperatureID;
    }

    public void setTemperatureID(int TemperatureID) {
        this.TemperatureID = TemperatureID;
    }
    
}
