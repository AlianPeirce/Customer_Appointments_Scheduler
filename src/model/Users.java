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
 * Each user record retrieved from the database (in other words, 
 * each record retrieved from the database's Users table) will be 
 * housed in an object of this class. 
 */
public class Users {
    
    /**
     * The ID number of the current user. 
     */
    private int userID;
    /**
     * The username of the current user account. 
     */
    private String username;
    /**
     * The password for the current user account. 
     */
    private String password;

    //--------------------------------------------------------------------------
    //---------------------------- CONSTRUCTOR ---------------------------------
    //--------------------------------------------------------------------------
    
    /**
     * This is the constructor for the Users class; upon the 
     * creation of a Users object, this method sets the variables 
     * (fields) of that Users object to be equal to the given 
     * parameters. 
     * @param userID The ID number of the new user
     * @param username The username of the new user account
     * @param password The password for the new user account
     */
    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    //--------------------------------------------------------------------------
    //------------------------------- GETTERS ----------------------------------
    //--------------------------------------------------------------------------
    
   /**
     * The accessor (getter) method for the "userID" variable of this 
     * class. 
     * @return Returns the ID number of the current user
     */
    public int getUserID() {
        return userID;
    }
    
    /**
     * The accessor (getter) method for the "username" variable of this 
     * class. 
     * @return Returns the username of the current user
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * The accessor (getter) method for the "password" variable of this 
     * class. 
     * @return Returns the password of the current user
     */
    public String getPassword() {
        return password;
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
     * there are places in the application where Users objects 
     * are displayed as choices in a combo box (on the Add Appointment and 
     * Modify Appointment screens), this method is necessary in order 
     * to make those Users objects human-readable in the 
     * combo boxes.
     * @return Returns the preferred output for the current Users object
     */
    @Override
    public String toString(){
        return ("ID: #" + Integer.toString(userID) + " - " + username);
    }
    
}
