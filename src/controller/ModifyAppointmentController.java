/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database_CRUD.AppointmentsCRUD;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.DataHolder;
import model.Users;

/**
 * This is an FXML Controller class which provides functionality to the Modify 
 * Appointment screen (the Modify Appointment FXML file). 
 *
 * @author Alano Peirce
 */
public class ModifyAppointmentController implements Initializable {
    /**
     * This is the header of the Modify Appointment page - it reads 
     * "MODIFY APPOINTMENT". 
     */
    @FXML
    private Label modifyAppointmentLabel;
    /**
     * This is the header text that precedes the appointment ID text field. 
     */
    @FXML
    private Label identificationNumberLabel;
    /**
     * This is the text field that displays the ID of the appointment 
     * currently being modified; however, the user cannot modify the 
     * contents of this text field, as appointment IDs are auto-generated 
     * and should not be modified by the user. 
     */
    @FXML
    private TextField apptIdTextField;
    /**
     * This is the header text that precedes the appointment title text field. 
     */
    @FXML
    private Label titleLabel;
    /**
     * This is the text field where the user can input a new title (if 
     * they wish to modify the title for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptTitleTextField;
     /**
     * This is the header text that precedes the appointment description 
     * text field. 
     */
    @FXML
    private Label descriptionLabel;
    /**
     * This is the text field where the user can input a new description (if 
     * they wish to modify the description for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptDescriptionTextField;
    /**
     * This is the header text that precedes the appointment type text field. 
     */
    @FXML
    private Label typeLabel;
    /**
     * This is the text field where the user can input a new type (if 
     * they wish to modify the type for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptTypeTextField;
    /**
     * This is the header text that precedes the appointment location text 
     * field. 
     */
    @FXML
    private Label locationLabel;
    /**
     * This is the text field where the user can input a new location (if 
     * they wish to modify the location for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptLocationTextField;
    /**
     * This is the header text that precedes the Date Picker that is used 
     * to indicate the appointment's start date. 
     */
    @FXML
    private Label startDateLabel;
    /**
     * This is the Date Picker where the user can input a new start date (if 
     * they wish to modify the start date for the appointment currently being 
     * modified). 
     */
    @FXML
    private DatePicker apptStartDateDatePicker;
    /**
     * This is the header text that precedes the appointment start time text 
     * field. 
     */
    @FXML
    private Label startTimeLabel;
     /**
     * This is the text field where the user can input a new start time (if 
     * they wish to modify the start time for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptStartTimeTextField;
    /**
     * This is the header text that precedes the appointment end time text 
     * field. 
     */
    @FXML
    private Label endTimeLabel;
    /**
     * This is the text field where the user can input a new end time (if 
     * they wish to modify the end time for the appointment currently being 
     * modified). 
     */
    @FXML
    private TextField apptEndTimeTextField;
    /**
     * This is the label wherein the text asks the user whether or not 
     * the new appointment extends into the next day. 
     */
    @FXML
    private Label endDateQuestionLabel;
    /**
     * A radio button marked "Yes"; if the user selects this, they are 
     * indicating that the appointment does indeed extend into the next 
     * day. 
     */
    @FXML
    private RadioButton endDateDifferentYES_RadioButton;
    /**
     * The toggle group consisting of the endDateDifferentYES_RadioButton and 
     * the endDateDifferentNO_RadioButton; putting these two radio buttons 
     * into the same toggle group ensures that only one of them can be 
     * selected at a time. 
     */
    @FXML
    private ToggleGroup endDateDifferentTG;
    /**
     * A radio button marked "No"; if the user selects this, they are 
     * indicating that the appointment does not extend into the next 
     * day (meaning that the appointment ends on the same date that 
     * it started). 
     */
    @FXML
    private RadioButton endDateDifferentNO_RadioButton;
     /**
     * This is the header text that precedes the combo box used to select 
     * the customer involved in the current appointment. 
     */
    @FXML
    private Label customerLabel;
    /**
     * A combo box (drop-down menu) used to select the customer that is 
     * involved in the current appointment. 
     */
    @FXML
    private ComboBox<Customers> apptCustomerComboBox;
    /**
     * This is the header text that precedes the combo box used to select 
     * the contact involved in the current appointment. 
     */
    @FXML
    private Label contactLabel;
    /**
     * A combo box (drop-down menu) used to select the contact that is 
     * involved in the current appointment. 
     */
    @FXML
    private ComboBox<Contacts> apptContactComboBox;
    /**
     * This is the header text that precedes the combo box used to select 
     * the user that is in charge of the current appointment. 
     */
    @FXML
    private Label userInChargeLabel;
    /**
     * A combo box (drop-down menu) used to select the user that is 
     * in charge of the current appointment. 
     */
    @FXML
    private ComboBox<Users> apptUserComboBox;
    /**
     * The button that updates the database record of the appointment 
     * currently being modified (by replacing the old details of the 
     * appointment record with the new user input); this button also brings the 
     * user back to the Appointments Table screen. 
     */
    @FXML
    private Button updateButton;
    /**
     * Brings the user back to the Appointments Table screen (if the user 
     * clicks the button) without updating the appointment. 
     */
    @FXML
    private Button backButton;
    /**
     * Brings the user back to the Login Screen (if the user 
     * clicks the button) without updating the appointment. 
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
    
    
    

    /**
     * Initializes the controller class. This method links an Observable List 
     * to each of the the three combo boxes on the Modify Appointment screen 
     * (the customer combo box, the contact combo box, and the user combo box). 
     * These Observable Lists are what will be displayed in each combo box. 
     * Additionally, this method determines the ZoneId, Locale, and local 
     * language (based on the retrieved Locale) of the current user; using 
     * this information, this method decides whether or not to translate 
     * the Modify Appointment screen to French.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        apptCustomerComboBox.setItems(DataHolder.getListOfAllCustomers());
        apptContactComboBox.setItems(DataHolder.getListOfAllContacts());
        apptUserComboBox.setItems(DataHolder.getListOfAllUsers());
        
        //--------------------------------------
        
        //Here, we find out where the current user is located. From that, we can figure out their local language.
        Locale localeOfCurrentUser = DataHolder.getLocaleOfCurrentUser();
        String localLanguage = localeOfCurrentUser.getDisplayLanguage().toString();
        
        //Here, we set everything on the Login Screen to French if the user's local language is French. Otherwise, we keep everything in English.
        if(localLanguage.equals("French") || localeOfCurrentUser.toString().startsWith("fr") || localeOfCurrentUser.getDisplayName().equals("français") ){
            
            localLanguageIsFrench = true;
            translateScreenToFrench();
        }
        
    }
    
     /* This method sets all of the input fields (text fields, combo boxes, radio buttons,  
        etc.) on the "Modify Appointment" screen to reflect the values of the row in the  
        Appointments TableView that the user selected. Thus, this method is called from the 
        Appointments Table Controller, where the row (object) that the user selected is 
        retrieved and then fed into this function as the parameter. 
    */
    /**
     * This method sets all of the input fields (text fields, combo boxes, 
     * radio buttons, etc.) on the Modify Appointment screen to reflect the 
     * values of the appointment record in the Appointments TableView that the 
     * user selected. Thus, this method is called from the Appointments 
     * Table Controller, where the appointment record (a.k.a. Appointments 
     * object) that the user selected is retrieved and then fed into 
     * this function as the parameter. 
     * @param selectedAppointment The appointment (a.k.a. Appointments 
     * object) that the user selected in the Appointments TableView 
     * (on the Appointments Table screen) before clicking the "Modify" 
     * button (also on the Appointments Table screen). (That "Modify" button 
     * is what brought the user onto the current screen [the Modify 
     * Appointment screen]).
     */
    public void setInputFieldsToContentOfSelectedAppointment(Appointments selectedAppointment){
        
        apptIdTextField.setText( String.valueOf(selectedAppointment.getAppointmentID()) );
        apptTitleTextField.setText(selectedAppointment.getAppointmentName());
        apptDescriptionTextField.setText(selectedAppointment.getAppointmentDescription());
        apptLocationTextField.setText(selectedAppointment.getAppointmentLocation());
        apptTypeTextField.setText(selectedAppointment.getAppointmentType());
        
        
        /*Since "selectedAppointment" holds information pulled straight from the database 
          (since the selected appointment was selected from the Appointments TableView, which shows 
          the contents of one of the DataHolder class's appointments lists, which get their data 
          straight from the database [using the SELECT function from AppointmentsCRUD] ), the start 
          time and end time variables always have a "seconds" value (because the database stores  
          the times as part of DATETIME variables, and the times in DATETIME variables always 
          have a "seconds" value).
        
          Thus, I am truncating the seconds before displaying the times on the "Modify Appointment" 
          screen. This is because I tell the user to input times as "HH:MM", so it would be 
          pretty hypocritical of me to automatically provide them with a time formatted as 
          "HH:MM:SS" (since the "Modify Appointment" screen automatically fills in the input fields 
          with the values of the object/row that the user wanted to modify).
        */
        LocalTime appointmentStartTime = LocalTime.parse(selectedAppointment.getAppointmentStartTime());
        appointmentStartTime = appointmentStartTime.truncatedTo(ChronoUnit.MINUTES);
        
        LocalTime appointmentEndTime = LocalTime.parse(selectedAppointment.getAppointmentEndTime());
        appointmentEndTime = appointmentEndTime.truncatedTo(ChronoUnit.MINUTES);
        
        apptStartTimeTextField.setText(appointmentStartTime.toString());
        apptEndTimeTextField.setText(appointmentEndTime.toString());
        
        
        LocalDate appointmentStartDate = LocalDate.parse(selectedAppointment.getAppointmentStartDate());
        apptStartDateDatePicker.setValue(appointmentStartDate);
                
        LocalDate appointmentEndDate = LocalDate.parse(selectedAppointment.getAppointmentEndDate());
        
        //If the start date and end date of the selected appointment are the same, then set the 
        //"NO" radio button to "selected". Otherwise, if the end date of the selected appointment 
        //is one day after the start date, then set the "YES" radio button to "selected". If 
        //something other than these two conditions is true, then something went wrong in the 
        //program somewhere.
        if(appointmentEndDate.equals(appointmentStartDate)){
            endDateDifferentNO_RadioButton.setSelected(true);
        } else if ( appointmentEndDate.equals(appointmentStartDate.plusDays(1)) ){
            endDateDifferentYES_RadioButton.setSelected(true);
        } else {
            //Something went wrong somewhere
        }
                       
        //----------------------------------------------------------------------------------------------
        
        //In this block of code, we are running through the list of customers to see which customer
        //has an ID that matches up with the ID of the customer who is involved in the selected 
        //appointment. Once we figure out which customer object (technically, object of type Customers) 
        //matches up, we set the value of  the customer combo box to be that customer object (object 
        //of type "Customers").
        
        Customers customerInvolvedInSelectedAppointment = null;  //I had to initialize this to something
        
        for(Customers customer: DataHolder.getListOfAllCustomers()){
            
            if( customer.getCustomerID() == selectedAppointment.getIdOfCustomerInvolved() ){
                
                customerInvolvedInSelectedAppointment = customer;
            }
        }
        
        apptCustomerComboBox.setValue(customerInvolvedInSelectedAppointment);
        apptCustomerComboBox.setItems(DataHolder.getListOfAllCustomers());
                
        //----------------------------------------------------------------------------------------------
        
        //In this block of code, we are running through the list of contacts to see which contact
        //has an ID that matches up with the ID of the contact who is involved in the selected 
        //appointment. Once we figure out which contact object (technically, object of type Contacts) 
        //matches up, we set the value of  the contact combo box to be that contact object (object 
        //of type "Contacts").
        
        Contacts contactInvolvedInSelectedAppointment = null;  //I had to initialize this to something
        
        for(Contacts contact: DataHolder.getListOfAllContacts()){
            
            if( contact.getContactID() == selectedAppointment.getIdOfContactInvolved() ){
                
                contactInvolvedInSelectedAppointment = contact;
            }
        }
        
        apptContactComboBox.setValue(contactInvolvedInSelectedAppointment);
        apptContactComboBox.setItems(DataHolder.getListOfAllContacts());
        
        //----------------------------------------------------------------------------------------------
        
        //In this block of code, we are running through the list of users to see which user
        //has an ID that matches up with the ID of the user who is in charge of the selected 
        //appointment. Once we figure out which user object (technically, object of type Users) 
        //matches up, we set the value of  the user combo box to be that user object (object 
        //of type "Users").
        
        Users userInChargeOfSelectedAppointment = null; //I had to initialize this to something
        
        for(Users user: DataHolder.getListOfAllUsers()){
            
            if( user.getUserID() == selectedAppointment.getIdOfUserInCharge() ){
                
                userInChargeOfSelectedAppointment = user;
            }
        }
        
        apptUserComboBox.setValue(userInChargeOfSelectedAppointment);
        apptUserComboBox.setItems(DataHolder.getListOfAllUsers());
        
        
    }
    
