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
import model.Contacts;

/**
 * This class contains all of the methods used to perform operations on the 
 * Contacts database table. This currently includes a singular Read (Retrieve) 
 * operation.
 * @author LabUser
 */
public class ContactsCRUD {
    
    
    /**
     * This method retrieves all records from the database's Contacts 
     * table. 
     * @return Returns an Observable List filled with Contacts objects 
     * (each Contacts object represents a contact record from the 
     * database)
     */
    ////////////////////////////THE "R" IN "CRUD"///////////////////////////////
    ////Function to retrieve all records from the "Contacts" database table/////
    ////////////////////////////////////////////////////////////////////////////
   
    public static ObservableList<Contacts> getAllContactsFromDatabase(){        
   
        ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();
           
        try {
            
            String sqlStatement = "SELECT * FROM Contacts";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int contactID = results.getInt("Contact_ID"); //Get contact ID from the query result table, then shove it into a variable
               String contactName = results.getString("Contact_Name"); //Get contact's name from the query result table, then shove it into a variable
               String contactEmailAddress = results.getString("Email"); ////Get the contact's email address from the query result table, then shove it into a variable
               
               Contacts newContact = new Contacts(contactID, contactName, contactEmailAddress); //Create a new object of the "Contacts" class using the info we just extracted.
               listOfContacts.add(newContact); //Add that new Contacts object to our Observable List of contacts.
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfContacts;       
   }
    
    
    
        
}
