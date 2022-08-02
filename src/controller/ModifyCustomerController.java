/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database_CRUD.CustomersCRUD;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import model.Countries;
import model.Customers;
import model.DataHolder;
import model.FirstLevelDivisions;

/**
 * This is an FXML Controller class which provides functionality to the Modify 
 * Customer screen (the Modify Customer FXML file). 
 *
 * @author LabUser
 */
public class ModifyCustomerController implements Initializable {
    
    /**
     * This is the header of the Modify Customer page - it reads 
     * "MODIFY CUSTOMER". 
     */
    @FXML
    private Label modifyCustomerLabel;
    /**
     * This is the header text that precedes the customer ID text field. 
     */
    @FXML
    private Label identificationNumberLabel;
    /**
     * This is the text field that displays the ID of the customer that is 
     * currently being modified; however, the user cannot modify the 
     * contents of this text field, as customer IDs are auto-generated 
     * (by the database) and should not be modified by the user. 
     */
    @FXML
    private TextField custIdTextField;
    /**
     * This is the header text that precedes the customer name text field. 
     */
    @FXML
    private Label fullNameLabel;
    /**
     * This is the text field where the user can input a new name for 
     * the customer (if they wish to modify the name of the customer 
     * currently being modified). 
     */
    @FXML
    private TextField custNameTextField;
    /**
     * This is the header text that precedes the customer street address field. 
     */
    @FXML
    private Label streetAddressLabel;
    /**
     * This is the text field where the user can input a new street address for 
     * the customer (if they wish to modify the street address of the customer 
     * currently being modified). 
     */
    @FXML
    private TextField custStreetAddressTextField;
    /**
     * This is the header text that precedes the customer postal code field. 
     */
    @FXML
    private Label postalCodeLabel;
    /**
     * This is the text field where the user can input a new postal code for 
     * the customer (if they wish to modify the postal code of the customer 
     * currently being modified). 
     */
    @FXML
    private TextField custPostalCodeTextField;
    /**
     * This is the header text that precedes the customer phone number field. 
     */
    @FXML
    private Label phoneNumberLabel;
    /**
     * This is the text field where the user can input a new phone number for 
     * the customer (if they wish to modify the phone number of the customer 
     * currently being modified). 
     */
    @FXML
    private TextField custPhoneNumberTextField;
    /**
     * This is the header text that precedes the combo box used to select 
     * the customer's country of residence. 
     */
    @FXML
    private Label countryLabel;
    /**
     * A combo box (drop-down menu) used to select the customer's 
     * country of residence. 
     */
    @FXML
    private ComboBox<Countries> custCountryComboBox;
    /**
     * This is the header text that precedes the combo box used to select 
     * the customer's division of residence (the first-level division of 
     * the customer's country in which the customer resides). 
     */
    @FXML
    private Label divisionLabel;
    /**
     * A combo box (drop-down menu) used to select the customer's 
     * division of residence (the first-level division of 
     * the customer's country in which the customer resides). 
     */
    @FXML
    private ComboBox<FirstLevelDivisions> custDivisionComboBox;
    /**
     * The button that updates the database record of the customer that is 
     * currently being modified (by replacing the old details of the 
     * customer record with the new user input); this button also brings the 
     * user back to the Customers Table screen. 
     */
    @FXML
    private Button updateButton;
    /**
     * Brings the user back to the Customers Table screen (if the user 
     * clicks the button) without saving any of the user input (from the 
     * Modify Customer screen). 
     */
    @FXML
    private Button backButton;
    /**
     * Brings the user back to the Login Screen (if the user 
     * clicks the button) without saving any of the user input (from the 
     * Modify Customer screen). 
     */
    @FXML
    private Button signOutButton;
    
    /**
     * Denotes whether the local language is French or a different language. If 
     * this variable is true, the application gets translated to French; 
     * otherwise (if this variable is false), the application remains in 
     * English. 
     */
    private boolean localLanguageIsFrench = false;
    
