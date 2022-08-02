/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database_CRUD.AppointmentsCRUD;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.DataHolder;
import model.Users;

/**
 * This is an FXML Controller class which provides functionality to the  
 * Appointments Table screen (the Appointments Table FXML file). 
 *
 * @author Alano Peirce
 */
public class AppointmentsTableController implements Initializable {
    
    /**
     * The header on the Appointments Table page, which reads "APPOINTMENTS". 
     */
    @FXML
    private Label appointmentsLabel;
    /**
     * The back button on the Appointments Table page, which leads the user 
     * back to the Main Menu. 
     */
    @FXML
    private Button backButton;
    /**
     * The "Sign Out" button on the Appointments Table page, which leads the 
     * user back to the Login Screen. 
     */
    @FXML
    private Button signOutButton;
    /**
     * A radio button that users may select in order to view all appointments 
     * that are currently in the database (they will see these appointments in 
     * the TableView). 
     */
    @FXML
    private RadioButton allAppointmentsRadioButton;
    /**
     * The toggle group that groups together the three radio buttons that 
     * control the TableView view options ("All Appointments", "This Month", 
     * and "This Week"). The toggle group ensures that only one of these three 
     * radio buttons can be selected at a time.
     */
    @FXML
    private ToggleGroup apptsTableDisplayOptionsTG;
    /**
     * A radio button that users may select in order to view appointments 
     * from the database that are scheduled for the current month (they will 
     * see these appointments in the TableView). 
     */
    @FXML
    private RadioButton thisMonthRadioButton;
    /**
     * A radio button that users may select in order to view appointments 
     * from the database that are scheduled for the current week (they will 
     * see these appointments in the TableView). For the purposes of this 
     * radio button, a "week" is considered to be Sunday to Saturday, 
     * inclusive. 
     */
    @FXML
    private RadioButton thisWeekRadioButton;
    /**
     * The TableView where users will see a list of the appointments that are 
     * currently in the database (during the time frame that they selected 
     * using the radio buttons). 
     */
    @FXML
    private TableView<Appointments> appointmentsTableView;
    /**
     * The column of the Appointments TableView that holds the appointment ID 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, Integer> apptIdColumn;
    /**
     * The column of the Appointments TableView that holds the title 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptTitleColumn;
    /**
     * The column of the Appointments TableView that holds the description 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptDescriptionColumn;
    /**
     * The column of the Appointments TableView that holds the location 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptLocationColumn;
    /**
     * The column of the Appointments TableView that holds the contact 
     * involved in each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptContactColumn;
    /**
     * The column of the Appointments TableView that holds the type 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptTypeColumn;
    /**
     * The column of the Appointments TableView that holds the start date and 
     * time of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptStartTimeColumn;
     /**
     * The column of the Appointments TableView that holds the end date and 
     * time of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptEndTimeColumn;
    /**
     * The column of the Appointments TableView that holds the customer 
     * involved in each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptCustomerColumn;
    /**
     * The column of the Appointments TableView that holds the user in charge 
     * of each appointment. 
     */
    @FXML
    private TableColumn<Appointments, String> apptUserColumn;
    /**
     * The "Statistics" button on the Appointments Table page, which shows the 
     * user the three appointment-related statistical reports that were 
     * generated. 
     */
    @FXML
    private Button statisticsButton;
    /**
     * The "Add" button on the Appointments Table page, which leads the 
     * user to the "Add Appointment" page (where they can create a new 
     * appointment, which gets added to the database). 
     */
    @FXML
    private Button addButton;
    /**
     * The "Modify" button on the Appointments Table page, which leads the 
     * user to the "Modify Appointment" page (where they can update an existing 
     * appointment that they have selected; the changes to the appointment 
     * then get saved to the database). 
     */
    @FXML
    private Button modifyButton;
    /**
     * The "Delete" button on the Appointments Table page, which deletes the 
     * selected appointment from the database (and also from the Appointments 
     * TableView). 
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
    
    //"DataHolder" is my own class that I created. I wrote a method (function) 
    //there that can be used to switch to a different scene.
    /**
     * An instantiation of the DataHolder class; this is necessary in order to 
     * call the methods that allow the application to switch to a different 
     * scene, as those methods cannot be static (and thus, an instance of the 
     * class is required in order to call those methods). 
     */
    DataHolder methodsLibrary = new DataHolder();
    
    
    
        
    //This method is basically a controller file's equivalent to the "main()" method.    
    /**
     * Initializes the controller class. This is essentially the controller's 
     * equivalent of the "main()" method. This method lets the Appointments 
     * TableView know what Observable List (of Appointment objects) it will be 
     * displaying; it also lets each column of the Appointments TableView know 
     * what variable of each Appointment object it should display. Finally, 
     * this method determines the ZoneId, Locale, and local language (based 
     * on the retrieved Locale) of the current user; using this information, 
     * this method decides whether or not to translate the Appointments 
     * Table screen to French.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        appointmentsTableView.setItems(DataHolder.getListOfAllAppointments());
        
        //The thing in the quotes is the variable of each Appointments object (each Appointments object in the Observable List, that is) 
        //that I want to display in that particular TableView column.
        apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentName"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfContactInvolved"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateAndTimeInMDYand12HourTime"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateAndTimeInMDYand12HourTime"));
        apptCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfCustomerInvolved"));
        apptUserColumn.setCellValueFactory(new PropertyValueFactory<>("usernameOfUserInCharge"));
        
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
     * French; it translates all components on the Appointments Table screen 
     * to French. 
     */
    private void translateScreenToFrench(){
        
        //French people use DMY for dates (instead of MDY) and the 24-hour system 
        //for time (instead of the 12-hour system).
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentStartDateAndTimeInDMYand24HourTime"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentEndDateAndTimeInDMYand24HourTime"));
        
        appointmentsLabel.setText("RENDEZ-VOUS");
        backButton.setText(" Retourner");
        allAppointmentsRadioButton.setText("Tous les rendez-vous");
        thisMonthRadioButton.setText("Ce mois-ci");
        thisWeekRadioButton.setText("Cette semaine");
        signOutButton.setText(" Déloguer");
        apptIdColumn.setText("ID");
        apptTitleColumn.setText("Titre");
        apptDescriptionColumn.setText("Description");
        apptLocationColumn.setText("Lieu");
        apptContactColumn.setText("Contact");
        apptTypeColumn.setText("Taper");
        apptStartTimeColumn.setText("Heure de début");
        apptEndTimeColumn.setText("Heure de fin");
        apptCustomerColumn.setText("Client");
        apptUserColumn.setText("Utilisateur");
        statisticsButton.setPrefWidth(190);
        statisticsButton.setText("Statistiques");
        addButton.setText("Ajouter");
        addButton.setPrefWidth(108);
        modifyButton.setText("Modifier");
        modifyButton.setPrefWidth(110);
        deleteButton.setText("Effacer");
        deleteButton.setPrefWidth(102);
        
    }
    
    /**
     * This method gets called whenever the "All Appointments" radio button is 
     * selected; it populates the Appointments TableView with all appointments 
     * that are currently in the database. 
     * @param event The ActionEvent that occurred; in this case, the "All  
     * Appointments" radio button was clicked.
     */
    @FXML
    private void onActionDisplayAllAppointments(ActionEvent event) {
        
        appointmentsTableView.setItems(DataHolder.getListOfAllAppointments());
    }
    
    /**
     * This method gets called whenever the "This Month" radio button is 
     * selected by the user; it populates the Appointments TableView with 
     * all appointments in the database that are scheduled for the current 
     * month. 
     * @param event The ActionEvent that occurred; in this case, the "This 
     * Month" radio button was clicked.
     */
    @FXML
    private void onActionDisplayThisMonthsAppointments(ActionEvent event) {
        
        appointmentsTableView.setItems(DataHolder.getListOfThisMonthsAppointments());
    }
    
    /**
     * This method gets called whenever the "This Week" radio button is 
     * selected by the user; it populates the Appointments TableView with 
     * all appointments in the database that are scheduled for the current 
     * week (Sunday to Saturday, inclusive). 
     * @param event The ActionEvent that occurred; in this case, the "This 
     * Week" radio button was clicked.
     */
    @FXML
    private void onActionDisplayThisWeeksAppointments(ActionEvent event) {
        
        appointmentsTableView.setItems(DataHolder.getListOfThisWeeksAppointments());
    }
    
    /**
     * This method gets called whenever the "Add" button on the Appointments 
     * Table screen is clicked by the user; it switches the visible screen to 
     * the "Add Appointment" screen, from where the user can create a new 
     * appointment. 
     * @param event The ActionEvent that occurred; in this case, the "Add" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayAddAppointmentMenu(ActionEvent event) throws IOException {
        
        methodsLibrary.switchToDifferentSceneUsingButton("/view/AddAppointment.fxml", event);
    }
    
    /**
     * This method gets called whenever the "Modify" button on the Appointments 
     * Table screen is clicked by the user; if the user selected an 
     * appointment from the Appointments TableView prior to clicking the 
     * "Modify" button, then this method switches the visible screen to 
     * the "Modify Appointment" screen, from where the user can make 
     * modifications to the appointment that they selected. Otherwise (if the 
     * user did not select an appointment prior to clicking the "Modify" 
     * button), an error message is output to the user informing them that they 
     * must first select an appointment before clicking the "Modify" button. 
     * Additionally, this method sends all of the data (relating to the 
     * selected appointment) from the Appointments Table screen to the Modify 
     * Appointment screen (it does this by creating an instance of the 
     * Modify Appointments controller, using that instance to call the 
     * "setInputFieldsToContentOfSelectedAppointment()" method [which is 
     * declared in the Modify Appointments controller] and passing the entire 
     * selected item [which is an Appointments object] into that method [so 
     * that the Modify Appointments controller now has access to the item 
     * that was just selected] ).
     * @param event The ActionEvent that occurred; in this case, the "Modify" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (if an 
     * error occurs while loading the desired scene), which is used in the 
     * process of switching screens
     */
    @FXML
    private void onActionDisplayModifyAppointmentMenu(ActionEvent event) throws IOException {
        
        try {
            
            //We have to create an object of the FXMLLoader class, because setLocation() and load()
            //are both instance methods (you have to call them from an object) (a.k.a. they aren't 
            //static methods [static methods belong to the class instead of each individual object, 
            //meaning you can call them without ever creating an object from the class]).
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            //Here, we are creating an object from the ModifyAppointmentController class, and we 
            //initialize it with the specific controller instance being used to control the 
            //specific screen instance that's loaded up in the loader.
            ModifyAppointmentController modifyAppointmentController = loader.getController();
            //Now, we retrieve the Appointments object that the user selected, and then we input 
            //that as a parameter when we call the setInputFieldsToContentOfSelectedAppointment() 
            //method (which is defined in the ModifyAppointmentController). This method, as the 
            //name suggests, sets all input fields on the "Modify Appointment" screen to reflect 
            //the contents of the Appointments object that was passed into the method as a parameter.
            modifyAppointmentController.setInputFieldsToContentOfSelectedAppointment(appointmentsTableView.getSelectionModel().getSelectedItem());


            methodsLibrary.switchToDifferentSceneAndSendDataToThatSceneUsingButton(loader, event);
            
        }
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez sélectionner le rendez-vous que vous souhaitez modifier.\n\n");
                alert.setTitle("Aucune ligne sélectionnée");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease select the appointment you would like to modify.\n\n");
                alert.setTitle("No Rows Selected");
                alert.showAndWait();
            }
            
        }
        
        
    }
    
    /**
     * This method gets called whenever the "Delete" button on the Appointments 
     * Table screen is clicked by the user; it outputs a confirmation dialog 
     * box to to the user (asking if they are sure that they want to delete 
     * the appointment that they previously selected), and if the user clicks 
     * "OK", the appointment is deleted from both the database and the 
     * Appointments TableView. If the user did not select an appointment in 
     * the TableView prior to clicking on the "Delete" button, this method 
     * outputs an error dialog box telling the user that they must select an 
     * appointment to delete before clicking on the "Delete" button.
     * @param event The ActionEvent that occurred; in this case, the "Delete" 
     * button was clicked.
     */
    @FXML
    private void onActionDeleteAppointment(ActionEvent event) {
        
        try {
           
            //We get the Appointments object that has been selected by the user (the user selected a row in our Appointments Tableview).
            Appointments selectedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
            
            if (selectedAppointment == null){
                
                throw new NullPointerException();
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            if(localLanguageIsFrench){
                
               alert.setTitle("Supprimer la confirmation");
               alert.setContentText("\nVoulez-vous vraiment supprimer ce rendez-vous?\n\n");
                
            } else {
                
               alert.setTitle("Delete Confirmation");
               alert.setContentText("\nAre you sure you want to delete this appointment?\n\n");
            }
            
            
            //This variable will hold whichever button the user clicks (since CONFIRMATION dialog 
            //boxes have both an "OK" button as well as a "CANCEL" button). NOTE: It's also possible 
            //for the user to click no button at all, since dialog boxes come with an "X" on the top 
            //right, which can be used to exit out of the dialog box.
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            int numOfRowsDeletedFromAppointmentsDatabaseTable = -1;

            //If the user clicked a button and that button is "OK", then we will delete the appointment  
            //from the database.
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.OK) ){

                //We retrieve the appointment ID from that Appointments object, and then we use that appointment ID as a specifier to tell 
                //the database which record (row) in the Appointments database table to delete. The deleteAppointmentFromDatabase() 
                //method returns the number of rows deleted from the Appointments database table.
                numOfRowsDeletedFromAppointmentsDatabaseTable = AppointmentsCRUD.deleteAppointmentFromDatabase(selectedAppointment.getAppointmentID());

                //Here, we refresh the Appointments TableView (by re-setting it with the appropriate updated list, 
                //based on which radio button is currently selected) so that the deleted row will visually disappear
                //after the deletion occurs.
                if(allAppointmentsRadioButton.isSelected()){

                    appointmentsTableView.setItems(DataHolder.getListOfAllAppointments());

                } else if (thisMonthRadioButton.isSelected()){

                    appointmentsTableView.setItems(DataHolder.getListOfThisMonthsAppointments());

                } else if (thisWeekRadioButton.isSelected()){

                    appointmentsTableView.setItems(DataHolder.getListOfThisWeeksAppointments());
                }
                
            }
            
            if(numOfRowsDeletedFromAppointmentsDatabaseTable == 1){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("\nLe rendez-vous nommé \"" + selectedAppointment.getAppointmentName() + 
                                          "\"  ( ID: #" + String.valueOf(selectedAppointment.getAppointmentID()) + 
                                          "  |  Taper: \"" + selectedAppointment.getAppointmentType() + 
                                          "\" )  a été supprimé du tableau des rendez-vous.\n\n" );
                    alert2.setTitle("Suppression réussie");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("\nThe appointment named \"" + selectedAppointment.getAppointmentName() + 
                                          "\"  ( ID: #" + String.valueOf(selectedAppointment.getAppointmentID()) + 
                                          "  |  Type: \"" + selectedAppointment.getAppointmentType() + 
                                          "\" )  was deleted from the Appointments table.\n\n" );
                    alert2.setTitle("Deletion Successful");
                    alert2.showAndWait();
                }
                
                
            } else if (numOfRowsDeletedFromAppointmentsDatabaseTable == 0){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nLe rendez-vous n'a pas pu être supprimé.\n\n" );
                    alert2.setTitle("Échec de la suppression");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nThe appointment could not be deleted.\n\n" );
                    alert2.setTitle("Deletion Unsuccessful");
                    alert2.showAndWait();
                }
                
                
            } else if (numOfRowsDeletedFromAppointmentsDatabaseTable > 1){
                
                if(localLanguageIsFrench){
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nPlusieurs rendez-vous ont été supprimés.\n\n" );
                    alert2.setTitle("Erreur de suppression");
                    alert2.showAndWait();
                    
                } else {
                    
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setContentText("\nMultiple appointments were deleted.\n\n" );
                    alert2.setTitle("Deletion Error");
                    alert2.showAndWait();
                }
                
            }

            
            
        }
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez sélectionner le rendez-vous que vous souhaitez supprimer.\n\n");
                alert.setTitle("Aucune ligne sélectionnée");
                alert.showAndWait();
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease select the appointment you would like to delete.\n\n");
                alert.setTitle("No Rows Selected");
                alert.showAndWait();
            }
            
            
        }
        
    }
    
    /**
     * This method gets called whenever the "Back" button on the Appointments 
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
     * Appointments Table screen is clicked by the user; it outputs a 
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
    
    
    /*
    This method provides functionality to the buttons on each screen (a.k.a. each dialog box 
    that displays one of the three Statistical Reports). The "Next" button on 
    page 1 (a.k.a. the dialog box displaying Statistical Report #1) brings you to page 2.  
    The "Next" button on page 2 brings you to page 3, and the "Back" button on page 2 brings you back to page 1.
    The "Done" button on page 3 stops the loop (a.k.a. no more pages are displayed), and the 
    "Back" button on page 3 brings you back to page 2. If one of the dialog boxes has finished 
    "showing" but no buttons have been pressed by the user, this means that the user exited out 
    of the dialog box (using the "X" in the upper left-hand corner); thus, in this case, the loop 
    stops and no more dialog boxes are shown.
    
    It is important to note that currently, none of the dialog boxes allow the user to close them.
    This is because, if you add a "PREVIOUS" button to a dialog box of type INFORMATION, the Alert class 
    (the class that each dialog box is an object of) disallows the user from closing the dialog box using 
    the "X" in the upper right corner of the dialog box. Because of this, two things are of note:
    
    1) Even though the dialog boxes currently can't be closed by the user, I coded the possibility 
       of that event/functionality into the method right below this comment so that just in case a user 
       finds some way to exit out of the dialog box, or just in case Java changes the way that the Alert 
       class functions, or just in case *I* decide to change the way my dialog boxes function, that 
       potential occurrence (a.k.a. the user closing out of the dialog box) is taken into account.
    
    2) Since the dialog boxes currently can't be closed by the user, the functionality I coded of how the 
       loop is supposed to stop (meaning no more dialog boxes are to be displayed) once the user exits  
       out of one of the dialog boxes HAS NOT BEEN TESTED. Thus, I CANNOT BE 100% SURE THAT IT WORKS,  
       and testing of this functionality should be commenced in the case that I decide to change 
       the dialog boxes such that they can be closed by the user.
    */
    /**
     * This method is called whenever the "Statistics" button on the 
     * Appointments Table screen is clicked; it calls other methods that 
     * calculate and display three separate statistical reports as dialog 
     * boxes, and provides functionality to the "Next", "Back", or "Done" 
     * buttons on these dialog boxes. ("Next" brings the user to the next 
     * statistical report, "Back" brings the user to the previous 
     * statistical report, and "Done" closes the last statistical report).
     * @param event The ActionEvent that occurred; in this case, the 
     * "Statistics" button was clicked.
     */
    @FXML
    private void onActionDisplayStatistics(ActionEvent event) {
        
        String btnClickedOnPage2;
        String btnClickedOnPage3;
        boolean nextBtnOnPage1IsClicked = false;
        boolean nextBtnOnPage2IsClicked = false;
        boolean backBtnOnPage2IsClicked = false;
        boolean doneBtnOnPage3IsClicked = false;
        boolean backBtnOnPage3IsClicked = false;
        boolean dialogBoxWasClosed = false;
        
        
        nextBtnOnPage1IsClicked = calculateAndDisplayStatisticalReport1();
        
        if(!nextBtnOnPage1IsClicked){
            
            dialogBoxWasClosed = true;
        }
        
        while(!dialogBoxWasClosed && !doneBtnOnPage3IsClicked){
            
            if(nextBtnOnPage1IsClicked){
                
                nextBtnOnPage1IsClicked = false;
                
                btnClickedOnPage2 = calculateAndDisplayStatisticalReport2();
                
                if(btnClickedOnPage2.equals("NEXT")){
                    nextBtnOnPage2IsClicked = true;
                } else if (btnClickedOnPage2.equals("BACK")){
                    backBtnOnPage2IsClicked = true;
                } else {
                    dialogBoxWasClosed = true;
                }
                
            } else if (nextBtnOnPage2IsClicked){
                
                nextBtnOnPage2IsClicked = false;
                
                btnClickedOnPage3 = calculateAndDisplayStatisticalReport3();
                
                if(btnClickedOnPage3.equals("DONE")){
                    doneBtnOnPage3IsClicked = true;
                } else if (btnClickedOnPage3.equals("BACK")){
                    backBtnOnPage3IsClicked = true;
                } else {
                    dialogBoxWasClosed = true;
                }
                
            } else if (backBtnOnPage2IsClicked){
                
                backBtnOnPage2IsClicked = false;
                
                nextBtnOnPage1IsClicked = calculateAndDisplayStatisticalReport1();
                
                if(!nextBtnOnPage1IsClicked){
                    dialogBoxWasClosed = true;
                }
                
            } else if (backBtnOnPage3IsClicked){
                
                backBtnOnPage3IsClicked = false;
                
                btnClickedOnPage2 = calculateAndDisplayStatisticalReport2();
                
                if(btnClickedOnPage2.equals("NEXT")){
                    nextBtnOnPage2IsClicked = true;
                } else if (btnClickedOnPage2.equals("BACK")){
                    backBtnOnPage2IsClicked = true;
                } else {
                    dialogBoxWasClosed = true;
                }
                
            }
            
        }
        
       
        
        
    }
    
    //This is a functional interface, which is kind of like a class with an abstract method 
    //(a method without a body, just a header). I can give the method a body by creating an object 
    //of this functional interface and then overriding the method within that object using a 
    //lambda expression. (In the process of overriding the method, I give it a body [meaning defining what the method will do] 
    //using the lambda expression.)
    //To see this in action (and to see where in the code I use this functional interface), 
    //go to the calculateAndDisplayStatisticalReport1() method (which is just under this functional interface).
     /**
     * This functional interface contains an abstract method that will be 
     * overridden (in other words, given a body) at a later point (in the 
     * "calculateAndDisplayStatisticalReport1()" method). 
     */
    interface MonthConverter{
        
        /**
         * This method is an abstract method that currently does not do 
         * anything (does not have a body); however, it will be overridden (in 
         * the "calculateAndDisplayStatisticalReport1()" method) such that 
         * it has the ability to convert the given number (which represents 
         * a particular month) into the name of that month (in the appropriate 
         * language). 
         * @param monthNumber A number representing a month of the year
         * @param localLanguageOfUserIsFrench "true" if the default language of 
         * the user's locale is French; "false" if the default language of the 
         * user's locale is any other language
         * @return Returns the name of the month that corresponds to the 
         * given month number; it does so in the appropriate language (based 
         * on the user's local language).
         */
        String convertMonthNumToMonthName(int monthNumber, boolean localLanguageOfUserIsFrench);
    }
    
    /**
     * This method calculates the total number of appointments scheduled for 
     * each appointment type, by month; it then outputs this information 
     * (for all months) in the form of a dialog box.
     * -------------------------------------------------------------------------
     * This method is the location of the second LAMBDA EXPRESSION (see 
     * line 851 for the actual lambda expression, see line 882 for the place 
     * where the overridden method [a.k.a. the method that the lambda 
     * expression overrode / gave a body to] is called, and see line 783 for 
     * the functional interface whose abstract method was overridden using the 
     * lambda expression). The lambda expression takes in (as parameters) 
     * a number representing a month as well as a boolean representing 
     * whether or not the local language of the user is French. Using 
     * this information, the lambda expression returns the name of the month 
     * associated with the given number, and it does so in the 
     * appropriate language.
     * -------------------------------------------------------------------------
     * JUSTIFICATION: This lambda expression is useful because it allows for 
     * the easy future implementation of additional languages, as well as 
     * easy reuse of the code. If other statistical reports are generated that 
     * require outputting the name of the month, then this lambda expression 
     * could simply be called every time that functionality is needed 
     * (instead of having to code that functionality into the code ever single 
     * time). Additionally, if the capability for additional languages 
     * (besides English and French) is added to the application in the future, 
     * then the future developers would simply have to add a bit of code to 
     * the lambda expression in order to provide the capability for outputting 
     * the name of the month in the appropriate language; this would save a 
     * lot of time because they wouldn't need to change the code in every 
     * single place where the lambda expression is called; instead, they only 
     * have to change the code in one place.
     * 
     * @return Returns "true" if the "Next" button on the dialog box is clicked; 
     * returns "false" if it is not (e.g. if the user clicks the "X" on the 
     * upper right corner of the dialog box in order to exit out of the 
     * dialog box).
     */
    //returns true if "Next" button clicked; returns false if it is not 
    //(e.g. if the user clicks the "X" on the upper right corner of the 
    //dialog box)
    private boolean calculateAndDisplayStatisticalReport1(){
        
        //This is Statistical Report #1.
        
        //This is the lambda expression that I use to override the method in the functional interface up above 
        //(the functional interface is defined above the beginning of the method we're currently in).
        //I first create an object of the functional interface, and then I override the method within that object and 
        //give it a body (a.k.a. I give some actual functionality to the method, like it actually does something now).
        MonthConverter monthConverter = (monthNumber, localLanguageOfUserIsFrench) -> {
            
            if(localLanguageOfUserIsFrench){
                
                return Month.of(monthNumber).getDisplayName(TextStyle.FULL, Locale.FRENCH);
                
            } else {
                
                return Month.of(monthNumber).getDisplayName(TextStyle.FULL, Locale.US);
            }
        };
        
        boolean nextButtonWasClicked = false;
        
        try
        {
            ArrayList<String> numberOfEachApptTypeScheduledForTheGivenMonth = new ArrayList<String>(0);
            StringBuilder textToOutput = new StringBuilder();

            int tracker = 0;
            int monthNum = 1;
            boolean thereAreAppointmentsInTheGivenMonth = false;
            
            
            while(monthNum <= 12){
                
                numberOfEachApptTypeScheduledForTheGivenMonth = AppointmentsCRUD.getFromDatabaseTheTotalForEachTypeOfAppointmentInAParticularMonth(monthNum);
                
                //This is where I call the method that I previously overrode using a lambda expression. 
                //See the beginning of the method that we're currently in to view the body of this function 
                //(a.k.a. so you can see what this method is programmed to do).
                String monthName = monthConverter.convertMonthNumToMonthName(monthNum, localLanguageIsFrench);
                
                textToOutput.append("\n•  " + monthName + "\n\n");
                
                                
                tracker = 0;
                
                thereAreAppointmentsInTheGivenMonth = false;
                                
                while( tracker < numberOfEachApptTypeScheduledForTheGivenMonth.size() ){
                    
                    thereAreAppointmentsInTheGivenMonth = true;
                    
                    String appointmentType = numberOfEachApptTypeScheduledForTheGivenMonth.get(tracker);

                    String numberOfApptsOfThatType = numberOfEachApptTypeScheduledForTheGivenMonth.get(tracker + 1);
                                        

                    //Singular vs. plural grammar
                    if( Integer.parseInt(numberOfApptsOfThatType) == 1 ){
                        
                        if(localLanguageIsFrench){
                            textToOutput.append("        ◦  Il y a " + numberOfApptsOfThatType + " rendez-vous de type \"" + 
                                                                                                    appointmentType + "\".\n\n");
                        } else {
                            textToOutput.append("        ◦  There is " + numberOfApptsOfThatType + " appointment of type \"" + 
                                                                                                    appointmentType + "\".\n\n");
                        }
                        

                    } else {
                        
                        if(localLanguageIsFrench){
                            textToOutput.append("        ◦  Il y a " + numberOfApptsOfThatType + " rendez-vous de type \"" + 
                                                                                                    appointmentType + "\".\n\n");
                        } else {
                            textToOutput.append("        ◦  There are " + numberOfApptsOfThatType + " appointments of type \"" + 
                                                                                                    appointmentType + "\".\n\n");
                        }
                        
                    }
                    
                    
                    tracker = tracker + 2;
                }
                
                if(!thereAreAppointmentsInTheGivenMonth){
                    
                    if(localLanguageIsFrench){
                        textToOutput.append("        ◦  Aucun rendez-vous n'est prévu ce mois-ci.\n\n");
                    } else {
                        textToOutput.append("        ◦  There are no appointments scheduled for this month.\n\n");
                    }
                    
                }
                
                
                monthNum++;
            }
            
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setResizable(true);
            alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMaxWidth(470);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinHeight(460);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            
            //I'm adding a "PREVIOUS" button and then hiding it because that prevents the dialog box 
            //from closing when the user hits the "X" in the upper right corner. This is good because 
            //I don't want the user to close the dialog box for Statistical Report #1 before they see 
            //Statistical Report #2 and Statistical Report #3.
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setVisible(false);
            
            TextArea textArea = new TextArea( textToOutput.toString() );
            textArea.setEditable(false);

            alert.getDialogPane().setContent(textArea);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #1");
                alert.setHeaderText("                       NOMBRE DE RENDEZ-VOUS PAR TYPE" + 
                                    "\n                                       (Pour chaque mois)");
                
            } else {
                
                alert.setTitle("Statistical Report #1");
                alert.setHeaderText("                       NUMBER OF APPOINTMENTS BY TYPE" + 
                                    "\n                                          (For Each Month)");
            }
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                
                nextButtonWasClicked = true;
            }
            
        }
        catch(NullPointerException error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            
            //I'm adding a "PREVIOUS" button and then hiding it because that prevents the dialog box 
            //from closing when the user hits the "X" in the upper right corner. This is good because 
            //I don't want the user to close the dialog box for Statistical Report #1 before they see 
            //Statistical Report #2 and Statistical Report #3.
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setVisible(false);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #1");
                alert.setHeaderText("NOMBRE DE RENDEZ-VOUS PAR TYPE\n(Pour chaque mois)");
                alert.setContentText("\nUne erreur est survenue.\n\n");
                
            } else {
                
                alert.setTitle("Statistical Report #1");
                alert.setHeaderText("NUMBER OF APPOINTMENTS BY TYPE\n(For Each Month)");
                alert.setContentText("\nAn error has occurred.\n\n");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                
                nextButtonWasClicked = true;
            }
            
        }
        catch(OutOfMemoryError error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            
            //I'm adding a "PREVIOUS" button and then hiding it because that prevents the dialog box 
            //from closing when the user hits the "X" in the upper right corner. This is good because 
            //I don't want the user to close the dialog box for Statistical Report #1 before they see 
            //Statistical Report #2 and Statistical Report #3.
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setVisible(false);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #1");
                alert.setHeaderText("NOMBRE DE RENDEZ-VOUS PAR TYPE\n(Pour chaque mois)");
                alert.setContentText("\nIl y a trop de statistiques à afficher.\n\n");
                
            } else {
                
                alert.setTitle("Statistical Report #1");
                alert.setHeaderText("NUMBER OF APPOINTMENTS BY TYPE\n(For Each Month)");
                alert.setContentText("\nThere are too many statistics to display.\n\n");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                
                nextButtonWasClicked = true;
            }
            
        }
        catch(NumberFormatException error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            
            //I'm adding a "PREVIOUS" button and then hiding it because that prevents the dialog box 
            //from closing when the user hits the "X" in the upper right corner. This is good because 
            //I don't want the user to close the dialog box for Statistical Report #1 before they see 
            //Statistical Report #2 and Statistical Report #3.
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setVisible(false);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #1");
                alert.setHeaderText("NOMBRE DE RENDEZ-VOUS PLANIFIÉS PAR TYPE\n(Pour chaque mois)");
                alert.setContentText("\nUne erreur de base de données s'est produite.\n\n");
                
            } else {
                
                alert.setTitle("Statistical Report #1");
                alert.setHeaderText("NUMBER OF APPOINTMENTS SCHEDULED BY TYPE\n(For Each Month)");
                alert.setContentText("\nA database error has occurred.\n\n");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                
                nextButtonWasClicked = true;
            }
            
        }
        
        
        return nextButtonWasClicked;
    }
    
    /**
     * This method calculates and outputs (on a dialog box) a schedule for 
     * every contact in the database; this schedule includes the ID, title, 
     * type, description, start date and time, end date and time, and 
     * customer involved for each appointment. 
     * @return Returns the name of the button clicked by the user ("NEXT" if 
     * the user clicks the Next button, "BACK" if the user clicks the button 
     * of type PREVIOUS, or just an empty string if the user does not click 
     * any button [e.g. if the user closes the dialog box by clicking the 
     * "X" button] ).
     */
    //returns name of button clicked by user ("NEXT", "BACK", or just an empty string if 
    //the user does not click any button [e.g. if the user closes the dialog box by clicking the 
    //"X" button] ).
    //WARNING: The "BACK" button is actually a button of type "PREVIOUS".
    private String calculateAndDisplayStatisticalReport2(){
        
        //This is Statistical Report #2.
        
        ObservableList<Contacts> listOfAllContacts = DataHolder.getListOfAllContacts();
        ObservableList<Appointments> listOfAllAppointmentsInvolvingACertainContact = null;
        boolean thereAreContactsInTheDatabase = false;
        StringBuilder scheduleToOutput = new StringBuilder();
        
        String nameOfButtonClicked = "";
        
        try
        {   
            for(Contacts contact : listOfAllContacts){
                
                thereAreContactsInTheDatabase = true;

                if(listOfAllAppointmentsInvolvingACertainContact != null){

                    listOfAllAppointmentsInvolvingACertainContact.clear();
                }
                
                listOfAllAppointmentsInvolvingACertainContact = AppointmentsCRUD.getFromDatabaseAllAppointmentsInvolvingACertainContact(contact.getContactID());
                
                if(localLanguageIsFrench){
                    scheduleToOutput.append("\n•  " + contact.getContactName().toUpperCase() + " est programmé pour les rendez-vous suivants:\n\n");
                } else {
                    scheduleToOutput.append("\n•  " + contact.getContactName().toUpperCase() + " is scheduled for the following appointments:\n\n");
                }


                if(listOfAllAppointmentsInvolvingACertainContact != null){
                    
                    
                    if(listOfAllAppointmentsInvolvingACertainContact.size() > 0){
                        
                        for(Appointments appointment : listOfAllAppointmentsInvolvingACertainContact){
                            
                            if(localLanguageIsFrench){
                                scheduleToOutput.append("        ◦  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITRE:  \"" + 
                                                                                   appointment.getAppointmentName() + "\"" + "\n" + 
                                                        "           TAPER:  " +  appointment.getAppointmentType() + "\n" +
                                                        "           DESCRIPTION:  " +  appointment.getAppointmentDescription() + "\n" + 
                                                        "           HEURE DE DÉBUT:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + "\n" + 
                                                        "           HEURE DE FIN:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + "\n" + 
                                                        "           CLIENT:  " + appointment.getNameOfCustomerInvolved() + " (ID: #" + 
                                                                                    appointment.getIdOfCustomerInvolved() + ")\n\n");
                            } else {
                                scheduleToOutput.append("        ◦  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITLE:  \"" + 
                                                                                   appointment.getAppointmentName() + "\"" + "\n" + 
                                                        "           TYPE:  " +  appointment.getAppointmentType() + "\n" +
                                                        "           DESCRIPTION:  " +  appointment.getAppointmentDescription() + "\n" + 
                                                        "           START TIME:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + "\n" + 
                                                        "           END TIME:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + "\n" + 
                                                        "           CUSTOMER:  " + appointment.getNameOfCustomerInvolved() + " (ID: #" + 
                                                                                    appointment.getIdOfCustomerInvolved() + ")\n\n");
                            }
                            
                        }
                        
                    } else {
                        
                        if(localLanguageIsFrench){
                            scheduleToOutput.append("        ◦  Ce contact n'a pas de rendez-vous planifiés.\n\n");
                        } else {
                            scheduleToOutput.append("        ◦  This contact has no appointments scheduled.\n\n");
                        }
                        
                    }
                    
                } else {

                    if(localLanguageIsFrench){
                            scheduleToOutput.append("        ◦  Ce contact n'a pas de rendez-vous planifiés.\n\n");
                    } else {
                        scheduleToOutput.append("        ◦  This contact has no appointments scheduled.\n\n");
                    }
                }

            }
            
            if(thereAreContactsInTheDatabase){
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setResizable(true);
                alert.getDialogPane().setMaxWidth(Region.USE_PREF_SIZE);
                alert.getDialogPane().setMaxWidth(435);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.getDialogPane().setMinHeight(460);
                alert.getButtonTypes().clear();
                alert.getButtonTypes().add(ButtonType.NEXT);
                alert.getButtonTypes().add(ButtonType.PREVIOUS);
                                
                TextArea textArea = new TextArea( scheduleToOutput.toString() );
                textArea.setEditable(false);

                alert.getDialogPane().setContent(textArea);
                
                if(localLanguageIsFrench){
                    
                    alert.setTitle("Rapport statistique #2");
                    alert.setHeaderText("                          CALENDRIER DE RENDEZ-VOUS\n" + 
                                        "                                   (Pour chaque contact)");
                    ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");
                    
                } else {
                    
                    alert.setTitle("Statistical Report #2");
                    alert.setHeaderText("                             APPOINTMENT SCHEDULE\n" + 
                                        "                                    (For Each Contact)");
                    ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
                }
                

                Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
                if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                    
                    nameOfButtonClicked = "NEXT";
                    
                } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){
                    
                    nameOfButtonClicked = "BACK";
                }
                
            } else {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.getButtonTypes().clear();
                alert.getButtonTypes().add(ButtonType.NEXT);
                alert.getButtonTypes().add(ButtonType.PREVIOUS);
                
                if(localLanguageIsFrench){
                    
                    alert.setTitle("Rapport statistique #2");
                    alert.setHeaderText("CALENDRIER DE RENDEZ-VOUS (Pour chaque contact)");
                    alert.setContentText("\nIl n'y a pas de contacts dans la base de données.\n\n");
                    ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");
                    
                } else {
                    
                    alert.setTitle("Statistical Report #2");
                    alert.setHeaderText("APPOINTMENT SCHEDULE (For Each Contact)");
                    alert.setContentText("\nThere are no contacts in the database.\n\n");
                    ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
                }
                                
                
                Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
                if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){
                    
                    nameOfButtonClicked = "NEXT";
                    
                } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){
                    
                    nameOfButtonClicked = "BACK";
                }
            }
            
        }
        catch(NullPointerException error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            alert.getButtonTypes().add(ButtonType.PREVIOUS);

            if(localLanguageIsFrench){

                alert.setTitle("Rapport statistique #2");
                alert.setHeaderText("CALENDRIER DE RENDEZ-VOUS (Pour chaque contact)");
                alert.setContentText("\nIl n'y a pas de contacts dans la base de données.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");

            } else {

                alert.setTitle("Statistical Report #2");
                alert.setHeaderText("APPOINTMENT SCHEDULE (For Each Contact)");
                alert.setContentText("\nThere are no contacts in the database.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){

                nameOfButtonClicked = "NEXT";

            } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){

                nameOfButtonClicked = "BACK";
            }
        }
        catch(OutOfMemoryError error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.NEXT);
            alert.getButtonTypes().add(ButtonType.PREVIOUS);

            if(localLanguageIsFrench){

                alert.setTitle("Rapport statistique #2");
                alert.setHeaderText("CALENDRIER DE RENDEZ-VOUS (Pour chaque contact)");
                alert.setContentText("\nIl y a trop de rendez-vous à afficher.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");

            } else {

                alert.setTitle("Statistical Report #2");
                alert.setHeaderText("APPOINTMENT SCHEDULE (For Each Contact)");
                alert.setContentText("\nThere are too many appointments to display.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.NEXT) ){

                nameOfButtonClicked = "NEXT";

            } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){

                nameOfButtonClicked = "BACK";
            }
        }
        
        
        return nameOfButtonClicked;
    }
    
    
    /**
     * This method calculates and outputs (on a dialog box) the number of 
     * scheduled appointments for each customer. 
     * @return Returns the name of the button clicked by the user ("DONE" if 
     * the user clicks the button of type FINISH, "BACK" if the user clicks 
     * the button of type PREVIOUS, or just an empty string if the user 
     * does not click any button [e.g. if the user closes the dialog box 
     * by clicking the "X" button] ).
     */
    //returns the name of the button that the user clicked ("DONE", "BACK", or an empty string 
    //if the user did not click a button [e.g. if the user closed the dialog box by clicking 
    //the "X"] ). 
    //WARNING: The "DONE" button is actually a button of type "FINISH", and the 
    //"BACK" button is actually a button of type "PREVIOUS".
    private String calculateAndDisplayStatisticalReport3(){
        
        //This is Statistical Report #3
        
        String nameOfButtonClicked = "";
        
        try
        {
            ObservableList<Customers> listOfAllCustomers = DataHolder.getListOfAllCustomers();
            ArrayList<Integer> listOfAppointmentTotalForEachCustomer = AppointmentsCRUD.getFromDatabaseTheAppointmentTotalForEachCustomer();
            StringBuilder textToOutput = new StringBuilder();

            String nameOfCustomer = "";
            int tracker = 0;
            

            while( tracker < listOfAppointmentTotalForEachCustomer.size() ){

                int currentCustomerID = listOfAppointmentTotalForEachCustomer.get(tracker);

                int apptTotalForCurrentCustomer = listOfAppointmentTotalForEachCustomer.get(tracker + 1);
                
                for(Customers customer: listOfAllCustomers){

                    if(customer.getCustomerID() == currentCustomerID){

                        nameOfCustomer = customer.getCustomerName();
                    }
                }
                
                //Singular vs. plural grammar
                if(apptTotalForCurrentCustomer == 1){
                    
                    if(localLanguageIsFrench){
                        textToOutput.append("•  " + nameOfCustomer.toUpperCase() + " (ID: #" + currentCustomerID + ") a actuellement " + 
                                            apptTotalForCurrentCustomer + " rendez-vous de prévu.\n\n\n");
                    } else {
                        textToOutput.append("•  " + nameOfCustomer.toUpperCase() + " (ID: #" + currentCustomerID + ") currently has " + 
                                            apptTotalForCurrentCustomer + " appointment scheduled.\n\n\n");
                    }
                                        
                } else {
                    
                    if(localLanguageIsFrench){
                        textToOutput.append("•  " + nameOfCustomer.toUpperCase() + " (ID: #" + currentCustomerID + ") a actuellement " + 
                                            apptTotalForCurrentCustomer + " rendez-vous programmés.\n\n\n");
                    } else {
                        textToOutput.append("•  " + nameOfCustomer.toUpperCase() + " (ID: #" + currentCustomerID + ") currently has " + 
                                            apptTotalForCurrentCustomer + " appointments scheduled.\n\n\n");
                    }
                    
                }
                

                tracker = tracker + 2;
            }

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.setResizable(true);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinWidth(500);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinHeight(460);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.FINISH);
            
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
                        
            TextArea textArea = new TextArea( "\n" + textToOutput.toString() );
            textArea.setEditable(false);

            alert.getDialogPane().setContent(textArea);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #3");
                alert.setHeaderText("                          NOMBRE DE RENDEZ-VOUS PAR CLIENT" + 
                                    "\n                                         (par ordre décroissant)");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Fini");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");
                
            } else {
                
                alert.setTitle("Statistical Report #3");
                alert.setHeaderText("                     NUMBER OF APPOINTMENTS PER CUSTOMER" + 
                                    "\n                                         (in Descending Order)");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Done");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
                
            }
            

            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){

                nameOfButtonClicked = "BACK";

            } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.FINISH) ){

                nameOfButtonClicked = "DONE";
            }
        }
        catch(NullPointerException error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
           
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.FINISH);
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #3");
                alert.setHeaderText("NOMBRE DE RENDEZ-VOUS PAR CLIENT\n(par ordre décroissant)");
                alert.setContentText("\nIl n'y a pas de clients dans la base de données.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Fini");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");
                
            } else {
                
                alert.setTitle("Statistical Report #3");
                alert.setHeaderText("NUMBER OF APPOINTMENTS PER CUSTOMER\n(in Descending Order)");
                alert.setContentText("\nThere are no customers in the database.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Done");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){

                nameOfButtonClicked = "BACK";

            } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.FINISH) ){

                nameOfButtonClicked = "DONE";
            }
        }
        catch(OutOfMemoryError error){
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.FINISH);
            alert.getButtonTypes().add(ButtonType.PREVIOUS);
            
            if(localLanguageIsFrench){
                
                alert.setTitle("Rapport statistique #3");
                alert.setHeaderText("NOMBRE DE RENDEZ-VOUS PAR CLIENT\n(par ordre décroissant)");
                alert.setContentText("\nIl y a trop de clients pour afficher des statistiques.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Fini");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Retourner");
                
            } else {
                
                alert.setTitle("Statistical Report #3");
                alert.setHeaderText("NUMBER OF APPOINTMENTS PER CUSTOMER\n(in Descending Order)");
                alert.setContentText("\nThere are too many customers to display statistics about.\n\n");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.FINISH)) ).setText("Done");
                ( (Button) (alert.getDialogPane().lookupButton(ButtonType.PREVIOUS)) ).setText("Back");
            }
            
            
            Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
            
            if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.PREVIOUS) ){

                nameOfButtonClicked = "BACK";

            } else if ( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.FINISH) ){

                nameOfButtonClicked = "DONE";
            }
        }
        
        
        return nameOfButtonClicked;
    }
    
    
    
    
    
}
