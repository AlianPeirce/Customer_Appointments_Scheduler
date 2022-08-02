/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database_CRUD.AppointmentsCRUD;
import database_CRUD.ContactsCRUD;
import database_CRUD.CountriesCRUD;
import database_CRUD.CustomersCRUD;
import database_CRUD.FirstLevelDivisionsCRUD;
import database_CRUD.UsersCRUD;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class contains static Observable Lists as well as static (and 
 * a few non-static) methods that are used throughout the application. 
 * @author LabUser
 */
public class DataHolder {
    
    /**
     * All appointment records that are currently in the Appointments 
     * database table. Each appointment record is held in an object of 
     * type Appointments; these objects are contained within an 
     * Observable List.
     */
    private static ObservableList<Appointments> listOfAllAppointments = AppointmentsCRUD.getAllAppointmentsFromDatabase();
    /**
     * All appointment records (that is, records from the database's 
     * Appointments table) that are scheduled for the current 
     * month. Each appointment record is held in an object of 
     * type Appointments; these objects are contained within an 
     * Observable List.
     */
    private static ObservableList<Appointments> listOfThisMonthsAppointments = AppointmentsCRUD.getThisMonthsAppointmentsFromDatabase();
     /**
     * All appointment records (that is, records from the database's 
     * Appointments table) that are scheduled for the current 
     * week (Sunday to Saturday, inclusive). Each appointment record is 
     * held in an object of type Appointments; these objects are 
     * contained within an Observable List.
     */
    private static ObservableList<Appointments> listOfThisWeeksAppointments = AppointmentsCRUD.getThisWeeksAppointmentsFromDatabase();
    /**
     * All records that are currently in the Contacts database table. 
     * Each contact record is held in an object of type Contacts; 
     * these objects are contained within an Observable List.
     */
    private static ObservableList<Contacts> listOfAllContacts = ContactsCRUD.getAllContactsFromDatabase();
    /**
     * All records that are currently in the Countries database table. 
     * Each country record is held in an object of type Countries; 
     * these objects are contained within an Observable List.
     */
    private static ObservableList<Countries> listOfAllCountries = CountriesCRUD.getAllCountriesFromDatabase();
    /**
     * All records that are currently in the Customers database table. 
     * Each customer record is held in an object of type Customers; 
     * these objects are contained within an Observable List.
     */
    private static ObservableList<Customers> listOfAllCustomers = CustomersCRUD.getAllCustomersFromDatabase();
    /**
     * The list of all first-level divisions (records from the 
     * First_Level_Divisions database table) that are in the given 
     * country (the country ID of the country to use is given as a parameter 
     * when calling this variable's getter method). Each division record 
     * in this list is housed in an object of type FirstLevelDivisions; 
     * these objects are contained within an Observable List.
     */
    private static ObservableList<FirstLevelDivisions> listOfAllDivisionsOfACertainCountry;
    /**
     * All records that are currently in the Users database table. 
     * Each user record is held in an object of type Users; 
     * these objects are contained within an Observable List.
     */
    private static ObservableList<Users> listOfAllUsers = UsersCRUD.getAllUsersFromDatabase();
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method takes in a date in the format MM/DD/YYYY (with or 
     * without a leading zero for the month and day values) and a time 
     * in the format HH:MM:SS or HH:MM:SS.ns ("ns" stands for "nanoseconds"); 
     * it returns a Timestamp variable created from these two parameters. 
     * @param date A date in the format MM/DD/YYYY (with or without 
     * leading zeros in the month and day values)
     * @param time A time in the format HH:MM:SS or 
     * HH:MM:SS.ns ("ns" stands for "nanoseconds"); the time must be in 
     * 24-hour format
     * @return Returns a Timestamp variable created from the given 
     * parameters
     */
    public static Timestamp convertMDYdateAndHMStimeToTimestamp(String date, String time){
                
        String reformattedDate;
        String dateTimeString;
        
        
        reformattedDate = convertMDYdateToYMDdate(date);
        dateTimeString = reformattedDate + " " + time;
        
        Timestamp timestamp = Timestamp.valueOf(dateTimeString);
        
        
        return timestamp;
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method takes in a date in MM/DD/YYYY format (with or without 
     * a leading zero for the month and day values) and converts it 
     * to YYYY-MM-DD format (with a leading zero added to the month and day 
     * values if they only consist of one digit). 
     * @param date A date in the format MM/DD/YYYY (with or without 
     * a leading zero for the month and day values)
     * @return Returns the given date in the format YYYY-MM-DD (with 
     * a leading zero added to the month and day values if they only 
     * consist of one digit)
     */
    public static String convertMDYdateToYMDdate(String date){
        
        String month;
        String day;
        String year;
        String reformattedDate;
        int indexOfForwardSlash1;
        int indexOfForwardSlash2;
        
        indexOfForwardSlash1 = date.indexOf('/', 0); //Search for the first forward slash in the "date" variable, starting from index 0.
        month = date.substring(0, indexOfForwardSlash1);
        
        indexOfForwardSlash2 = date.indexOf('/', indexOfForwardSlash1 + 1);
        day = date.substring(indexOfForwardSlash1 + 1, indexOfForwardSlash2);
        
        year = date.substring(indexOfForwardSlash2 + 1);
        
        if(month.length() == 1){
            month = "0" + month;
        }
        
        if(day.length() == 1){
            day = "0" + day;
        }
        
        reformattedDate = year + "-" + month + "-" + day;
        
        
        return reformattedDate;
    }
    
    
    //------------------------------------------------------------------------//
    
    
    /**
     * This method takes in a date in YYYY-MM-DD format (with or without 
     * a leading zero for the month and day values) and converts it 
     * to MM/DD/YYYY format (with any leading zeros in the month and day 
     * values removed). 
     * @param date A date in the format YYYY-MM-DD (with or without 
     * a leading zero for the month and day values)
     * @return Returns the given date in the format MM/DD/YYYY (with 
     * any leading zeros in the month and day values removed)
     */
    public static String convertYMDdateToMDYdate(String date){
        
        String month;
        String day;
        String year;
        String reformattedDate;
        int indexOfDash1;
        int indexOfDash2;
        
        indexOfDash1 = date.indexOf('-', 0); //Search for the first dash in the "date" variable, starting from index 0.
        year = date.substring(0, indexOfDash1);
        
        indexOfDash2 = date.indexOf('-', indexOfDash1 + 1);
        month = date.substring(indexOfDash1 + 1, indexOfDash2);
        
        day = date.substring(indexOfDash2 + 1);
        
        //The reason I'm converthing "month" and "day" from String to integer and then back to String is to get rid of any 
        //leading zeros (if there are any).
        reformattedDate = String.valueOf(Integer.parseInt(month) ) + "/" + String.valueOf(Integer.parseInt(day) ) + "/" + year;
        
        
        return reformattedDate;
    }
    
    
    //------------------------------------------------------------------------//
    
    
    /**
     * This method takes in a date in YYYY-MM-DD format (with or without 
     * a leading zero for the month and day values) and converts it 
     * to DD/MM/YYYY format (with a leading zero added to the month and day 
     * values if they only consist of one digit). 
     * @param date A date in the format YYYY-MM-DD (with or without 
     * a leading zero for the month and day values)
     * @return Returns the given date in the format DD/MM/YYYY (with a 
     * leading zero added to the month and day values if they only 
     * consist of one digit)
     */
    public static String convertYMDdateToDMYdate(String date){
        
        String month;
        String day;
        String year;
        String reformattedDate;
        int indexOfDash1;
        int indexOfDash2;
        
        indexOfDash1 = date.indexOf('-', 0); //Search for the first dash in the "date" variable, starting from index 0.
        year = date.substring(0, indexOfDash1);
        
        indexOfDash2 = date.indexOf('-', indexOfDash1 + 1);
        month = date.substring(indexOfDash1 + 1, indexOfDash2);
        
        day = date.substring(indexOfDash2 + 1);
        
        if(month.length() == 1){
            month = "0" + month;
        }
        
        if(day.length() == 1){
            day = "0" + day;
        }
        
        
        reformattedDate = day + "/" + month + "/" + year;
        
        
        return reformattedDate;
    }
    
    //------------------------------------------------------------------------//
    
    /**
     * This method takes in a time in 24-hour format (in the configuration 
     * HH:MM, HH:MM:SS, or HH:MM:SS.ns [with or without a leading zero for 
     * the hour value]) and converts it to the same time in 12-hour format 
     * (HH:MM along with AM or PM) without a leading zero for the hour 
     * value if the hour value is a single digit. 
     * @param timeIn24HourFormat The time to be converted (its format 
     * must be HH:MM, HH:MM:SS, or HH:MM:SS.ns [with or without a leading zero for 
     * the hour value])
     * @return Returns the given time converted to 12-hour format 
     * (HH:MM along with AM or PM) without a leading zero for the hour 
     * value if the hour value is a single digit
     */
    public static String convert24HourTimeTo12HourTime(String timeIn24HourFormat){
        
        int oldHour;
        int newHour;
        String minutes;
        String amOrPm;
        String timeIn12HourFormat;
        int indexOfFirstColon;
        
        
        indexOfFirstColon = timeIn24HourFormat.indexOf(':', 0); //Search for the first colon in the "timeIn24HourFormat" variable, starting from index 0.
        oldHour = Integer.parseInt( timeIn24HourFormat.substring(0, indexOfFirstColon) );
        
        minutes = timeIn24HourFormat.substring(indexOfFirstColon + 1, indexOfFirstColon + 3);
        
        
        if(oldHour == 0 || oldHour == 12){
            
            newHour = 12;
            
        } else {
            
            newHour = oldHour % 12;
        }
        
        
        if(oldHour >= 12){
            
            amOrPm = "PM";
            
        } else {
            
            amOrPm = "AM";
        }
        
        
        timeIn12HourFormat = String.valueOf(newHour) + ":" + minutes + " " + amOrPm;
        
        
        
        return timeIn12HourFormat;
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method takes in a date in MM/DD/YYYY format (with or without 
     * a leading zero for the month and day values) and calculates the 
     * date of the day after that given date. 
     * @param date The date whose next day (in date form) will be calculated
     * @return The date of the day after the given parameter 
     * (in MM/DD/YYYY format, without any leading zero for the month and day 
     * values)
     */
    public static String getDayAfterMDYDate(String date){
        
        LocalDate reformattedDate = LocalDate.parse( convertMDYdateToYMDdate(date) );
        
        String dateOfDayAfterInYMD = reformattedDate.plusDays(1).toString();
        
        String dateOfDayAfterInMDY = convertYMDdateToMDYdate(dateOfDayAfterInYMD);
        
        
        return dateOfDayAfterInMDY;
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method calculates the Timestamp (meaning the date and time) 
     * of the first day of the current week (the Sunday directly previous 
     * to today, or today if it is currently Sunday), at the beginning of 
     * that day (midnight). 
     * @return Returns the Timestamp representing the date of the 
     * first day of the current week (at the start of that day)
     */
    public static Timestamp getFirstDayOfThisWeekAtStartOfDay(){
        
        final LocalDate currentDate = LocalDate.now();
        final String nameOfCurrentDay = currentDate.getDayOfWeek().toString();
        
        
        LocalDate dateOfFirstDayOfThisWeek = currentDate;
        String nameOfFirstDayOfThisWeek = nameOfCurrentDay;
        
        while(nameOfFirstDayOfThisWeek != "SUNDAY"){
            dateOfFirstDayOfThisWeek = dateOfFirstDayOfThisWeek.minusDays(1);
            nameOfFirstDayOfThisWeek = dateOfFirstDayOfThisWeek.getDayOfWeek().toString();
        }
        
        Timestamp beginningOfFirstDayOfThisWeek = Timestamp.valueOf(dateOfFirstDayOfThisWeek.atStartOfDay());
        
        
        return beginningOfFirstDayOfThisWeek;
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method calculates the Timestamp (meaning the date and time) 
     * of the first day of next week (next Sunday, even if today is Sunday), 
     * at the beginning of that day (midnight). 
     * @return Returns the Timestamp representing the date of the 
     * first day of next week (at the start of that day)
     */
    public static Timestamp getFirstDayOfNextWeekAtStartOfDay(){
        
        LocalDate firstDayOfThisWeek = getFirstDayOfThisWeekAtStartOfDay().toLocalDateTime().toLocalDate();
        
        //We add seven days to the date of the first day of this week. This gives us the first day of next week.
        //Then, we tack on a time (midnight), and the variable thus turns from a LocalDate to a LocalDateTime.
        //Finally, we convert the LocalDateTime to a Timestamp.
        Timestamp firstDayOfNextWeekAtStartOfDay = Timestamp.valueOf( firstDayOfThisWeek.plusDays(7).atStartOfDay() );
        
        
        return firstDayOfNextWeekAtStartOfDay;
        
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method returns a Locale variable representing the locale 
     * of the current user. 
     * @return Returns a Locale variable representing the locale 
     * of the current user
     */
    //The purpose of this tiny function is so I only have to set one place (this function) to "French" in order to 
    //test out one method of activating the French language capability, rather than having to set the locale 
    //in every controller to "France".
    public static Locale getLocaleOfCurrentUser(){
        
        //This figures out where the current user is located.
        Locale localeOfCurrentUser = Locale.getDefault();
        
        //Uncomment this for one way to translate the application to French
        //localeOfCurrentUser = Locale.FRANCE;
        
        return localeOfCurrentUser;
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method switches the current visible screen to the FXML file 
     * (UI screen) indicated in the first parameter; this method only works 
     * if the event that triggered the screen switch came from a button. 
     * @param urlOfDestinationScene The URL of the FXML file (UI screen) 
     * that the user will be switched to
     * @param event The action that occurred that caused the scene to 
     * switch (e.g. a certain button was clicked)
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene)
     */
    public void switchToDifferentSceneUsingButton (String urlOfDestinationScene, ActionEvent event) throws IOException{
        
        Stage stage;
        Parent scene;
        
        //The source of the event that occurred is typecasted (converted) into a Button type.
        //Also, the window where that button is located is typecasted (converted) into type Stage.
        stage = (Stage) ( (Button) event.getSource() ).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(urlOfDestinationScene));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
    
    
    //------------------------------------------------------------------------//
    
    
    /**
     * This method switches the current visible screen to the FXML file 
     * (UI screen) indicated in the first parameter; this method only works 
     * if the event that triggered the screen switch came from a text field 
     * (for example, if the user's cursor was in a certain text field, and 
     * they then hit the "Enter" key on their keyboard). 
     * @param urlOfDestinationScene The URL of the FXML file (UI screen) 
     * that the user will be switched to
     * @param event The action that occurred that caused the scene to 
     * switch (e.g. the user's cursor was in a certain text field, and 
     * they then hit the "Enter" key on their keyboard)
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene)
     */
    public void switchToDifferentSceneUsingTextField (String urlOfDestinationScene, ActionEvent event) throws IOException{
        
        Stage stage;
        Parent scene;
        
        //The source of the event that occurred is typecasted (converted) into a TextField type.
        //Also, the window where that text field is located is typecasted (converted) into type Stage.
        stage = (Stage) ( (TextField) event.getSource() ).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(urlOfDestinationScene));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method switches the current visible screen to the FXML file 
     * (UI screen) that is loaded up in the FXMLLoader (the first parameter 
     * of this method); this method only works if the event that triggered the 
     * screen switch came from a button. Additionally, this method is 
     * only used when the old scene (UI screen) must send data to the new 
     * scene.
     * @param loader An FXMLLoader object that has the FXML file (UI screen) 
     * to switch to already loaded up inside of it
     * @param event The action that occurred that caused the scene to 
     * switch (e.g. a certain button was clicked)
     */
    public void switchToDifferentSceneAndSendDataToThatSceneUsingButton(FXMLLoader loader, ActionEvent event) {
        
        Stage stage;
        Parent scene;
        
        //The source of the event that occurred is typecasted (converted) into a Button type.
        //Also, the window where that button is located is typecasted (converted) into type Stage.
        stage = (Stage) ( (Button) event.getSource() ).getScene().getWindow();
        //In the next line of code, we are getting the container of the screen that's loaded up into "loader".
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
    
    
    //------------------------------------------------------------------------//
    
    /**
     * This method switches the current visible screen to the FXML file 
     * (UI screen) that is loaded up in the FXMLLoader (the first parameter 
     * of this method); this method only works if the event that triggered the 
     * screen switch came from a text field (for example, if the user's 
     * cursor was in a certain text field, and they then hit the "Enter" key 
     * on their keyboard). Additionally, this method is only used when the 
     * old scene (UI screen) must send data to the new scene.
     * @param loader An FXMLLoader object that has the FXML file (UI screen) 
     * to switch to already loaded up inside of it
     * @param event The action that occurred that caused the scene to 
     * switch (e.g. the user's cursor was in a certain text field, and they 
     * then hit the "Enter" key on their keyboard)
     * 
     */
    public void switchToDifferentSceneAndSendDataToThatSceneUsingTextField(FXMLLoader loader, ActionEvent event) {
        
        Stage stage;
        Parent scene;
        
        //The source of the event that occurred is typecasted (converted) into a TextField type.
        //Also, the window where that text field is located is typecasted (converted) into type Stage.
        stage = (Stage) ( (TextField) event.getSource() ).getScene().getWindow();
        //In the next line of code, we are getting the container of the screen that's loaded up into "loader".
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }
    
    
    //----------------------------- GETTERS ----------------------------------//
    
    /**
     * The getter (accessor) method for this class's "listOfAllAppointments" 
     * variable. 
     * @return Returns a list of all appointment records that are currently 
     * in the Appointments database table. Each appointment record is held 
     * in an object of type Appointments; these objects are contained within 
     * the Observable List that is being returned.
     */
    public static ObservableList<Appointments> getListOfAllAppointments() {
        
        //You CAN clear an empty list that has already been initialized, but you CANNOT clear a list 
        //that has NOT been initialized. THE PROGRAM WILL THROW AN ERROR IF YOU TRY TO DO THAT.
        //Thus, we have to check that the list has indeed been initialized before we try and clear it.
        //(I did initialize this list when I declared it further up, but it's possible that something 
        //went wront and the list didn't get initialized).
        //Also, the reason we clear the contents of the list is because we don't want a random list just 
        //hanging around in memory with no pointer pointing to the beginning of that list. (Because right 
        //after this, we create a whole new list and then assign that value to the Observable List variable that 
        //we're working with.
        if(listOfAllAppointments != null){
            listOfAllAppointments.clear();
        }
        
        //refreshes the list (repopulates it with the most current data from the database)
        listOfAllAppointments = AppointmentsCRUD.getAllAppointmentsFromDatabase();
        
        return listOfAllAppointments;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfThisMonthsAppointments" variable. 
     * @return Returns a list of all appointment records from the 
     * Appointments database table that are scheduled for the current 
     * month. Each appointment record is held in an object of type 
     * Appointments; these objects are contained within the Observable List 
     * that is being returned.
     */
    public static ObservableList<Appointments> getListOfThisMonthsAppointments() {
        
        //You CAN clear an empty list that has already been initialized, but you CANNOT clear a list 
        //that has NOT been initialized. THE PROGRAM WILL THROW AN ERROR IF YOU TRY TO DO THAT.
        //Thus, we have to check that the list has indeed been initialized before we try and clear it.
        //(I did initialize this list when I declared it further up, but it's possible that something 
        //went wront and the list didn't get initialized).
        //Also, the reason we clear the contents of the list is because we don't want a random list just 
        //hanging around in memory with no pointer pointing to the beginning of that list. (Because right 
        //after this, we create a whole new list and then assign that value to the Observable List variable that 
        //we're working with.
        if(listOfThisMonthsAppointments != null){
            listOfThisMonthsAppointments.clear();
        }
        
        //refreshes the list (repopulates it with the most current data from the database)
        listOfThisMonthsAppointments = AppointmentsCRUD.getThisMonthsAppointmentsFromDatabase();
        
        return listOfThisMonthsAppointments;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfThisWeeksAppointments" variable. 
     * @return Returns a list of all appointment records from the 
     * Appointments database table that are scheduled for the current 
     * week (Sunday to Saturday, inclusive). Each appointment record is 
     * held in an object of type Appointments; these objects are 
     * contained within the Observable List that is being returned.
     */
    public static ObservableList<Appointments> getListOfThisWeeksAppointments() {
        
        //You CAN clear an empty list that has already been initialized, but you CANNOT clear a list 
        //that has NOT been initialized. THE PROGRAM WILL THROW AN ERROR IF YOU TRY TO DO THAT.
        //Thus, we have to check that the list has indeed been initialized before we try and clear it.
        //(I did initialize this list when I declared it further up, but it's possible that something 
        //went wront and the list didn't get initialized).
        //Also, the reason we clear the contents of the list is because we don't want a random list just 
        //hanging around in memory with no pointer pointing to the beginning of that list. (Because right 
        //after this, we create a whole new list and then assign that value to the Observable List variable that 
        //we're working with.
        if(listOfThisWeeksAppointments != null){
            listOfThisWeeksAppointments.clear();
        }
        
        //refreshes the list (repopulates it with the most current data from the database)
        listOfThisWeeksAppointments = AppointmentsCRUD.getThisWeeksAppointmentsFromDatabase();
        
        return listOfThisWeeksAppointments;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfAllContacts" variable. 
     * @return Returns a list of all contact records that are currently 
     * in the Contacts database table. Each contact record is housed 
     * in an object of type Contacts; these objects are contained within 
     * the Observable List that is being returned.
     */
    public static ObservableList<Contacts> getListOfAllContacts() {
        return listOfAllContacts;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfAllCountries" variable. 
     * @return Returns a list of all country records that are currently 
     * in the Countries database table. Each country record is housed 
     * in an object of type Countries; these objects are contained within 
     * the Observable List that is being returned.
     */
    public static ObservableList<Countries> getListOfAllCountries() {
        return listOfAllCountries;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfAllCustomers" variable. 
     * @return Returns a list of all customer records that are currently 
     * in the Customers database table. Each customer record is housed 
     * in an object of type Customers; these objects are contained within 
     * the Observable List that is being returned.
     */
    public static ObservableList<Customers> getListOfAllCustomers() {
        
        //You CAN clear an empty list that has already been initialized, but you CANNOT clear a list 
        //that has NOT been initialized. THE PROGRAM WILL THROW AN ERROR IF YOU TRY TO DO THAT.
        //Thus, we have to check that the list has indeed been initialized before we try and clear it.
        //(I did initialize this list when I declared it further up, but it's possible that something 
        //went wront and the list didn't get initialized).
        //Also, the reason we clear the contents of the list is because we don't want a random list just 
        //hanging around in memory with no pointer pointing to the beginning of that list. (Because right 
        //after this, we create a whole new list and then assign that value to the Observable List variable that 
        //we're working with.
        if(listOfAllCustomers != null){
            listOfAllCustomers.clear();
        }
        
        //refreshes the list (repopulates it with the most current data from the database)
        listOfAllCustomers = CustomersCRUD.getAllCustomersFromDatabase();
        
        return listOfAllCustomers;
    }
    
    
    /**
     * The getter (accessor) method for this class's 
     * "listOfAllDivisionsOfACertainCountry" variable. 
     * @param idOfCountry The country ID of the country whose first-level 
     * divisions will be returned
     * @return Returns a list of all division records that are linked to the 
     * country whose country ID was input as a parameter (in more 
     * colloquial language, the list is a list of all first-level 
     * divisions that are in the given country). Each division record 
     * is housed in an object of type FirstLevelDivisions; these objects 
     * are contained within the Observable List that is being returned.
     */
    public static ObservableList<FirstLevelDivisions> getListOfAllDivisionsOfACertainCountry(int idOfCountry) {
        
        //You CAN clear an empty list that has already been initialized, but you CANNOT clear a list 
        //that has NOT been initialized. THE PROGRAM WILL THROW AN ERROR IF YOU TRY TO DO THAT.
        //Thus, we have to check that the list has indeed been initialized before we try and clear it.
        //Also, the reason we clear the contents of the list is because we don't want a random list just 
        //hanging around in memory with no pointer pointing to the beginning of that list. (Because right 
        //after this, we create a whole new list and then assign that value to the Observable List variable that 
        //we're working with.
        if(listOfAllDivisionsOfACertainCountry != null){
            listOfAllDivisionsOfACertainCountry.clear();
        }
        
        //refreshes the list (repopulates it [or populates it for the first time] with the most current data from the database)
        listOfAllDivisionsOfACertainCountry = FirstLevelDivisionsCRUD.getAllDivisionsOfACertainCountryFromDatabase(idOfCountry);
        
        return listOfAllDivisionsOfACertainCountry;
    }
    
    
    /**
     * The getter (accessor) method for this class's "listOfAllUsers" variable. 
     * @return Returns a list of all user records that are currently 
     * in the Users database table. Each user record is housed 
     * in an object of type Users; these objects are contained within 
     * the Observable List that is being returned.
     */
    public static ObservableList<Users> getListOfAllUsers() {
        return listOfAllUsers;
    }
    
    
}
