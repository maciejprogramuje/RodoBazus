package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;

import java.io.File;

import static pl.maciejprogramuje.rodobazus.Main.EXCLUDED_EXTENSIONS;

public class DeleteFilesUtility {
    public static void deleteAllFilesInFolder(String rootPath, final StringProperty messageStringProperty) {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();

                    deleteFile(f, name);
                } else if (f.isDirectory()) {
                    deleteExcludedFiles(f.getAbsolutePath(), messageStringProperty);
                }
            }
        }
    }

    public static void deleteExcludedFiles(String rootPath, StringProperty pathProperty) {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    for (String excludedExtension : EXCLUDED_EXTENSIONS) {
                        if (excludedExtension.equals(extension)) {
                            deleteFile(f, name);
                        }
                    }
                } else if (f.isDirectory()) {
                    deleteExcludedFiles(f.getAbsolutePath(), pathProperty);
                }
            }
        }
    }

    private static void deleteFile(File f, String name) {
        boolean delete = f.delete();

        if (delete) {
            System.out.println("Deleted: " + name);
        } else {
            System.out.println("PROBLEM not deleted: " + name);
        }
    }
}
