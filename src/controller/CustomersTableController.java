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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.DataHolder;
import model.FirstLevelDivisions;

/**
 * This is an FXML Controller class which provides functionality to the 
 * Customers Table screen (the Customers Table FXML file). 
 *
 * @author LabUser
 */
public class CustomersTableController implements Initializable {
    /**
     * This is the header for the Customers Table screen; it reads "Customer 
     * Records". 
     */
    @FXML
    private Label customerRecordsLabel;
    /**
     * The back button on the Customers Table page, which leads the user 
     * back to the Main Menu. 
     */
    @FXML
    private Button backButton;
    /**
     * The "Sign Out" button on the Customers Table page, which leads the 
     * user back to the Login Screen. 
     */
    @FXML
    private Button signOutButton;
    /**
     * The TableView where users will see a list of all customer records that 
     * are currently in the database. 
     */
    @FXML
    private TableView<Customers> customersTableView;
    /**
     * The column of the Customers TableView that displays the customer ID 
     * of each customer. 
     */
    @FXML
    private TableColumn<Customers, Integer> custIdColumn;
    /**
     * The column of the Customers TableView that displays the full name 
     * of each customer. 
     */
    @FXML
    private TableColumn<Customers, String> custNameColumn;
    /**
     * The column of the Customers TableView that displays the street address 
     * of each customer. 
     */
    @FXML
    private TableColumn<Customers, String> custStreetAddressColumn;
    /**
     * The column of the Customers TableView that displays the 
     * first-level division (as in country division) where the customer 
     * resides. 
     */
    @FXML
    private TableColumn<Customers, String> custDivisionColumn;
    /**
     * The column of the Customers TableView that displays the 
     * country where the customer resides. 
     */
    @FXML
    private TableColumn<Customers, String> custCountryColumn;
    /**
     * The column of the Customers TableView that displays the postal code 
     * of each customer. 
     */
    @FXML
    private TableColumn<Customers, String> custPostalCodeColumn;
    /**
     * The column of the Customers TableView that displays the phone number 
     * of each customer. 
     */
    @FXML
    private TableColumn<Customers, String> custPhoneNumberColumn;
    /**
     * The "Add" button on the Customers Table page, which leads the 
     * user to the "Add Customer" page (where they can add a new 
     * customer, which gets added to the database [and is also visible in 
     * the Customers TableView]). 
     */
    @FXML
    private Button addButton;
    /**
     * The "Modify" button on the Customers Table page, which leads the 
     * user to the "Modify Customer" page (where they can update an existing 
     * customer record that they have selected; the changes to the customer 
     * record are then saved to the database [and are also visible in the 
     * Customers TableView]). 
     */
    @FXML
    private Button modifyButton;
    /**
     * The "Delete" button on the Customers Table page, which deletes the 
     * selected customer from the database (this is also reflected in the 
     * Customers TableView). 
     */
    @FXML
    private Button deleteButton;
    
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
     * Initializes the controller class. This is essentially the controller's 
     * equivalent of the "main()" method. This method lets the Customers 
     * TableView know what Observable List (of Customer objects) it will be 
     * displaying; it also lets each column of the Customers TableView know 
     * what variable of each Customer object it should display. Finally, 
     * this method determines the ZoneId, Locale, and local language (based 
     * on the retrieved Locale) of the current user; using this information, 
     * this method decides whether or not to translate the Customers 
     * Table screen to French.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        customersTableView.setItems(DataHolder.getListOfAllCustomers());
        
