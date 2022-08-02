/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataHolder;
import model.Users;

/**
 * This is an FXML Controller class which provides functionality to the Login
 * Screen (the Login Screen FXML file). 
 *
 * @author Alano Peirce
 */
public class LoginScreenController implements Initializable {
    /**
     * Text field where the user inputs the username of the account that they 
     * wish to log in to the application with. 
     */
    @FXML
    private TextField usernameTextField;
    /**
     * Text field where the user inputs the password of the account that they 
     * wish to log in to the application with. 
     */
    @FXML
    private PasswordField passwordTextField;
    /**
     * This is the header displayed on the Login Screen; it reads "SIGN IN". 
     */
    @FXML
    private Label signInLabel;
    /**
     * This is the label that immediately precedes the label that displays the 
     * user's time zone; this label explains what will appear on the label that 
     * follows. 
     */
    @FXML
    private Label timeZoneHeaderLabel;
    /**
     * This is the label that displays the name of the user's current time zone 
     * (in two different formats).
     */
    @FXML
    private Label timeZoneLabel;
    /**
     * This is the label that precedes the username text field; it simply reads 
     * "USERNAME". 
     */
    @FXML
    private Label usernameLabel;
    /**
     * This is the label that precedes the password text field; it simply reads 
     * "PASSWORD". 
     */
    @FXML
    private Label passwordLabel;
    /**
     * This is the button that users click in order to sign in (after they have 
     * entered their username and password). 
     */
    @FXML
    private Button signInButton;
    
    /**
     * Denotes whether the local language is French or a different language. If 
     * this variable is true, the application gets translated to French; 
     * otherwise (if this variable is false), the application remains in 
     * English. 
     */
    private boolean localLanguageIsFrench = false;
    
    //"DataHolder" is my own class that I created. I wrote a method (function) 
    //there that can be used to switch to a different scene.
    /**
     * An instantiation of the DataHolder class; this is necessary in order to 
     * call the methods that allow the application to switch to a different 
     * scene, as those methods cannot be static (and thus, an instance of the 
     * class is required in order to call those methods). 
     */
    DataHolder methodsLibrary = new DataHolder();
    
    
    
    
    //This is a functional interface, which is kind of like a class with an abstract method 
    //(a method without a body, just a header). I can give the method a body by creating an object 
    //of this functional interface and then overriding the method within that object using a 
    //lambda expression. (In the process of overriding the method, I give it a body [meaning defining what the method will do] 
    //using the lambda expression.)
    //To see this in action (and to see where in the code I use this functional interface), 
    //go to the initialize() method (which is just under this functional interface).
    /**
     * This functional interface contains an abstract method that will be 
     * overridden (in other words, given a body) at a later point (in the 
     * "initialize()" method). 
     */
    interface TimeZoneLabelSetter{
        /**
         * This method is an abstract method that currently does not do 
         * anything (does not have a body); however, it will be overridden (in 
         * the "initialize()" method) such that it has the ability to set the 
         * Time Zone label to the user's current time zone (in the appropriate 
         * language). 
         * @param zoneIDofUser The zone ID of the current user
         * @param localeOfUser the Locale of the current user
         * @param localLanguageOfUserIsFrench "true" if the default language of 
         * the user's locale is French; "false" if the default language of the 
         * user's locale is any other language
         */
        void setTimeZoneLabelInAppropriateLanguage(ZoneId zoneIDofUser, Locale localeOfUser, boolean localLanguageOfUserIsFrench);
    }
    
    
    
