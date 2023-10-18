package pl.maciejprogramuje.rodobazus.controllers;

import javafx.concurrent.Task;
import javafx.scene.control.SpinnerValueFactory;
import pl.maciejprogramuje.rodobazus.*;
import pl.maciejprogramuje.rodobazus.models.FileRow;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainControllerActions {
    private final MainController mainController;
    private FolderReader folderReader;
    private int rowToAnaliseIndex;
    private ArrayList<String> lines;
    private FilesComparator fileComparator;

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
            mainController.setDisableButtonsProperty(true);
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

            task.setOnSucceeded(event -> {
                mainController.setDisableButtonsProperty(false);
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

    /*
    public void handleGetFileListFromGit() {
        final String folder = mainController.getEnterLinkStringProperty();

        String gitCommitId = mainController.getEnterGitCommitIdTextFieldStringProperty();
        String gitBranch = mainController.getEnterGitBranchTextFieldStringProperty();

        System.out.println("folder=" + folder + ", gitCommitId=" + gitCommitId + ", gitBranch=" + gitBranch);

        String command = "git checkout";
        GitUtility.doGitCommand(command, folder, gitBranch, false);

        command = "git diff " + gitCommitId + " HEAD --name-only";
        GitUtility.doGitCommand(command, folder, "", true);
    }
    */

    public void handleBazusStartButton() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                mainController.setDisableButtonsProperty(true);

                fileComparator = new FilesComparator(
                        "BazusRodo",
                        mainController.getEnterBazusATextFieldProperty(),
                        mainController.getEnterBazusBTextFieldProperty(),
                        mainController.pathStringPropertyProperty()
                );
                fileComparator.downloadBazusRepos();
                fileComparator.extractRepos();
                fileComparator.cleanFoldersFromExcludedFiles();

                return null;
            }
        };

        task.setOnSucceeded(event -> mainController.setDisableButtonsProperty(false));

        Thread thread = new Thread(task);
        thread.start();
    }

    public void handleWuStartButton() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                mainController.setDisableButtonsProperty(true);

                fileComparator = new FilesComparator(
                        "WuRodo",
                        mainController.getEnterWuATextFieldProperty(),
                        mainController.getEnterWuBTextFieldProperty(),
                        mainController.pathStringPropertyProperty()
                );
                fileComparator.extractRepos();
                fileComparator.cleanFoldersFromExcludedFiles();

                return null;
            }
        };

        task.setOnSucceeded(event -> mainController.setDisableButtonsProperty(false));

        Thread thread = new Thread(task);
        thread.start();
    }

    public void handleDeleteBazusButton() {
        DeleteFilesUtility.deleteAllFilesInFolders(mainController,
                "C:\\RodoTemp\\BazusRodo\\_temp\\A",
                "C:\\RodoTemp\\BazusRodo\\_temp\\B",
                "C:\\RodoTemp\\BazusRodo\\A",
                "C:\\RodoTemp\\BazusRodo\\B",
                "C:\\RodoTemp\\BazusRodo\\Docs",
                "C:\\RodoTemp\\BazusRodo\\Pics"
        );
    }

    public void handleDeleteWuButton() {
        DeleteFilesUtility.deleteAllFilesInFolders(mainController,
                "C:\\RodoTemp\\WuRodo\\A",
                "C:\\RodoTemp\\WuRodo\\B",
                "C:\\RodoTemp\\WuRodo\\Docs",
                "C:\\RodoTemp\\WuRodo\\Pics"
        );
    }

    public void handleCopyAWuButton() {
        copyWu(mainController.getEnterWuATextFieldProperty(), "A");
    }

    public void handleCopyBWuButton() {
        copyWu(mainController.getEnterWuBTextFieldProperty(), "B");
    }

    private void copyWu(String enterWuTextFieldProperty, String branch) {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                mainController.setDisableButtonsProperty(true);
                mainController.setPathStringProperty(
                        "Trwa kopiowanie z " + enterWuTextFieldProperty + " do C:\\RodoTemp\\WuRodo\\" + branch);

                CopyDirectoriesUtils.copyWuRepos(
                        enterWuTextFieldProperty,
                        branch
                );

                return null;
            }
        };

        task.setOnSucceeded(event -> {
            mainController.setDisableButtonsProperty(false);
            mainController.setPathStringProperty("Kopiowanie " + branch + " ukończone");
        });

        Thread thread = new Thread(task);
        thread.start();
    }


}
