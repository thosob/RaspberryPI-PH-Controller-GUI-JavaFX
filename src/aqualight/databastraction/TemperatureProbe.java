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
import java.util.List;

/**
 * @brief Temperature Probe
 * @author Thomas Sobieroy
 */
public class TemperatureProbe implements IProbe {
    
    private String Address;
    private String Name; 
    private String Path;
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
     * @return the Path
     */
    public String getPath() {
        return Path;
    }

    /**
     * @param Path the Path to set
     */
    public void setPath(String Path) {
        this.Path = Path;
    }

    /**
     * @return the TemperatureID
     */
    public int getTemperatureID() {
        return TemperatureID;
    }

    /**
     * @param TemperatureID the TemperatureID to set
     */
    public void setTemperatureID(int TemperatureID) {
        this.TemperatureID = TemperatureID;
    }
    /**
     * @brief gets the last value that was added to list
     * @return 
     */
    @Override
    public IProbeData getLastValue() {
        return Data.get(Data.size() - 1);
    }
    
     @Override
    public boolean hasValues() {
        return !Data.isEmpty();
    }

    @Override
    public boolean writeChanges() {
                
        Connection Connection;                
        
        try {

            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());            
            System.out.println(GlobalObjects.getDatabaseFile().isFile());
            //Initialize conductivity probes
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM temperatureSensor WHERE temperatureID='"+this.Address+"'");            
            ResultSet Result = statement.executeQuery();                       
            if(Result.first()){
                statement = Connection.prepareStatement("UPDATE temperatureSensor SET "
                        + "temperatureID = '"+this.Address+"',"
                        + "path = '"+this.Path+"',"
                        + "name = '"+this.Name+"',"                      
                        + " WHERE address='"+this.Address+"'");            
                Result = statement.executeQuery();                       
                
            }
            else{
                statement = Connection.prepareStatement("INSERT INTO temperatureSensor "
                        + "(temperatureID, path, name)"
                        + "VALUES ('"+this.Address+"',"
                        + "'"+this.Path+"',"
                        + "'"+this.Name+"')");            
                Result = statement.executeQuery();                       
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