    //This method is basically a controller file's equivalent to the "main()" method.
    /**
     * Initializes the controller class. This is essentially the controller's 
     * equivalent of the "main()" method. This method determines the ZoneId, 
     * Locale, and local language (based on the retrieved Locale) of the 
     * current user; using this information, this method sets the Time Zone 
     * label to display the user's current time zone in either English or 
     * French, and translates the other contents of the scene to French if 
     * appropriate (or retains the original English of the scene).
     * -------------------------------------------------------------------------
     * This method is the location of the first LAMBDA EXPRESSION (see line 205 
     * for the actual lambda expression, see line 228 for the place where the 
     * overridden method [a.k.a. the method that the lambda expression overrode 
     * / gave a body to] is called, and see line 128 for the functional 
     * interface whose abstract method gets overridden by the lambda 
     * expression). The lambda expression takes in (as parameters) the 
     * ZoneId of the user, the locale of the user, and a boolean 
     * representing whether or not the local language of the user is French. 
     * Using this information, it sets the Login Screen's time zone label 
     * to the user's time zone in the appropriate language.
     * -------------------------------------------------------------------------
     * JUSTIFICATION: This lambda is especially useful because it allows 
     * abstraction and simplification of the code, which allows for easy reuse 
     * of the code. This provides the potential for future implementation of 
     * Time Zone labels application-wide (i.e. putting a Time Zone label on 
     * every scene of the application in order to better remind the user that 
     * all times and dates in the application are presented in the user's local 
     * time zone). Future developers would simply be able to call the lambda 
     * expression to set each time zone label instead of having to manually 
     * apply code to set each time zone label.
     * -------------------------------------------------------------------------
     * In addition, the lambda expression allows for the easy future 
     * implementation of other languages. In the case that the capability for 
     * other languages (besides English and French) is added to this 
     * application in the future, the future developers would simply have to 
     * add onto the code in one place (i.e. the lambda expression), and then 
     * they would be able to use that lambda expression for all instances of 
     * a time zone label.
     * 
     * @param url Default parameter
     * 
     * @param rb Default parameter
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Here, we get two different names for the same time zone (the local time zone of the current user).
        ZoneId localZoneID = ZoneId.systemDefault();
        
        //Here, we find out where the current user is located. From that, we can figure out their local language.
        Locale localeOfCurrentUser = DataHolder.getLocaleOfCurrentUser();
        String localLanguage = localeOfCurrentUser.getDisplayLanguage();
        
        //This is the lambda expression that I use to override the method in the functional interface up above 
        //(the functional interface is defined above the beginning of the initialize() method).
        //I first create an object of the functional interface, and then I override the method within that object and 
        //give it a body (a.k.a. I give some actual functionality to the method, like it actually does something now).
        TimeZoneLabelSetter timeZoneLabelSetter = (zoneIDofUser, localeOfUser, localLanguageOfUserIsFrench) -> {
            
            if(localLanguageOfUserIsFrench){
                timeZoneLabel.setText(zoneIDofUser.getDisplayName(TextStyle.FULL, localeOfUser) + " (" + zoneIDofUser + ")");
            } else {
                timeZoneLabel.setText(zoneIDofUser.getDisplayName(TextStyle.FULL, Locale.US) + " (" + zoneIDofUser + ")");
            }
        };
        
        //Here, we set everything on the Login Screen to French if the user's local language is French. Otherwise, we keep everything in English.
        if(localLanguage.equals("French") || localeOfCurrentUser.toString().startsWith("fr") || localeOfCurrentUser.getDisplayName().equals("français") ){
            
            localLanguageIsFrench = true;
            translateScreenToFrench();
            
        } else {
            
            localLanguageIsFrench = false;
        }
        
        //This is where I call the method that I previously overrode using a lambda expression. 
        //Go to the block of code that's right above the if-else statement above in order to 
        //view the body of this function (a.k.a. so you can see what this function is programmed to do).
        timeZoneLabelSetter.setTimeZoneLabelInAppropriateLanguage(localZoneID, localeOfCurrentUser, localLanguageIsFrench);
        
    }    
    /**
     * This method is called if the user's local language is determined to be 
     * French; it translates all components on the Login Screen to French.
     */
    private void translateScreenToFrench(){
        signInLabel.setText("LOGUER");
        timeZoneHeaderLabel.setText("Toutes les dates et heures de cette app sont présentées en");
        usernameLabel.setText("NOM D'UTILISATEUR");
        passwordLabel.setText("MOT DE PASSE");
        signInButton.setText("Loguer");
    }
    
    
    /**
     * This method displays the application's Main Menu upon the clicking of 
     * the "Sign In" button (if the user's username and password combination 
     * are valid); otherwise, an error dialog box is presented to the user 
     * and the Main Menu is not shown. If the user's input username is the 
     * username of an existing account, but the user's password is incorrect, 
     * the user is informed of the situation. Otherwise, if the user's username 
     * is not recognized, the user is also informed of this. Additionally, if 
     * the user does not enter a username or password (or both), the user is 
     * prompted to provide the missing input.
     * -------------------------------------------------------------------------
     * Upon successfully logging into the application, this method calls the 
     * "displayUpcomingAppointmentsOfUser()" method (which is defined in the 
     * Main Menu controller) and passes into that method the user ID of the 
     * user that just logged in as well as a boolean indicating whether or not 
     * the user's local language is French. The reason that the 
     * "displayUpcomingAppointmentsOfUser()" method is declared in the Main 
     * Menu controller instead of this controller (the Login Screen Controller) 
     * is so that the dialog box that details the user's upcoming appointments 
     * displays on the Main Menu instead of the Login Screen.
     * -------------------------------------------------------------------------
     * NOTE: The called method executes before the Main Menu's "initialize()" 
     * method (a.k.a. the main method of a controller) does; thus, since the 
     * initialize() method is the one that determines the local language of the 
     * user, the "displayUpcomingAppointmentsOfUser()" method will not have an 
     * accurate gauge of whether or not the user's local language is French. 
     * To combat this, the current method has to pass in a boolean (indicating 
     * the user's language) from the Login Screen controller (the current 
     * controller), and since this boolean was initialized in the Login Screen 
     * controller's "initialize()" method, it will provide an accurate gauge 
     * as to whether or not the user's local language is French.
     * 
     * @param event The ActionEvent that occurred; in this case, the "Sign In" 
     * button was clicked.
     * 
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene)
     */
    @FXML
    private void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        
        //Here, we retrieve the user input from the Username text field as well as the Password text field.
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        boolean usernameAndPasswordComboIsValid = false;
        boolean usernameIsValidButPasswordIsInvalid = false;
        
