<h1 align="center">Customer Appointments Scheduler</h1>

<p align="center">
  <a href="#features">Features</a> • 
  <a href="#system-requirements">System requirements</a> • 
  <a href="#installation">Installation</a> • 
  <a href="#usage">Usage</a> • 
  <a href="#other-information">Other information</a> • 
  <a href="#acknowledgements">Acknowledgements</a>
</p>

&nbsp;

<p align="center">
    <img src="https://user-images.githubusercontent.com/110432500/184468131-365957c5-593c-4765-8360-e8dce4715a17.gif" alt="Demo of application in GIF format" />
</p>

<p align="center">
    <a href="#usage">Click here to skip to the full demo!</a>
</p>

&nbsp;

The Customer Appointments Scheduler allows you to easily maintain an index of all your customers as well as the appointments you have with them.

This application was designed for the Langley Consulting Group ("LCG" – a fictitious company), but it can be used by other organizations as long as their database is configured according to [the instructions below](#database-specifications).

## Table of Contents
* [Features](#features)
* [System requirements](#system-requirements)
	* [Operating System](#operating-system)
	* [IDE (Integrated Development Environment)](#ide-integrated-development-environment)
	* [Database specifications](#database-specifications)
	* [Database layout](#database-layout)
	* [Other prerequisites](#other-prerequisites)
* [Installation](#installation)
* [Usage](#usage)
* [Other information](#other-information)
* [Acknowledgements](#acknowledgements)

## Features

* **Database storage.** The application is designed to be linked to LCG's database, and all appointment and customer records created in the application will be stored in this database. Because of this, all changes made to those records (including deletion) will be reflected in the database.
    
- **Generation of statistical reports.** The application generates three main reports. In order to view these reports,  please click on the "Appointments" button after logging into the application; then, click on the "Statistics" button in the lower left corner of the screen.
	- <ins>Report 1</ins>: The first report displays the number of appointments scheduled, sorted by both type and month.
	- <ins>Report 2</ins>: The second report displays an appointment schedule for each business contact listed in LCG's database.
	- <ins>Report 3</ins>: The third report displays the number of appointments currently scheduled for each customer; this list is in descending order.

* **Automatic time zone detection.** The time zone of the user is automatically detected, and all dates and times within the application are displayed in the user's time zone.
	
* **Automatic language translation.** The entire application automatically translates to French if it detects that the user of the application is currently in a French-speaking locale.
	
* **Record of all login attempts.** All login attempts are recorded in the file named "login_activity.txt". The details of these attempts are also recorded, including whether the attempt was successful or unsuccessful, the username used during the login attempt, and the date and time the attempt occurred.

* **Upcoming appointment notifications.** After the user logs into the application, a notification appears informing the user whether or not they have an appointment within the next fifteen minutes; if they do, the details of those appointment(s) are listed.
	
* **Overlapping appointment protection.** If a user attempts to schedule an appointment for a customer who already has an appointment during the specified timeframe, the application warns the user of this and does not allow the appointment to be scheduled.
	
* **Awareness of business hours.** If a user attempts to schedule an appointment outside of LCG's business hours (currently 8 AM to 10 PM EST), the application warns the user of this and does not allow the appointment to be scheduled.
	
* **Extensive code documentation.** The application code includes detailed Javadoc comments above every class, method, and variable, and there are even more detailed (non-Javadoc) comments explaining certain snippets of code; this comprises extensive and easily-understandable documentation.
	
* **Many other features.** The application includes many other features and error-checking attributes that have not been named here.

## System requirements

Before installing the Customer Appointments Scheduler, please ensure that your systems match the following criteria.

### Operating System
* Windows 10

### IDE (Integrated Development Environment)
* Apache Netbeans 12.6

### Database specifications
* <ins>Protocol:</ins> JDBC
* <ins>Vendor:</ins> MySQL
* <ins>Location (relative to the program file):</ins> //localhost/
* <ins>Database name:</ins> client_schedule
* <ins>Database driver:</ins> mysql-connector-java-8.0.25
* <ins>Database username:</ins> sqlUser
* <ins>Database password:</ins> Passw0rd!

### Database layout
<ul>
    <li>
	Your database must conform to the following ERD diagram:<p></p>
	<details>
	    <summary><b>Show diagram</b></summary>
	    <br>
	    &nbsp;   &nbsp;<img src="https://i.imgur.com/7xLMo3V.png" alt="ERD diagram of required database layout"><p></p>
	</details>
    </li>
</ul>
	
* Furthermore, the contents of each table in the database must match the following descriptions:<p></p>
	* The <ins>Countries</ins> table must be pre-populated with the countries in which LCG does business.
	* The <ins>First_Level_Divisions</ins> table must be pre-populated with the first-level divisions (e.g. states or provinces) of each of those countries.
	* The <ins>Users</ins> table must be pre-populated with the user accounts that are authorized to access the application.
	* The <ins>Contacts</ins> table must be pre-populated with the business contacts of LCG.
	* The <ins>Customers</ins> and <ins>Appointments</ins> tables *may* be pre-populated with customer records and appointment records (respectively), but this is 		 NOT a requirement. These tables will be the storage locations for any customer and appointment records that are created using the application.

### Other prerequisites
* Please follow the instructions below so that the application can accurately detect users' time zone.<p></p>

	<div>
	    <ol>
		<li>
		    Click on the Start icon of your computer and select the "Settings" application.<p></p>
		    <details>
			<summary><b>Show image</b></summary>
			<br>
			&nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/ocOKayp.png" alt="Settings icon in the Start Menu" width=35%><p></p>
		    </details>
		</li>
		<li>
		    Once the setting application opens, select the "Time and Language" option.<p></p>
		    <details>
			<summary><b>Show image</b></summary>
			<br>
			&nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/9VChGDF.png" alt="Time and Language option in the Settings app" width=75%><p></p>
		    </details>
		</li>
		<li>
		    Next, select the "Date & time" tab.<p></p>
		    <details>
			<summary><b>Show image</b></summary>
			<br>
			&nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/duZ90LL.png" alt="Date & time tab in the Settings app" width=33%><p></p>
		    </details>
		</li>
		<li>
		    Finally, scroll down within the "Date & time" tab to look for the "Adjust for daylight saving time automatically" option. Please ensure 				that the toggle switch associated with this option is set to "On".<p></p>
		    <details>
			<summary><b>Show image</b></summary>
			<br>
			&nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/sMG0Hcn.png" alt="DST toggle option in the Settings app" width=75%><p>&nbsp;</p>
		    </details>
		</li>
	    </ol>
	</div>

		
## Installation

<ol>
    <li>
        Starting from <a href="https://github.com/AlanoPeirce/Customer_Appointments_Scheduler">the main project page on Github</a>, click on the green "Code" button 	     that appears above the upper right corner of the application files (see the blue rectangle in the image below).<p></p>
        From the drop-down menu that opens, click "Download ZIP" (see the red rectangle in the image below). This will download the application onto your computer as a 	ZIP file.<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/KQmp87l.png" alt="How to download application files from Github" width=90%><p>&nbsp;</p>
	</details>
    </li>
    <li>
        Open up NetBeans and click on File -> Import Project -> From ZIP.<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/lSEA6V1.png" alt="How to import project into NetBeans"><p>&nbsp;</p>
	</details>
    </li>
    <li>
        A popup window will appear. On this popup window, click the "Browse" button that appears next to the "ZIP File:" label (see image below).<p></p>
	Now, navigate through your local directories to find the ZIP file that you downloaded in step 1. Open and then import this ZIP file.<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/6rEBl5e.png" alt="NetBeans import project popup window"><p>&nbsp;</p>
	</details>
    </li>
    <li>
        Now, you can build the project. To do this, first highlight the project file by clicking on it (see rectangle #1 in the image below). Then, click the "Clean 	     and Build" button (see rectangle #2).<p></p>
	After waiting for the project to finish building, you should see a message in the Output window that says "BUILD SUCCESSFUL" (see rectangle #3).<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/c0PH2H0.png" alt="How to build NetBeans project" width=90%><p>&nbsp;</p>
	</details>
    </li>
    <li>
        Lastly, you need to run the project. Make sure the project file is still highlighted from step 4 — then, click on the "Run" button (which looks like a green 	     arrow) in NetBeans' toolbar.<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/GWZ8jKd.png" alt="NetBeans Run button"><p>&nbsp;</p>
	</details>
    </li>
    <li>
        The application should now start up! The first page you should see is the login page of the application, which looks like this:<p></p>
	<details>
	    <summary><b>Show image</b></summary>
	    <br>
	    &nbsp;   &nbsp;   &nbsp;<img src="https://i.imgur.com/mmHjG3x.png" alt="Login page of application"><p>&nbsp;</p>
	</details>
    </li>
</ol>


## Usage

Please view the following video for a demonstration of the Customer Appointments Scheduler. Don't forget to unmute the video! 🔊

https://user-images.githubusercontent.com/110432500/184267328-99ea4d28-6741-4b4d-a36b-4841258c72b9.mp4

## Other information
* The version of JDK used in this application is Java SE 17.0.2.
* The version of JavaFX used in this application is JavaFX-SDK-17.0.1.

## Acknowledgements
* All aspects of this application were created by <a href="https://github.com/AlanoPeirce">Alano Peirce</a>.
