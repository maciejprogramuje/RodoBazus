package pl.maciejprogramuje.rodobazus.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import pl.maciejprogramuje.rodobazus.FolderReader;
import pl.maciejprogramuje.rodobazus.Main;

public class MainControllerActions {
    private MainController mainController;

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

            final String finalCombinedFileName = path.substring(path.lastIndexOf("\\") + 1);
            mainController.messageStringProperty.setValue(Main.bundles.getString("label.message.processing.text") + " " + finalCombinedFileName + "...");

            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    FolderReader folderReader = new FolderReader();
                    folderReader.readNamesOfFiles(path, mainController.messageStringProperty);

                    return null;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                public void handle(WorkerStateEvent event) {
                    mainController.spinnerVisibleProperty.setValue(false);
                    mainController.startButtonDisableProperty.setValue(false);
                    MainControllerUtils.showOnMessageLabelPattern(Main.bundles.getString("label.message.done.text"), mainController.messageStringProperty);
                }
            });

            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
