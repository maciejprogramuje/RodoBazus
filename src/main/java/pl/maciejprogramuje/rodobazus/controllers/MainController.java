package pl.maciejprogramuje.rodobazus.controllers;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public ProgressIndicator spinner;

    @FXML
    public TextField enterLinkTextField;

    @FXML
    public Button nextRowButton;

    @FXML
    public Spinner<Integer> customRowSpinner;

    @FXML
    public Button customRowButton;

    @FXML
    public TextArea pathLabel;

    @FXML
    private Button startButton;

    @FXML
    private Label messageLabel;

    BooleanProperty spinnerVisibleProperty;
    StringProperty enterLinkStringProperty;
    BooleanProperty startButtonDisableProperty;
    StringProperty messageStringProperty;
    StringProperty nextRowStringProperty;
    BooleanProperty customRowBooleanProperty;
    StringProperty pathStringProperty;

    MainControllerActions mainControllerActions;


    public void initialize(URL location, ResourceBundle resources) {
        mainControllerActions = new MainControllerActions(this);

        spinnerVisibleProperty = new SimpleBooleanProperty(false);
        spinner.visibleProperty().bind(spinnerVisibleProperty);

        enterLinkStringProperty = new SimpleStringProperty();
        enterLinkTextField.textProperty().bindBidirectional(enterLinkStringProperty);

        startButtonDisableProperty = new SimpleBooleanProperty();
        startButton.disableProperty().bind(startButtonDisableProperty);

        messageStringProperty = new SimpleStringProperty("");
        messageLabel.textProperty().bind(messageStringProperty);

        nextRowStringProperty = new SimpleStringProperty("Rozpocznij analize plikow!");
        nextRowButton.textProperty().bind(nextRowStringProperty);

        customRowBooleanProperty = new SimpleBooleanProperty(true);
        customRowSpinner.disableProperty().bind(customRowBooleanProperty);
        customRowButton.disableProperty().bind(customRowBooleanProperty);
        nextRowButton.disableProperty().bind(customRowBooleanProperty);

        pathStringProperty = new SimpleStringProperty("");
        pathLabel.textProperty().bind(pathStringProperty);
    }

    public void handleStartButtonAction() {
        mainControllerActions.handleStartButton();
    }

    public void handleNextRowButtonAction() { mainControllerActions.handleNextRowButton();
    }

    public void handleCustomRowButtonAction() { mainControllerActions.handleCustomRowButton();
    }
}
