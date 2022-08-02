
 •  TITLE: The title of this application is the "Customer Appointments Scheduler".


 •  PURPOSE: This application was designed to be used in a business context for the 
    purpose of maintaining customer records as well as scheduling appointments for 
    said customers. Appointments and customer records can be created, modified, and/or 
    deleted; all of these records are stored in a database.
	

 •  ADDITIONAL DETAILS (non-exhaustive):

    	◦  The application is designed to be linked to a database, and all appointment 
	   and customer records created in the application will be stored to this 
	   database. Because of this, all modifications and/or deletions of said 
	   records will be reflected in the database. The application also relies on 
	   this database for certain business information (e.g. the list of user 
	   accounts that have authorization to access the application).
    
	◦  The application generates three main reports. In order to view these reports, 
	   please click on the "Appointments" button after logging into the application; 
	   then, click on the "Statistics" button in the lower left corner of the screen.

		□  The first report displays the number of appointments scheduled, sorted 
		   by both type and month.

		□  The second report displays an appointment schedule for each business 
	   	   contact listed in the database.
	
		□  The third report displays the number of appointments currently 
		   scheduled for each customer; this list is in descending order.

	◦  The time zone of the user is automatically detected, and all dates and times 
	   within the application are displayed in the user's time zone.
	
	◦  The entire application automatically translates to French if it 
	   detects that the user of the application is currently in a French-speaking 
	   locale.
	
	◦  All login attempts are recorded in the file named "login_activity.txt". The 
	   details of these attempts are also recorded, including whether the attempt 
	   was successful or unsuccessful, the username used during the login attempt, 
	   and the date and time the attempt occurred.

	◦  After the user logs into the application, a notification appears informing 
	   the user whether or not they have an appointment within the next fifteen 
	   minutes; if they do, the details of those appointment(s) are listed.
	
	◦  If a user attempts to schedule an appointment for a customer who already 
	   has an appointment during the specified timeframe, the application warns 
	   the user of this and does not allow the appointment to be scheduled.
	
	◦  If a user attempts to schedule an appointment outside of the business's 
	   office hours (currently set to 8 AM to 10 PM EST), the application warns 
	   the user of this and does not allow the appointment to be scheduled.
	
	◦  The application code includes detailed Javadoc comments above every class, 
	   method, and variable, and there are even more detailed (non-Javadoc) 
	   comments explaining certain snippets of code; this comprises extensive 
	   and easily-understandable documentation.
	
	◦  The application includes many other features and error-checking attributes 
	   that have not been named here.
	



 •  AUTHOR: The author of this application is Alian Peirce.
    CONTACT INFORMATION: The author may be reached at the email apeirc2@wgu.edu .
    STUDENT APPLICATION VERSION: This is application version 1.0 .
    DATE: The current date is 08/01/2022.



 •  IDE SPECIFICATIONS: The IDE used for this project is Apache Netbeans 12.6 .
    JDK SPECIFICATIONS: The version of JDK used is Java SE 17.0.2 .
    JAVAFX SPECIFICATIONS: The version of JavaFX used is JavaFX-SDK-17.0.1 .



 • INSTRUCTIONS FOR RUNNING THE PROGRAM: 

   Before running the program, if you are planning to run it in Windows 10 (and perhaps 
   Windows in general), please make sure to go to the Windows settings, navigate to 
   the "Date & time" section, and ensure that the "Adjust for daylight saving time 
   automatically" option is turned on. If it is left off, the java.time package will 
   NOT work correctly - it will output something like "GMT-07:00" anytime an attempt 
   is made to output the display name of the local time zone or the local zone ID.
  
   Additionally, please make sure you have a database installed that matches the 
   ERD diagram and has the following specifications:
  
       ◦  Protocol: JDBC
       ◦  Vendor: MySQL
       ◦  Location (relative to the program file): //localhost/
       ◦  Database name: client_schedule
       ◦  Database driver: mysql-connector-java-8.0.25
       ◦  Database username: sqlUser
       ◦  Database password: Passw0rd!

   The application also expects that the database already contains certain populated 
   tables; these tables contain business data necessary for the application to 
   function properly. These expected tables must be named Contacts (referring to the 
   business's contacts), Countries (referring to the countries in which the business 
   operates), First_Level_Divisions (referring to the first-level divisions of the 
   given Countries), and Users (a.k.a. the accounts that are allowed access to the 
   application).
  
   Once all the prerequisites are met, all that is left to do is import the project 
   ZIP file into Netbeans, build the project, and then execute it.



 •  MySQL CONNECTOR DRIVER VERSION NUMBER:
    The driver version number is mysql-connector-java-8.0.25

