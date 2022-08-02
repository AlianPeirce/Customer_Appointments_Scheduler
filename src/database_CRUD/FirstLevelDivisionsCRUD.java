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
import model.FirstLevelDivisions;

/**
 * This class contains all of the methods used to perform operations on the 
 * First_Level_Divisions database table. This currently includes a singular 
 * Read (Retrieve) operation.
 * @author LabUser
 */
public class FirstLevelDivisionsCRUD {
    
    
    /**
     * This method retrieves all records that have the given country ID 
     * from the database's First_Level_Divisions table. In more colloquial 
     * language, this method returns all divisions that exist within a 
     * certain country.
     * @param idOfCountry The ID of the country whose divisions are to 
     * be retrieved from the database
     * @return Returns an Observable List filled with FirstLevelDivisions 
     * objects (each FirstLevelDivisions object represents a division record 
     * from the database)
     */
    /////////////////////////// THE "R" IN "CRUD" ////////////////////////////////////////
    // Function to retrieve all records from the "First_Level_Divisions" database table //
    //////////////////////////////////////////////////////////////////////////////////////
   
    public static ObservableList<FirstLevelDivisions> getAllDivisionsOfACertainCountryFromDatabase(int idOfCountry){
           
        ObservableList<FirstLevelDivisions> listOfDivisionsOfACertainCountry = FXCollections.observableArrayList();
           
        try {
            
            String sqlStatement = "SELECT * FROM First_Level_Divisions " + 
                                  "WHERE Country_ID = ? " + 
                                  "ORDER BY Division";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
            
            //This sets question mark #1 in the SQL statement to be idOfCountry / the value of idOfCountry
            //(which is a parameter of this function I'm currently making).
            preparedStatement1.setInt(1, idOfCountry);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int divisionID = results.getInt("Division_ID"); //Get the division's ID from the query result table, then shove it into a variable
               String divisionName = results.getString("Division"); //Get the division's name from the query result table, then shove it into a variable
               int idOfHostCountry = results.getInt("Country_ID");
               
               FirstLevelDivisions newDivision = new FirstLevelDivisions(divisionID, divisionName, idOfHostCountry); //Create a new object of my "FirstLevelDivisions" class using the info we just extracted.
               listOfDivisionsOfACertainCountry.add(newDivision); //Add that new FirstLevelDivisions object (which represents a division) to our Observable List of divisions.
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfDivisionsOfACertainCountry;       
   }
    
    
    
}
