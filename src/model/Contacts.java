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
 * Each contact record retrieved from the database (in other words, 
 * each record retrieved from the database's Contacts table) will be 
 * housed in an object of this class. 
 */
public class Contacts {
    
    /**
     * The ID number of the current contact. 
     */
    private int contactID;
    /**
     * The full name of the current contact. 
     */
    private String contactName;
    /**
     * The email address of the current contact. 
     */
    private String contactEmailAddress;
    
    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the Contacts class; upon the 
     * creation of a Contacts object, this method sets the variables 
     * (fields) of that Contacts object to be equal to the given 
     * parameters. 
     * @param contactID The ID number of the new contact
     * @param contactName The full name of the new contact
     * @param contactEmailAddress The email address of the new contact
     */
    public Contacts(int contactID, String contactName, String contactEmailAddress) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmailAddress = contactEmailAddress;
    }
    
    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * The accessor (getter) method for the "contactID" variable of this 
     * class. 
     * @return Returns the ID number of the current contact
     */
    public int getContactID() {
        return contactID;
    }
    
    /**
     * The accessor (getter) method for the "contactName" variable of this 
     * class. 
     * @return Returns the full name of the current contact
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * The accessor (getter) method for the "contactEmailAddress" variable 
     * of this class. 
     * @return Returns the email address of the current contact
     */
    public String getContactEmailAddress() {
        return contactEmailAddress;
    }
    
    //--------------------------------------------------------------------------
    //-------------------------------- OTHER -----------------------------------
    //--------------------------------------------------------------------------
    
    //Here, I am overriding the default toString() function that automatically comes with all objects. 
    //This is because the toString() function (the default one, if you don’t create an overriding definition) 
    //is used to print each item in a combo box (that is, each object of the Observable List that the combo 
    //box has been tied to). And you usually don’t want that, because the default toString() function is 
    //kind of weird and the output isn’t very legible or helpful.
    /**
     * This method overrides the default toString() method that 
     * automatically comes with all objects. The toString() method 
     * determines how an object is displayed in a combo box; thus, since 
     * there are places in the application where Contacts objects are 
     * displayed as choices in a combo box (on the Add Appointment and 
     * Modify Appointment screens), this method is necessary in order 
     * to make those Contacts objects human-readable in the combo boxes.
     * @return Returns the preferred output for the current Contacts object
     */
    @Override
    public String toString(){
        return ("ID: #" + Integer.toString(contactID) + " - " + contactName);
    }
    
}
