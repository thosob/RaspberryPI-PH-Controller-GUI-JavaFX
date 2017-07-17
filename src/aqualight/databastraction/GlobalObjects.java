/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @brief has a list of global objects, that are used over multiple fxml pages
 * @author Thomas Sobieroy
 */
public class GlobalObjects {
    
    private static Probes Probes;
    private final static File Database = new File("resources/symbiofilter.db");       
    private static String ServerName;
    private static String DeviceName;
    private final static String PhProgramCalib = "/aqualight-phcontroller-calibrator";
    private final static String EcProgramCalib = "/aqualight-conductivity-calibrator";
    private final static String PhProgram = "/aqualight-phcontroller";
    private final static String EcProgram = "/aqualight-conductivity";
    
    /**
     * @brief initializes all global objects - useful for unit-tests
     */
    public static void initializeGlobalObjects() throws SQLException{
                        
        //DeviceName
        Connection Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());        
        //Should be limited to an hours data, so the raspberry pi is not killed
        PreparedStatement statement = Connection.prepareStatement("SELECT deviceName FROM device");        
        ResultSet res = statement.executeQuery();
        String sqlDeviceName = res.getString(1);
        if(sqlDeviceName != null){
            DeviceName = sqlDeviceName;
        }
        
        //ServerName
        statement = Connection.prepareStatement("SELECT sendAddress FROM device");        
        res = statement.executeQuery();
        String sqlServerName = res.getString(1);
        if(sqlServerName != null){
            ServerName = sqlServerName;
        }
        statement.close();
        Connection.close();
        
        //Initializes all probes
        getProbes();
        
    }
    
    
    /**
     * @brief gets probe objects
     * @return 
     */
    public static Probes getProbes(){        
        if(Probes == null){
            Probes = new Probes();
            //Assign probe data to the probes
            ReadProbeData readProbeData = new ReadProbeData();
            readProbeData.run();
        }
        
        return Probes;        
    }
    /**
     * @brief gets database path
     * @return 
     */
    public static String getDatabasePath(){        
        return "jdbc:sqlite:"+Database.getAbsolutePath();
    }
    
    /**
     * @brief gets database path
     * @return 
     */
    public static File getDatabaseFile(){        
        return Database;
    }

    /**
     * @return the ServerName
     */
    public static String getServerName() {
        return ServerName;
    }

    /**
     * @param aServerName the ServerName to set
     */
    public static void setServerName(String aServerName) {
        ServerName = aServerName;
        Connection Connection = null;        
        try {           
            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());

            //Should be limited to a hours data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("UPDATE device SET sendAddress = ?");
            statement.setString(1, ServerName);
            statement.executeUpdate();
            statement.close();
            Connection.close();
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
     * @return the DeviceName
     */
    public static String getDeviceName() {
        return DeviceName;
    }

    /**
     * @param aDeviceName the DeviceName to set
     */
    public static void setDeviceName(String aDeviceName) {
        DeviceName = aDeviceName;
        Connection Connection = null;        
        try {           
            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());

            //Should be limited to a hours data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("UPDATE device SET deviceName = ?");
            statement.setString(1, DeviceName);
            statement.executeUpdate();
            statement.close();
            Connection.close();
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
     * @return the PhProgramCalib
     */
    public static String getPhProgramCalib() {
        return PhProgramCalib;
    }

    /**
     * @return the EcProgramCalib
     */
    public static String getEcProgramCalib() {
        return EcProgramCalib;
    }

    /**
     * @return the PhProgram
     */
    public static String getPhProgram() {
        return PhProgram;
    }

    /**
     * @return the EcProgram
     */
    public static String getEcProgram() {
        return EcProgram;
    }
    
   
    
    
}
