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
     * @brief gets the values (note: Data is derived)
     * @return 
     */
    @Override
    public List<IProbeData> getValues() {
        return Data;
    }
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
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM phProbe WHERE address='"+this.Address+"'");            
            ResultSet Result = statement.executeQuery();                       
            if(Result.next()){
                statement = Connection.prepareStatement("UPDATE phProbe SET "
                        + "address = '"+this.Address+"',"
                        + "ph4 = '"+this.Ph4+"',"
                        + "ph7 = '"+this.Ph7+"',"
                        + "ph9 = '"+this.Ph9+"',"
                        + "name = '"+this.Name+"',"
                        + "temperatureID = '"+this.TemperatureID+"'"
                        + " WHERE address='"+this.Address+"'");            
                statement.executeUpdate();                     
                
            }
            else{
                statement = Connection.prepareStatement("INSERT INTO phProbe "
                        + "(address, ph4, ph7, ph9, temperatureID, name)"
                        + "VALUES ('"+this.Address+"',"
                        + "'"+this.Ph4+"',"
                        + "'"+this.Ph7+"',"
                        + "'"+this.Ph9+"',"
                        + "'"+this.TemperatureID+"',"
                        + "'"+this.Name+"')");            
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
    /**
     * @brief saves the calibration of the ph probe
     * @param value possble values: ph4, ph7, ph9
     * @param output the measured voltage
     * @return true if saving was successful
     */
    @Override
    public boolean saveCalibration(String value, String output) {
        value = value.toLowerCase();
        
        
        try {
            PreparedStatement statement = null;
            Connection connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());
            statement = connection.prepareStatement("SELECT * FROM phProbe WHERE address = '"+this.Address+"'");
            ResultSet Result = statement.executeQuery();            
            if(Result.next()){                
                statement = connection.prepareStatement("UPDATE phProbe SET " + value +" = '"+output+"'"  + " WHERE address='"+this.Address+"'");
                statement.executeUpdate();
            }                                    
            else{
                statement = connection.prepareStatement("INSERT INTO phProbe "
                        + "(address, "+value+", temperatureID)"
                        + "VALUES ('"+this.Address+"',"
                        + "'"+output+"',"
                        + "'"+this.TemperatureID+"')");   
                statement.execute();
            }                               
            Result.close();
            connection.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger(PhProbe.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        return true;        
    }
    
}
