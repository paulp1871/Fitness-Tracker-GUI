package com.example.demo03;

import com.example.demo03.enums.MeasurementEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {
    // app.Main menu options
    public static final int ADD_NEW_CLIENT = 0;
    public static final int ADD_PRINT_DATA_OF_EXISTING_CLIENT = 1;
    public static final int PRINT_ALL_CLIENTS = 2;
    public static final int COMPARE_ALL_CLIENTS_HEIGHT = 3;
    public static final int EXIT_PROGRAM = 4;
    public static final int LOAD_FROM_FILE = 5;
    public static final int SAVE_TO_FILE = 6;

    // Second menu options
    public static final int ADD_NEW_MEASUREMENT = 0;
    public static final int SPECIFIC_PAST_MEASUREMENT = 1;
    public static final int BMI_TREND = 2;
    public static final int WEIGHT_TREND = 3;
    public static final int WEEKLY_CAL_INTAKE = 4;
    public static final int PRINT_ALL_PAST_MEASUREMENTS = 5;
    public static final int BACK_TO_MAIN_MENU = 6;

    // Date entry menu options
    public static final int BACK_TO_CLIENT_MENU = 7;

    // options in the app.Main Menu
    static String[] mainMenu = {
            "Add new client",
            "Add/print data of an existing client",
            "Print list of all existing clients",
            "Compare all clients by height",
            "Exit program",
            "Load from file",
            "Save to file"};

    // options in the Client Menu
    static String[] clientMenu = {
            "Add new measurement",
            "See a specific past measurements",
            "BMI trend",
            "Weight trend",
            "Average weekly calorie intake",
            "Print all past measurements",
            "Go back to main menu"
    };

    // constants to refer to trackedData elements in a clearer format
    public static final int WEIGHT = 0;
    public static final int BICEP_SIZE = 1;
    public static final int WAIST_SIZE = 2;
    public static final int CHEST_SIZE = 3;
    public static final int THIGHS_SIZE = 4;
    public static final int CALORIE_INTAKE = 5;
    public static final int HEIGHT = 6;

    /**
     * This function shows the main menu and prompts the user to choose an option
     * @return the int associated with the option
     */
    public static int getMainMenu() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("-- MAIN MENU --");
        System.out.println("Please choose one of the following:");

        for (int i = 0; i < mainMenu.length; i++) {
            System.out.println(i + ". " + mainMenu[i]);
        }
        // convert chosen number to int and return it
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * This function shows the client menu and prompts the user to choose an option
     * @return the int associated with the option
     */
    public static int getClientMenu(){

        Scanner optionScanner = new Scanner(System.in);
        System.out.println("-- CLIENT MENU --");
        System.out.println("Please choose one of the options below");

        // loop the list of things we want to measure and print them one by one
        for (int i = 0; i < clientMenu.length; i++) {
            System.out.println(i + ". " + clientMenu[i]);
        }
        return Integer.parseInt(optionScanner.nextLine());
    }

    /**
     * Shows the data entry menu and prompts the user to choose an option
     * @return the int associated with the option
     */
    public static int getDataEntryMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- DATA ENTRY MENU --");
        System.out.println("Please choose one of the following:");

        // loop the list of things we want to measure and print them one by one
        for (int i = 0; i < MeasurementEnum.values().length; i++) {
            System.out.println(i + ". " + MeasurementEnum.getEnumAt(i).getString());
        }
        System.out.println("7. Got back to previous menu");

        // convert chosen number to int and return it
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Prompts the user for a date in the format (DD/MM/YY) and parses it into a LocalDate class
     * @return the LocalDate with which the data entries are associated
     */
    public static LocalDate askForDate() {
        Scanner dateScanner = new Scanner(System.in);
        System.out.println("What date would be associated with the entries? (DD/MM/YY)");

        // date stored as string
        String dateString = dateScanner.nextLine();

        // format the string as date
        DateTimeFormatter stringToDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        // parse the formatted date into a LocalDate class and return it
        return LocalDate.parse(dateString, stringToDateFormatter);
    }

    public static Float askForEntry() {
        System.out.println("What is the measurement value? Use kg and cm.");
        Scanner scanner = new Scanner(System.in);
        return Float.parseFloat(scanner.nextLine());
    }

    /**
     * Prompts the user for a client name
     * @return a String containing the client's name
     */
    public static String getName() {
        Scanner nameScanner = new Scanner(System.in);
        System.out.println("What is the client's name?");
        return nameScanner.nextLine();
    }

    public static int getHeight() {
        Scanner nameScanner = new Scanner(System.in);
        System.out.println("What is the client's height?");
        return Integer.parseInt(nameScanner.nextLine());
    }
}
