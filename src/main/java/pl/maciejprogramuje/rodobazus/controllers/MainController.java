package pl.maciejprogramuje.rodobazus.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public ProgressIndicator spinner;

    @FXML
    public TextField enterLinkTextField;

    @FXML
    private Button startButton;

    @FXML
    private Label messageLabel;

    BooleanProperty spinnerVisibleProperty;
    StringProperty enterLinkStringProperty;
    BooleanProperty startButtonDisableProperty;
    StringProperty messageStringProperty;

    MainControllerActions mainControllerActions;


    public void initialize(URL location, ResourceBundle resources) {
        mainControllerActions = new MainControllerActions(this);

        spinnerVisibleProperty = new SimpleBooleanProperty();
        spinner.visibleProperty().bind(spinnerVisibleProperty);

        enterLinkStringProperty = new SimpleStringProperty();
        enterLinkTextField.textProperty().bindBidirectional(enterLinkStringProperty);

        startButtonDisableProperty = new SimpleBooleanProperty();
        startButton.disableProperty().bind(startButtonDisableProperty);

        messageStringProperty = new SimpleStringProperty();
        messageLabel.textProperty().bind(messageStringProperty);

        spinnerVisibleProperty.setValue(false);
        messageStringProperty.setValue("");
    }

    public void handleStartButtonAction() {
        mainControllerActions.handleStartButton();
    }
}