    /**
     * An instantiation of the DataHolder class; this is necessary in order to 
     * call the methods that allow the application to switch to a different 
     * scene, as those methods cannot be static (and thus, an instance of the 
     * class is required in order to call those methods). 
     */
    //"DataHolder" is my own class that I created. I wrote a method (function) 
    //there that can be used to switch to a different scene.
    DataHolder methodsLibrary = new DataHolder();
    
    
    
    
    //This method is basically a controller file's equivalent to the "main()" method. 
    /**
     * Initializes the controller class. This method links an Observable List 
     * to the country combo box. The objects in this Observable List are 
     * what will be displayed in the combo box. 
     * Additionally, this method determines the ZoneId, Locale, and local 
     * language (based on the retrieved Locale) of the current user; using 
     * this information, this method decides whether or not to translate 
     * the Modify Customer screen to French.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        custCountryComboBox.setItems(DataHolder.getListOfAllCountries());
        
        //-------------------------------------------
        
        //Here, we find out where the current user is located. From that, we can figure out their local language.
        Locale localeOfCurrentUser = DataHolder.getLocaleOfCurrentUser();
        String localLanguage = localeOfCurrentUser.getDisplayLanguage().toString();
        
        //Here, we set everything on the Login Screen to French if the user's local language is French. Otherwise, we keep everything in English.
        if(localLanguage.equals("French") || localeOfCurrentUser.toString().startsWith("fr") || localeOfCurrentUser.getDisplayName().equals("français") ){
            
            localLanguageIsFrench = true;
            translateScreenToFrench();
        }
        
    }    
    
    
    
    
    /* This method sets all of the input fields (text fields, combo boxes, etc.) 
       on the "Modify Customer" screen to reflect the values of the row in the Customers 
       TableView that the user selected. Thus, this method is called from the 
       Customers Table Controller, where the row (object) that the user selected is 
       retrieved and then fed into this function as the parameter. 
    */
    /**
     * This method sets all of the input fields (text fields, combo boxes, 
     * etc.) on the Modify Customer screen to reflect the 
     * values of the customer record that the user selected in the  
     * Customers TableView (while they were still on the Customers Table 
     * screen, prior to clicking the "Modify" button on that same screen). 
     * Thus, this method is called from the Customers Table Controller, 
     * where the customer record (a.k.a. Customers object) that the user 
     * selected is retrieved and then fed into this function as the parameter.
     * @param selectedCustomer The customer (a.k.a. Customers 
     * object) that the user selected in the Customers TableView 
     * (on the Customers Table screen) prior to clicking the "Modify" 
     * button (also on the Customers Table screen). (That "Modify" button 
     * is what brought the user onto the current screen [the Modify 
     * Customer screen]).
     */
    public void setInputFieldsToContentOfSelectedCustomer(Customers selectedCustomer){
        
        custIdTextField.setText( String.valueOf(selectedCustomer.getCustomerID()) );
        custNameTextField.setText(selectedCustomer.getCustomerName());
        custStreetAddressTextField.setText(selectedCustomer.getStreetAddress());
        custPostalCodeTextField.setText(selectedCustomer.getPostalCode());
        custPhoneNumberTextField.setText(selectedCustomer.getPhoneNumber());
        
        
        //-----------------------------------------------------------------------------------------
        
        //In this block of code, we are running through the list of countries to see which country
        //has an ID that matches up with the country ID of the customer. Once we figure out which 
        //country object (technically, object of type Countries) matches up, we set the value of 
        //the country combo box to be that country object (object of type "Countries").
        
        Countries countryOfSelectedCustomer = null;  //I had to initialize this to something
        
        for(Countries country: DataHolder.getListOfAllCountries()){
            
            if(country.getCountryID() == selectedCustomer.getCountryOfResidenceID()){
                
                countryOfSelectedCustomer = country;
            }
        }
        
        custCountryComboBox.setValue(countryOfSelectedCustomer);
        
        
        //-------------------------------------------------------------------------------------------
        
        //In this block of code, we get a list of the divisions of the customer's country. Then,  
        //we run through the list of divisions to see which division has an ID that matches up with 
        //the division ID of the customer. Once we figure out which division object (technically, 
        //object of type FirstLevelDivisions) matches up, we set the value of the division combo box 
        //to be that division object (FirstLevelDivisions object).
        
        FirstLevelDivisions divisionOfSelectedCustomer = null;  //I had to initialize this to something
        ObservableList<FirstLevelDivisions> listOfAllDivisionsOfSelectedCustomersCountry = DataHolder.getListOfAllDivisionsOfACertainCountry(countryOfSelectedCustomer.getCountryID());
        
        for( FirstLevelDivisions division: listOfAllDivisionsOfSelectedCustomersCountry){
            
            if(division.getDivisionID() == selectedCustomer.getDivisionOfResidenceID()){
                
                divisionOfSelectedCustomer = division;
            }
        }
        
        custDivisionComboBox.setValue(divisionOfSelectedCustomer);
        custDivisionComboBox.setItems(listOfAllDivisionsOfSelectedCustomersCountry);
        
        
    }
    
    
    
