package pl.maciejprogramuje.rodobazus;

import javafx.concurrent.Task;
import org.apache.commons.io.FileUtils;
import pl.maciejprogramuje.rodobazus.controllers.MainController;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

import static pl.maciejprogramuje.rodobazus.Main.*;

public class DeleteFilesUtility {
    public static void cleanFolders(String rootPath, String branch) throws IOException {
        File directory = new File(rootPath);

        File[] files = directory.listFiles();

        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    if (!clean(f, name, extension)) {
                        if (!move("C:\\RodoTemp\\BazusRodo\\Docs\\", branch, DOC_FILES, extension, f, name)) {
                            move("C:\\RodoTemp\\BazusRodo\\Pics\\", branch, PIC_FILES, extension, f, name);
                        }
                    }
                } else if (f.isDirectory()) {
                    System.out.println("dir " + f.getAbsolutePath());
                    cleanFolders(f.getAbsolutePath(), branch);
                }
            }
        }
    }

    private static boolean move(String x, String branch, String[] docFiles, String extension, File f, String name) throws IOException {
        System.out.println("move");

        String destFolder = x + branch;

        for (String docExtension : docFiles) {
            if (docExtension.equals(extension)) {

                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
                name = name.substring(0, name.lastIndexOf(".")) + "_" + timeStamp + "." + extension;

                FileUtils.moveFile(
                        f,
                        new File(destFolder + "\\" + name),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved: " + name + " to: " + destFolder + "\\" + name);

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
}
