/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_CRUD;

import database_connection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import java.sql.*;

/**
 * This class contains all of the methods used to perform operations on the 
 * Countries database table. This currently includes a singular Read (Retrieve) 
 * operation.
 * @author LabUser
 */
public class CountriesCRUD {
    
    
    /**
     * This method retrieves all records from the database's Countries 
     * table. 
     * @return Returns an Observable List filled with Countries objects 
     * (each Countries object represents a country record from the 
     * database)
     */
    /////////////////////////// THE "R" IN "CRUD" //////////////////////////////
    /// Function to retrieve all records from the "Countries" database table ///
    ////////////////////////////////////////////////////////////////////////////
   
    public static ObservableList<Countries> getAllCountriesFromDatabase(){
           
        ObservableList<Countries> listOfCountries = FXCollections.observableArrayList();
           
        try {
            
            String sqlStatement = "SELECT * FROM Countries " + 
                                  "ORDER BY Country";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int countryID = results.getInt("Country_ID"); //Get country ID from the query result table, then shove it into a variable
               String countryName = results.getString("Country"); //Get country name from the query result table, then shove it into a variable
               
               Countries newCountry = new Countries(countryID, countryName); //Create a new object of the "Countries" class using the info we just extracted.
               listOfCountries.add(newCountry); //Add that new Countries object (which represents a country) to our Observable List of countries.
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfCountries;       
   }
   
    
    
}