    /**
     * This method is called if the user's local language is determined to be 
     * French; it translates all components on the Modify Customer screen 
     * to French. 
     */
    private void translateScreenToFrench(){
        modifyCustomerLabel.setText(" MODIFIER LE CLIENT");
        backButton.setText("Retourner");
        backButton.setFont(new Font("Segoe UI Semibold", 13));
        signOutButton.setText(" Déloguer");
        identificationNumberLabel.setText("Numéro d'identification");
        custIdTextField.setPromptText("Genere automatiquement");
        fullNameLabel.setText("Nom et prénom");
        streetAddressLabel.setText("Adresse de la rue");
        postalCodeLabel.setText("Code postal");
        phoneNumberLabel.setText("Numéro de téléphone");
        countryLabel.setText("De campagne");
        custCountryComboBox.setPromptText("Veuillez sélectionner un pays");
        divisionLabel.setText("Division");
        custDivisionComboBox.setPromptText("Veuillez sélectionner une division");
        updateButton.setText("Réactualiser");
    }
    
    
    
    
    //Once a country is selected (in the country combo box), populate the division combo box 
    //with all divisions of the selected country.
    /**
     * This method is called when the user selects a country from the country 
     * combo box; it populates the division combo box with the first-level 
     * divisions of that selected country. 
     * @param event The ActionEvent that occurred; in this case, a selection 
     * was made in the country combo box.
     */
    @FXML
    void onActionDisplayDivisionsOfSelectedCountry(ActionEvent event) {
        
        //In this next line, we are setting the selected value of the Division combo box
        //to be null (nothing) in case the user had already selected a division in the 
        //division combo box before changing their selection in the Country combo box 
        //(the latter of which would have activated this function we're currently in).
        custDivisionComboBox.valueProperty().set(null);
        custDivisionComboBox.setPromptText("Please select a division");
        
        //For this next line, you could also use ".getValue()" instead of ".getSelectionModel().getSelectedItem()".
        Countries selectedCountry = custCountryComboBox.getSelectionModel().getSelectedItem();
        int idOfSelectedCountry = selectedCountry.getCountryID();
        
        custDivisionComboBox.setItems(DataHolder.getListOfAllDivisionsOfACertainCountry(idOfSelectedCountry));
        custDivisionComboBox.setVisibleRowCount(7);
    }
    
    
    
