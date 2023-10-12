package pl.maciejprogramuje.rodobazus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static pl.maciejprogramuje.rodobazus.Main.EXCLUDED_EXTENSIONS;

public class DeleteFilesUtility {
    public static void deleteAllFilesInFolder(String rootPath) throws IOException {
        FileUtils.deleteDirectory(new File(rootPath));
        FileUtils.forceMkdir(new File(rootPath));
    }

    public static void deleteExcludedFiles(String rootPath) throws IOException {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    for (String excludedExtension : EXCLUDED_EXTENSIONS) {
                        if (excludedExtension.equals(extension)) {
                            FileUtils.delete(f);
                            System.out.println("Deleted: " + name);
                        }
                    }
                } else if (f.isDirectory()) {
                    deleteExcludedFiles(f.getAbsolutePath());
                }
            }
        }
    }
}
