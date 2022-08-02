/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerappointmentsapplication;

import database_CRUD.AppointmentsCRUD;
import database_CRUD.CustomersCRUD;
import database_CRUD.FirstLevelDivisionsCRUD;
import database_connection.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;
import model.DataHolder;
import model.FirstLevelDivisions;

/**
 *
 * @author Alano Peirce
 */
/** 
 * This class creates an application that is designed to be used in a business 
 * context for the purpose of creating, modifying, and deleting appointments as 
 * well as customer records. The application is linked to a particular 
 * database, where it stores all appointment and customer records; because of 
 * this, all modifications to and/or deletions of said records will be 
 * reflected in the database. The application also expects the relevant 
 * Contacts, Countries, First-Level Divisions (of the given Countries), and 
 * Users (a.k.a. the accounts that have access to the application) to 
 * already be populated in the database. 
 * In addition, this application automatically translates to 
 * French if it detects that the user of the application is currently in a 
 * French-speaking locale.
 */
public class CustomerAppointmentsApplication extends Application {

    /**
     * This is the main method of the application; program execution begins 
     * here. This method opens the connection to the database. Then, it calls 
     * the JavaFX launch() method, which calls the three JavaFX life cycle 
     * methods - "init()", "start()" (which starts up the application), and 
     * "stop()" (which is called once the JavaFX application is closed). 
     * Finally, after the application has completed executing, the connection 
     * to the database is closed.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*Uncomment this to translate the application to French (by setting the 
          default locale to French or a French-speaking country); comment this 
          out to return the application to English.
        */
        //Locale.setDefault(new Locale("fr"));
                
        DatabaseConnection.openConnection();
        
        launch(args);
        
        DatabaseConnection.closeConnection();
    }
    
    /**
     * This is the JavaFX life cycle method that starts up the application. 
     * This method creates an object of the appropriate FXML file (i.e. the 
     * FXML file that is to be shown upon start-up of the application), loads 
     * it into a new scene, loads the scene into the given stage, and then 
     * displays the stage (with the start-up scene loaded into it) to the user.
     * @param stage The Stage variable where the scenes will reside during the 
     * course of running the application
     * @throws Exception IOException (thrown by the FXMLLoader's "load()" 
     * method, which is called in the body of this method)
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Customer Appointments Scheduler");
        stage.centerOnScreen();
        stage.show();
    }
    
}
