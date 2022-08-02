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
 * Each division record retrieved from the database (in other words, 
 * each record retrieved from the database's FirstLevelDivisions table) will be 
 * housed in an object of this class. 
 */
public class FirstLevelDivisions {
    
    /**
     * The ID number of the current division. 
     */
    private int divisionID;
    /**
     * The name of the current division. 
     */
    private String divisionName;
    /**
     * The country ID of the country that the current division is part of. 
     */
    private int idOfHostCountry;
    
    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the FirstLevelDivisions class; upon the 
     * creation of a FirstLevelDivisions object, this method sets the variables 
     * (fields) of that FirstLevelDivisions object to be equal to the given 
     * parameters. 
     * @param divisionID The ID number of the new division
     * @param divisionName The name of the new division
     * @param idOfHostCountry The country ID of the country that the new 
     * division is part of
     */
    public FirstLevelDivisions(int divisionID, String divisionName, int idOfHostCountry) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.idOfHostCountry = idOfHostCountry;
    }
    
    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * The accessor (getter) method for the "divisionID" variable of this 
     * class. 
     * @return Returns the ID number of the current division
     */
    public int getDivisionID() {
        return divisionID;
    }
    
     /**
     * The accessor (getter) method for the "divisionName" variable of this 
     * class. 
     * @return Returns the name of the current division
     */
    public String getDivisionName() {
        return divisionName;
    }
    
    /**
     * The accessor (getter) method for the "idOfHostCountry" variable of this 
     * class. 
     * @return Returns the country ID of the country that the 
     * current division is part of
     */
    public int getIdOfHostCountry() {
        return idOfHostCountry;
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
     * there are places in the application where FirstLevelDivisions objects 
     * are displayed as choices in a combo box (on the Add Customer and 
     * Modify Customer screens), this method is necessary in order 
     * to make those FirstLevelDivisions objects human-readable in the 
     * combo boxes.
     * @return Returns the preferred output for the current 
     * FirstLevelDivisions object
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
