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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.DataHolder;

/**
 * This class contains all of the methods used to perform operations on the 
 * Appointments database table. This includes Create (Insert), Read (Retrieve), 
 * Update, and Delete operations.
 * @author LabUser
 */
public class AppointmentsCRUD {
    
    ////////////////////////////THE "R" IN "CRUD"///////////////////////////////
    //Function to retrieve all records from the "Appointments" database table///
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all records from the database's Appointments 
     * table. 
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getAllAppointmentsFromDatabase(){
           
        ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
           
        try {
            
            //We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
            //user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
            //We then store all the values we extracted as variables in each Appointment object.
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "ORDER BY Appointments.Start";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We convert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
               
               
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfAppointments.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfAppointments;       
    }
    
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    ///// A function to retrieve all of this month's appointments from the /////
    ////////////////////// "Appointments" database table ///////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all of the current month's appointments from the 
     * Appointments database table. 
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getThisMonthsAppointmentsFromDatabase(){
        
        ObservableList<Appointments> listOfThisMonthsAppointments = FXCollections.observableArrayList();
           
        try {
            
            //We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
            //user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
            //We then store all the values we extracted as variables in each Appointment object.
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "WHERE (Appointments.Start >= ? AND Appointments.Start < ?) " + 
                                  "ORDER BY Appointments.Start";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
            
            //This first statement gets the current date in local time. We do NOT want the current date in UTC time, because the database takes 
            //care of that conversion for us if we feed it a Timestamp. The contents of the database are stored in UTC time, so  
            //we want to use the same metric to compare (and again, the database does the conversion from our local time to UTC for us).
            LocalDate currentDate = LocalDate.now();
            
            LocalDateTime firstDayOfCurrentMonth = currentDate.withDayOfMonth(1).atStartOfDay();
            LocalDateTime firstDayOfNextMonth = currentDate.withDayOfMonth(1).plusMonths(1).atStartOfDay();
            
            Timestamp dateOfFirstDayOfCurrentMonth = Timestamp.valueOf(firstDayOfCurrentMonth);
            Timestamp dateOfFirstDayOfNextMonth = Timestamp.valueOf(firstDayOfNextMonth);
            
        
            //In this first statement, we're setting the first question mark in the SQL statement to be the date of the first day of the current month.
            preparedStatement1.setTimestamp(1, dateOfFirstDayOfCurrentMonth);
            preparedStatement1.setTimestamp(2, dateOfFirstDayOfNextMonth);

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
                
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN (that is, from the "Appointments" database table) WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We concert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
               
               
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfThisMonthsAppointments.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfThisMonthsAppointments;     
    }
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    ///// A function to retrieve all of this week's appointments from the //////
    ////////////////////// "Appointments" database table ///////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all of the current week's appointments from the 
     * Appointments database table. A "week" is considered to be Sunday to 
     * Saturday, inclusive.
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getThisWeeksAppointmentsFromDatabase(){
        
        ObservableList<Appointments> listOfThisWeeksAppointments = FXCollections.observableArrayList();
           
        try {
            
            //We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
            //user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
            //We then store all the values we extracted as variables in each Appointment object.
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "WHERE (Appointments.Start >= ? AND Appointments.Start < ?) " + 
                                  "ORDER BY Appointments.Start";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
                        
            //In this first statement, we're setting the first question mark in the SQL statement to be the date of the first day of the current week.
            //This is based on local time, since the database takes care of converting to UTC for us (as long as we feed it the dates as Timestamp 
            //variables).
            preparedStatement1.setTimestamp(1, DataHolder.getFirstDayOfThisWeekAtStartOfDay());
            preparedStatement1.setTimestamp(2, DataHolder.getFirstDayOfNextWeekAtStartOfDay());

            ResultSet results = preparedStatement1.executeQuery();
            
            while(results.next()){ //While there is still a next row in the query result table
                
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN (that is, from the "Appointments" database table) WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We concert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
               
               
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfThisWeeksAppointments.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfThisWeeksAppointments;     
    }
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    /////// A function to retrieve any overlapping appointments from the ///////
    /// "Appointments" database table (a particular customer's appointments ////
    ///////////////////// cannot overlap with each other). /////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all appointments from the Appointments database 
     * table that belong to the given customer (whose ID was input as a 
     * parameter) and overlap with the given time range (which is input as 
     * the startDateTime and endDateTime parameters). 
     * @param startDateTime The start date and time of the time range during 
     * which any overlapping appointments will be retrieved
     * @param endDateTime The end date and time of the time range during 
     * which any overlapping appointments will be retrieved
     * @param customerID The ID of the customer whose overlapping 
     * appointments will be retrieved
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getAnyOverlappingAppointmentsFromDatabase(LocalDateTime startDateTime, LocalDateTime endDateTime, 
                                                                                         int customerID){
        
        ObservableList<Appointments> listOfOverlappingAppointments = FXCollections.observableArrayList();
           
        try {
            
            /* We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
               user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
               We then store those all the values we extracted as variables in each Appointment object. 
               
               Here's the logic behind looking for overlapping appointments (in the following pseudocode, "Start" and "End" refer to the start 
               datetime and end datetime of appointments that are already stored in the database. "startTimeStamp" and "endTimeStamp" are the 
               start timestamp and end timestamp [timestamp is just another version of date and time] of the appointment that I *want* to put 
               in the database, but haven't done so yet [because I have to check if it overlaps with any of the existing appointments first].).
               
               Anyways, here is the pseudocode:
               
               if ( (Start <= startTimestamp < End) OR (Start < endTimestamp <= End) OR (startTimestamp <= Start < endTimeStamp) OR (startTimestamp < End <= endTimestamp) )
                   AND the customer ID of that overlapping appointment matches the customer ID of the customer that I'm trying to schedule the new appointment for, 
                   THEN that customer would have overlapping appointments (so I should not be allowed to schedule this new appointment [a.k.a. put the new appointment 
                        into the database]; this thing in the parentheses is not handled in the current method, though -- instead, it will be handled 
                        whenever/wherever I call this method.)
            */           
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "WHERE ((Appointments.Start <= ? AND Appointments.End > ?) " + 
                                  "OR (Appointments.Start < ? AND Appointments.End >= ?) " + 
                                  "OR (Appointments.Start >= ? AND Appointments.Start < ?) " + 
                                  "OR (Appointments.End > ? AND Appointments.End <= ?)) " + 
                                  "AND Appointments.Customer_ID = ? " + 
                                  "ORDER BY Appointments.Start";

            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
                        
            Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
            
            //In this first statement, we're setting the first question mark in the SQL statement to be the start Timestamp.
            //This is Timestamp is currently in the local time of the user, since the database takes care of converting to UTC for us  
            //(as long as we feed it the dates as Timestamp variables).
            preparedStatement1.setTimestamp(1, startTimestamp);
            preparedStatement1.setTimestamp(2, startTimestamp);
            
            preparedStatement1.setTimestamp(3, endTimestamp);
            preparedStatement1.setTimestamp(4, endTimestamp);
            
            preparedStatement1.setTimestamp(5, startTimestamp);
            preparedStatement1.setTimestamp(6, endTimestamp);
            
            preparedStatement1.setTimestamp(7, startTimestamp);
            preparedStatement1.setTimestamp(8, endTimestamp);
            
            preparedStatement1.setInt(9, customerID);
            

            ResultSet results = preparedStatement1.executeQuery();
            
            
            while(results.next()){ //While there is still a next row in the query result table
                
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN (that is, from the "Appointments" database table) WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We concert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
               
               
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfOverlappingAppointments.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfOverlappingAppointments;     
    }
    
    
    
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    /////// A function that retrieves a list of appointments (involving a //////
    /////// particular user) that begin within the next fifteen minutes. ///////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all appointments from the Appointments database 
     * table that involve a particular user (whose ID was input as a 
     * parameter) and occur within the next fifteen minutes (from the 
     * current time). 
     * @param userID The ID of the user whose appointments will be 
     * retrieved
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getFromDatabaseTheUsersAppointmentsThatStartWithinTheNextFifteenMinutes(int userID) {
        
        ObservableList<Appointments> listOfAppointmentsThatStartInTheNextFifteenMinutes = FXCollections.observableArrayList();
           
        try {
            
            /* We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
               user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
               We then store those all the values we extracted as variables in each Appointment object. 
            */           
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "WHERE Appointments.Start >= ? " + 
                                  "AND Appointments.Start < ? " + 
                                  "AND Appointments.User_ID = ? " + 
                                  "ORDER BY Appointments.Start";
            
            
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
                        
            
            LocalDateTime currentDateAndTime = LocalDateTime.now();
            
            //This is the date and time at the start of the current minute (a.k.a. at 0 seconds and 0 nanoseconds). 
            //We're going to check for appointments in the database that have a start time 
            //greater than or equal to this value.
            LocalDateTime dateAndTimeAtTheStartOfTheCurrentMinute = currentDateAndTime.toLocalDate().atTime( currentDateAndTime.toLocalTime().withSecond(0).withNano(0) );
            
            //This is the date and time that is 16 minutes after dateAndTimeAtTheStartOfTheCurrentMinute.
            //We're going to check for appointments in the database that have a start time less than this 
            //value.
            LocalDateTime dateAndTime16MinutesFromNow = dateAndTimeAtTheStartOfTheCurrentMinute.plusMinutes(16);
            
            //In this first statement, we're setting the first question mark in the SQL statement to be the Timestamp of the 
            //current date and time. This Timestamp is currently in the local time of the user, since the database 
            //takes care of converting to UTC for us (as long as we feed it the dates as Timestamp variables).
            preparedStatement1.setTimestamp( 1, Timestamp.valueOf(dateAndTimeAtTheStartOfTheCurrentMinute) );
            preparedStatement1.setTimestamp( 2, Timestamp.valueOf(dateAndTime16MinutesFromNow) );
            
            preparedStatement1.setInt(3, userID);
            

            ResultSet results = preparedStatement1.executeQuery();
            
            
            while(results.next()){ //While there is still a next row in the query result table
                
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN (that is, from the "Appointments" database table) WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We concert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
               
               
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfAppointmentsThatStartInTheNextFifteenMinutes.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfAppointmentsThatStartInTheNextFifteenMinutes;     
    }
    
    
    
    
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    ////// A function to retrieve all appointments from the database that //////
    /////////////////////// involve a certain contact. /////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method retrieves all appointments from the Appointments database 
     * table that involve a particular contact (whose ID was input as a 
     * parameter). 
     * @param contactID The ID of the contact whose appointments will be 
     * retrieved
     * @return Returns an Observable List filled with Appointments objects 
     * (each Appointments object represents an appointment record from the 
     * database)
     */
    public static ObservableList<Appointments> getFromDatabaseAllAppointmentsInvolvingACertainContact(int contactID){
        
        ObservableList<Appointments> listOfAppointmentsInvolvingTheContact = FXCollections.observableArrayList();
           
        try {
            
            //We have to join a bunch of tables so that we can have have access to stuff like the name of the customer involved, the name of the 
            //user in charge, and the name of the contact involved. (All of those were in tables other than the Appointments database table). 
            //We then store all the values we extracted as variables in each Appointment object.
            String sqlStatement = "SELECT Appointments.*, Customers.Customer_Name, Users.User_Name, Contacts.Contact_Name " + 
                                  "FROM Appointments " + 
                                  "INNER JOIN Customers " + 
                                  "INNER JOIN Users " + 
                                  "INNER JOIN Contacts " + 
                                  "ON Appointments.Customer_ID = Customers.Customer_ID " + 
                                  "AND Appointments.User_ID = Users.User_ID " + 
                                  "AND Appointments.Contact_ID = Contacts.Contact_ID " + 
                                  "WHERE Appointments.Contact_ID = ? " + 
                                  "ORDER BY Appointments.Start";

            
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
            
                   
            //In this first statement, we're setting the first question mark in the SQL statement to be the contact ID (which was input as a 
            //parameter into this method we're currently in).
            preparedStatement1.setInt(1, contactID);
            

            ResultSet results = preparedStatement1.executeQuery();
            
            
            while(results.next()){ //While there is still a next row in the query result table
                
               int appointmentID = results.getInt("Appointment_ID"); //Get appointment ID from the query result table, then shove it into a variable
               String appointmentName = results.getString("Title"); //Get appointment name from the query result table, then shove it into a variable
               String appointmentDescription = results.getString("Description"); //THE THING IN THE QUOTES IS THE NAME OF THE COLUMN (that is, from the "Appointments" database table) WE WANT TO RETRIEVE THE INFO FROM FOR A PARTICULAR APPOINTMENT
               String appointmentLocation = results.getString("Location");
               String appointmentType = results.getString("Type");
               
               Timestamp appointmentStartDateAndTime = results.getTimestamp("Start"); //We pull the datetime variable out using a "Timestamp" variable, which automatically
                                                                                      //converts UTC to the default time zone (local time zone of the computer running the 
                                                                                      //program) for us.
               String appointmentStartDate = appointmentStartDateAndTime.toString().substring(0, 10); //We convert that Timestamp variable into a string, and then extract just the date
               String appointmentStartTime = appointmentStartDateAndTime.toString().substring(11, 19); //We concert that Timestamp variable into a string, and then extract just the time
               
               Timestamp appointmentEndDateAndTime = results.getTimestamp("End"); //Same deal going on here. See above.
               
               String appointmentEndDate = appointmentEndDateAndTime.toString().substring(0, 10); //Same deal going on here. See above.
               String appointmentEndTime = appointmentEndDateAndTime.toString().substring(11, 19); //Same deal going on here. See above.
               
               int idOfCustomerInvolved = results.getInt("Customer_ID");
               String nameOfCustomerInvolved = results.getString("Customer_Name");
               
               int idOfUserInCharge = results.getInt("User_ID");
               String usernameOfUserInCharge = results.getString("User_Name");
               
               int idOfContactInvolved = results.getInt("Contact_ID");
               String nameOfContactInvolved = results.getString("Contact_Name");
                              
               
               //Create a new object of the "Appointments" class using the info we just extracted.
               Appointments newAppointment = new Appointments(appointmentID, appointmentName, appointmentDescription, appointmentLocation, 
                                                              appointmentType, appointmentStartDate, appointmentEndDate, appointmentStartTime, 
                                                              appointmentEndTime, idOfCustomerInvolved, nameOfCustomerInvolved, idOfUserInCharge, 
                                                              usernameOfUserInCharge, idOfContactInvolved, nameOfContactInvolved);
               
               //Add that new appointment (the Appointments object we just created) to our Observable List of appointments.
               listOfAppointmentsInvolvingTheContact.add(newAppointment); 
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return listOfAppointmentsInvolvingTheContact;  
    }
    
    
    
    
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    //////// A function to retrieve the number of appointments scheduled ///////
    ////////////////////// for each and every customer. ////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method returns the number of appointments currently scheduled 
     * for each and every customer that is in the database. 
     * @return Returns an Integer ArrayList. The elements of said 
     * ArrayList alternate - the first element contains the ID of a 
     * particular customer, the second element contains the number of 
     * appointments currently scheduled for that customer, the third element 
     * contains the ID of a different customer, the fourth element contains the 
     * number of appointments currently scheduled for that customer, and so on.
     */
    public static ArrayList<Integer> getFromDatabaseTheAppointmentTotalForEachCustomer(){
        
        ArrayList<Integer> numberOfAppointmentsScheduledForEveryCustomer = new ArrayList<Integer>(0);
           
        try {
            
            //This will return a table with the customer IDs in the left column, and the number of appointments each customer has scheduled will be 
            //in the corresponding row of the right column.
            String sqlStatement = "SELECT Customers.Customer_ID, COUNT(Appointments.Appointment_ID) AS Number_of_Appointments " + 
                                  "FROM Customers " + 
                                  "LEFT JOIN Appointments " + 
                                  "ON Customers.Customer_ID = Appointments.Customer_ID " + 
                                  "GROUP BY Customers.Customer_ID " + 
                                  "ORDER BY Number_of_Appointments DESC";

            
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
            

            ResultSet results = preparedStatement1.executeQuery();
            
            
            while(results.next()){ //While there is still a next row in the query result table
               
               //Get the customer ID (for the current record) from the query result table, then shove it into a variable
               int customerID = results.getInt("Customer_ID");
               
               //Get the number of appointments that exist in the database for that particular customer; then, just that number into a variable.
               int numberOfAppointmentsForThatCustomer = results.getInt("Number_of_Appointments");
              
               
               //Add those two numbers that we just extracted to our ArrayList.
               numberOfAppointmentsScheduledForEveryCustomer.add(customerID);
               numberOfAppointmentsScheduledForEveryCustomer.add(numberOfAppointmentsForThatCustomer);
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return numberOfAppointmentsScheduledForEveryCustomer;  
    }
    
    
    
    
    
    
    ////////////////////////// Another "R" IN "CRUD" ///////////////////////////
    //////// A function to calculate how many of each type of appointment //////
    /////////////////////// there is for the given month. //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method calculate how many of each type of appointment there is 
     * for the given month (whose month number was input as a parameter).
     * @param monthNum A number (from 1 - 12) indicating which month to use
     * @return Returns a String ArrayList. The elements of said 
     * ArrayList alternate - the first element contains the name of a type of 
     * appointment, the second element contains the number of 
     * appointments of that type that are scheduled for the current month, 
     * the third element contains the name of a different type of appointment, 
     * the fourth element contains the number of appointments of that type 
     * that are scheduled for the current month, and so on.
     */
    public static ArrayList<String> getFromDatabaseTheTotalForEachTypeOfAppointmentInAParticularMonth(int monthNum){
        
        ArrayList<String> totalForEachTypeOfAppointmentInTheGivenMonth = new ArrayList<String>(0);
           
        try {
            
            //This will return a table with the appointment type in the left column, and the right column holds the 
            //number of appointments of that type that are scheduled for the given month.
            String sqlStatement = "SELECT Type, COUNT(*) AS Number_of_Appointments " + 
                                  "FROM Appointments " + 
                                  "WHERE Month(Start) = ? OR Month(End) = ? " + 
                                  "GROUP BY Type " + 
                                  "ORDER BY Number_of_Appointments DESC";

            
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);
            
            
            //In this first statement, we're setting the first question mark in the SQL statement to be the value of monthNum (which was input as a 
            //parameter into this method we're currently in).
            preparedStatement1.setInt(1, monthNum);
            preparedStatement1.setInt(2, monthNum);
            

            ResultSet results = preparedStatement1.executeQuery();
            
            
            while(results.next()){ //While there is still a next row in the query result table
               
               //Get the appointment type (for the current record) from the query result table, then shove it into a variable.
               String appointmentType = results.getString("Type");
               
               //Get the number of appointments of that type that are scheduled for the given month; then, shove that number into a variable.
               String numberOfAppointmentsOfThatType = String.valueOf( results.getInt("Number_of_Appointments") );
              
               
               //Add those two values that we just extracted to our ArrayList.
               totalForEachTypeOfAppointmentInTheGivenMonth.add(appointmentType);
               totalForEachTypeOfAppointmentInTheGivenMonth.add(numberOfAppointmentsOfThatType);
            }
                    
        } catch(SQLException error){
           
           error.printStackTrace();
           
        }
        
        
        return totalForEachTypeOfAppointmentInTheGivenMonth;  
    }
    
    
    
    
    
    
    /////////////////////////// THE "C" IN "CRUD" //////////////////////////////
    //// Function to insert a record into the "Appointments" database table ////
    ////////////////////////////////////////////////////////////////////////////
    
    
    /**
     * This method adds a new record to the database's Appointments table; 
     * the details of this record (as in, the various column 
     * values for this record) are input as parameters into this method. 
     * @param appointmentName The title of the new appointment
     * @param appointmentDescription The description of the new appointment
     * @param appointmentLocation The location of the new appointment
     * @param appointmentType The type of the new appointment
     * @param appointmentStartDate The start date of the new appointment
     * @param appointmentEndDate The end date of the new appointment
     * @param appointmentStartTime The start time of the new appointment 
     * in the format HH:MM:SS.ns (this is 24-hour time; "ns" stands for 
     * "nanoseconds"). 
     * @param appointmentEndTime The end time of the new appointment 
     * in the format HH:MM:SS.ns (this is 24-hour time; "ns" stands for 
     * "nanoseconds"). 
     * @param idOfCustomerInvolved The customer ID of the customer involved 
     * in this new appointment
     * @param idOfUserInCharge The user ID of the user in charge of this 
     * new appointment
     * @param idOfContactInvolved The contact ID of the contact involved 
     * in this new appointment
     * @return Returns the number of table rows affected (added) in the 
     * database
     */
    //We don't need to insert the appointment ID, since Appointment_ID is the primary key
    //(of the database's Appointments table), and thus, it auto-increments on its own.
    public static int addAppointmentToDatabase(String appointmentName, String appointmentDescription, String appointmentLocation, 
                                               String appointmentType, LocalDate appointmentStartDate, LocalDate appointmentEndDate, 
                                               String appointmentStartTime, String appointmentEndTime, int idOfCustomerInvolved, 
                                               int idOfUserInCharge, int idOfContactInvolved){
        
        int numOfTableRowsAdded = 0;
        
        try {
            
            String sqlStatement = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " + 
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);            
            
            preparedStatement1.setString(1, appointmentName); //This sets question mark #1 in the SQL statement to be appointmentName / the value of appointmentName (which is a parameter of this function I'm currently making).
            preparedStatement1.setString(2, appointmentDescription);
            preparedStatement1.setString(3, appointmentLocation);
            preparedStatement1.setString(4, appointmentType);
            
            LocalTime startTime = LocalTime.parse(appointmentStartTime);
            LocalTime endTime = LocalTime.parse(appointmentEndTime);
            
            preparedStatement1.setTimestamp(5, Timestamp.valueOf( appointmentStartDate.atTime(startTime) ) );
            preparedStatement1.setTimestamp(6, Timestamp.valueOf( appointmentEndDate.atTime(endTime) ) );
            preparedStatement1.setInt(7, idOfCustomerInvolved);
            preparedStatement1.setInt(8, idOfUserInCharge);
            preparedStatement1.setInt(9, idOfContactInvolved);
            
            numOfTableRowsAdded = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                      //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
        } catch(SQLException error) {                                 //the number of rows affected by the SQL statement. We store this number in a
                                                                      //variable, and then we return this variable at the end of the function.            
            error.printStackTrace();
        }
      
        return numOfTableRowsAdded;
    }
    
    
    
    
    
    
      //////////////////////////////// THE "U" IN "CRUD" /////////////////////////////////////
     /// Function to update a record that's already in the "Appointments" database table ////
    ////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * This method updates an existing record that is in the database's 
     * Appointments table; the new details for this record (as in, the new 
     * values for this record) are input as parameters into this method. 
     * @param appointmentID The ID number of the appointment (appointment 
     * record) that is to be modified
     * @param appointmentName The new title that will replace the 
     * old title for the appointment being modified
     * @param appointmentDescription The new description that will 
     * replace the old description for the appointment being modified
     * @param appointmentLocation The new location that will 
     * replace the old location for the appointment being modified
     * @param appointmentType The new appointment type that will 
     * replace the old type for the appointment being modified
     * @param appointmentStartDate The new start date that will 
     * replace the old start date for the appointment being modified
     * @param appointmentEndDate The new end date that will 
     * replace the old end date for the appointment being modified
     * @param appointmentStartTime The new start time that will replace 
     * the old start time of the appointment being modified. The format 
     * should be HH:MM:SS.ns (this is 24-hour time; "ns" stands for 
     * "nanoseconds").
     * @param appointmentEndTime The new end time that will replace 
     * the old end time of the appointment being modified. The format 
     * should be HH:MM:SS.ns (this is 24-hour time; "ns" stands for 
     * "nanoseconds").
     * @param idOfCustomerInvolved The customer ID of the customer involved 
     * in the appointment that is being modified. This value will replace 
     * the old value.
     * @param idOfUserInCharge The user ID of the user involved 
     * in the appointment that is being modified. This value will replace 
     * the old value.
     * @param idOfContactInvolved The contact ID of the contact involved 
     * in the appointment that is being modified. This value will replace 
     * the old value.
     * @return Returns the number of table rows affected (updated) in the 
     * database
     */
    public static int modifyAppointmentInDatabase(int appointmentID, String appointmentName, String appointmentDescription, 
                                                  String appointmentLocation, String appointmentType, LocalDate appointmentStartDate, 
                                                  LocalDate appointmentEndDate, String appointmentStartTime, String appointmentEndTime, 
                                                  int idOfCustomerInvolved, int idOfUserInCharge, int idOfContactInvolved){
        
        int numOfTableRowsModified = 0;
        
        try {
            
            String sqlStatement = "UPDATE Appointments " + 
                                  "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " + 
                                  "WHERE Appointment_ID = ?";
        
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);            
            
            preparedStatement1.setString(1, appointmentName); //This sets question mark #1 in the SQL statement to be appointmentName / the value of appointmentName (which is a parameter of this function I'm currently making).
            preparedStatement1.setString(2, appointmentDescription);
            preparedStatement1.setString(3, appointmentLocation);
            preparedStatement1.setString(4, appointmentType);
            
            LocalTime startTime = LocalTime.parse(appointmentStartTime);
            LocalTime endTime = LocalTime.parse(appointmentEndTime);
            
            preparedStatement1.setTimestamp(5, Timestamp.valueOf( appointmentStartDate.atTime(startTime) ) );
            preparedStatement1.setTimestamp(6, Timestamp.valueOf( appointmentEndDate.atTime(endTime) ) );
            
            preparedStatement1.setInt(7, idOfCustomerInvolved);
            preparedStatement1.setInt(8, idOfUserInCharge);
            preparedStatement1.setInt(9, idOfContactInvolved);
            preparedStatement1.setInt(10, appointmentID);
            
            numOfTableRowsModified = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                        //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
        } catch(SQLException error) {                                  //the number of rows affected by the SQL statement. We store this number in a
                                                                      //variable, and then we return this variable at the end of the function.            
            error.printStackTrace();
        }
      
        return numOfTableRowsModified;
    }
    
    
    
    
    
    /**
     * This method deletes the record that has the given appointment ID 
     * from the database's Appointments table. 
     * @param appointmentID The ID of the appointment record 
     * that is to be deleted
     * @return Returns the number of table rows affected (deleted) from the 
     * database
     */
      //////////////////////////////// THE "D" IN "CRUD" /////////////////////////////////////
     ///////// Function to delete a record from the "Appointments" database table ///////////
    ////////////////////////////////////////////////////////////////////////////////////////
    
    public static int deleteAppointmentFromDatabase(int appointmentID){
        
        int numOfTableRowsDeleted = 0;
        
        try {
            
            String sqlStatement = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        
            PreparedStatement preparedStatement1 = DatabaseConnection.getConnection().prepareStatement(sqlStatement);            
            
            preparedStatement1.setInt(1, appointmentID); //This sets question mark #1 in the SQL statement to be appointmentID / the value of appointmentID (which is a parameter of this function I'm currently making).
            
            numOfTableRowsDeleted = preparedStatement1.executeUpdate(); //The SQL statment is executed (using "executeUpdate()" instead of "executeQuery()" 
                                                                       //because "query" means you're using SQL's SELECT keyword). "executeUpdate()" returns                                                                                                                                             
        } catch(SQLException error) {                                 //the number of rows affected by the SQL statement. We store this number in a
                                                                     //variable, and then we return this variable at the end of the function.            
            error.printStackTrace();
        }
      
        return numOfTableRowsDeleted;
    }

    
}
