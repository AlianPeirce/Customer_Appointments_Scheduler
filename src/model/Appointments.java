/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author LabUser
 */
/**
 * 
 * Each appointment record retrieved from the database (in other words, 
 * each record retrieved from the database's Appointments table) will be 
 * housed in an object of this class. 
 */
public class Appointments {
    /**
     * The ID of the appointment. 
     */
    private int appointmentID;
    /**
     * The title of the appointment. 
     */
    private String appointmentName;
    /**
     * The description of the appointment. 
     */
    private String appointmentDescription;
    /**
     * The location where the appointment will take place. 
     */
    private String appointmentLocation;
    /**
     * The appointment's type. 
     */
    private String appointmentType;
    /**
     * The date that the appointment starts on (in the 
     * format YYYY-MM-DD). The date in this variable will ALWAYS have 
     * exactly that many characters.
     */
    private String appointmentStartDate;
    /**
     * The date that the appointment starts on, in the format MM/DD/YYYY 
     * (with any leading zeros in the month and day value removed). 
     */
    private String appointmentStartDateInMDY;
    /**
     * The date that the appointment starts on, in the format DD/MM/YYYY 
     * (with leading zeros used if the day or month value is a single digit). 
     */
    private String appointmentStartDateInDMY;
    /**
     * The date that the appointment ends on (in the 
     * format YYYY-MM-DD). The date in this variable will ALWAYS have 
     * exactly that many characters.
     */
    private String appointmentEndDate;
    /**
     * The time that the appointment starts, in the format 
     * HH:MM:SS.ns (this is 24-hour time; "ns" stands for "nanoseconds"). 
     */
    private String appointmentStartTime;
    /**
     * The time that the appointment ends, in the format 
     * HH:MM:SS.ns (this is 24-hour time; "ns" stands for "nanoseconds"). 
     */
    private String appointmentEndTime;
    /**
     * The date and time that the appointment starts, in the format MM/DD/YY 
     * (with any leading zeros in the month and day value removed) and 
     * 12-hour time (in the format HH:MM, along with AM or PM). This is 
     * used for the Appointments Table when the application is presented in 
     * English.
     */
    private String appointmentStartDateAndTimeInMDYand12HourTime;
     /**
     * The date and time that the appointment ends, in the format MM/DD/YY 
     * (with any leading zeros in the month and day value removed) and 
     * 12-hour time (in the format HH:MM, along with AM or PM). This is 
     * used for the Appointments Table when the application is presented in 
     * English.
     */
    private String appointmentEndDateAndTimeInMDYand12HourTime;
     /**
     * The date and time that the appointment starts, in the format DD/MM/YY 
     * (with leading zeros used if the day or month value is a single digit) 
     * and 24-hour time (in the format HH:MM). This is used for the 
     * Appointments Table when the application is presented in French.
     */
    private String appointmentStartDateAndTimeInDMYand24HourTime;
    /**
     * The date and time that the appointment ends, in the format DD/MM/YY 
     * (with leading zeros used if the day or month value is a single digit) 
     * and 24-hour time (in the format HH:MM). This is used for the 
     * Appointments Table when the application is presented in French.
     */
    private String appointmentEndDateAndTimeInDMYand24HourTime;
    /**
     * The start time and end time of the appointment (in 24-hour time, with 
     * the format HH:MM) with a dash between the start time and end time 
     * (so as to signify a range). 
     */
    private String appointmentTimeRange;
    /**
     * The Customer ID of the customer that is involved in this appointment. 
     */
    private int idOfCustomerInvolved;
    /**
     * The full name of the customer that is involved in this appointment. 
     */
    private String nameOfCustomerInvolved;
    /**
     * The User ID of the user that is in charge of this appointment. 
     */
    private int idOfUserInCharge;
    /**
     * The username of the user that is in charge of this appointment. 
     */
    private String usernameOfUserInCharge;
     /**
     * The Contact ID of the contact that is involved in this appointment. 
     */
    private int idOfContactInvolved;
    /**
     * The full name of the contact that is involved in this appointment. 
     */
    private String nameOfContactInvolved;

    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the Appointments class; upon the 
     * creation of an Appointment object, this method sets the variables 
     * (fields) of that Appointment object to be equal to the given 
     * parameters. It also uses the parameter values to calculate the values 
     * of certain variables in the Appointment object.
     * @param appointmentID The ID of the new appointment
     * @param appointmentName The title of the new appointment
     * @param appointmentDescription The description of the new appointment
     * @param appointmentLocation The location of the new appointment
     * @param appointmentType The type for the new appointment
     * @param appointmentStartDate The start date of the new appointment 
     * (in YYYY-MM-DD format)
     * @param appointmentEndDate The end date of the new appointment 
     * (in YYYY-MM-DD format)
     * @param appointmentStartTime The start time of the new appointment in 
     * 24-hour time (in the format HH:MM:SS.ns ; "ns" stands for "nanoseconds")
     * @param appointmentEndTime The end time of the new appointment in 
     * 24-hour time (in the format HH:MM:SS.ns ; "ns" stands for "nanoseconds")
     * @param idOfCustomerInvolved The customer ID of the customer that is 
     * involved in this new appointment
     * @param nameOfCustomerInvolved The full name of the customer that is 
     * involved in this new appointment
     * @param idOfUserInCharge The user ID of the user that is in charge 
     * of this new appointment
     * @param usernameOfUserInCharge The username of the user that is in charge 
     * of this new appointment
     * @param idOfContactInvolved The contact ID of the contact that is 
     * involved in this new appointment
     * @param nameOfContactInvolved The full name of the contact that is 
     * involved in this new appointment
     */
    public Appointments(int appointmentID, String appointmentName, String appointmentDescription, String appointmentLocation, 
                        String appointmentType, String appointmentStartDate, String appointmentEndDate, String appointmentStartTime, 
                        String appointmentEndTime, int idOfCustomerInvolved, String nameOfCustomerInvolved, int idOfUserInCharge, 
                        String usernameOfUserInCharge, int idOfContactInvolved, String nameOfContactInvolved) {
        this.appointmentID = appointmentID;
        this.appointmentName = appointmentName;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStartDate = appointmentStartDate;
        this.appointmentEndDate = appointmentEndDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.idOfCustomerInvolved = idOfCustomerInvolved;
        this.nameOfCustomerInvolved = nameOfCustomerInvolved;
        this.idOfUserInCharge = idOfUserInCharge;
        this.usernameOfUserInCharge = usernameOfUserInCharge;
        this.idOfContactInvolved = idOfContactInvolved;
        this.nameOfContactInvolved = nameOfContactInvolved;
        
        this.appointmentStartDateInMDY = DataHolder.convertYMDdateToMDYdate(appointmentStartDate);
        this.appointmentStartDateInDMY = DataHolder.convertYMDdateToDMYdate(appointmentStartDate);
        
        //We're basically just getting rid of the seconds here. We're converting the times from "HH:MM:SS" to "HH:MM".
        //Then, we add a dash between the start time and end time (to create a displayable time range).
        this.appointmentTimeRange = appointmentStartTime.substring(0, 5) + " - " + appointmentEndTime.substring(0, 5);
        
        this.appointmentStartDateAndTimeInMDYand12HourTime = DataHolder.convertYMDdateToMDYdate(appointmentStartDate) + "  " + 
                                                             DataHolder.convert24HourTimeTo12HourTime(appointmentStartTime);
        
        this.appointmentEndDateAndTimeInMDYand12HourTime = DataHolder.convertYMDdateToMDYdate(appointmentEndDate) + "  " + 
                                                           DataHolder.convert24HourTimeTo12HourTime(appointmentEndTime);
        
        this.appointmentStartDateAndTimeInDMYand24HourTime = DataHolder.convertYMDdateToDMYdate(appointmentStartDate) + "  " + 
                                                             appointmentStartTime.substring(0, 5);
        
        this.appointmentEndDateAndTimeInDMYand24HourTime = DataHolder.convertYMDdateToDMYdate(appointmentEndDate) + "  " + 
                                                           appointmentEndTime.substring(0, 5);
    }
    
    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * The accessor (getter) method for the "appointmentID" variable of this 
     * class. 
     * @return Returns the appointment ID of the current appointment
     */
    public int getAppointmentID() {
        return appointmentID;
    }
    
