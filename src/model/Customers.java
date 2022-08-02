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
 * Each customer record retrieved from the database (in other words, 
 * each record retrieved from the database's Customers table) will be 
 * housed in an object of this class. 
 */
public class Customers {
    /**
     * The customer ID of this customer. 
     */
    private int customerID;
    /**
     * The full name of this customer. 
     */
    private String customerName;
    /**
     * The street address of this customer. 
     */
    private String streetAddress;
    /**
     * The postal code of this customer. 
     */
    private String postalCode;
    /**
     * The phone number of this customer. 
     */
    private String phoneNumber;
    /**
     * The division ID of the first-level division (as in divisions of 
     * a country) where this customer resides. 
     */
    private int divisionOfResidenceID;
    /**
     * The name of the first-level division (as in divisions of 
     * a country) where this customer resides. 
     */
    private String divisionOfResidence;
    /**
     * The country ID of the country where this customer resides. 
     */
    private int countryOfResidenceID;
    /**
     * The name of the country where this customer resides. 
     */
    private String countryOfResidence;
    
    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the Customers class; upon the 
     * creation of a Customers object, this method sets the variables 
     * (fields) of that Customers object to be equal to the given 
     * parameters. 
     * @param customerID The customer ID of the new customer
     * @param customerName The full name of the new customer
     * @param streetAddress The street address of the new customer
     * @param postalCode The postal code of the new customer
     * @param phoneNumber The phone number of the new customer
     * @param divisionOfResidenceID The division ID of the first-level 
     * division (as in divisions of a country) where this new customer resides
     * @param divisionOfResidence The name of the first-level 
     * division (as in divisions of a country) where this new customer resides
     * @param countryOfResidenceID The country ID of the country where 
     * this new customer resides
     * @param countryOfResidence The name of the country where 
     * this new customer resides
     */
    public Customers(int customerID, String customerName, String streetAddress, 
                     String postalCode, String phoneNumber, int divisionOfResidenceID, 
                     String divisionOfResidence, int countryOfResidenceID, String countryOfResidence) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionOfResidenceID = divisionOfResidenceID;
        this.divisionOfResidence = divisionOfResidence;
        this.countryOfResidenceID = countryOfResidenceID;
        this.countryOfResidence = countryOfResidence;
    }
    
    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * The accessor (getter) method for the "customerID" variable of this 
     * class. 
     * @return Returns the ID of this customer
     */
    public int getCustomerID() {
        return customerID;
    }
    
    /**
     * The accessor (getter) method for the "customerName" variable of this 
     * class. 
     * @return Returns the full name of this customer
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * The accessor (getter) method for the "streetAddress" variable of this 
     * class. 
     * @return Returns the street address of this customer
     */
    public String getStreetAddress() {
        return streetAddress;
    }
    
    /**
     * The accessor (getter) method for the "postalCode" variable of this 
     * class. 
     * @return Returns the postal code of this customer
     */
    public String getPostalCode() {
        return postalCode;
    }
    
    /**
     * The accessor (getter) method for the "phoneNumber" variable of this 
     * class. 
     * @return Returns the phone number of this customer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * The accessor (getter) method for the "divisionOfResidenceID" 
     * variable of this class. 
     * @return Returns the division ID of the first-level division 
     * (as in divisions of a country) where this customer resides
     */
    public int getDivisionOfResidenceID() {
        return divisionOfResidenceID;
    }
    
    /**
     * The accessor (getter) method for the "divisionOfResidence" 
     * variable of this class. 
     * @return Returns the name of the first-level division 
     * (as in divisions of a country) where this customer resides
     */
    public String getDivisionOfResidence() {
        return divisionOfResidence;
    }
    
    /**
     * The accessor (getter) method for the "countryOfResidenceID" 
     * variable of this class. 
     * @return Returns the country ID of the country where this 
     * customer resides
     */
    public int getCountryOfResidenceID() {
        return countryOfResidenceID;
    }
    
    /**
     * The accessor (getter) method for the "countryOfResidence" 
     * variable of this class. 
     * @return Returns the name of the country where this 
     * customer resides
     */
    public String getCountryOfResidence() {
        return countryOfResidence;
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
     * there are places in the application where Customers objects are 
     * displayed as choices in a combo box (on the Add Appointment and 
     * Modify Appointment screens), this method is necessary in order 
     * to make those Customers objects human-readable in the combo boxes.
     * @return Returns the preferred output for the current Customers object
     */
    @Override
    public String toString(){
        return ("ID: #" + Integer.toString(customerID) + " - " + customerName);
    }
    
}
