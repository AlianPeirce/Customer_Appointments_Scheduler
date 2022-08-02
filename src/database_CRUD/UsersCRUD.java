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
import model.Countries;
import model.Users;

/**
 * This class contains all of the methods used to perform operations on the 
 * Users database table. This currently includes a singular Read (Retrieve) 
 * operation.
 * @author LabUser
 */
public class UsersCRUD {
    
    
    /**
     * This method retrieves all records from the database's Users 
     * table. 
     * @return Returns an Observable List filled with Users objects 
     * (each Users object represents a user record from the 
     * database)
     */
    /////////////////////////// THE "R" IN "CRUD" //////////////////////////////
    /// Function to retrieve all records from the "Users" database table ///
    ////////////////////////////////////////////////////////////////////////////
   
    public static ObservableList<Users> getAllUsersFromDatabase(){
           
        ObservableList<Users> listOfUsers = FXCollections.observableArrayList();
           
        try {
            
            String sqlStatement = "SELECT * FROM Users " + 
                                  "ORDER BY User_Name";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int userID = results.getInt("User_ID"); //Get the user's ID from the query result table, then shove it into a variable
               String username = results.getString("User_Name"); //Get the user's username from the query result table, then shove it into a variable
               String password = results.getString("Password");
               
               Users newUser = new Users(userID, username, password); //Create a new object of the "Users" class using the info we just extracted.
               listOfUsers.add(newUser); //Add that new Users object (which represents a user) to our Observable List of users.
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfUsers;       
   }
    
    
    
}