    /**
     * The accessor (getter) method for the "appointmentName" variable of this 
     * class. 
     * @return Returns the full name of this appointment
     */
    public String getAppointmentName() {
        return appointmentName;
    }
    
    /**
     * The accessor (getter) method for the "appointmentDescription" variable 
     * of this class. 
     * @return Returns the description of this appointment
     */
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    
    /**
     * The accessor (getter) method for the "appointmentLocation" variable 
     * of this class. 
     * @return Returns the location of this appointment
     */
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    
    /**
     * The accessor (getter) method for the "appointmentType" variable 
     * of this class. 
     * @return Returns the type of this appointment
     */
    public String getAppointmentType() {
        return appointmentType;
    }
    
     /**
     * The accessor (getter) method for the "appointmentStartDate" variable 
     * of this class. 
     * @return Returns the start date of the appointment (in the 
     * format YYYY-MM-DD). The date returned will ALWAYS have 
     * exactly that many characters (because this date value comes 
     * straight from the database).
     */
    public String getAppointmentStartDate() {
        return appointmentStartDate;
    }
    
    /**
     * The accessor (getter) method for the "appointmentStartDateInMDY" 
     * variable of this class. 
     * @return Returns the start date of the appointment in the 
     * format MM/DD/YYYY (with any leading zeros for the month and day 
     * values removed).
     */
    public String getAppointmentStartDateInMDY() {
        return appointmentStartDateInMDY;
    }
    
    /**
     * The accessor (getter) method for the "appointmentStartDateInDMY" 
     * variable of this class. 
     * @return Returns the start date of the appointment in the 
     * format DD/MM/YYYY (with leading zeros used if the day or month 
     * value is a single digit).
     */
    public String getAppointmentStartDateInDMY() {
        return appointmentStartDateInDMY;
    }
    
