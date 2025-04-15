package com.example.demo03;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutController {

    /**
     * About program pop-up window
     *
     * @author Abdullah Kabbani (T03 April 15 2025)
     */

    @FXML
    private Button closeButton;

    /**
     * closes the about pop-up, returning to the main menu
     */
    @FXML
    void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
