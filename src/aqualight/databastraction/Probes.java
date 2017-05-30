/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import static aqualight.databastraction.ReadProbeData.getProbeType;
import aqualight.dataprocessing.ECProbeData;
import aqualight.dataprocessing.PhProbeData;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @brief Contains all Probes
 * @author Thomas Sobieroy
 */
public class Probes {
    
    
    private IProbe[] Probes;
    
    /**
     * @brief gets all Probes from database and registers them
     */
    public Probes(){
        
        Connection Connection = null;        
        LinkedList<IProbe> list = new LinkedList<>();
        
        try {
            // db parameters
            String Url = "jdbc:sqlite:resources/symbiofilter.db";
            // create a connection to the database
            Connection = DriverManager.getConnection(Url);

            //Initialize conductivity probes
            PreparedStatement statement = Connection.prepareStatement("SELECT * FROM conductivityProbe");
            ResultSet Result = statement.executeQuery();                       
            
            //Go through all data and add it to the list
            while (Result.next()) {
                ECProbe probe = new ECProbe();
                probe.setAddress(Result.getString(1));                                
                probe.setLowCalibration(Result.getInt(2));
                probe.setHighCalibration(Result.getInt(3));
                probe.setName(Result.getString(4));
                list.add(probe);
            }
            
            //Initialize ph probes
            statement = Connection.prepareStatement("SELECT * FROM conductivityProbe");
            Result = statement.executeQuery();
            
            //Go through all data and add it to the list
            while (Result.next()) {
                PhProbe probe = new PhProbe();
                probe.setAddress(Result.getString(1));                                
                probe.setPh4(Result.getInt(2));
                probe.setPh7(Result.getInt(3));
                probe.setPh9(Result.getInt(4));
                probe.setTemperatureID(Result.getInt(5));
                probe.setName(Result.getString(6));  
                list.add(probe);
            }
            
            //Initialize ph probes
            statement = Connection.prepareStatement("SELECT * FROM phProbe");
            Result = statement.executeQuery();
            
            //Go through all data and add it to the list
            while (Result.next()) {
                PhProbe probe = new PhProbe();
                probe.setAddress(Result.getString(1));                                
                probe.setPh4(Result.getInt(2));
                probe.setPh7(Result.getInt(3));
                probe.setPh9(Result.getInt(4));
                probe.setTemperatureID(Result.getInt(5));
                probe.setName(Result.getString(6));  
                list.add(probe);
            }
            
            //Initialize ph probes
            statement = Connection.prepareStatement("SELECT * FROM temparatureSensor");
            Result = statement.executeQuery();
            
            //Go through all data and add it to the list
            while (Result.next()) {
                TemperatureProbe probe = new TemperatureProbe();
                probe.setTemperatureID(Result.getInt(1));
                probe.setPath(Result.getString(2));
                probe.setAddress(Result.getString(2));                                                
                probe.setName(Result.getString(3));   
                list.add(probe);
            }              
            Probes = list.toArray(new IProbe[list.size()]);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (Connection != null) {
                    //And close connection, to free up space
                    Connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /**
     * @brief gives back all probes registered
     * @return 
     */
    public IProbe[] getProbes(){
        return Probes;
    }
    
    
    
}
