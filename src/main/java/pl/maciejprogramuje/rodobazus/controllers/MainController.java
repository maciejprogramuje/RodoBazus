package pl.maciejprogramuje.rodobazus.controllers;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
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
    public Button getFileListFromGit;

    @FXML
    public TextField enterGitCommitIdTextField;

    @FXML
    public TextField enterGitBranchTextField;

    @FXML
    public TextField enterBazusATextField;

    @FXML
    public TextField enterBazusBTextField;

    @FXML
    public Button startBazusButton;

    @FXML
    private Button startButton;

    @FXML
    private Label messageLabel;

    private BooleanProperty spinnerVisibleProperty;
    private StringProperty enterLinkStringProperty;
    private BooleanProperty startButtonDisableProperty;
    private StringProperty messageStringProperty;
    private StringProperty nextRowStringProperty;
    private BooleanProperty customRowBooleanProperty;
    private StringProperty pathStringProperty;
    private StringProperty enterGitCommitIdTextFieldStringProperty;
    private StringProperty enterGitBranchTextFieldStringProperty;
    private StringProperty enterBazusATextFieldProperty;
    private StringProperty enterBazusBTextFieldProperty;
    private BooleanProperty startBazusButtonDisableProperty;

    MainControllerActions mainControllerActions;


    public void initialize(URL location, ResourceBundle resources) {
        mainControllerActions = new MainControllerActions(this);

        spinnerVisibleProperty = new SimpleBooleanProperty(false);
        spinner.visibleProperty().bind(spinnerVisibleProperty);

        enterLinkStringProperty = new SimpleStringProperty("C:\\Tomcat\\webBazusWU\\tomcat\\webapps\\wu");
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

        enterGitCommitIdTextFieldStringProperty = new SimpleStringProperty("925ccdf");
        enterGitCommitIdTextField.textProperty().bindBidirectional(enterGitCommitIdTextFieldStringProperty);

        enterGitBranchTextFieldStringProperty = new SimpleStringProperty("BUILD_master_WU_PROD");
        enterGitBranchTextField.textProperty().bindBidirectional(enterGitBranchTextFieldStringProperty);

        enterBazusATextFieldProperty = new SimpleStringProperty("C:\\BazusTemp\\bazus A.jnlp");
        enterBazusATextField.textProperty().bindBidirectional(enterBazusATextFieldProperty);

        enterBazusBTextFieldProperty = new SimpleStringProperty("C:\\BazusTemp\\bazus B.jnlp");
        enterBazusBTextField.textProperty().bindBidirectional(enterBazusBTextFieldProperty);

        startBazusButtonDisableProperty = new SimpleBooleanProperty();
        startBazusButton.disableProperty().bind(startBazusButtonDisableProperty);
    }

    public void handleStartButtonAction() {
        mainControllerActions.handleStartButton();
    }

    public void handleNextRowButtonAction() { mainControllerActions.handleNextRowButton();
    }

    public void handleCustomRowButtonAction() { mainControllerActions.handleCustomRowButton();
    }

    public void handleGetFileListFromGitAction() {
        mainControllerActions.handleGetFileListFromGit();
    }

    public void handleBazusStartButtonAction() {
        mainControllerActions.handleBazusStartButton();
    }


    //--------------------------------------------


    public boolean isSpinnerVisibleProperty() {
        return spinnerVisibleProperty.get();
    }

    public BooleanProperty spinnerVisiblePropertyProperty() {
        return spinnerVisibleProperty;
    }

    public void setSpinnerVisibleProperty(boolean spinnerVisibleProperty) {
        this.spinnerVisibleProperty.set(spinnerVisibleProperty);
    }

    public String getEnterLinkStringProperty() {
        return enterLinkStringProperty.get();
    }

    public StringProperty enterLinkStringPropertyProperty() {
        return enterLinkStringProperty;
    }

    public void setEnterLinkStringProperty(String enterLinkStringProperty) {
        this.enterLinkStringProperty.set(enterLinkStringProperty);
    }

    public boolean isStartButtonDisableProperty() {
        return startButtonDisableProperty.get();
    }

    public BooleanProperty startButtonDisablePropertyProperty() {
        return startButtonDisableProperty;
    }

    public void setStartButtonDisableProperty(boolean startButtonDisableProperty) {
        this.startButtonDisableProperty.set(startButtonDisableProperty);
    }

    public String getMessageStringProperty() {
        return messageStringProperty.get();
    }

    public StringProperty messageStringPropertyProperty() {
        return messageStringProperty;
    }

    public void setMessageStringProperty(String messageStringProperty) {
        this.messageStringProperty.set(messageStringProperty);
    }

    public String getNextRowStringProperty() {
        return nextRowStringProperty.get();
    }

    public StringProperty nextRowStringPropertyProperty() {
        return nextRowStringProperty;
    }

    public void setNextRowStringProperty(String nextRowStringProperty) {
        this.nextRowStringProperty.set(nextRowStringProperty);
    }

    public boolean isCustomRowBooleanProperty() {
        return customRowBooleanProperty.get();
    }

    public BooleanProperty customRowBooleanPropertyProperty() {
        return customRowBooleanProperty;
    }

    public void setCustomRowBooleanProperty(boolean customRowBooleanProperty) {
        this.customRowBooleanProperty.set(customRowBooleanProperty);
    }

    public String getPathStringProperty() {
        return pathStringProperty.get();
    }

    public StringProperty pathStringPropertyProperty() {
        return pathStringProperty;
    }

    public void setPathStringProperty(String pathStringProperty) {
        this.pathStringProperty.set(pathStringProperty);
    }

    public String getEnterGitCommitIdTextFieldStringProperty() {
        return enterGitCommitIdTextFieldStringProperty.get();
    }

    public StringProperty enterGitCommitIdTextFieldStringPropertyProperty() {
        return enterGitCommitIdTextFieldStringProperty;
    }

    public void setEnterGitCommitIdTextFieldStringProperty(String enterGitCommitIdTextFieldStringProperty) {
        this.enterGitCommitIdTextFieldStringProperty.set(enterGitCommitIdTextFieldStringProperty);
    }

    public String getEnterGitBranchTextFieldStringProperty() {
        return enterGitBranchTextFieldStringProperty.get();
    }

    public StringProperty enterGitBranchTextFieldStringPropertyProperty() {
        return enterGitBranchTextFieldStringProperty;
    }

    public void setEnterGitBranchTextFieldStringProperty(String enterGitBranchTextFieldStringProperty) {
        this.enterGitBranchTextFieldStringProperty.set(enterGitBranchTextFieldStringProperty);
    }

    public String getEnterBazusATextFieldProperty() {
        return enterBazusATextFieldProperty.get();
    }

    public StringProperty enterBazusATextFieldPropertyProperty() {
        return enterBazusATextFieldProperty;
    }

    public void setEnterBazusATextFieldProperty(String enterBazusATextFieldProperty) {
        this.enterBazusATextFieldProperty.set(enterBazusATextFieldProperty);
    }

    public String getEnterBazusBTextFieldProperty() {
        return enterBazusBTextFieldProperty.get();
    }

    public StringProperty enterBazusBTextFieldPropertyProperty() {
        return enterBazusBTextFieldProperty;
    }

    public void setEnterBazusBTextFieldProperty(String enterBazusBTextFieldProperty) {
        this.enterBazusBTextFieldProperty.set(enterBazusBTextFieldProperty);
    }

    public boolean isStartBazusButtonDisableProperty() {
        return startBazusButtonDisableProperty.get();
    }

    public BooleanProperty startBazusButtonDisablePropertyProperty() {
        return startBazusButtonDisableProperty;
    }

    public void setStartBazusButtonDisableProperty(boolean startBazusButtonDisableProperty) {
        this.startBazusButtonDisableProperty.set(startBazusButtonDisableProperty);
    }
}
