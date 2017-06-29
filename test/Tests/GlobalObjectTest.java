/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import aqualight.databastraction.GlobalObjects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @brief test class for global object functions
 * @author Thomas Sobieroy
 */
public class GlobalObjectTest {
   @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException { 
        GlobalObjects.initializeGlobalObjects();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testDeviceName() throws SQLException { 
        String tmpDeviceName = GlobalObjects.getDeviceName();
        assert(tmpDeviceName != null);
        GlobalObjects.setDeviceName("TestDevice");
        // create a connection to the database
        Connection Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());        
        //Should be limited to a hours data, so the raspberry pi is not killed
        PreparedStatement statement = Connection.prepareStatement("SELECT deviceName FROM device");        
        ResultSet res = statement.executeQuery();       
        String sqlDeviceName = res.getString(1);
        Connection.close();
        assert(sqlDeviceName.trim().equals("TestDevice"));
        GlobalObjects.setDeviceName(tmpDeviceName);                
    }
    
    @Test
    public void testSendAddress() throws SQLException{
       String tmpSendAddress = GlobalObjects.getServerName();
        assert(tmpSendAddress != null);
        GlobalObjects.setServerName("localhost:1234");
        // create a connection to the database
        Connection Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());        
        //Should be limited to a hours data, so the raspberry pi is not killed
        PreparedStatement statement = Connection.prepareStatement("SELECT sendAddress FROM device");        
        ResultSet res = statement.executeQuery();
        String sqlSendAddress = res.getString(1);
        Connection.close();
        assert(sqlSendAddress.trim().equals("localhost:1234"));
        GlobalObjects.setServerName(tmpSendAddress); 
    }
}
