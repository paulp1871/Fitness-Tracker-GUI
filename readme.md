# Fitness Tracker (CPSC 233 Group Project)

## Authors
- Vini de Melo
- Paul Pham
- Abdullah Kabbani

## About
This is a group project for the course CPSC 233 at the University of Calgary. The program tracks client body measurements over time. It is intended for personal trainers who manage multiple clients and need to record progress.

## Key Features
1. List registered clients
2. Compare all clients by height
3. Add / print data for a selected client: weight, biceps size, waist size, chest size, and calorie intake
4. Calculate weight and BMI trend, and weekly calorie intake

## How to Run
Install the JavaFX SDK from Gluon, then use one of the following:

Option A — from compiled classes
cd <project-root>/target/classes
java --module-path "<PATH_TO_FX>\lib" --add-modules javafx.controls,javafx.fxml com.example.demo03.MainGUI

Option B — from the JAR
java --module-path "<PATH_TO_FX>\lib" --add-modules javafx.controls,javafx.fxml -jar demo03.jar

<PATH_TO_FX> is the folder where you installed the JavaFX SDK, for example:
"C:\Program Files\Java\javafx-sdk-23.0.2\lib"
(Keep the path in quotes if it contains spaces.)

## How to Use
1. Provide a name and height to add a new client.
2. Use "Print client list" and "Compare clients by height" after clients are added.
3. To add or print data for an existing client, enter the name and click "Select client" first.
4. When adding entries for a selected client, provide the date first, then the entry values (weight, biceps, waist, chest, calorie intake).
