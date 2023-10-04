package pl.maciejprogramuje.rodobazus.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SpinnerValueFactory;
import pl.maciejprogramuje.rodobazus.FolderReader;
import pl.maciejprogramuje.rodobazus.Main;
import pl.maciejprogramuje.rodobazus.models.FileRow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainControllerActions {
    private MainController mainController;
    private FolderReader folderReader;
    private int rowToAnaliseIndex;

    public MainControllerActions(MainController mainController) {
        this.mainController = mainController;
    }

    public void handleStartButton() {
        rowToAnaliseIndex = 0;

        mainController.messageStringProperty.setValue("");
        final String path = mainController.enterLinkStringProperty.getValue();
        if (path.isEmpty()) {
            mainController.messageStringProperty.setValue(Main.bundles.getString("label.message.noLink.text"));
        } else {
            mainController.spinnerVisibleProperty.setValue(true);
            mainController.startButtonDisableProperty.setValue(true);
            mainController.messageStringProperty.setValue("");
            mainController.pathStringProperty.setValue("");
            mainController.nextRowStringProperty.setValue("Rozpocznij analize plikow!");

            final String finalCombinedFileName = path.substring(path.lastIndexOf("\\") + 1);
            mainController.messageStringProperty.setValue(Main.bundles.getString("label.message.processing.text") + " " + finalCombinedFileName + "...");

            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    folderReader = new FolderReader();
                    folderReader.readNamesOfFiles(path, mainController.messageStringProperty);

                    return null;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                public void handle(WorkerStateEvent event) {
                    mainController.spinnerVisibleProperty.setValue(false);
                    mainController.startButtonDisableProperty.setValue(false);
                    mainController.customRowBooleanProperty.setValue(false);

                    setSpinnerProperties(rowToAnaliseIndex);

                    MainControllerUtils.showOnMessageLabelPattern(
                            Main.bundles.getString("label.message.done.text"),
                            mainController.messageStringProperty,
                            folderReader.getFileRows().size());
                }
            });

            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public void handleNextRowButton() {
        int size = folderReader.getFileRows().size();
        if (size > 0 && rowToAnaliseIndex < size) {
            FileRow fileRow = folderReader.getFileRows().get(rowToAnaliseIndex);

            String openFileMessage = openFile(fileRow);

            setSpinnerProperties(rowToAnaliseIndex);

            rowToAnaliseIndex++;
        }
    }

    public String openFile(FileRow fr) {
        File file = new File(fr.getRowPath());

        mainController.pathStringProperty.setValue(fr.getRowPath());

        MainControllerUtils.showOnMessageLabelPattern(
                Main.bundles.getString("button.next.title"),
                mainController.nextRowStringProperty,
                fr.getRowNumber(),
                fr.getRowName()
        );

        try {
            if (file.exists()) {
                Desktop.getDesktop().open(file);
                return fr.getRowName();
            } else {
                return Main.bundles.getString("file.not.exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Main.bundles.getString("some.file.error");
        }
    }

    public void handleCustomRowButton() {
        Integer customRowNumber = mainController.customRowSpinner.getValue();

        rowToAnaliseIndex = customRowNumber + 1;

        FileRow row = folderReader.getFileRows().get(customRowNumber);
        openFile(row);
    }

    private void setSpinnerProperties(int initialValue) {
        mainController.customRowSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        0,
                        folderReader.getFileRows().size(),
                        initialValue));
    }
}
