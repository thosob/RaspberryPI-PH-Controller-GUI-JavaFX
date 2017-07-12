/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @brief Describes EC-Probe
 * @author THomas Sobieroy
 */
public class ECProbe implements IProbe{

    private String Address;
    private String Name;
    private int LowCalibration;
    private int HighCalibration;    
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

    /**
     * @return the LowCalibration
     */
    public int getLowCalibration() {
        return LowCalibration;
    }

    /**
     * @param LowCalibration the LowCalibration to set
     */
    public void setLowCalibration(int LowCalibration) {
        this.LowCalibration = LowCalibration;
    }

    /**
     * @return the HighCalibration
     */
    public int getHighCalibration() {
        return HighCalibration;
    }

    /**
     * @param HighCalibration the HighCalibration to set
     */
    public void setHighCalibration(int HighCalibration) {
        this.HighCalibration = HighCalibration;
    }
    
    public int getTemperatureID() {
        return TemperatureID;
    }

    public void setTemperatureID(int TemperatureID) {
        this.TemperatureID = TemperatureID;
    }

    @Override
    public IProbeData getLastValue() {
        return Data.get(Data.size() - 1);
    }
    
     @Override
    public boolean hasValues() {
        return !Data.isEmpty();
    }
    /**
     * @brief writes back changes on ec-probe
     * @return 
     */
    @Override
    public boolean writeChanges() {
          
        Connection Connection;                
        
        try {

            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());            
            System.out.println(GlobalObjects.getDatabaseFile().isFile());
            //Initialize conductivity probes
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM conductivityProbe WHERE probeAddress='"+this.Address+"'");            
            ResultSet Result = statement.executeQuery();                       
            if(Result.next()){
                statement = Connection.prepareStatement("UPDATE conductivityProbe SET "
                        + "probeAddress = '"+this.Address+"',"
                        + "lowCal = '"+this.LowCalibration+"',"
                        + "highCal = '"+this.HighCalibration+"',"
                        + "name = '"+this.Name+"',"
                        + "temperatureID = '"+this.TemperatureID+"'"
                        + " WHERE probeAddress='"+this.Address+"'");            
                statement.executeUpdate();
                
            }
            else{
                statement = Connection.prepareStatement("INSERT INTO conductivityProbe "
                        + "(probeAddress, lowCal, highCal, name, temperatureID)"
                        + "VALUES ('"+this.Address+"',"
                        + "'"+this.LowCalibration+"',"
                        + "'"+this.HighCalibration+"',"
                        + "'"+this.Name+"',"
                        + "'"+this.TemperatureID+"')");            
                statement.execute();
            }
            Result.close();
            Connection.close();
        }
        catch(SQLException sqlexc){
            System.err.println(sqlexc);
            return false;
        }
        return true;
    }    
}