        Users loggedInUser = null;
        
        //This for loop goes through the Observable List of all users and checks to see
        //if the user-input username and password combination matches up with a username-password 
        //combo in the Observable List. If it does, the boolean variable gets set to true; 
        //otherwise, the boolean variable remains false.
        for(Users user : DataHolder.getListOfAllUsers()){
            
            //To compare strings, you MUST USE THE METHOD "equals()"!! You CANNOT use "==" 
            //to compare strings in Java! That's because "==" compares references (meaning 
            //it checks to see if the two strings are literally the exact same string object).
            if( username.equals(user.getUsername()) && password.equals(user.getPassword()) ){
               
                usernameAndPasswordComboIsValid = true;
                
                loggedInUser = user;
                
            } else if ( username.equals(user.getUsername()) && !password.equals(user.getPassword()) ){
                
                usernameIsValidButPasswordIsInvalid = true;
            }
        }
        
        
        //Here, we only switch to the next screen if the username and password combo is valid.
        //Otherwise, we output descriptive errors for different types of invalid input.
        if(usernameAndPasswordComboIsValid){
            
            //We have to create an object of the FXMLLoader class, because setLocation() and load()
            //are both instance methods (you have to call them from an object) (a.k.a. they aren't 
            //static methods [static methods belong to the class instead of each individual object, 
            //meaning you can call them without ever creating an object from the class]).
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
            loader.load();
            
            //Here, we are creating an object from the MainMenyController class, and we 
            //initialize it with the specific controller instance being used to control the 
            //specific screen instance that's loaded up in the loader.
            MainMenuController mainMenuController = loader.getController();
            //Now, we get the user ID of the user that just logged in, and then we input 
            //that as a parameter when we call the displayUpcomingAppointmentsOfUser() 
            //method (which is defined in the MainMenuController). This method, as the 
            //name suggests, displays a dialog box that either lists the user's upcoming 
            //appointment(s) (appointments that start within the next fifteen minutes) or 
            //lets the user know that there are no upcoming appointments.
            mainMenuController.displayUpcomingAppointmentsOfUser(loggedInUser.getUserID(), localLanguageIsFrench);
            
            methodsLibrary.switchToDifferentSceneAndSendDataToThatSceneUsingButton(loader, event);
            
        } else if ( usernameIsValidButPasswordIsInvalid && !password.equals("") ){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe mot de passe que vous avez entré est incorrect.\n\n");
                alert.setTitle("Mot de passe incorrect");
                alert.setHeaderText("Veuillez réessayer");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe password you entered is incorrect.\n\n");
                alert.setTitle("Incorrect Password");
                alert.setHeaderText("Please try again");
                alert.showAndWait();
            }
            
        } else if (username.equals("") && password.equals("")){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez entrer un nom d'utilisateur et un mot de passe.\n\n");
                alert.setHeaderText("Erreur");
                alert.setTitle("Identifiants manquants");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a username and password.\n\n");
                alert.setTitle("Missing Credentials");
                alert.showAndWait();
            }
            
        } else if (username.equals("")){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nMerci d'entrer un nom d'utilisateur.\n\n");
                alert.setHeaderText("Erreur");
                alert.setTitle("Nom d'utilisateur manquant");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a username.\n\n");
                alert.setTitle("Missing Username");
                alert.showAndWait();
            }
            
        } else if (password.equals("")){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez entrer un mot de passe.\n\n");
                alert.setTitle("Mot de passe manquant");
                alert.setHeaderText("Erreur");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a password.\n\n");
                alert.setTitle("Missing Password");
                alert.showAndWait(); 
            }
            
        } else {
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe nom d'utilisateur que vous avez entré n'a pas été reconnu.\n\n");
                alert.setTitle("Nom d'utilisateur non reconnu");
                alert.setHeaderText("Veuillez réessayer");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe username you entered was not recognized.\n\n");
                alert.setTitle("Username Not Recognized");
                alert.setHeaderText("Please try again");
                alert.showAndWait();
            }
            
        }
        
        //This block of code takes details about all login attempts (that use the "Sign In" button  
        //to attempt a login [versus the "Enter" key] ) and outputs them to a record called 
        //"login_activity.txt".
        try
        {
            //Allows us to *append* a file that already exists (and has the given name)
            //instead of *creating* a whole new file every time the program is run.
            //(The "true" denotes that we want to be able to append the file instead of 
            //creating a new file every time the program is run).
            FileWriter appendToEndOfLoginAttemptsFile = new FileWriter("login_activity.txt", true);
            
            if(usernameAndPasswordComboIsValid){
                
                appendToEndOfLoginAttemptsFile.write(" • A successful login attempt was detected.\n\n");
                
            } else {
                
                appendToEndOfLoginAttemptsFile.write(" • An unsuccessful login attempt was detected.\n\n");
            }
                        
            LocalDateTime currentDateTimeInUTC = LocalDateTime.now(ZoneOffset.UTC);
            
            appendToEndOfLoginAttemptsFile.write("     ◦ Timestamp:  " + currentDateTimeInUTC.toLocalDate().toString() + " " + 
                                                                        currentDateTimeInUTC.toLocalTime().toString() + " UTC" + 
                                                 "\n\n     ◦ Username used:  " + username + "\n\n\n");
            
            appendToEndOfLoginAttemptsFile.close(); //Here, we close the output connection the file.
            
        }
        catch (IOException ex) {
            
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /* This method pretty much does the exact same thing as the previous method ( onActionDisplayMainMenu() ), 
       except it executes whenever the user hits the "Enter" key from either the Username text field or the 
       Password text field (versus the previous method, which executes when the "Sign in" button is pressed). 
       However, I had to copy the contents of the previous method into this method (instead of just calling 
       that method from this one) because of one small difference - to switch scenes in the last method, I 
       called the method (which I created) that allows you to switch scenes via an action done to a button 
       ( "switchToDifferentSceneUsingButton()" ); however, in this method, I had to call the method that 
       allows you to switch scenes via an action done to a text field ( "switchToDifferentSceneUsingTextField()" ).
       
       Due to the name of the name of this method ( "onEnter()" ), the program / JavaFX automatically knows that 
       I want this method to execute when enter is pressed (specifically, when enter is pressed while in/on the 
       text fields / controls that I hooked up to this method (by assigning the onAction label of "onEnter" to 
       those controls via SceneBuilder).
    */
    /**
     * This method displays the application's Main Menu upon the user pressing 
     * their "Enter" key while the cursor is either in the username text field 
     * or the password text field (assuming the user's username and password 
     * combination are valid); otherwise, if the combination is not valid, an 
     * error dialog box is presented to the user and the Main Menu is not 
     * shown. If the user's input username is the username of an existing 
     * account, but the user's password is incorrect, the user is informed of 
     * the situation. Otherwise, if the user's username is not recognized, 
     * the user is also informed of this. Additionally, if the user does not 
     * enter a username or password (or both), the user is prompted to provide 
     * the missing input.
     * 
     * Upon successfully logging into the application, this method calls the 
     * "displayUpcomingAppointmentsOfUser()" method (which is defined in the 
     * Main Menu controller) and passes into that method the user ID of the 
     * user that just logged in as well as a boolean indicating whether or not 
     * the user's local language is French. The reason that the 
     * "displayUpcomingAppointmentsOfUser()" method is declared in the Main 
     * Menu controller instead of this controller (the Login Screen Controller) 
     * is so that the dialog box that details the user's upcoming appointments 
     * displays on the Main Menu instead of the Login Screen.
     * 
     * NOTE: The called method executes before the Main Menu's "initialize()" 
     * method (a.k.a. the main method of a controller) does; thus, since the 
     * initialize() method is the one that determines the local language of the 
     * user, the "displayUpcomingAppointmentsOfUser()" method will not have an 
     * accurate gauge of whether or not the user's local language is French. 
     * To combat this, the current method has to pass in a boolean (indicating 
     * the user's language) from the Login Screen controller (the current 
     * controller), and since this boolean was initialized in the Login Screen 
     * controller's "initialize()" method, it will provide an accurate gauge 
     * as to whether or not the user's local language is French.
     * 
     * @param event The ActionEvent that occurred; in this case, the "Enter" 
     * key was pressed while the cursor was in the username text field or the 
     * password text field.
     * 
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene)
     */
    @FXML
    void onEnter(ActionEvent event) throws IOException {
        
        //Here, we retrieve the user input from the Username text field as well as the Password text field.
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        
        boolean usernameAndPasswordComboIsValid = false;
        boolean usernameIsValidButPasswordIsInvalid = false;
        
        Users loggedInUser = null;
        
        //This for loop goes through the Observable List of all users and checks to see
        //if the user-input username and password combination matches up with a username-password 
        //combo in the Observable List. If it does, the boolean variable gets set to true; 
        //otherwise, the boolean variable remains false.
        for(Users user : DataHolder.getListOfAllUsers()){
            
            //To compare strings, you MUST USE THE METHOD "equals()"!! You CANNOT use "==" 
            //to compare strings in Java! That's because "==" compares references (meaning 
            //it checks to see if the two strings are literally the exact same string object).
            if( username.equals(user.getUsername()) && password.equals(user.getPassword()) ){
               
                usernameAndPasswordComboIsValid = true;
                
                loggedInUser = user;
                
            } else if ( username.equals(user.getUsername()) && !password.equals(user.getPassword()) ){
                
                usernameIsValidButPasswordIsInvalid = true;
            }
        }
        
        
        //Here, we only switch to the next screen if the username and password combo is valid.
        //Otherwise, we output descriptive errors for different types of invalid input.
        if(usernameAndPasswordComboIsValid){
            
            //We have to create an object of the FXMLLoader class, because setLocation() and load()
            //are both instance methods (you have to call them from an object) (a.k.a. they aren't 
            //static methods [static methods belong to the class instead of each individual object, 
            //meaning you can call them without ever creating an object from the class]).
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainMenu.fxml"));
            loader.load();
            
            //Here, we are creating an object from the MainMenyController class, and we 
            //initialize it with the specific controller instance being used to control the 
            //specific screen instance that's loaded up in the loader.
            MainMenuController mainMenuController = loader.getController();
            //Now, we get the user ID of the user that just logged in, and then we input 
            //that as a parameter when we call the displayUpcomingAppointmentsOfUser() 
            //method (which is defined in the MainMenuController). This method, as the 
            //name suggests, displays a dialog box that either lists the user's upcoming 
            //appointment(s) (appointments that start within the next fifteen minutes) or 
            //lets the user know that there are no upcoming appointments.
            mainMenuController.displayUpcomingAppointmentsOfUser(loggedInUser.getUserID(), localLanguageIsFrench);
            
            methodsLibrary.switchToDifferentSceneAndSendDataToThatSceneUsingTextField(loader, event);
            
        } else if ( usernameIsValidButPasswordIsInvalid  && !password.equals("") ){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe mot de passe que vous avez entré est incorrect.\n\n");
                alert.setTitle("Mot de passe incorrect");
                alert.setHeaderText("Veuillez réessayer");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe password you entered is incorrect.\n\n");
                alert.setTitle("Incorrect Password");
                alert.setHeaderText("Please try again");
                alert.showAndWait();
            }
            
        } else if (username.equals("") && password.equals("")){
            
           if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez entrer un nom d'utilisateur et un mot de passe.\n\n");
                alert.setHeaderText("Erreur");
                alert.setTitle("Identifiants manquants");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a username and password.\n\n");
                alert.setTitle("Missing Credentials");
                alert.showAndWait();
            }
            
        } else if (username.equals("")){
            
           if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nMerci d'entrer un nom d'utilisateur.\n\n");
                alert.setHeaderText("Erreur");
                alert.setTitle("Nom d'utilisateur manquant");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a username.\n\n");
                alert.setTitle("Missing Username");
                alert.showAndWait();
            }
            
        } else if (password.equals("")){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez entrer un mot de passe.\n\n");
                alert.setTitle("Mot de passe manquant");
                alert.setHeaderText("Erreur");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease enter a password.\n\n");
                alert.setTitle("Missing Password");
                alert.showAndWait(); 
            }
            
        } else {
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe nom d'utilisateur que vous avez entré n'a pas été reconnu.\n\n");
                alert.setTitle("Nom d'utilisateur non reconnu");
                alert.setHeaderText("Veuillez réessayer");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe username you entered was not recognized.\n\n");
                alert.setTitle("Username Not Recognized");
                alert.setHeaderText("Please try again");
                alert.showAndWait();
            }
        }
        
        
        //This block of code takes details about all login attempts (that use the "Enter" key  
        //to attempt a login instead of the "Sign In" button) and outputs them to a record called 
        //"login_activity.txt".
        try
        {
            //Allows us to *append* a file that already exists (and has the given name)
            //instead of *creating* a whole new file every time the program is run.
            //(The "true" denotes that we want to be able to append the file instead of 
            //creating a new file every time the program is run).
            FileWriter appendToEndOfLoginAttemptsFile = new FileWriter("login_activity.txt", true);
            
            if(usernameAndPasswordComboIsValid){
                
                appendToEndOfLoginAttemptsFile.write(" • A successful login attempt was detected.\n\n");
                
            } else {
                
                appendToEndOfLoginAttemptsFile.write(" • An unsuccessful login attempt was detected.\n\n");
            }
                        
            LocalDateTime currentDateTimeInUTC = LocalDateTime.now(ZoneOffset.UTC);
            
            appendToEndOfLoginAttemptsFile.write("     ◦ Timestamp:  " + currentDateTimeInUTC.toLocalDate().toString() + " " + 
                                                                        currentDateTimeInUTC.toLocalTime().toString() + " UTC" + 
                                                 "\n\n     ◦ Username used:  " + username + "\n\n\n");
            
            appendToEndOfLoginAttemptsFile.close(); //Here, we close the output connection to the file.
            
        }
        catch (IOException ex) {
            
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    
}
