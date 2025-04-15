package com.example.demo03;

import java.io.File;
import java.time.LocalDate;

import com.example.demo03.enums.MeasurementEnum;
import com.example.demo03.tracker.Client;
import com.example.demo03.util.DataBase;

import static com.example.demo03.Menu.*;

/**
 * CPSC 233 W25 Project - Fitness Tracker
 * @author Vini Godoy de Melo, Abdullah Kabani, Paul Pham
 * @date 02/03/25
 * @tutorial T03
 * @email vini.godoydemelo@ucalgary.ca, abdullah.kabbani@ucalgary.ca, paul.pham@ucalgary,ca
 */

public class Main{

    public static void main(String[] args) {

        // show the main menu and store the user's choice
        int mainMenuChoice;

        // keep showing the main menu until the user chooses to exit
        do {
            mainMenuChoice = getMainMenu();

            if(mainMenuChoice == ADD_NEW_CLIENT) {
                String newClientName = getName();
                int newClientHeight = getHeight();

                // add the client to the clientList with an empty hashmap
                DataBase.addClient(newClientName, newClientHeight);

            } else if (mainMenuChoice == ADD_PRINT_DATA_OF_EXISTING_CLIENT) {

                // Name of the current client whose data we're tracking
                String clientName = "";
                // initialize the variable to store the user's menu choice
                int clientMenuChoice = 0;

                // loop the client menu until user chooses to go back to main menu
                do {
                    // loop until the user gives a valid client name
                    while (!DataBase.isExistingClient(clientName)) {
                        System.out.println("Please provide an existing client's name");
                        clientName = getName();
                    }

                    // the client whose data we'll be updating in this session
                    Client clientInSession = DataBase.getClient(clientName);

                    // show the sub menu and store the chosen mainMenuChoice
                    clientMenuChoice = getClientMenu();

                    if (clientMenuChoice == ADD_NEW_MEASUREMENT) {
                        // ask the user which type of measurement they would like to add a new entry
                        int dataEntryMenuChoice;

                        // loop the data entry menu until the user chooses to go back
                        do {
                            dataEntryMenuChoice = getDataEntryMenu();

                            if (dataEntryMenuChoice == BACK_TO_CLIENT_MENU) {
                                break;
                            }

                            // ask for the date and value of the entries
                            LocalDate date = askForDate();
                            Float entry = askForEntry();

                            // add the entry to the hash map
                            clientInSession.getMeasurement(MeasurementEnum.getEnumAt(dataEntryMenuChoice)).addEntry(date, entry);

                        } while (true);

                    } else if (clientMenuChoice == SPECIFIC_PAST_MEASUREMENT) {

                        // store the user's choice and print the measurements
                        int dataEntryMenuChoice = getDataEntryMenu();
                        clientInSession.printSpecificClientMeasurement(MeasurementEnum.getEnumAt(dataEntryMenuChoice));

                    } else if (clientMenuChoice == BMI_TREND) {
                        DataBase.getClient(clientName).getBMI();
                    } else if (clientMenuChoice == WEIGHT_TREND) {
                        // Print the predicted weight for the client
                        clientInSession.getMeasurement(MeasurementEnum.WEIGHT).printTrend();
                    } else if (clientMenuChoice == PRINT_ALL_PAST_MEASUREMENTS) {
                        // Print all measurements for the client
                        clientInSession.printAllClientMeasurements();
                    } else if (clientMenuChoice == WEEKLY_CAL_INTAKE) {
                        // Print the client's average calorie intake
                       clientInSession.getMeasurement(MeasurementEnum.CALORIES).printTrend();
                    }
                } while (clientMenuChoice != BACK_TO_MAIN_MENU);

            } else if (mainMenuChoice == PRINT_ALL_CLIENTS) {
                DataBase.printAllClientNames();
            } else if (mainMenuChoice == COMPARE_ALL_CLIENTS_HEIGHT) {
                DataBase.sortClientsByHeight();
            } else if (mainMenuChoice == LOAD_FROM_FILE) {
                File f = new File("save.txt");
                DataBase.loadSave(f);
            } else if (mainMenuChoice == SAVE_TO_FILE) {
                File f = new File("save.txt");
                DataBase.logSave(f);
            }
        } while (mainMenuChoice != EXIT_PROGRAM);
    }
}
