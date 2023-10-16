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
    public TextField enterBazusATextField;

    @FXML
    public TextField enterBazusBTextField;

    @FXML
    public Button startBazusButton;

    @FXML
    public Button deleteBazusButton;

    @FXML
    public TextField enterWuATextField;

    @FXML
    public TextField enterWuBTextField;

    @FXML
    public Button startWuButton;

    @FXML
    public Button deleteWuButton;

    @FXML
    private Button startButton;

    @FXML
    private Label messageLabel;

    /*
    @FXML
    public Button getFileListFromGit;
    @FXML
    public TextField enterGitBranchTextField;
    @FXML
    public TextField enterGitCommitIdTextField;
    */

    private StringProperty enterLinkStringProperty;
    private StringProperty messageStringProperty;
    private StringProperty nextRowStringProperty;
    private BooleanProperty customRowBooleanProperty;
    private StringProperty pathStringProperty;
    private StringProperty enterBazusATextFieldProperty;
    private StringProperty enterBazusBTextFieldProperty;
    private StringProperty enterWuATextFieldProperty;
    private StringProperty enterWuBTextFieldProperty;
    private BooleanProperty disableButtonsProperty;

    /*
    private StringProperty enterGitCommitIdTextFieldStringProperty;
    private StringProperty enterGitBranchTextFieldStringProperty;
    */

    MainControllerActions mainControllerActions;


    public void initialize(URL location, ResourceBundle resources) {
        mainControllerActions = new MainControllerActions(this);

        disableButtonsProperty = new SimpleBooleanProperty(false);
        spinner.visibleProperty().bind(disableButtonsProperty);
        startButton.disableProperty().bind(disableButtonsProperty);
        startBazusButton.disableProperty().bind(disableButtonsProperty);
        deleteBazusButton.disableProperty().bind(disableButtonsProperty);
        startWuButton.disableProperty().bind(disableButtonsProperty);
        deleteWuButton.disableProperty().bind(disableButtonsProperty);

        enterLinkStringProperty = new SimpleStringProperty("C:\\Tomcat\\webBazusWU\\tomcat\\webapps");
        enterLinkTextField.textProperty().bindBidirectional(enterLinkStringProperty);

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

        /*
        enterGitCommitIdTextFieldStringProperty = new SimpleStringProperty("925ccdf");
        enterGitCommitIdTextField.textProperty().bindBidirectional(enterGitCommitIdTextFieldStringProperty);

        enterGitBranchTextFieldStringProperty = new SimpleStringProperty("BUILD_master_WU_PROD");
        enterGitBranchTextField.textProperty().bindBidirectional(enterGitBranchTextFieldStringProperty);
        */

        enterBazusATextFieldProperty = new SimpleStringProperty("C:\\RodoTemp\\BazusRodo\\bazus A.jnlp");
        enterBazusATextField.textProperty().bindBidirectional(enterBazusATextFieldProperty);
        enterBazusBTextFieldProperty = new SimpleStringProperty("C:\\RodoTemp\\BazusRodo\\bazus B.jnlp");
        enterBazusBTextField.textProperty().bindBidirectional(enterBazusBTextFieldProperty);

        enterWuATextFieldProperty = new SimpleStringProperty("C:\\RodoTemp\\WuRodo\\A");
        enterWuATextField.textProperty().bindBidirectional(enterWuATextFieldProperty);
        enterWuBTextFieldProperty = new SimpleStringProperty("C:\\RodoTemp\\WuRodo\\B");
        enterWuBTextField.textProperty().bindBidirectional(enterWuBTextFieldProperty);
    }

    public void handleStartButtonAction() {
        mainControllerActions.handleStartButton();
    }

    public void handleNextRowButtonAction() {
        mainControllerActions.handleNextRowButton();
    }

    public void handleCustomRowButtonAction() {
        mainControllerActions.handleCustomRowButton();
    }

    public void handleBazusStartButtonAction() {
        mainControllerActions.handleBazusStartButton();
    }

    public void handleDeleteBazusButtonAction() {
        mainControllerActions.handleDeleteBazusButton();
    }

    public void handleWuStartButtonAction() {
        mainControllerActions.handleWuStartButton();
    }

    public void handleDeleteWuButtonAction() {
        mainControllerActions.handleDeleteWuButton();
    }

    /*
    public void handleGetFileListFromGitAction() {
        mainControllerActions.handleGetFileListFromGit();
    }
    */

    //--------------------------------------------

    public String getEnterLinkStringProperty() {
        return enterLinkStringProperty.get();
    }

    public StringProperty enterLinkStringPropertyProperty() {
        return enterLinkStringProperty;
    }

    public void setEnterLinkStringProperty(String enterLinkStringProperty) {
        this.enterLinkStringProperty.set(enterLinkStringProperty);
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

    public boolean isDisableButtonsProperty() {
        return disableButtonsProperty.get();
    }

    public BooleanProperty disableButtonsPropertyProperty() {
        return disableButtonsProperty;
    }

    public void setDisableButtonsProperty(boolean disableButtonsProperty) {
        this.disableButtonsProperty.set(disableButtonsProperty);
    }

    public String getEnterWuATextFieldProperty() {
        return enterWuATextFieldProperty.get();
    }

    public StringProperty enterWuATextFieldPropertyProperty() {
        return enterWuATextFieldProperty;
    }

    public void setEnterWuATextFieldProperty(String enterWuATextFieldProperty) {
        this.enterWuATextFieldProperty.set(enterWuATextFieldProperty);
    }

    public String getEnterWuBTextFieldProperty() {
        return enterWuBTextFieldProperty.get();
    }

    public StringProperty enterWuBTextFieldPropertyProperty() {
        return enterWuBTextFieldProperty;
    }

    public void setEnterWuBTextFieldProperty(String enterWuBTextFieldProperty) {
        this.enterWuBTextFieldProperty.set(enterWuBTextFieldProperty);
    }

    /*
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
    */
}