     /**
     * This method is called when the user clicks the "Update" 
     * button at the bottom of the Modify Customer screen; it replaces 
     * the old contents of the customer record that the user was 
     * modifying with the new user-input details (which may or may not have 
     * been modified by the user). Additionally, this method also brings 
     * the user back to the Customers Table screen. However, before 
     * updating the customer record with the new user input, this method 
     * checks to make sure that all user input is valid; if it is not, a 
     * descriptive error dialog box is output to the user that prompts them 
     * to edit that input (and also gives them pointers on what would be 
     * considered valid input). NOTE: The only input field that is allowed 
     * to remain empty is the "Phone Number" field; that way, if a customer 
     * does not have a phone number, they can still be added to the 
     * database. (For purposes of contacting the customer, their home 
     * address should suffice).
     * @param event The ActionEvent that occurred; in this case, the 
     * "Update" button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionUpdateCustomer(ActionEvent event) throws IOException {
        
        try {
            
            //In the next few blocks of code, we are retrieving the values that the user inserted 
            //into each text field as well as the division combo box (or the values that were 
            //already there, if the customer didn't change them). Then, we are using all of that 
            //data to update the existing record in the database (in the "Customers" table) that 
            //has the matching customer ID.

            int customerID = Integer.parseInt(custIdTextField.getText());
            String customerName = custNameTextField.getText();
            String streetAddress = custStreetAddressTextField.getText();
            String postalCode = custPostalCodeTextField.getText();
            String phoneNumber = custPhoneNumberTextField.getText();

            FirstLevelDivisions divisionOfResidence = custDivisionComboBox.getValue();
            int divisionOfResidenceID = divisionOfResidence.getDivisionID();
            
            //------------------------------------------------------------------
            
            boolean thereIsAnInputError = false;
            
            if(customerName.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez fournir le nom complet du client.\n\n");
                    alert.setTitle("Aucun nom fourni");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide the customer's Full Name.\n\n");
                    alert.setTitle("No Name Provided");
                    alert.showAndWait();
                }
                                
            } else if (streetAddress.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez fournir l'adresse municipale du client.\n\n");
                    alert.setTitle("Aucune adresse municipale fournie");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide the customer's Street Address.\n\n");
                    alert.setTitle("No Street Address Provided");
                    alert.showAndWait();
                }
                                
            } else if (postalCode.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez fournir le code postal du client.\n\n");
                    alert.setTitle("Aucun code postal fourni");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide the customer's Postal Code.\n\n");
                    alert.setTitle("No Postal Code Provided");
                    alert.showAndWait();
                }
                                
            } else if (customerName.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe nom complet du client ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe customer's Full Name cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                                
            } else if (streetAddress.length() > 50){
                
                thereIsAnInputError = true;
                
               if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nL'adresse municipale du client ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe customer's Street Address cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                                
            } else if (postalCode.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe code postal du client ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe customer's Postal Code cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                                
            } else if (phoneNumber.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe numéro de téléphone du client ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe customer's Phone Number cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                
            }
            
            //------------------------------------------------------------------
                        
            if(!thereIsAnInputError){
                
                CustomersCRUD.modifyCustomerInDatabase(customerID, customerName, streetAddress, 
                                                       postalCode, phoneNumber, divisionOfResidenceID);

                methodsLibrary.switchToDifferentSceneUsingButton("/view/CustomersTable.fxml", event);               
            }
            
                        
        }
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez sélectionner à la fois un pays et une division.\n\n");
                alert.setTitle("Aucun pays ou division sélectionné");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease select both a Country and a Division.\n\n");
                alert.setTitle("No Country or Division Selected");
                alert.showAndWait();
            }
        }
                
    }
    
    
    
    /**
     * This method gets called whenever the "Back" button on the Modify 
     * Customer screen is clicked by the user; first, a confirmation 
     * dialog box is output to the user (asking the user if they are sure 
     * they want to leave the "Modify Customer" screen without saving 
     * their input); if they click "OK", this method switches the visible 
     * screen back to the "Customers Table" screen. 
     * @param event The ActionEvent that occurred; in this case, the "Back" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionDisplayCustomersTable(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        if(localLanguageIsFrench){
            alert.setTitle("Les données peuvent ne pas être enregistrées");
            alert.setContentText("\nToute entrée non enregistrée sur cette page sera perdue.\n\n" + 
                                 "Êtes-vous sûr de vouloir quitter?\n\n");
        } else {
            alert.setTitle("Data May Be Unsaved");
            alert.setContentText("\nAny unsaved input on this page will be lost.\n\n" + 
                                 "Are you sure you want to leave?\n\n");
        }
        
        //This variable will hold whichever button the user clicks (since CONFIRMATION dialog 
        //boxes have both an "OK" button as well as a "CANCEL" button). NOTE: It's also possible 
        //for the user to click no button at all, since dialog boxes come with an "X" on the top 
        //right, which can be used to exit out of the dialog box.
        Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
        
        //If the user clicked a button and that button is "OK", then we will sign the user out 
        //(a.k.a. switch the display to the login screen).
        if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.OK) ){
            
            methodsLibrary.switchToDifferentSceneUsingButton("/view/CustomersTable.fxml", event);
        }
        
    }
    
    
    
    /**
     * This method gets called whenever the "Sign out" button on the 
     * Modify Customer screen is clicked by the user; it outputs a 
     * confirmation dialog box asking the user if they are sure they want to 
     * sign out, and if the user confirms, then this method switches the 
     * visible screen to be the Login Screen once again (without saving any 
     * of the user input from the Modify Customer screen). 
     * @param event The ActionEvent that occurred; in this case, the "Sign out" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionDisplayLoginScreen(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        if(localLanguageIsFrench){
            alert.setTitle("Confirmation de déconnexion");
            alert.setContentText("\nToute entrée non enregistrée sur cette page sera perdue.\n\n" + 
                                 "Êtes-vous certain de vouloir vous déconnecter?\n\n");
        } else {
            alert.setTitle("Sign Out Confirmation");
            alert.setContentText("\nAny unsaved input on this page will be lost.\n\n" + 
                                 "Are you sure you want to sign out?\n\n");
        }
        
        //This variable will hold whichever button the user clicks (since CONFIRMATION dialog 
        //boxes have both an "OK" button as well as a "CANCEL" button). NOTE: It's also possible 
        //for the user to click no button at all, since dialog boxes come with an "X" on the top 
        //right, which can be used to exit out of the dialog box.
        Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
        
        //If the user clicked a button and that button is "OK", then we will sign the user out 
        //(a.k.a. switch the display to the login screen).
        if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.OK) ){
            
            methodsLibrary.switchToDifferentSceneUsingButton("/view/LoginScreen.fxml", event);
        }
        
        
    }
    
}
