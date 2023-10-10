package pl.maciejprogramuje.rodobazus.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
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

                    MainControllerUtils.showOnMessageLabelPattern(
                            Main.bundles.getString("button.next.title"),
                            mainController.nextRowStringProperty,
                            rowToAnaliseIndex,
                            folderReader.getFileRows().get(rowToAnaliseIndex).getRowName()
                    );
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

            rowToAnaliseIndex++;

            openFile(fileRow);
        }
    }

    public String openFile(FileRow fr) {
        File file = new File(fr.getRowPath());

        mainController.pathStringProperty.setValue(fr.getRowPath());

        try {
            if (file.exists()) {
                if (fr.getRowName().contains("bazus") && fr.getRowExtension().equals("jar")) {
                    Runtime runtime = Runtime.getRuntime();
                    Process process = runtime.exec("\"C:\\java_decompiler\\jd-gui.exe\" \"" + file.getAbsolutePath() + "\"");
                } else {
                    Desktop.getDesktop().open(file);
                }

                setSpinnerProperties(rowToAnaliseIndex);

                MainControllerUtils.showOnMessageLabelPattern(
                        Main.bundles.getString("button.next.title"),
                        mainController.nextRowStringProperty,
                        rowToAnaliseIndex,
                        folderReader.getFileRows().get(rowToAnaliseIndex).getRowName()
                );

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
        int customRowNumber = Integer.parseInt(mainController.customRowSpinner.editorProperty().getValue().textProperty().getValue());

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
