package pl.maciejprogramuje.rodobazus.Controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import pl.maciejprogramuje.rodobazus.FileUnzipped;
import pl.maciejprogramuje.rodobazus.FileZipped;
import pl.maciejprogramuje.rodobazus.Main;

public class MainControllerActions {
    private MainController mainController;
    private Integer rowsNumber;

    public MainControllerActions(MainController mainController) {
        this.mainController = mainController;
    }

    public void handleStartButton() {
        mainController.messageStringProperty.setValue("");
        final String path = mainController.enterLinkStringProperty.getValue();
        if (path.isEmpty()) {
            mainController.messageStringProperty.setValue(Main.bundles.getString("label.message.noLink.text"));
        } else {
            mainController.spinnerVisibleProperty.setValue(true);
            mainController.startButtonDisableProperty.setValue(true);

            final String finalCombinedFileName = Main.fileName + path.substring(path.lastIndexOf("\\") + 1);
            mainController.messageStringProperty.setValue(Main.bundles.getString("label.message.processing.text") + " " + finalCombinedFileName + "...");

            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    if (path.endsWith(".zip")) {
                        FileZipped fileZipped = new FileZipped();
                        fileZipped.readNamesOfFiles(path, finalCombinedFileName, mainController.messageStringProperty);
                        rowsNumber = fileZipped.getRows().size();
                        MainControllerUtils.showOnMessageLabel( rowsNumber + " " + Main.bundles.getString("label.message.created.n.rows"), mainController.messageStringProperty);
                        fileZipped.writeToXlsx(fileZipped.getRows(), finalCombinedFileName);
                    } else {
                        FileUnzipped fileUnzipped = new FileUnzipped();
                        fileUnzipped.readNamesOfFiles(path, finalCombinedFileName, mainController.messageStringProperty);
                        rowsNumber = fileUnzipped.getRows().size();
                        MainControllerUtils.showOnMessageLabel(rowsNumber + " " + Main.bundles.getString("label.message.created.n.rows"), mainController.messageStringProperty);
                        fileUnzipped.writeToXlsx(fileUnzipped.getRows(), finalCombinedFileName);
                    }
                    return null;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                public void handle(WorkerStateEvent event) {
                    mainController.spinnerVisibleProperty.setValue(false);
                    mainController.startButtonDisableProperty.setValue(false);
                    MainControllerUtils.showOnMessageLabelPattern(Main.bundles.getString("label.message.done.text"), mainController.messageStringProperty, rowsNumber);
                }
            });

            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
