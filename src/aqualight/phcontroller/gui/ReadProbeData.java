/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


/**
 * @brief Reads runnable data 
 * @author Thomas Sobieroy
 */
public class ReadProbeData implements Runnable{

    @Override
    public void run() {
        Connection Connection = null;
        String Address = "";
        
        try {
            // db parameters
            String Url = "jdbc:sqlite:resources/symbiofilter.db";
            // create a connection to the database
            Connection = DriverManager.getConnection(Url);       
            
            //Should be limited to a weeks data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("SELECT address, time, ph, temperature, conductivity FROM queue");
            ResultSet Result = statement.executeQuery();
            String Date = null;
            //Go through all data and add it to the list
            while(Result.next()){
                 Address = Result.getString(1);
                
                //If we cannot find the address as a value, the probe was not registered, yet.
                if(!DataHandler.DataHandlerList.containsKey(Address)){                                            
                    //we have to initialize a new list, always using Address as ID
                    //to register the probe
                    DataHandler.DataHandlerList.put(Address, new DataHandler(Address,new LinkedList<>(),getProbeType(Result)));                                        
                }            
                
                //If data comes from ph probe
                if(DataHandler.DataHandlerList.get(Address).getProbeType().compareTo("ph") > -1){
                    
                    Date date = Result.getDate(2);
                    double ph = Result.getDouble(3);
                    double temperature = Result.getDouble(4);
                                                        
                    //then we write the Data to a list
                    DataHandler.DataHandlerList.get(Address).Values.add(new PhProbeData(date, temperature,ph));
                }
                else{
                    //Data comes from EC-Probe
                    Date date = Result.getDate(2);
                    double ec = Result.getDouble(3);
                    double temperature = Result.getDouble(4);
                    //This should be for ECProbe
                    DataHandler.DataHandlerList.get(Address).Values.add(new ECProbeData(date, temperature,ec));
                }
            }    
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
     * @brief determines by a result set the type of probe
     * @param Result ResultSet with data 
     * @return e.g. "ph", "conductivity" or sth. else
     */
    public static String getProbeType(ResultSet Result){
        //Distinction is missing here
        return "ph";
    }
}
