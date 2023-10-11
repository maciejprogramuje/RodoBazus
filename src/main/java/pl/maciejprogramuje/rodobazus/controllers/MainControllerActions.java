package pl.maciejprogramuje.rodobazus.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.SpinnerValueFactory;
import pl.maciejprogramuje.rodobazus.JarComparator;
import pl.maciejprogramuje.rodobazus.FolderReader;
import pl.maciejprogramuje.rodobazus.Main;
import pl.maciejprogramuje.rodobazus.models.FileRow;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainControllerActions {
    private MainController mainController;
    private FolderReader folderReader;
    private int rowToAnaliseIndex;
    private ArrayList<String> lines;
    private JarComparator jarComparator;

    public MainControllerActions(MainController mainController) {
        this.mainController = mainController;
    }

    public void handleStartButton() {
        rowToAnaliseIndex = 0;

        mainController.setMessageStringProperty("");
        final String path = mainController.getEnterLinkStringProperty();
        if (path.isEmpty()) {
            mainController.setMessageStringProperty(Main.bundles.getString("label.message.noLink.text"));
        } else {
            mainController.setSpinnerVisibleProperty(true);
            mainController.setStartButtonDisableProperty(true);
            mainController.setMessageStringProperty("");
            mainController.setPathStringProperty("");

            final String finalCombinedFileName = path.substring(path.lastIndexOf("\\") + 1);
            mainController.setMessageStringProperty(Main.bundles.getString("label.message.processing.text") + " " + finalCombinedFileName + "...");

            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() {
                    folderReader = new FolderReader();
                    folderReader.readNamesOfFiles(path, mainController.messageStringPropertyProperty());

                    return null;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                public void handle(WorkerStateEvent event) {
                    mainController.setSpinnerVisibleProperty(false);
                    mainController.setStartButtonDisableProperty(false);
                    mainController.setCustomRowBooleanProperty(false);

                    setSpinnerProperties(rowToAnaliseIndex);

                    MainControllerUtils.showOnMessageLabelPattern(
                            Main.bundles.getString("label.message.done.text"),
                            mainController.messageStringPropertyProperty(),
                            folderReader.getFileRows().size());

                    MainControllerUtils.showOnMessageLabelPattern(
                            Main.bundles.getString("button.next.title"),
                            mainController.nextRowStringPropertyProperty(),
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

        mainController.setPathStringProperty(fr.getRowPath());

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
                        mainController.nextRowStringPropertyProperty(),
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

    public void handleGetFileListFromGit() {
        final String folder = mainController.getEnterLinkStringProperty();
        String gitCommitId = mainController.getEnterGitCommitIdTextFieldStringProperty();
        String gitBranch = mainController.getEnterGitBranchTextFieldStringProperty();

        System.out.println("folder=" + folder + ", gitCommitId=" + gitCommitId + ", gitBranch=" + gitBranch);

        String command = "git checkout";
        doGitCommand(command, folder, gitBranch, false);

        command = "git diff " + gitCommitId + " HEAD --name-only";
        doGitCommand(command, folder, "", true);
    }

    private void doGitCommand(String command, String folder, String branchName, boolean isFileListGenerated) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            String[] commands = {
                    "cmd",
                    "/c",
                    command + " " + branchName
            };

            Process process = Runtime.getRuntime().exec(commands, null, new File(folder));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8 * 1024);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    System.out.println("GIT -->>" + line);

                    if (isFileListGenerated) {
                        lines.add(line.trim());
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBazusStartButton() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws IOException {
                mainController.setSpinnerVisibleProperty(true);
                mainController.setStartBazusButtonDisableProperty(true);

                jarComparator = new JarComparator(mainController.getEnterBazusATextFieldProperty(), mainController.getEnterBazusBTextFieldProperty());
                jarComparator.downloadRepos(mainController.pathStringPropertyProperty());

                //to przeszkadza w usuwaniu
                jarComparator.extractRepos(mainController.pathStringPropertyProperty());

                jarComparator.deleteExcludedFileTypes(mainController.pathStringPropertyProperty());

                return null;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
                mainController.setSpinnerVisibleProperty(false);
                mainController.setStartBazusButtonDisableProperty(false);
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }
}
