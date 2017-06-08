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
import java.util.LinkedList;
import java.util.List;

/**
 * @brief Reads runnable data
 * @author Thomas Sobieroy
 */
public class ProbeDataReader{
    
    
    //Assembles all data in model 
    public static List<ProbeData> ReadProbeData() {
        
        Connection Connection = null;
        String Address;
        LinkedList<ProbeData> list = new LinkedList<>();

        try {
            // db parameters
            String Url = "jdbc:sqlite:resources/symbiofilter.db";
            // create a connection to the database
            Connection = DriverManager.getConnection(Url);

            //Should be limited to a weeks data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("SELECT address, time, value FROM queue");
            ResultSet Result = statement.executeQuery();
            String Date = null;
            //Go through all data and add it to the list
            while (Result.next()) {                
                Address = Result.getString(1);                
                long date = Result.getLong(2);
                Date realDate = new Date(date);
                double value = Result.getDouble(3); 
                ProbeData data = new ProbeData(Address, realDate, value );
                list.add(data);
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
        return list;
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
