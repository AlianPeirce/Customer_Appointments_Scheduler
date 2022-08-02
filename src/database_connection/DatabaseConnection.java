/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;


/**
 * This class handles all things related to the database connection; it 
 * provides methods that can open the connection to the database, close 
 * the connection to the database, and return the Connection object itself. 
 * @author LabUser
 */
public abstract class DatabaseConnection {
    
    /**
     * The protocol of the database that the application will connect to. 
     */
    private static final String protocol = "jdbc";
    /**
     * The vendor of the database that the application will connect to. 
     */
    private static final String vendor = ":mysql:";
    /**
     * The location of the database that the application will connect to. 
     */
    private static final String location = "//localhost/";
    /**
     * The name of the database that the application will connect to. 
     */
    private static final String databaseName = "client_schedule";
    /**
     * The URL that the application uses in order to connect with the 
     * database. 
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**
     * The name of the database driver. 
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * The username used to log into the database. 
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * The password used to log into the database. 
     */
    private static String password = "Passw0rd!"; // Password
    /**
     * The connection to the database itself. 
     */
    public static Connection connection;  // Connection Interface
    
    /**
     * This method opens the connection to the database. If this action is 
     * successful, this method outputs a statement indicating as such; 
     * otherwise, if the action is unsuccessful, an exception is thrown and 
     * the stack trace is output to the console (which is useful for 
     * debugging purposes for any developers who might see it).
     */
    public static void openConnection()
    {   
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * This method returns the database connection itself (in the form 
     * of a Connection object). 
     * @return Returns a Connection object referencing the database connection
     */
    public static Connection getConnection(){
        return connection;
    }
    
    /**
     * This method closes the connection to the database. If this action is 
     * successful, this method outputs a statement indicating as such; 
     * otherwise, if the action is unsuccessful, an exception is thrown and 
     * the stack trace is output to the console (which is useful for 
     * debugging purposes for any developers who might see it).
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