        //The thing in the quotes is the variable of each Customers object (each Customers object in the Observable List, that is) 
        //that I want to display in that particular TableView column.
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custStreetAddressColumn.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
        custDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionOfResidence"));
        custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("countryOfResidence"));
        custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
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
     * French; it translates all components on the Customers Table screen 
     * to French. 
     */
    private void translateScreenToFrench(){
        customerRecordsLabel.setText("DOSSIERS CLIENTS");
        backButton.setText(" Retourner");
        signOutButton.setText(" Déloguer");
        custIdColumn.setText("ID");
        custNameColumn.setText("Nom");
        custStreetAddressColumn.setText("Adresse de la rue");
        custDivisionColumn.setText("Subdivision de pays");
        custCountryColumn.setText("De campagne");
        custPostalCodeColumn.setText("Code postal");
        custPhoneNumberColumn.setText("Téléphoner");
        addButton.setText("Ajouter");
        addButton.setPrefWidth(108);
        modifyButton.setText("Modifier");
        modifyButton.setPrefWidth(110);
        deleteButton.setText("Effacer");
        deleteButton.setPrefWidth(102);
    }
    
    /**
     * This method gets called whenever the "Add" button on the Customers 
     * Table screen is clicked by the user; it switches the visible screen to 
     * the "Add Customer" screen, from where the user can add a new 
     * customer. 
     * @param event The ActionEvent that occurred; in this case, the "Add" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayAddCustomerMenu(ActionEvent event) throws IOException {
        
       methodsLibrary.switchToDifferentSceneUsingButton("/view/AddCustomer.fxml", event);
    }
    
    /**
     * This method gets called whenever the "Modify" button on the Customers 
     * Table screen is clicked by the user; if the user selected an 
     * appointment from the Customers TableView prior to clicking the 
     * "Modify" button, then this method switches the visible screen to 
     * the "Modify Customer" screen, from where the user can make 
     * modifications to the customer record that they selected. Otherwise (if 
     * the user did not select a customer record prior to clicking the "Modify" 
     * button), an error message is output to the user informing them that they 
     * must first select a customer record before clicking the "Modify" button. 
     * Additionally, this method sends all of the data in the customer 
     * record from the Customers Table screen to the Modify 
     * Customer screen (it does this by creating an instance of the 
     * Modify Customers controller, using that instance to call the 
     * "setInputFieldsToContentOfSelectedCustomer()" method [which is 
     * declared in the Modify Customers controller] and passing the entire 
     * selected item [which is a Customers object] into that method [so 
     * that the Modify Customer controller now has access to the item 
     * that was just selected] ).
     * @param event The ActionEvent that occurred; in this case, the "Modify" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayModifyCustomerMenu(ActionEvent event) throws IOException {
        
        try {
            
            //We have to create an object of the FXMLLoader class, because setLocation() and load()
            //are both instance methods (you have to call them from an object) (a.k.a. they aren't 
            //static methods [static methods belong to the class instead of each individual object, 
            //meaning you can call them without ever creating an object from the class]).
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();

            //Here, we are creating an object from the ModifyCustomerController class, and we 
            //initialize it with the specific controller instance being used to control the 
            //specific screen instance that's loaded up in the loader.
            ModifyCustomerController modifyCustomerController = loader.getController();
            //Now, we retrieve the Customers object that the user selected, and then we input 
            //that as a parameter when we call the setInputFieldsToContentOfSelectedCustomer() 
            //method (which is defined in the ModifyCustomerController). This method, as the 
            //name suggests, sets all input fields on the "Modify Customer" screen to reflect 
            //the contents of the Customers object that was passed into the method as a parameter.
            modifyCustomerController.setInputFieldsToContentOfSelectedCustomer(customersTableView.getSelectionModel().getSelectedItem());


            methodsLibrary.switchToDifferentSceneAndSendDataToThatSceneUsingButton(loader, event);
        
        } 
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez sélectionner le client que vous souhaitez modifier.\n\n");
                alert.setTitle("Aucune ligne sélectionnée");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease select the customer you would like to modify.\n\n");
                alert.setTitle("No Rows Selected");
                alert.showAndWait();
            }
        }
        
    }
    
    /**
     * This method gets called whenever the "Delete" button on the Customers 
     * Table screen is clicked by the user; it outputs a confirmation dialog 
     * box to to the user (asking if they are sure that they want to delete 
     * the customer record that they previously selected), and if the user 
     * clicks "OK", the customer is deleted from the database (and since 
     * the Customers TableView pulls its contents directly from the database, 
     * the Customers TableView will reflect this deletion as well). 
     * If the user did not select a customer record in 
     * the TableView prior to clicking on the "Delete" button, this method 
     * outputs an error dialog box telling the user that they must select a 
     * customer record to delete before clicking on the "Delete" button. 
     * Additionally, before the customer record is actually deleted from 
     * the database, all of the customer's appointments are deleted from the 
     * database first (so that there aren't any appointments scheduled for 
     * customers that don't exist). This helps maintain referential integrity 
     * of the database.
     * @param event The ActionEvent that occurred; in this case, the "Delete" 
     * button was clicked.
     */
    @FXML
    private void onActionDeleteCustomer(ActionEvent event) {
        
        try {
            
            //We get the Customers object that has been selected by the user (the user selected a row in our Customers Tableview).
            Customers selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
            
            if (selectedCustomer == null){
                
                throw new NullPointerException();
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Supprimer la confirmation");
                alert.setContentText("\nVoulez-vous vraiment supprimer ce client?\n\n");
                
            } else {
                
                alert.setTitle("Delete Confirmation");
                alert.setContentText("\nAre you sure you want to delete this customer?\n\n");
            }
            
            //This variable will hold whichever button the user clicks (since CONFIRMATION dialog 
            //boxes have both an "OK" button as well as a "CANCEL" button). NOTE: It's also possible 
            //for the user to click no button at all, since dialog boxes come with an "X" on the top 
            //right, which can be used to exit out of the dialog box.
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            int numOfRowsDeletedFromCustomersDatabaseTable = -1;
                    
            //If the user clicked a button and that button is "OK", then we will delete the customer  
            //from the database.
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.OK) ){

                //We retrieve the customer ID from that Customers object, and then we use that customer ID as a specifier to tell 
                //the database which record (row) in the Customers database table to delete. The deleteCustomerFromDatabase() 
                //method returns the number of rows deleted from the Customers database table.
                numOfRowsDeletedFromCustomersDatabaseTable = CustomersCRUD.deleteCustomerFromDatabase(selectedCustomer.getCustomerID());

                //Here, we refresh the Customers TableView so that the deleted row will visually disappear
                //after the deletion occurs.
                customersTableView.setItems(DataHolder.getListOfAllCustomers());
            }
            
            
            if(numOfRowsDeletedFromCustomersDatabaseTable == 1){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("\nLe client nommé \"" + selectedCustomer.getCustomerName() + 
                                          "\" (ID: #" + String.valueOf(selectedCustomer.getCustomerID()) + 
                                          ") a été supprimé de la table des enregistrements clients.\n\n" );
                    alert2.setTitle("Suppression réussie");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("\nThe customer named \"" + selectedCustomer.getCustomerName() + 
                                          "\" (ID: #" + String.valueOf(selectedCustomer.getCustomerID()) + 
                                          ") was deleted from the Customer Records table.\n\n" );
                    alert2.setTitle("Deletion Successful");
                    alert2.showAndWait();
                }
                
                
            } else if (numOfRowsDeletedFromCustomersDatabaseTable == 0){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nLe client n'a pas pu être supprimé.\n\n" );
                    alert2.setTitle("Échec de la suppression");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nThe customer could not be deleted.\n\n" );
                    alert2.setTitle("Deletion Unsuccessful");
                    alert2.showAndWait();
                }
                
                
            } else if (numOfRowsDeletedFromCustomersDatabaseTable > 1){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nPlusieurs clients ont été supprimés.\n\n" );
                    alert2.setTitle("Erreur de suppression");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nMultiple customers were deleted.\n\n" );
                    alert2.setTitle("Deletion Error");
                    alert2.showAndWait();
                }
                
            }
                       
        }
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez sélectionner le client que vous souhaitez supprimer.\n\n");
                alert.setTitle("Aucune ligne sélectionnée");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease select the customer you would like to delete.\n\n");
                alert.setTitle("No Rows Selected");
                alert.showAndWait();
            }
            
        }
        
        
    }
    
    /**
     * This method gets called whenever the "Back" button on the Customers 
     * Table screen is clicked by the user; it switches the visible screen back 
     * to the "Main Menu" screen. 
     * @param event The ActionEvent that occurred; in this case, the "Back" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        
        methodsLibrary.switchToDifferentSceneUsingButton("/view/MainMenu.fxml", event);
    }
    
    
    /**
     * This method gets called whenever the "Sign out" button on the 
     * Customers Table screen is clicked by the user; it outputs a 
     * confirmation dialog box asking the user if they are sure they want to 
     * sign out, and if the user confirms, then this method switches the 
     * visible screen to be the Login Screen once again. 
     * @param event The ActionEvent that occurred; in this case, the "Sign out" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayLoginScreen(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        
        if(localLanguageIsFrench){
            
            alert.setTitle("Confirmation de déconnexion");
            alert.setContentText("\nÊtes-vous certain de vouloir vous déconnecter?\n\n");
            
        } else {
            
            alert.setTitle("Sign Out Confirmation");
            alert.setContentText("\nAre you sure you want to sign out?\n\n");
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
