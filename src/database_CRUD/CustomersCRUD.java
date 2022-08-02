/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_CRUD;

import database_connection.DatabaseConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

/**
 * This class contains all of the methods used to perform operations on the 
 * Customers database table. This includes Create (Insert), Read (Retrieve), 
 * Update, and Delete operations.
 * @author LabUser
 */
public class CustomersCRUD {
    
    
    /**
     * This method retrieves all records from the database's Customers 
     * table. 
     * @return Returns an Observable List filled with Customers objects 
     * (each Customers object represents a customer record from the 
     * database)
     */
    ////////////////////////////THE "R" IN "CRUD"///////////////////////////////
    ////Function to retrieve all records from the "Customers" database table////
    ////////////////////////////////////////////////////////////////////////////
   
    public static ObservableList<Customers> getAllCustomersFromDatabase(){
           
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
           
        try {
            
            //We have to join a bunch of tables so that we can have have access to the division and country where the customer lives. 
            // (These were stored in tables other than the Customers database table.) We then store those values as variables in each Customers object.
            String sqlStatement = "SELECT Customers.*, First_Level_Divisions.Division, Countries.Country_ID, Countries.Country " +
                                  "FROM Customers " +
                                  "INNER JOIN First_Level_Divisions " +
                                  "INNER JOIN Countries " +
                                  "ON Customers.Division_ID = First_Level_Divisions.Division_ID " +
                                  "AND First_Level_Divisions.Country_ID = Countries.Country_ID " + 
                                  "ORDER BY Customers.Customer_Name";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int customerID = results.getInt("Customer_ID"); //Get the customer's ID from the query result table, then shove it into a variable
               String customerName = results.getString("Customer_Name"); //Get the customer's name from the query result table, then shove it into a variable
               String customerAddress = results.getString("Address");
               String customerPostalCode = results.getString("Postal_Code");
               String customerPhoneNumber = results.getString("Phone");
               int divisionOfResidenceID = results.getInt("Division_ID");
               String divisionOfResidence = results.getString("Division");
               int countryOfResidenceID = results.getInt("Country_ID");
               String countryOfResidence = results.getString("Country");
               
               Customers newCustomer = new Customers(customerID, customerName, customerAddress, customerPostalCode, 
                                                     customerPhoneNumber, divisionOfResidenceID, divisionOfResidence, 
                                                     countryOfResidenceID, countryOfResidence); //Create a new object of the "Customers" class using the info we just extracted.
               
               listOfCustomers.add(newCustomer); //Add that new Customers object (which represents a customer) to our Observable List of customers.
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfCustomers;       
    }
    
    
    
    
    
    /**
     * This method adds a new record to the database's Customers table; 
     * the details of this record (as in, the various column 
     * values for this record) are input as parameters into this method. 
     * @param customerName The full name of the new customer
     * @param streetAddress The street address of the new customer
     * @param postalCode The postal code of the new customer
     * @param phoneNumber The phone number of the new customer
     * @param divisionOfResidenceID The ID of the first-level division 
     * (as in divisions of a country) where the new customer resides
     * @return Returns the number of table rows affected (added) in the 
     * database
     */
    /////////////////////////// THE "C" IN "CRUD" //////////////////////////////
    ///// Function to insert a record into the "Customers" database table //////
    ////////////////////////////////////////////////////////////////////////////
    
    public static int addCustomerToDatabase(String customerName, String streetAddress, 
                                            String postalCode, String phoneNumber, int divisionOfResidenceID){ //We don't need to insert a customerID, since Customer_ID is the primary key
                                                                                                              //(of the database's Customers table), and thus, it auto-increments on its own.
        int numOfTableRowsAdded = 0;
        
        try {
            
            String sqlStatement = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " + 
                                  "VALUES (?, ?, ?, ?, ?)";
        
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);            
            
            preparedStatement1.setString(1, customerName); //This sets question mark #1 in the SQL statement to be customerName / the value of customerName (which is a parameter of this function I'm currently making).
            preparedStatement1.setString(2, streetAddress);
            preparedStatement1.setString(3, postalCode);
            preparedStatement1.setString(4, phoneNumber);
            preparedStatement1.setInt(5, divisionOfResidenceID);
            
            numOfTableRowsAdded = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                      //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
        } catch(SQLException error) {                                 //the number of rows affected by the SQL statement. We store this number in a
                                                                      //variable, and then we return this variable at the end of the function.            
            error.printStackTrace();
        }
      
        return numOfTableRowsAdded;
    }
    
    
    
    
    /**
     * This method updates an existing record that is in the database's 
     * Customers table; the new details for this record (as in, the new 
     * values for this record) are input as parameters into this method. 
     * @param customerID The ID number of the customer (customer 
     * record) that is to be modified
     * @param customerName The new name for the existing customer
     * @param streetAddress The new street address for the existing customer
     * @param postalCode The new postal code for the existing customer
     * @param phoneNumber The new phone number for the existing customer
     * @param divisionOfResidenceID The ID of the new first-level division 
     * (as in divisions of a country) where the existing customer resides
     * @return Returns the number of table rows affected (updated) in the 
     * database
     */
      ///////////////////////////////// THE "U" IN "CRUD" //////////////////////////////////////
     ///// Function to update a record that was already in the "Customers" database table /////
    //////////////////////////////////////////////////////////////////////////////////////////
    
