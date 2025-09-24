# Information

## Authors

Vini de Melo\
Paul Pham\
Abdullah Kabbani

## About
This was a group project for my CPSC 233 class at the University of Calgary. This program tracks body measurements over time. It is primarily intended for personal trainers who have multiple clients whose progress needs to be recorded. 

## Some key functionalities:
1. Printing the name of registered clients
2. Comparing all clients by height
3. Adding / printing data such as Weight, Biceps size, Waist size, Chest size and Calorie intake for a given client.
4. Calculating weight and BMI trend, as well as weekly calorie intake.

## How to run it
Download the JavaFX SDK from Gluon.
Then go to myprojectname\target\classes folder and run the command: java --module-path "sdk location*" --add-modules
javafx.controls,javafx.fxml com.example.demo03.MainGUI

Or if running the jar file, run the command: java --module-path "sdk location*" --add-modules
javafx.controls,javafx.fxml -jar demo03.jar

*SDK location is the folder where you put the sdk files, like "C:\Program Files\Java\javafx-sdk-23.0.2\lib" for example.

## How to interact with it
1. Provide a name and height before adding a new client
2. Once clients are added, you can use the options "print client list" and "compare clients by height"
3. To add or print data of a a client that has already been added, please provide a name and click "select client" first.
4. To add new entries to a client's data set, please provide a date first, then the entry values.
