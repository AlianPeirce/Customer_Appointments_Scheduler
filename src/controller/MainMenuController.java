/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import database_CRUD.AppointmentsCRUD;
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
import javafx.scene.control.Label;
import model.Appointments;
import model.DataHolder;

/**
 * This is an FXML Controller class which provides functionality to the Main 
 * Menu screen (the Main Menu FXML file). 
 *
 * @author LabUser
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label mainMenuLabel;
    @FXML
    private Label directingQuestionLabel;
    @FXML
    private Button customersButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button signOutButton;
    
    private boolean localLanguageIsFrench = false;
    
    //"DataHolder" is my own class that I created. I wrote a method (function) 
    //there that can be used to switch to a different scene.
    DataHolder methodsLibrary = new DataHolder();
    
    
    
    
    //This method is basically a controller file's equivalent to the "main()" method.
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Here, we find out where the current user is located. From that, we can figure out their local language.
        Locale localeOfCurrentUser = DataHolder.getLocaleOfCurrentUser();
        String localLanguage = localeOfCurrentUser.getDisplayLanguage().toString();
        
        //Here, we set everything on the Login Screen to French if the user's local language is French. Otherwise, we keep everything in English.
        if(localLanguage.equals("French") || localeOfCurrentUser.toString().startsWith("fr") || localeOfCurrentUser.getDisplayName().equals("français") ){
            
            localLanguageIsFrench = true;
            translateScreenToFrench();
        }
        
    }
    
    /* This method is called from the LoginScreenController (since the parameter of this 
       method requires passing data from the LoginScreenController to the MainMenuController).
       If a correct username and password is entered (and the user then hits the "Sign In" 
       button), the LoginScreenController (specifically, the method "onActionDisplayMainMenu()" 
       and/or the method "onEnter()" ) figures out which Users object (from the list of 
       all users in the DataHolder class) matches the input username and password combo. 
       Then, it takes the user ID from that Users object and calls this method below using 
       that user ID as a parameter.
    
       The function below displays a dialog box that either lists information about 
       the user's upcoming appointment(s) (appointments that start within the next 
       fifteen minutes) or lets the user know that there are no upcoming appointments. 
       If the list of information about the upcoming appointments is too large (a.k.a. 
       if it breaks the StringBuilder, which I fill using a loop) then I still output 
       a dialog box telling the user how many upcoming appointments they have, but I 
       don't list the information about each appointment and I instead tell them that 
       there was too much information to list.
       
       The only time this method is called is from the LoginScreenController, and whenever 
       a method in one controller is called from another controller, that method executes 
       before initialize() (in this controller) executes. Since initialize() is what sets 
       the class variable "localLanguageIsFrench" to true or false, the value of 
       "localLanguageIsFrench" is not going to be accurate while this method below is 
       executing. Thus, to get around that, I pass the Login Screen Controller's 
       "localLanguageIsFrench" class variable into this method instead, and I reference 
       that variable (instead of the counterpart variable in this class) throughout the 
       method below. This provides an accurate measure of whether or not the application 
       should be translated to French (a.k.a. whether or not the current user is in a 
       French-speaking country).
    */
    public void displayUpcomingAppointmentsOfUser(int userID, boolean usersLocalLanguageIsFrench){
        
        ObservableList<Appointments> listofUsersAppointmentsStartingInTheNext15Minutes 
                = AppointmentsCRUD.getFromDatabaseTheUsersAppointmentsThatStartWithinTheNextFifteenMinutes(userID);
        
        //The list could be uninitialized (meaning it is equal to null), so we have to check for that first 
        //(because doing any sort of operations on an uninitialized list, even checking its size, would 
        //lead to an Exception and the whole program breaking.
        if(listofUsersAppointmentsStartingInTheNext15Minutes != null){
            
            //If there's only one upcoming appointment, we display it using singular grammar.
            if(listofUsersAppointmentsStartingInTheNext15Minutes.size() == 1){
                try
                {
                    
                    StringBuilder infoAboutEachUpcomingAppointment = new StringBuilder();
                        
                    for(Appointments appointment: listofUsersAppointmentsStartingInTheNext15Minutes){
                        
                        if(usersLocalLanguageIsFrench){
                            infoAboutEachUpcomingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITRE:  \"" + 
                                                                                       appointment.getAppointmentName() + "\"" + "\n" + 
                                                                    "   HEURE DE DÉBUT:  " + appointment.getAppointmentStartDateAndTimeInDMYand24HourTime() + 
                                                                    "\n   HEURE DE FIN:  " + appointment.getAppointmentEndDateAndTimeInDMYand24HourTime() + 
                                                                    "\n\n");
                        } else {
                            infoAboutEachUpcomingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITLE:  \"" + 
                                                                                       appointment.getAppointmentName() + "\"" + "\n" + 
                                                                    "   START TIME:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + 
                                                                    "\n   END TIME:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + 
                                                                    "\n\n");
                        }
                        
                    }

                    if(usersLocalLanguageIsFrench){
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alerte");
                        alert.setContentText( "\nVous avez un rendez-vous qui commence dans les quinze prochaines minutes:\n\n" + 
                                              infoAboutEachUpcomingAppointment.toString() );
                        alert.setTitle("Rendez-vous à venir");
                        alert.show();
                        
                    } else {
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alert");
                        alert.setContentText( "\nYou have an appointment that begins within the next fifteen minutes:\n\n" + 
                                              infoAboutEachUpcomingAppointment.toString() );
                        alert.setTitle("Upcoming Appointment");
                        alert.show();
                    }
                    
                
                }
                catch(OutOfMemoryError error){
                    
                    if(usersLocalLanguageIsFrench){
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alerte");
                        alert.setContentText( "\nVous avez un rendez-vous qui commence dans les quinze prochaines minutes; " + 
                                              "cependant, les détails de ce rendez-vous sont extrêmement volumineux et ne peuvent donc pas être affichés.\n\n");
                        alert.setTitle("Rendez-vous à venir");
                        alert.show();
                        
                    } else {
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alert");
                        alert.setContentText( "\nYou have an appointment that begins within the next fifteen minutes; " + 
                                              "however, the details of this appointment are exceedingly large and thus cannot be displayed.\n\n");
                        alert.setTitle("Upcoming Appointment");
                        alert.show();
                    }
                    
                }
                
                        
            //If there's more than one upcoming appointment, we display it using plural grammar.    
            } else if(listofUsersAppointmentsStartingInTheNext15Minutes.size() > 1){
                
                try
                {
                    
                    StringBuilder infoAboutEachUpcomingAppointment = new StringBuilder();
                        
                    for(Appointments appointment: listofUsersAppointmentsStartingInTheNext15Minutes){
                        
                        if(usersLocalLanguageIsFrench){
                            infoAboutEachUpcomingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITRE:  \"" + 
                                                                                       appointment.getAppointmentName() + "\"" + "\n" + 
                                                                    "   HEURE DE DÉBUT:  " + appointment.getAppointmentStartDateAndTimeInDMYand24HourTime() + 
                                                                    "\n   HEURE DE FIN:  " + appointment.getAppointmentEndDateAndTimeInDMYand24HourTime() + 
                                                                    "\n\n");
                        } else {
                            infoAboutEachUpcomingAppointment.append("•  " + "ID:  #" + appointment.getAppointmentID() + "   |   " + "TITLE:  \"" + 
                                                                                       appointment.getAppointmentName() + "\"" + "\n" + 
                                                                    "   START TIME:  " + appointment.getAppointmentStartDateAndTimeInMDYand12HourTime() + 
                                                                    "\n   END TIME:  " + appointment.getAppointmentEndDateAndTimeInMDYand12HourTime() + 
                                                                    "\n\n");
                        }
                        
                    }

                    if(usersLocalLanguageIsFrench){
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alerte");
                        alert.setContentText( "\nVous avez " + String.valueOf(listofUsersAppointmentsStartingInTheNext15Minutes.size()) + 
                                              " rendez-vous qui commencent dans les quinze prochaines minutes:\n\n" + 
                                              infoAboutEachUpcomingAppointment.toString() );
                        alert.setTitle("Rendez-vous à venir");
                        alert.show();
                        
                    } else {
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alert");
                        alert.setContentText( "\nYou have " + String.valueOf(listofUsersAppointmentsStartingInTheNext15Minutes.size()) + 
                                              " appointments that begin within the next fifteen minutes:\n\n" + 
                                              infoAboutEachUpcomingAppointment.toString() );
                        alert.setTitle("Upcoming Appointments");
                        alert.show();
                    }
                    
                }
                catch(OutOfMemoryError error){
                    
                    if(usersLocalLanguageIsFrench){
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alerte");
                        alert.setContentText( "\nVous avez " + String.valueOf(listofUsersAppointmentsStartingInTheNext15Minutes.size()) + 
                                              " rendez-vous qui commencent dans les quinze prochaines minutes; " + 
                                              "cependant, les détails de ces rendez-vous sont extrêmement volumineux et ne peuvent donc pas être affichés.\n\n");
                        alert.setTitle("Rendez-vous à venir");
                        alert.show();
                        
                    } else {
                        
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("Alert");
                        alert.setContentText( "\nYou have " + String.valueOf(listofUsersAppointmentsStartingInTheNext15Minutes.size()) + 
                                              " appointments that begin within the next fifteen minutes; " + 
                                              "however, the details of these appointments are exceedingly large and thus cannot be displayed.\n\n");
                        alert.setTitle("Upcoming Appointments");
                        alert.show();
                    }
                    
                }
                
            //If there are no upcoming appointments, we output a dialog box stating as such.
            } else if(listofUsersAppointmentsStartingInTheNext15Minutes.isEmpty()){
                
                if(usersLocalLanguageIsFrench){
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Notification");
                    alert.setContentText( "\nVous n'avez aucun rendez-vous qui commence dans les quinze prochaines minutes.\n\n");
                    alert.setTitle("Aucun rendez-vous à venir");
                    alert.show();
                    
                } else {
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Notification");
                    alert.setContentText( "\nYou do not have any appointments that begin within the next fifteen minutes.\n\n");
                    alert.setTitle("No Upcoming Appointments");
                    alert.show();
                }
                
            }
        
        //If the list is equal to null, then there are no upcoming appointments; thus, we 
        //output a dialog box stating as such.
        } else {
            
            if(usersLocalLanguageIsFrench){
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Notification");
                    alert.setContentText( "\nVous n'avez aucun rendez-vous qui commence dans les quinze prochaines minutes.\n\n");
                    alert.setTitle("Aucun rendez-vous à venir");
                    alert.show();
                    
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Notification");
                alert.setContentText( "\nYou do not have any appointments that begin within the next fifteen minutes.\n\n");
                alert.setTitle("No Upcoming Appointments");
                alert.show();
            }
            
        }
    }
    
    private void translateScreenToFrench(){
        mainMenuLabel.setText("MENU PRINCIPAL");
        directingQuestionLabel.setText("Où voudrais-tu aller?");
        customersButton.setText("        Clients");
        appointmentsButton.setText("    Rendez-vous");
        signOutButton.setText("    Déloguer");
    }

    @FXML
    private void onActionDisplayCustomersTable(ActionEvent event) throws IOException {
        
        methodsLibrary.switchToDifferentSceneUsingButton("/view/CustomersTable.fxml", event);
    }

    @FXML
    private void onActionDisplayAppointmentsTable(ActionEvent event) throws IOException {
        
        methodsLibrary.switchToDifferentSceneUsingButton("/view/AppointmentsTable.fxml", event);
    }
    
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
