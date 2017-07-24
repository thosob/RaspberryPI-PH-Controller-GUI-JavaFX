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
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @brief gets the probes temperature path 
     * @return temperaturePath
     */
    public String getTemperaturePath(){
        Probes probes = new Probes();
        
        for(IProbe probe : probes.getProbes()){
            if(probe.getClass() == TemperatureProbe.class){
                TemperatureProbe tempProbe = (TemperatureProbe)probe;
                if(tempProbe.getTemperatureID() == this.TemperatureID){
                    return tempProbe.getPath();
                }
            }
        }
        return null;
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

    @Override
    public boolean saveCalibration(String value, String output) {   
        
        try {
            PreparedStatement statement = null;
            Connection connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());
            statement = connection.prepareStatement("SELECT * FROM conductivityProbe WHERE probeAddress='"+this.Address+"'");                                    
            ResultSet Result = statement.executeQuery();
            
            if(Result.next()){                                    
                statement = connection.prepareStatement("UPDATE conductivityProbe SET "+ value +" = '"+output+"'" + " WHERE probeAddress='"+this.Address+"'");
                statement.executeUpdate();
            }
            else{            
                statement = connection.prepareStatement("INSERT INTO conductivityProbe "
                        + "(probeAddress, "+value+", temperatureID)"
                        + "VALUES ('"+this.Address+"',"
                        + "'"+output+"','"
                        + this.TemperatureID+"')");            
                statement.execute();
            }                       
            Result.close();
            connection.close();                        
        } catch (SQLException ex) {
            Logger.getLogger(ECProbe.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
        return true;
    }
}