    /**
     * This method is called if the user's local language is determined to be 
     * French; it translates all components on the Modify Appointments screen 
     * to French. 
     */
    private void translateScreenToFrench(){
        modifyAppointmentLabel.setText(" MODIFIER LE RENDEZ-VOUS");
        modifyAppointmentLabel.setFont(new Font("Arial Black", 27));
        backButton.setText("Retourner");
        backButton.setFont(new Font("Segoe UI Semibold", 13));
        signOutButton.setText(" Déloguer");
        identificationNumberLabel.setText("Numéro d'identification");
        apptIdTextField.setPromptText("Genere automatiquement");
        titleLabel.setText("Titre");
        descriptionLabel.setText("Description");
        typeLabel.setText("Taper");
        locationLabel.setText("Lieu");
        startDateLabel.setText("Date");
        startTimeLabel.setText("Heure de début");
        endTimeLabel.setText("Heure de fin");
        endDateQuestionLabel.setText("Le rendez-vous se poursuit-il le lendemain?");
        endDateQuestionLabel.setPadding(new Insets(0, 0, 0, 20));
        endDateDifferentYES_RadioButton.setText("Oui");
        endDateDifferentNO_RadioButton.setText("Non");
        customerLabel.setText("Client");
        apptCustomerComboBox.setPromptText("Sélectionnez un client");
        contactLabel.setText("Contact");
        apptContactComboBox.setPromptText("Sélectionnez un contact");
        userInChargeLabel.setText("Utilisateur en charge");
        apptUserComboBox.setPromptText("Sélectionnez un utilisateur");
        updateButton.setText("Réactualiser");
        updateButton.setPrefWidth(165);
    }
    
    
    /**
     * This method is called when the user clicks the "Update" 
     * button at the bottom of the Modify Appointment screen; it replaces 
     * the old contents of the appointment record that the user was 
     * modifying with the new user-input details (which may or may not have 
     * been modified by the user). Additionally, this method also brings 
     * the user back to the Appointments Table screen. However, before 
     * updating the appointment record with the new user input, this method 
     * checks to make sure that all input is valid; if it is not, a 
     * descriptive error dialog box is output to the user that prompts them 
     * to edit that input (and also gives them pointers on what would be 
     * considered valid input). NOTE: The only input field that is allowed 
     * to remain empty is the "Description" field; that way, if a user 
     * needs to modify an appointment but isn't entirely sure yet about 
     * the specifics of that appointment, they are still able to modify 
     * the appointment and can input the description at a later point in 
     * time.
     * @param event The ActionEvent that occurred; in this case, the 
     * "Update" button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionUpdateAppointment(ActionEvent event) throws IOException {
        
        try {
            
            //In the next several blocks of code, we are retrieving the values that the user inserted 
            //into each input field (text fields, combo boxes, and radio buttons) or the values that were 
            //already there (if the customer didn't change them). Then, we are using all of that 
            //data to update the existing record in the database (in the "Appointments" table) that 
            //has the matching appointment ID.

            int appointmentID = Integer.parseInt(apptIdTextField.getText());
            String appointmentName = apptTitleTextField.getText();
            String appointmentDescription = apptDescriptionTextField.getText();
            String appointmentLocation = apptLocationTextField.getText();
            String appointmentType = apptTypeTextField.getText();

            LocalDate appointmentStartDate = apptStartDateDatePicker.getValue();
            LocalDate appointmentEndDate = appointmentStartDate; //I had to initialize this variable, so that's why I set it equal to something here.
            if(endDateDifferentNO_RadioButton.isSelected()){
                appointmentEndDate = appointmentStartDate;
            } else if (endDateDifferentYES_RadioButton.isSelected()){
                appointmentEndDate = appointmentStartDate.plusDays(1);
            }

            String appointmentStartTime = apptStartTimeTextField.getText();
            LocalTime apptStartTimeLocalTimeVar = LocalTime.parse(appointmentStartTime);
            
            String appointmentEndTime = apptEndTimeTextField.getText();
            LocalTime apptEndTimeLocalTimeVar = LocalTime.parse(appointmentEndTime);
            
            Customers customerInvolved = apptCustomerComboBox.getValue();
            int idOfCustomerInvolved = customerInvolved.getCustomerID();

            Contacts contactInvolved = apptContactComboBox.getValue();
            int idOfContactInvolved = contactInvolved.getContactID();

            Users userInCharge = apptUserComboBox.getValue();
            int idOfUserInCharge = userInCharge.getUserID();
            
            //------------------------------------------------------------------
            
            //The next few blocks of code deal with the start datetime and end datetime of the 
            //appointment that the user wants to schedule. It converts those datetimes from 
            //the user's local time zone to EST. This is because, after this (in the giant 
            //"if" statement that follows), we will error-check to make sure that the 
            //appointment's start and end times are not outside of the company's 
            //business hours (which are 8 a.m. to 10 p.m. EST).
            
            LocalDateTime appointmentStartDateAndTime = appointmentStartDate.atTime(apptStartTimeLocalTimeVar);
            LocalDateTime appointmentEndDateAndTime = appointmentEndDate.atTime(apptEndTimeLocalTimeVar);
            
            ZoneId zoneIdOfUser = ZoneId.systemDefault();
            ZoneId zoneIdOfCompanysBusinessHours = ZoneId.of("Etc/GMT+5"); //EST is GMT minus 5 hours, which is written as "GMT+5" for some reason in code
            
            LocalDateTime startDateAndTimeConvertedToEST =  appointmentStartDateAndTime.atZone(zoneIdOfUser)
                                                                                       .withZoneSameInstant(zoneIdOfCompanysBusinessHours)
                                                                                       .toLocalDateTime();
            
            LocalDateTime endDateAndTimeConvertedToEST =  appointmentEndDateAndTime.atZone(zoneIdOfUser)
                                                                                   .withZoneSameInstant(zoneIdOfCompanysBusinessHours)
                                                                                   .toLocalDateTime();
            
            LocalTime appointmentStartTimeInEST = startDateAndTimeConvertedToEST.toLocalTime();
            LocalTime appointmentEndTimeInEST = endDateAndTimeConvertedToEST.toLocalTime();
            LocalTime openingTimeOfCompanyInEST = LocalTime.parse("08:00:00");
            LocalTime closingTimeOfCompanyInEST = LocalTime.parse("22:00:00");
            
            //This was just some test code that I used to test the functionality of the user's-time-zone-to-EST-conversion.
            //System.out.println("User input -----------  Start: " + apptStartTimeLocalTimeVar + " End: " + apptEndTimeLocalTimeVar);
            //System.out.println("Same times in EST ----  Start: " + appointmentStartTimeInEST + " End: " + appointmentEndTimeInEST);
            
            //These variables are for later, in the giant if/else statement. Basically, if these two 
            //variables are different (implying, from the way I've written my code and set up the 
            //program, that the end date is the day after the start date), then we output an error. 
            //Because these two dates have been converted to EST, we never want these two variables 
            //to have different values -- this is because we never want to have overnight appointments.
            //This differs from the fact that we allow the *user* to input an end date that is the day 
            //after the start date; this is because in the user's time zone, they may have to indicate 
            //that the end of the appointment is on a different day (and this is fine as long as the 
            //appointment starts and ends on the same day in EST).
            LocalDate startDateOfAppointmentInEST = startDateAndTimeConvertedToEST.toLocalDate();
            LocalDate endDateOfAppointmentInEST = endDateAndTimeConvertedToEST.toLocalDate();
            
            //------------------------------------------------------------------
            
            boolean thereIsAnInputError = false;
            
            if(appointmentName.equals("")){
                
                thereIsAnInputError = true;
                
               if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez fournir un titre pour le rendez-vous.\n\n");
                    alert.setTitle("Aucun titre fourni");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide a title for the appointment.\n\n");
                    alert.setTitle("No Title Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentType.equals("")){
                
                thereIsAnInputError = true;
                
               if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez indiquer un type de rendez-vous.\n\n");
                    alert.setTitle("Aucun emplacement fourni");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease indicate a type for the appointment.\n\n");
                    alert.setTitle("No Location Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentLocation.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez indiquer un lieu pour le rendez-vous.\n\n");
                    alert.setTitle("Aucun emplacement fourni");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide a location for the appointment.\n\n");
                    alert.setTitle("No Location Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentStartDate == null){
                
                thereIsAnInputError = true;
                
               if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nMerci de préciser une date de rendez-vous.\n\n");
                    alert.setTitle("Aucune date fournie");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide a date for the appointment.\n\n");
                    alert.setTitle("No Date Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentStartTime.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez indiquer une heure de début de rendez-vous.\n\n");
                    alert.setTitle("Aucune heure de début fournie");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide a start time for the appointment.\n\n");
                    alert.setTitle("No Start Time Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentEndTime.equals("")){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez indiquer une heure de fin pour le rendez-vous.\n\n");
                    alert.setTitle("Aucune heure de fin fournie");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide an end time for the appointment.\n\n");
                    alert.setTitle("No End Time Provided");
                    alert.showAndWait();
                }
                
            } else if (appointmentName.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe titre du rendez-vous ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe appointment's Title cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                
            } else if (appointmentDescription.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLa description du rendez-vous ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe appointment's Description cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                
            } else if (appointmentType.length() > 50){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe Type du rendez-vous ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe appointment's Type cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                
            } else if (appointmentLocation.length() > 50){
                
                thereIsAnInputError = true;
                
               if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nL'emplacement du rendez-vous ne peut pas dépasser 50 caractères.\n\n");
                    alert.setTitle("Limite de 50 caractères");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe appointment's Location cannot be longer than 50 characters.\n\n");
                    alert.setTitle("50 Character Limit");
                    alert.showAndWait();
                }
                
            } else if ( !endDateDifferentYES_RadioButton.isSelected() && !endDateDifferentNO_RadioButton.isSelected() ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez indiquer si le rendez-vous se poursuit ou non le lendemain.\n\n");
                    alert.setTitle("Date de fin non indiquée");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease indicate whether or not the appointment continues into the next day.\n\n");
                    alert.setTitle("End Date Not Indicated");
                    alert.showAndWait();
                }
                
            } else if ( apptEndTimeLocalTimeVar.isBefore(apptStartTimeLocalTimeVar) && endDateDifferentNO_RadioButton.isSelected() ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nÉtant donné que le rendez-vous ne se prolonge pas jusqu'au lendemain, " + 
                                                                   "l'heure de fin doit être supérieure à l'heure de début.\n\n");
                    alert.setTitle("L'heure de fin doit être postérieure à l'heure de début");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nSince the appointment does not extend into the next day, " + 
                                                                   "the end time must be greater than the start time.\n\n");
                    alert.setTitle("End Time Must Be After Start Time");
                    alert.showAndWait();
                }
                
            } else if ( (appointmentStartTimeInEST.isBefore(openingTimeOfCompanyInEST) || appointmentStartTimeInEST.isAfter(closingTimeOfCompanyInEST))
                       && (appointmentEndTimeInEST.isBefore(openingTimeOfCompanyInEST) || appointmentEndTimeInEST.isAfter(closingTimeOfCompanyInEST)) ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLes rendez-vous ne peuvent pas avoir lieu en dehors des heures de bureau\n" + 
                                                                   "( 08:00 - 22:00  heure normale de l'Est ).\n\n" + 
                                                                   "Veuillez ajuster l'heure de début et l'heure de fin du rendez-vous.\n\n");
                    alert.setTitle("Heure invalide");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nAppointments cannot take place outside of business hours\n" + 
                                                                   "( 08:00 - 22:00  Eastern Standard Time ).\n\n" + 
                                                                   "Please adjust the appointment's Start Time and End Time.\n\n");
                    alert.setTitle("Invalid Time");
                    alert.showAndWait();
                }
                
            } else if ( appointmentStartTimeInEST.isBefore(openingTimeOfCompanyInEST) || appointmentStartTimeInEST.isAfter(closingTimeOfCompanyInEST) ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLes rendez-vous ne peuvent pas avoir lieu en dehors des heures de bureau\n" + 
                                                                   "( 08:00 - 22:00  heure normale de l'Est ).\n\n" + 
                                                                   "Veuillez ajuster l'heure de début du rendez-vous.\n\n");
                    alert.setTitle("Heure de début invalide");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nAppointments cannot take place outside of business hours\n" + 
                                                                   "( 08:00 - 22:00  Eastern Standard Time ).\n\n" + 
                                                                   "Please adjust the appointment's Start Time.\n\n");
                    alert.setTitle("Invalid Start Time");
                    alert.showAndWait();
                }
                
            } else if ( appointmentEndTimeInEST.isBefore(openingTimeOfCompanyInEST) || appointmentEndTimeInEST.isAfter(closingTimeOfCompanyInEST) ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLes rendez-vous ne peuvent pas avoir lieu en dehors des heures de bureau\n" + 
                                                                   "( 08:00 - 22:00  heure normale de l'Est ).\n\n" + 
                                                                   "Veuillez ajuster l'heure de fin du rendez-vous.\n\n");
                    alert.setTitle("Heure de fin non valide");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nAppointments cannot take place outside of business hours\n" + 
                                                                   "( 08:00 - 22:00  Eastern Standard Time ).\n\n" + 
                                                                   "Please adjust the appointment's End Time.\n\n");
                    alert.setTitle("Invalid End Time");
                    alert.showAndWait();
                }
                
            } else if ( endDateOfAppointmentInEST.isEqual(startDateOfAppointmentInEST.plusDays(1)) ){
                
                thereIsAnInputError = true;
                
                if(localLanguageIsFrench){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nLes rendez-vous ne peuvent pas avoir lieu en dehors des heures de bureau\n" + 
                                                                   "( 08:00 - 22:00  heure normale de l'Est ).\n\n" + 
                                                                   "Bien que l'heure de début et l'heure de fin de ce rendez-vous se " + 
                                                                   "situent pendant les heures ouvrables, une partie intermédiaire du " + 
                                                                   "rendez-vous a lieu *en dehors* des heures ouvrables.\n\n" + 
                                                                   "Veuillez ajuster l'heure de fin ou la date de fin du rendez-vous " + 
                                                                   "afin que le rendez-vous ne dépasse pas la fin des heures ouvrables.\n\n");
                    alert.setTitle("Heure invalide");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "\nAppointments cannot take place outside of business hours\n" + 
                                                                   "( 08:00 - 22:00  Eastern Standard Time ).\n\n" + 
                                                                   "Although the start time and end time of this appointment are within " + 
                                                                   "business hours, an intervening portion of the appointment occurs " + 
                                                                   "*outside* of business hours.\n\n" + 
                                                                   "Please adjust the appointment's End Time or End Date " + 
                                                                   "so that the appointment does not extend past the end of business hours.\n\n");
                    alert.setTitle("Invalid Time");
                    alert.showAndWait();
                }
                
            }
            
            //------------------------------------------------------------------
            
            //This if statement will check whether there are any overlapping appointments 
            //(for the particular customer that the user is currently trying to create 
            //an appointment for). If so, an error is output to the UI.
            if(!thereIsAnInputError){
                
                ObservableList<Appointments> listOfAnyOverlappingAppointments = AppointmentsCRUD.getAnyOverlappingAppointmentsFromDatabase(appointmentStartDateAndTime, 
                                                                                                                                           appointmentEndDateAndTime, 
                                                                                                                                           idOfCustomerInvolved);
                //There's a possibility that the list could be equal to null (meaning it was 
                //uninitialized), which is why I have to check whether the list is equal to 
                //null or not before doing anything else with list (including checking the 
                //size, which would throw an exception if the list does equal to null).
                if(listOfAnyOverlappingAppointments != null){
                    
                    //If there's only one overlapping appointment, the error that's output to the user uses singular grammar (versus plural).
                    //-------------
                    //There's two scenarios at play here: the appointment currently being modified might overlap with itself, or it might not. 
                    //If the size of the list of overlapping appointments is 2, then we know for sure that there is at least one *actually* 
                    //overlapping appointment (i.e. one appointment that overlaps with the appointment currently being modified THAT ISN'T 
                    //ITSELF). Since we're about to enter the output-singular-grammar section, we check to make sure that one of the 
                    //objects in the 2-sized list of overlapping appointments IS INDEED the appointment being modified itself (because that 
                    //means that there is only one TRUE overlapping appointment, thus calling for the singular grammar.
                    //On the other hand, if the size of the list of overlapping appointments is 1, there's a possibility that that 1 
                    //appointment is the appointment currently being modified (a.k.a. it overlaps with itself); thus, we have to check that 
                    //that is NOT the case so that we don't erroneously output an error dialog box to the user.
                    if( (listOfAnyOverlappingAppointments.size() == 2 && 
                            (listOfAnyOverlappingAppointments.get(0).getAppointmentID() == appointmentID 
                            || listOfAnyOverlappingAppointments.get(1).getAppointmentID() == appointmentID) ) 
                       || (listOfAnyOverlappingAppointments.size() == 1 && listOfAnyOverlappingAppointments.get(0).getAppointmentID() != appointmentID) ){
                        
                        
                        thereIsAnInputError = true;
                        
                        try
                        {
                            StringBuilder infoAboutEachOverlappingAppointment = new StringBuilder();
                        
                            for(Appointments appointment: listOfAnyOverlappingAppointments){

                                if(appointment.getAppointmentID() != appointmentID){

                                    if(localLanguageIsFrench){
                                        infoAboutEachOverlappingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITRE:  \"" + 
                                                                                                      appointment.getAppointmentName() + "\"" + "\n" + 
                                                                                   "   HEURE DE DÉBUT:  " + appointment.getAppointmentStartDateAndTimeInDMYand24HourTime() + 
                                                                                   "\n   HEURE DE FIN:  " + appointment.getAppointmentEndDateAndTimeInDMYand24HourTime() + 
                                                                                   "\n\n");
                                    } else {
                                        infoAboutEachOverlappingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITLE:  \"" + 
                                                                                                      appointment.getAppointmentName() + "\"" + "\n" + 
                                                                                   "   START TIME:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + 
                                                                                   "\n   END TIME:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + 
                                                                                   "\n\n");
                                    }
                                    
                                }
                            }


                            if(localLanguageIsFrench){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe client sélectionné, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), a déjà un rendez-vous qui a lieu pendant la période spécifiée.\n\n" + 
                                                                               "Le rendez-vous qui se chevauche est le suivant:\n\n" + 
                                                                                 infoAboutEachOverlappingAppointment.toString());
                                alert.setTitle("Chevauchement de rendez-vous");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe selected customer, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), already has an appointment that occurs during the specified time frame.\n\n" + 
                                                                               "The overlapping appointment is as follows:\n\n" + 
                                                                                 infoAboutEachOverlappingAppointment.toString());
                                alert.setTitle("Overlapping Appointment");
                                alert.showAndWait();
                            }
                            
                        }
                        catch(OutOfMemoryError error){
                            
                            if(localLanguageIsFrench){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe client sélectionné, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), a déjà un rendez-vous qui a lieu pendant la période spécifiée.\n\n");
                                alert.setTitle("Chevauchement de rendez-vous");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe selected customer, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), already has an appointment that occurs during the specified time frame.\n\n");
                                alert.setTitle("Overlapping Appointment");
                                alert.showAndWait();
                            }
                            
                        }
                        
                        
                    //If the list of overlapping appointments is of size 2, and NEITHER of those appointments are the appointment currently 
                    //being modified, then that means that the appointment currently being modified does NOT overlap with itself; thus, that 
                    //means that there are 2 actual appointments that overlap with the appointment currently being modified. Thus, in that 
                    //case, execution should enter THIS part of the if statement (versus the previous part), because we need PLURAL grammar 
                    //(which is what this "else if" is for).
                    //---------------------------------------
                    //Additionally, if the list of overlapping appointments has MORE than 2 appointments in it, then that automatically 
                    //means that there are at least 2 appointments that ACTUALLY overlap with the appointment currently being modified; 
                    //thus, that automatically calls for PLURAL GRAMMAR (which, again, is what this "else if" statement is for).
                    } else if (listOfAnyOverlappingAppointments.size() >= 2){
                        
                        thereIsAnInputError = true;
                        
                        try
                        {
                            StringBuilder infoAboutEachOverlappingAppointment = new StringBuilder();
                        
                            for(Appointments appointment: listOfAnyOverlappingAppointments){

                                if(appointment.getAppointmentID() != appointmentID){

                                    if(localLanguageIsFrench){
                                        infoAboutEachOverlappingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITRE:  \"" + 
                                                                                                      appointment.getAppointmentName() + "\"" + "\n" + 
                                                                                   "   HEURE DE DÉBUT:  " + appointment.getAppointmentStartDateAndTimeInDMYand24HourTime() + 
                                                                                   "\n   HEURE DE FIN:  " + appointment.getAppointmentEndDateAndTimeInDMYand24HourTime() + 
                                                                                   "\n\n");
                                    } else {
                                        infoAboutEachOverlappingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITLE:  \"" + 
                                                                                                      appointment.getAppointmentName() + "\"" + "\n" + 
                                                                                   "   START TIME:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + 
                                                                                   "\n   END TIME:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + 
                                                                                   "\n\n");
                                    }
                                    
                                }
                            }


                            if(localLanguageIsFrench){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe client sélectionné, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), a déjà des rendez-vous qui se produisent pendant la période spécifiée.\n\n" + 
                                                                               "Les rendez-vous qui se chevauchent sont répertoriés comme suit:\n\n" + 
                                                                                infoAboutEachOverlappingAppointment.toString());
                                alert.setTitle("Rendez-vous qui se chevauchent");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe selected customer, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), already has appointments that occur during the specified time frame.\n\n" + 
                                                                               "The overlapping appointments are listed as follows:\n\n" + 
                                                                                infoAboutEachOverlappingAppointment.toString());
                                alert.setTitle("Overlapping Appointments");
                                alert.showAndWait();
                            }
                            
                        }
                        catch(OutOfMemoryError error){
                            
                            if(localLanguageIsFrench){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nLe client sélectionné, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), a déjà des rendez-vous qui se produisent pendant la période spécifiée.\n\n");
                                alert.setTitle("Rendez-vous qui se chevauchent");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "\nThe selected customer, " + customerInvolved.getCustomerName() + 
                                                                               " (ID: #" + customerInvolved.getCustomerID() + 
                                                                               "), already has appointments that occur during the specified time frame.\n\n");
                                alert.setTitle("Overlapping Appointments");
                                alert.showAndWait();
                            }
                        }
                        
                    }
                    
                }
            }
            
            //------------------------------------------------------------------
            
            if(!thereIsAnInputError){
                
                AppointmentsCRUD.modifyAppointmentInDatabase(appointmentID, appointmentName, appointmentDescription, 
                                                             appointmentLocation, appointmentType, appointmentStartDate, 
                                                             appointmentEndDate, appointmentStartTime, appointmentEndTime, 
                                                             idOfCustomerInvolved, idOfUserInCharge, idOfContactInvolved);
            
                methodsLibrary.switchToDifferentSceneUsingButton("/view/AppointmentsTable.fxml", event);
            }

            
        }
        catch(NullPointerException error){
            
            if(localLanguageIsFrench){
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez vous assurer d'avoir fourni une date pour le rendez-vous " + 
                                                               "ainsi qu'une sélection dans chacun des menus déroulants.\n\n");
                alert.setTitle("Entrée manquante");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease ensure that you have provided a date for the appointment " + 
                                                               "as well as a selection from each of the drop-down menus.\n\n");
                alert.setTitle("Missing Input");
                alert.showAndWait();
            }
            
        }
        catch(DateTimeParseException error){
            
            if(localLanguageIsFrench){
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nVeuillez fournir l'heure de début et l'heure de fin " + 
                                                               "au format \"HH:MM\" (heure sur 24 heures).\n\n");
                alert.setTitle("Entrée manquante ou mal formatée");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "\nPlease provide the Start Time and End Time " + 
                                                               "in the format \"HH:MM\" (24-hour time).\n\n");
                alert.setTitle("Missing or Improperly-Formatted Input");
                alert.showAndWait();
            }
            
        }
        
    }
    
    /**
     * This method gets called whenever the "Back" button on the Modify 
     * Appointments screen is clicked by the user; first, a confirmation 
     * dialog box is output to the user (asking the user if they are sure 
     * they want to leave the "Modify Appointment" screen without saving 
     * their input); if they click "OK", this method switches the visible 
     * screen back to the "Appointments Table" screen. 
     * @param event The ActionEvent that occurred; in this case, the "Back" 
     * button was clicked.
     * @throws IOException Thrown by the FXMLLoader's "load()" method (which 
     * is used in the process of switching screens); the exception is only 
     * thrown if an error occurs while loading the desired scene.
     */
    @FXML
    private void onActionDisplayAppointmentsTable(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        if(localLanguageIsFrench){
            alert.setTitle("Les données peuvent ne pas être enregistrées");
            alert.setContentText("\nToute entrée non enregistrée sur cette page sera perdue.\n\nÊtes-vous sûr de vouloir quitter?\n\n");
        } else {
            alert.setTitle("Data May Be Unsaved");
            alert.setContentText("\nAny unsaved input on this page will be lost.\n\nAre you sure you want to leave?\n\n");
        }
        
        //This variable will hold whichever button the user clicks (since CONFIRMATION dialog 
        //boxes have both an "OK" button as well as a "CANCEL" button). NOTE: It's also possible 
        //for the user to click no button at all, since dialog boxes come with an "X" on the top 
        //right, which can be used to exit out of the dialog box.
        Optional<ButtonType> buttonClickedByUser = alert.showAndWait();
        
        //If the user clicked a button and that button is "OK", then we will sign the user out 
        //(a.k.a. switch the display to the login screen).
        if( buttonClickedByUser.isPresent() && buttonClickedByUser.get().equals(ButtonType.OK) ){
            
            methodsLibrary.switchToDifferentSceneUsingButton("/view/AppointmentsTable.fxml", event);
        }
        
    }
    
    
    /**
     * This method gets called whenever the "Sign out" button on the 
     * Modify Appointment screen is clicked by the user; it outputs a 
     * confirmation dialog box asking the user if they are sure that they want 
     * to sign out, and if the user confirms, then this method switches the 
     * visible screen to be the Login Screen once again. 
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