    public static int modifyCustomerInDatabase(int customerID, String customerName, String streetAddress, 
                                               String postalCode, String phoneNumber, int divisionOfResidenceID){
        
        int numOfTableRowsModified = 0;
        
        try {
            
            String sqlStatement = "UPDATE Customers " + 
                                  "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? " + 
                                  "WHERE Customer_ID = ?";
        
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);            
            
            preparedStatement1.setString(1, customerName); //This sets question mark #1 in the SQL statement to be customerName / the value of customerName (which is a parameter of this function I'm currently making).
            preparedStatement1.setString(2, streetAddress);
            preparedStatement1.setString(3, postalCode);
            preparedStatement1.setString(4, phoneNumber);
            preparedStatement1.setInt(5, divisionOfResidenceID);
            preparedStatement1.setInt(6, customerID);
            
            numOfTableRowsModified = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                        //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
        } catch(SQLException error) {                                  //the number of rows affected by the SQL statement. We store this number in a
                                                                      //variable, and then we return this variable at the end of the function.            
            error.printStackTrace();
        }
      
        return numOfTableRowsModified;
    }
    
    
    
    
    
    /**
     * This method deletes the record that has the given customer ID 
     * from the database's Customers table. 
     * @param customerID The ID of the customer record 
     * that is to be deleted
     * @return Returns the number of table rows affected (deleted) from the 
     * database
     */
      ///////////////////////////////// THE "D" IN "CRUD" //////////////////////////////////////
     //////////// Function to delete a record from the "Customers" database table /////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    
    public static int deleteCustomerFromDatabase(int customerID){
        
        int numOfRowsDeletedFromAppointmentsTable = -1;
        int numOfRowsDeletedFromCustomersTable = 0;
        
        try {
            
            //First, we want to delete the appointments involving the customer that we're going 
            //to delete (due to foreign key constraints). We don't want to have appointments 
            //that involve a customer who doesn't exist.
            
            String sqlStatement1 = "DELETE FROM Appointments WHERE Customer_ID = ?";
            
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement1);            
            
            preparedStatement1.setInt(1, customerID); //This sets question mark #1 in the SQL statement to be customerID / the value of customerID (which is a parameter of this function I'm currently making).
                        
            numOfRowsDeletedFromAppointmentsTable = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                       //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
                                                                      //the number of rows affected by the SQL statement. We store this number in a
                                                                     //variable, and then we return this variable at the end of the function.
            
            //------------------------------------------------------------------
            
            if(numOfRowsDeletedFromAppointmentsTable >= 0){
                
                String sqlStatement2 = "DELETE FROM Customers WHERE Customer_ID = ?";

                PreparedStatement preparedStatement2 = DatabaseConnection.getConnection().prepareStatement(sqlStatement2);            

                preparedStatement2.setInt(1, customerID); //This sets question mark #1 in the SQL statement to be customerID / the value of customerID (which is a parameter of this function I'm currently making).

                numOfRowsDeletedFromCustomersTable = preparedStatement2.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
            }                                                                           //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns 
                                                                                       //the number of rows affected by the SQL statement. We store this number in a
                                                                                      //variable, and then we return this variable at the end of the function.
        } catch(SQLException error) {                                  
                                                                                  
            error.printStackTrace();
        }
      
        
        return numOfRowsDeletedFromCustomersTable;
    }
    
    
}
