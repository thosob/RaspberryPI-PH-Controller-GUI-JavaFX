/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @brief Reads runnable data
 * @author Thomas Sobieroy
 */
public class ReadProbeData implements Runnable {

    private boolean firstrun = true;
    
    //Assembles all data in model 
    @Override
    public void run() {
        Connection connection = null;
        String address;
             
        
        try {
            // db parameters
            String url = "jdbc:sqlite:resources/symbiofilter.db";
            ResultSet Result;
            //Get all registered probes
            Probes probes = GlobalObjects.getProbes();
            IProbe probe;
            
            // create a connection to the database
            connection = DriverManager.getConnection(url);                        
                        
            if(firstrun){
            
                //Should be limited to a couple of weeks or months data, so the raspberry pi is not killed
                //address, time, temperature and ph or conductivity are firmly set
                //ph or conductivity can be null, but not both should be or else we have a problem.
                PreparedStatement statement = connection.prepareStatement("SELECT address, time,temperature, ph, conductivity, mV FROM queue ORDER BY time ASC");            
                Result = statement.executeQuery();                            
                firstrun = true;
            }
            else{
                //It is vital to keep the order, the list will not have correct last value data item
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM (SELECT address, time,temperature, ph, conductivity, mV FROM queue ORDER BY time DESC LIMIT 50) ORDER BY time ASC");            
                Result = statement.executeQuery();                                                            
            }                        
            
            //Go through all data and add it to the list
            while (Result.next()) {
                address = Result.getString(1);
                probe = probes.getProbe(address);                                
                
                //Assign values, based on class of the registered probe
                //For ph consider class PhProbe
                if(probe.getClass().equals(PhProbe.class)){
                    long date = Result.getLong(2);
                    Date realDate = new Date(date);
                    ProbeData data = new ProbeData(address, realDate, Result.getDouble(4));
                    probe.setValue(data);
                }
                //For ec consider class ECProbe
                if(probe.getClass().equals(ECProbe.class)){
                    long date = Result.getLong(2);
                    Date realDate = new Date(date);
                    ProbeData data = new ProbeData(address, realDate, Result.getDouble(5));
                    probe.setValue(data);
                }                                                                                
            }            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    //And close connection, to free up space
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * @brief determines by a result set the type of probe
     * @param Result ResultSet with data
     * @return e.g. "ph", "conductivity" or sth. else
     */
    public static String getProbeType(ResultSet Result) {
        //Distinction is missing here
        return "ph";
    }
}