    /**
     * The accessor (getter) method for the "appointmentEndDate" variable 
     * of this class. 
     * @return Returns the end date of the appointment (in the 
     * format YYYY-MM-DD). The date returned will ALWAYS have 
     * exactly that many characters because this date value comes 
     * straight from the database).
     */
    public String getAppointmentEndDate() {
        return appointmentEndDate;
    }
    
    /**
     * The accessor (getter) method for the "appointmentStartTime" variable 
     * of this class. 
     * @return Returns the start time of this appointment in 
     * 24-hour time (in the format HH:MM:SS.ns ; "ns" stands for "nanoseconds")
     */
    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }
    
    /**
     * The accessor (getter) method for the "appointmentEndTime" variable 
     * of this class. 
     * @return Returns the end time of this appointment in 
     * 24-hour time (in the format HH:MM:SS.ns ; "ns" stands for "nanoseconds")
     */
    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }
    
    /**
     * The accessor (getter) method for the 
     * "appointmentStartDateAndTimeInMDYand12HourTime" variable 
     * of this class. 
     * @return Returns the date and time that the appointment starts, in the 
     * format MM/DD/YY (with any leading zeros in the month and day value 
     * removed) and 12-hour time (in the format HH:MM, along with AM or PM). 
     * This is used for the Appointments Table when the application is 
     * presented in English.
     */
    public String getAppointmentStartDateAndTimeInMDYand12HourTime() {
        return appointmentStartDateAndTimeInMDYand12HourTime;
    }
    
    /**
     * The accessor (getter) method for the 
     * "appointmentEndDateAndTimeInMDYand12HourTime" variable 
     * of this class. 
     * @return Returns the date and time that the appointment ends, in the 
     * format MM/DD/YY (with any leading zeros in the month and day value 
     * removed) and 12-hour time (in the format HH:MM, along with AM or PM). 
     * This is used for the Appointments Table when the application is 
     * presented in English.
     */
    public String getAppointmentEndDateAndTimeInMDYand12HourTime() {
        return appointmentEndDateAndTimeInMDYand12HourTime;
    }
    
    /**
     * The accessor (getter) method for the 
     * "appointmentStartDateAndTimeInDMYand24HourTime" variable 
     * of this class. 
     * @return Returns the date and time that the appointment starts, in 
     * the format DD/MM/YY (with leading zeros used if the day or month 
     * value is a single digit) and 24-hour time (in the format HH:MM). 
     * This is used for the Appointments Table when the application is 
     * presented in French.
     */
    public String getAppointmentStartDateAndTimeInDMYand24HourTime() {
        return appointmentStartDateAndTimeInDMYand24HourTime;
    }
    
    /**
     * The accessor (getter) method for the 
     * "appointmentEndDateAndTimeInDMYand24HourTime" variable 
     * of this class. 
     * @return Returns the date and time that the appointment ends, in 
     * the format DD/MM/YY (with leading zeros used if the day or month 
     * value is a single digit) and 24-hour time (in the format HH:MM). 
     * This is used for the Appointments Table when the application is 
     * presented in French.
     */
    public String getAppointmentEndDateAndTimeInDMYand24HourTime() {
        return appointmentEndDateAndTimeInDMYand24HourTime;
    }
    
    /**
     * The accessor (getter) method for the "appointmentTimeRange" variable 
     * of this class. 
     * @return Returns the start time and end time of the appointment 
     * (in 24-hour time, with the format HH:MM) with a dash between the 
     * start time and end time so as to signify a range). 
     */
    public String getAppointmentTimeRange() {
        return appointmentTimeRange;
    }
    
    /**
     * The accessor (getter) method for the "idOfCustomerInvolved" variable 
     * of this class. 
     * @return Returns the customer ID of the customer involved in this 
     * appointment
     */
    public int getIdOfCustomerInvolved() {
        return idOfCustomerInvolved;
    }
    
    /**
     * The accessor (getter) method for the "nameOfCustomerInvolved" variable 
     * of this class. 
     * @return Returns the full name of the customer involved in this 
     * appointment
     */
    public String getNameOfCustomerInvolved() {
        return nameOfCustomerInvolved;
    }
    
    /**
     * The accessor (getter) method for the "idOfUserInCharge" variable 
     * of this class. 
     * @return Returns the user ID of the user in charge of this 
     * appointment
     */
    public int getIdOfUserInCharge() {
        return idOfUserInCharge;
    }
    
    /**
     * The accessor (getter) method for the "usernameOfUserInCharge" variable 
     * of this class. 
     * @return Returns the username of the user in charge of this 
     * appointment
     */
    public String getUsernameOfUserInCharge() {
        return usernameOfUserInCharge;
    }
    
    /**
     * The accessor (getter) method for the "idOfContactInvolved" variable 
     * of this class. 
     * @return Returns the contact ID of the contact involved in this 
     * appointment
     */
    public int getIdOfContactInvolved() {
        return idOfContactInvolved;
    }
    
    /**
     * The accessor (getter) method for the "nameOfContactInvolved" variable 
     * of this class. 
     * @return Returns the full name of the contact involved in this 
     * appointment
     */
    public String getNameOfContactInvolved() {
        return nameOfContactInvolved;
    }
          
    
}
