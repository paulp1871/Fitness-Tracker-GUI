package com.example.demo03;

import com.example.demo03.enums.MeasurementEnum;
import com.example.demo03.tracker.Client;
import com.example.demo03.util.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    // constants to refer to trackedData elements in a clearer format
    public static final int WEIGHT = 0;
    public static final int BICEP_SIZE = 1;
    public static final int WAIST_SIZE = 2;
    public static final int CHEST_SIZE = 3;
    public static final int THIGHS_SIZE = 4;
    public static final int CALORIE_INTAKE = 5;
    public static final int HEIGHT = 6;

    private Client selectedClient;
    private LocalDate date;

    /**
     * initializes variables
     */
    @FXML
    public void Initialize() {
        statusMessage.setText("Create new client or select existing client to see data");

    }

    @FXML
    private Button selectExistingClient;

    @FXML
    private TextField waistSizeInput;

    @FXML
    private TextField dateInput;

    @FXML
    private TextField weightInput;

    @FXML
    private TextField chestSizeInput;

    @FXML
    private TextField bicepSizeInput;

    @FXML
    private TextField calIntakeInput;

    @FXML
    private Button addBicepsSize;

    @FXML
    private Label addNewEntryLabel;

    @FXML
    private Label selectedClientNameDisplay;

    @FXML
    private VBox addNewEntryPane;

    @FXML
    private Button addNewClientButton;

    @FXML
    private Button addWaistSize;

    @FXML
    private Button addWeight;

    @FXML
    private Button chestSize;

    @FXML
    private TextArea clientInfoPane;

    @FXML
    private TextField existingClientName;

    @FXML
    private TextField newClientName;

    @FXML
    private TextField newClientHeight;

    @FXML
    private Button printBMITrend;

    @FXML
    private Button printBiceps;

    @FXML
    private Button printCalIntake;

    @FXML
    private Button printChest;

    @FXML
    private Button printWaist;

    @FXML
    private Button printWeeklyCalIntake;

    @FXML
    private Button printWeight;

    @FXML
    private Button printWeightTrend;

    @FXML
    private Label statusMessage;

    @FXML
    void About(ActionEvent event) {

    }

    @FXML
    void Load(ActionEvent event) {

    }

    @FXML
    void SaveAs(ActionEvent event) {

    }

    /** Adds a bicep size measurement to the client's data
     *
     * @param event Button click
     */
    @FXML
    void addBicepsSize(ActionEvent event) {
        // Check if client selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        float entry;

        // checking user input
        try {
            entry = Float.parseFloat(bicepSizeInput.getText());
            //add the entry to the clients waist size data
            selectedClient.getMeasurement(MeasurementEnum.getEnumAt(BICEP_SIZE)).addEntry(date, entry);
            statusMessage.setText("Bicep size added to database");
            bicepSizeInput.clear();
        } catch (NumberFormatException e) {
            statusMessage.setText("Bicep size needs to be a number.");
        }
    }

    /** Adds a chest size measurement to the client's data
     *
     * @param event Button click
     */
    @FXML
    void addChestSize(ActionEvent event) {
        // Check if client selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        float entry;

        // checking user input
        try {
            entry = Float.parseFloat(chestSizeInput.getText());
            //add the entry to the clients waist size data
            selectedClient.getMeasurement(MeasurementEnum.getEnumAt(CHEST_SIZE)).addEntry(date, entry);
            statusMessage.setText("Chest size added to database");
            chestSizeInput.clear();
        } catch (NumberFormatException e) {
            statusMessage.setText("Chest size needs to be a number.");
        }
    }

    /**
     * Adds a waist size entry to the selected client's data
     * @param event Button click event
     */
    @FXML
    void addWaistSize(ActionEvent event) {
        // Check if client selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        float entry;

        // checking user input
        try {
            entry = Float.parseFloat(waistSizeInput.getText());
            //add the entry to the clients waist size data
            selectedClient.getMeasurement(MeasurementEnum.getEnumAt(WAIST_SIZE)).addEntry(date, entry);
            statusMessage.setText("Waist size added to database");
            waistSizeInput.clear();
        } catch (NumberFormatException e) {
            statusMessage.setText("Waist size needs to be a number.");
        }
    }


    /**
     * Adds a weight entry to the selected client's data
     * @param event Button click event
     */
    @FXML
    void addWeight(ActionEvent event) {
        // Check if client selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        float entry;

        // checking user input
        try {
            entry = Float.parseFloat(weightInput.getText());
            //add the entry to the clients weight data
            selectedClient.getMeasurement(MeasurementEnum.getEnumAt(WEIGHT)).addEntry(date, entry);
            statusMessage.setText("Weight added to database");
            weightInput.clear();
        } catch (NumberFormatException e) {
            statusMessage.setText("Weight needs to be a number.");
        }

    }

    /** Compares all clients in database by height
     *
     * @param event Button click
     */
    @FXML
    void compareClientsByHeight(ActionEvent event) {
        clientInfoPane.setText(DataBase.sortClientsByHeight());

        if (DataBase.sortClientsByHeight().equals("Clients listed from tallest to shortest:\n")) {
            statusMessage.setText("No clients in database currently.");
        } else {
            statusMessage.setText("Clients sorted by height.");
        }
    }

    /**
     * adds a new client to the database
     * @param event Button click
     */
    @FXML
    void addNewClient(ActionEvent event) {

        // Get client name and height
        String name = newClientName.getText();
        int height;

        // Add client to database and display
        try {
            height = Integer.parseInt(newClientHeight.getText());
            DataBase.addClient(name, height);
            statusMessage.setText("Client added to database");
            newClientName.clear();
            newClientHeight.clear();

        } catch (NumberFormatException e) {
            statusMessage.setText("Client height must be a number");
        }

    }

    /**
     * Prints all the clients onto the info panel
     * @param event Button click
     */
    @FXML
    void printAllClients(ActionEvent event) {
        // Show all clients
        clientInfoPane.setText(DataBase.printAllClientNames());

        // Check if there are any clients to show status msg
        if (DataBase.printAllClientNames().equals("[]")) {
            statusMessage.setText("No clients in database currently.");
        }
    }

    /** Displays the selected client's BMI prediction in the into panel
     *
     * @param event Button click
     */
    @FXML
    void printBMITrend(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

    }

    /** Displays the client's bicep size to the info panel
     *
     * @param event Button click
     */
    @FXML
    void printBiceps(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        // Obtain bicep trend info
        String bicepInfo = selectedClient.printSpecificClientMeasurement(MeasurementEnum.getEnumAt(BICEP_SIZE));

        // Display
        clientInfoPane.setText(bicepInfo);

        // Messages to show if data present or empty
        if (bicepInfo != null  && !bicepInfo.trim().isEmpty()) {
            statusMessage.setText("Now viewing " + selectedClient.getName() + "'s bicep size data.");
        } else {
            statusMessage.setText("No data found for " + selectedClient.getName() + "'s bicep size.");
        }
    }

    /** Displays the client's caloric intake data
     *
     * @param event Button click
     */
    @FXML
    void printCalIntake(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }


    }

    /** Displays the client's chest size to the info panel
     *
     * @param event Button click
     */
    @FXML
    void printChest(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        // Obtain chest trend info
        String chestInfo = selectedClient.printSpecificClientMeasurement(MeasurementEnum.getEnumAt(CHEST_SIZE));

        // Display
        clientInfoPane.setText(chestInfo);

        // Messages to show if data present or empty
        if (chestInfo != null  && !chestInfo.trim().isEmpty()) {
            statusMessage.setText("Now viewing " + selectedClient.getName() + "'s chest size data.");
        } else {
            statusMessage.setText("No data found for " + selectedClient.getName() + "'s chest size.");
        }
    }

    /** Assigns a value to the data variable, to be associated with future
      * entries until user provides a new date
      * @param event Button click
      */
    @FXML
    void assignDate(ActionEvent event) {

        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        // get the date input from the user
        String dateString = dateInput.getText();

        // format the string as date
        DateTimeFormatter stringToDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        try {
            // parse the formatted date into a LocalDate class
            date = LocalDate.parse(dateString, stringToDateFormatter);
            statusMessage.setText("Date assigned to future entries.");
            dateInput.clear();
        } catch ( Exception e ) {
            // Show error message if date is invalid
           statusMessage.setText("Date must be in the format DD/MM/YY");
        }
    }

    /** Displays the selected client from the database
     *
     * @param event Button click
     */
    @FXML
    void selectExistingClient(ActionEvent event) {
        // Check database for an existing client and assign it to selectedClient
        selectedClient = DataBase.getClient(existingClientName.getText());

        // Messages to display if client found or no client found
        if (selectedClient != null) {
            selectedClientNameDisplay.setText("Entering / printing data for " + existingClientName.getText());
            statusMessage.setText("Now looking at " + existingClientName.getText() + "'s data.");
            existingClientName.clear();
        } else {
            statusMessage.setText("No client with that name found.");
        }
    }

    /** Displays the client's waist size to the info panel
     *
     * @param event Button click
     */
    @FXML
    void printWaist(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        // Obtain waist trend info
        String waistInfo = selectedClient.printSpecificClientMeasurement(MeasurementEnum.getEnumAt(WAIST_SIZE));

        // Display
        clientInfoPane.setText(waistInfo);

        // Messages to show if data present or empty
        if (waistInfo != null  && !waistInfo.trim().isEmpty()) {
            statusMessage.setText("Now viewing " + selectedClient.getName() + "'s waist size data.");
        } else {
            statusMessage.setText("No data found for " + selectedClient.getName() + "'s waist size.");
        }
    }

    /** Displays the client's predicted weekly caloric intake
     *
     * @param event Button click
     */
    @FXML
    void printWeeklyCalIntake(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }
    }

    /** Displays the client's weight to the info panel
     *
     * @param event Button click
     */
    @FXML
    void printWeight(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }

        // Obtain weight info
        String weightInfo = selectedClient.printSpecificClientMeasurement(MeasurementEnum.getEnumAt(WEIGHT));

        // Display
        clientInfoPane.setText(weightInfo);

        // Messages if data present or empty
        if (weightInfo != null  && !weightInfo.trim().isEmpty()) {
            statusMessage.setText("Now viewing " + selectedClient.getName() + "'s weight data.");
        } else {
            statusMessage.setText("No data found for " + selectedClient.getName() + "'s weight.");
        }
    }

    /** Displays the client's predicted weight trend
     *
     * @param event Button click
     */
    @FXML
    void printWeightTrend(ActionEvent event) {
        // Check client was selected
        if (selectedClient == null) {
            statusMessage.setText("No client selected. Please select a client first.");
            return;
        }
    }

}