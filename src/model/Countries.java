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
 * Each country record retrieved from the database (in other words, 
 * each record retrieved from the database's Countries table) will be 
 * housed in an object of this class. 
 */
public class Countries {
    /**
     * The ID number of the current country. 
     */
    private int countryID;
    /**
     * The name of the current country. 
     */
    private String countryName;
    
    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the Countries class; upon the 
     * creation of a Countries object, this method sets the variables 
     * (fields) of that Countries object to be equal to the given 
     * parameters. 
     * @param countryID The ID number of the new country
     * @param countryName The name of the new country
     */
    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }
    
    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * The accessor (getter) method for the "countryID" variable of this 
     * class. 
     * @return Returns the ID number of the current country
     */
    public int getCountryID() {
        return countryID;
    }
    
    /**
     * The accessor (getter) method for the "countryName" variable of this 
     * class. 
     * @return Returns the name of the current country
     */
    public String getCountryName() {
        return countryName;
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
     * there are places in the application where Countries objects are 
     * displayed as choices in a combo box (on the Add Customer and 
     * Modify Customer screens), this method is necessary in order 
     * to make those Countries objects human-readable in the combo boxes.
     * @return Returns the preferred output for the current Countries object
     */
    @Override
    public String toString(){
        return countryName;
    }
    
}
