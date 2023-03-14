package com.undelir.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MainAppController {
    public void handleExit(ActionEvent actionEvent) {
        System.exit(0);
    }
    //Help Menu button behavior
    public void handleHelp(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Program Information");
        alert.setHeaderText("This is a sample JAVAFX application for PATA ACADEMY!");
        alert.setContentText("An application for employees.");
        alert.show();
    }
}