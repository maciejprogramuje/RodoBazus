package pl.maciejprogramuje.rodobazus;

import javafx.concurrent.Task;
import org.apache.commons.io.FileUtils;
import pl.maciejprogramuje.rodobazus.controllers.MainController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static pl.maciejprogramuje.rodobazus.Main.*;

public class CleanFoldersUtility {
    public static void cleanFolders(String rootPath, String app) throws IOException {
        File directory = new File(rootPath);

        File[] files = directory.listFiles();

        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    if (!clean(f, name, extension)) {
                        if (!move("C:\\RodoTemp\\" + app + "\\Docs\\", DOC_FILES, f)) {
                            move("C:\\RodoTemp\\" + app + "\\Pics\\", PIC_FILES, f);
                        }
                    }
                } else if (f.isDirectory()) {
                    cleanFolders(f.getAbsolutePath(), app);
                }
            }
        }
    }

    private static boolean move(String destFolder, String[] docFiles, File oldFile) throws IOException {
        String name = oldFile.getName();
        String extension = name.substring(name.lastIndexOf(".") + 1);

        for (String docExtension : docFiles) {
            if (docExtension.equals(extension)) {
                File newFile = new File(destFolder + "\\" + name);

                Files.move(oldFile.toPath(), newFile.toPath(), REPLACE_EXISTING);

                System.out.println("Moved: " + name + " to: " + destFolder + name);

                return true;
            }
        }

        return false;
    }

    private static boolean clean(File f, String name, String extension) throws IOException {
        for (String excludedExtension : EXCLUDED_EXTENSIONS) {
            if (excludedExtension.equals(extension)) {
                FileUtils.delete(f);
                System.out.println("Deleted: " + name);

                return true;
            }
        }

        return false;
    }

    public static void deleteAllFilesInFolders(MainController mainController, String... paths) {
        mainController.setDisableButtonsProperty(true);

        AtomicInteger pathsNumber = new AtomicInteger();

        for (String rootPath : paths) {
            final Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws IOException {
                    FileUtils.deleteDirectory(new File(rootPath));
                    FileUtils.forceMkdir(new File(rootPath));

                    return null;
                }
            };

            task.setOnSucceeded(event -> {
                pathsNumber.getAndIncrement();
                if (pathsNumber.get() == paths.length) {
                    mainController.setDisableButtonsProperty(false);
                }
            });

            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public static void distillateFiles(String rootPath, String app, String branch) throws IOException {
        File directory = new File(rootPath);

        File[] files = directory.listFiles();

        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getAbsolutePath()
                            .replaceAll("\\\\", "_");
                    name = name.substring(name.indexOf("_" + branch + "_") + 3);

                    File newFile = new File("C:\\RodoTemp\\" + app + "\\" + branch + "\\" + name);

                    System.out.println("Destylacja: " + f.getAbsolutePath() + " do: " + newFile.getAbsolutePath());

                    Files.move(f.toPath(), newFile.toPath(), REPLACE_EXISTING);
                } else if (f.isDirectory()) {
                    distillateFiles(f.getAbsolutePath(), app, branch);
                }
            }
        }
    }

    public static void deleteEmptyFolders(String rootPath) throws IOException {
        File directory = new File(rootPath);

        File[] files = directory.listFiles();

        if (files != null) {
            for (final File f : files) {
                if (f.isDirectory()) {
                    FileUtils.deleteDirectory(f);
                }
            }
        }
    }
}
