/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


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
            
            
            while(Result.next()){
                Address = Result.getString(0);
                //If we cannot find the address as a value
                if(!DataHandler.DataHandlerList.containsKey(Address)){    
                                        
                    //we have to initialize a new list, always using Address as ID
                    DataHandler.DataHandlerList.put(Address, new DataHandler(Address,new LinkedList<>(),getProbeType(Result)));                                        
                }
                if(DataHandler.DataHandlerList.get(Address).Values.getFirst().getProbeType() == "ph")
                //Then we need to save data as PH
                DataHandler.DataHandlerList.get(Address).Values.add(new PhProbeData(Date.valueOf(Result.getString(1)),Double.valueOf(Result.getString(2)), Double.valueOf(Result.getString(3))));
                //or as Conductivity, which is not yet defined
                //DataHandler.DataHandlerList.get(Address).Values.add(new ConProbeData(Date.valueOf(Result.getString(1)),Double.valueOf(Result.getString(2)), Double.valueOf(Result.getString(3))));            
            }    
        } catch (SQLException e) {
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
