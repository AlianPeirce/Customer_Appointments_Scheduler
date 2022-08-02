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
import model.DataHolder;
import model.FirstLevelDivisions;

/**
 * This is an FXML Controller class which provides functionality to the Add 
 * Customer screen (the Add Customer FXML file). 
 *
 * @author Alano Peirce
 */
public class AddCustomerController implements Initializable {
    
    /**
     * This is the header of the Add Customer page - it reads 
     * "ADD CUSTOMER". 
     */
    @FXML
    private Label addCustomerLabel;
    /**
     * This is the header text that precedes the customer name text field. 
     */
    @FXML
    private Label fullNameLabel;
    /**
     * This is the text field where the user inputs the full name of the 
     * customer that they wish to input into the database. 
     */
    @FXML
    private TextField custNameTextField;
    /**
     * This is the header text that precedes the customer street address field. 
     */
    @FXML
    private Label streetAddressLabel;
    /**
     * This is the text field where the user inputs the street address of the 
     * customer that they wish to input into the database. 
     */
    @FXML
    private TextField custStreetAddressTextField;
    /**
     * This is the header text that precedes the customer postal code field. 
     */
    @FXML
    private Label postalCodeLabel;
    /**
     * This is the text field where the user inputs the postal code of the 
     * customer that they wish to input into the database. 
     */
    @FXML
    private TextField custPostalCodeTextField;
    /**
     * This is the header text that precedes the customer phone number field. 
     */
    @FXML
    private Label phoneNumberLabel;
    /**
     * This is the text field where the user inputs the phone number of the 
     * customer that they wish to input into the database. 
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
     * The button that saves the user-input details as a new 
     * customer record (in the database's Customers table); it also brings 
     * the user back to the Customers Table screen. 
     */
    @FXML
    private Button addCustomerButton;
    /**
     * Brings the user back to the Customers Table screen (if the user 
     * clicks the button) without saving any of the user input (from the 
     * Add Customer screen). 
     */
    @FXML
    private Button backButton;
    /**
     * Brings the user back to the Login Screen (if the user 
     * clicks the button) without saving any of the user input (from the 
     * Add Customer screen). 
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
     * the Add Customer screen to French.
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
    
    
    /**
     * This method is called if the user's local language is determined to be 
     * French; it translates all components on the Add Customer screen 
     * to French. 
     */
    private void translateScreenToFrench(){
        addCustomerLabel.setText("  AJOUTER UN CLIENT");
        backButton.setText("Retourner");
        backButton.setFont(new Font("Segoe UI Semibold", 13));
        signOutButton.setText(" Déloguer");
        fullNameLabel.setText("Nom et prénom");
        streetAddressLabel.setText("Adresse de la rue");
        postalCodeLabel.setText("Code postal");
        phoneNumberLabel.setText("Numéro de téléphone");
        countryLabel.setText("De campagne");
        custCountryComboBox.setPromptText("Veuillez sélectionner un pays");
        divisionLabel.setText("Division");
        custDivisionComboBox.setPromptText("Veuillez sélectionner une division");
        addCustomerButton.setText("Ajouter un client");
        addCustomerButton.setPrefWidth(220);
    }
    
    
    /**
     * This method is called when the user selects a country from the country 
     * combo box; it populates the division combo box with the first-level 
     * divisions of that selected country. 
     * @param event The ActionEvent that occurred; in this case, a selection 
     * was made in the country combo box.
     */
    //Once a country is selected (in the country combo box), populate the division combo box 
    //with all divisions of the selected country.
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
     * This method is called when the user clicks the "Add Customer" 
     * button at the bottom of the Add Customer screen; it saves 
     * the user-input details as a new customer record (in the database's 
     * Customers table), and also brings the user back to the Customers 
     * Table screen. However, before saving the new customer record, this 
     * method checks to make sure that all input is valid; if it is not, a 
     * descriptive error dialog box is output to the user that prompts them 
     * to edit that input (and also gives them pointers on what would be 
     * considered valid input). NOTE: The only input field that is allowed 
     * to remain empty is the "Phone Number" field; that way, if a customer 
     * does not have a phone number, they can still be added to the 
     * database. (For purposes of contacting the customer, their home 
     * address should suffice).
     * @param event The ActionEvent that occurred; in this case, the 
     * "Add Customer" button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionAddCustomer(ActionEvent event) throws IOException {
        
        try {
            
            //In the next few blocks of code, we are retrieving the values that the user inserted 
            //into each text field (and the division combo box), and then we are using all of that 
            //data to insert a new record into the database (in the "Customers" table).

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
                
                CustomersCRUD.addCustomerToDatabase(customerName, streetAddress, postalCode, phoneNumber, divisionOfResidenceID);
                
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
     * This method gets called whenever the "Back" button on the Add 
     * Customer screen is clicked by the user; first, a confirmation 
     * dialog box is output to the user (asking the user if they are sure 
     * they want to leave the "Add Customer" screen without saving 
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
            alert.setTitle("Données non enregistrées");
            alert.setContentText("\nToute entrée sur cette page n'est pas enregistrée et sera perdue.\n\n" + 
                                 "Êtes-vous sûr de vouloir quitter?\n\n");
        } else {
            alert.setTitle("Unsaved Data");
            alert.setContentText("\nAny input on this page is unsaved and will be lost.\n\n" + 
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
     * Add Customer screen is clicked by the user; it outputs a 
     * confirmation dialog box asking the user if they are sure they want to 
     * sign out, and if the user confirms, then this method switches the 
     * visible screen to be the Login Screen once again (without saving any 
     * of the user input from the Add Customer screen). 
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
            alert.setContentText("\nToute entrée sur cette page n'est pas enregistrée et sera perdue.\n\n" + 
                                 "Êtes-vous certain de vouloir vous déconnecter?\n\n");
        } else {
            alert.setTitle("Sign Out Confirmation");
            alert.setContentText("\nAny input on this page is unsaved and will be lost.\n\n" + 
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
