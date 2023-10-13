package pl.maciejprogramuje.rodobazus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

public class MoveFilesUtility {
    public static void moveSpecificFiles(String[] extensions, String rootPath, String destFolder) {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    System.out.println("ANALISING Moved: " + name + ", f.getAbsolutePath()=" + f.getAbsolutePath() + ", destFolder=" + destFolder);

                    for (String excludedExtension : extensions) {
                        if (excludedExtension.equals(extension)) {
                            try {
                                FileUtils.moveFile(
                                        f,
                                        new File(destFolder + "\\" + name),
                                        StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Moved: " + name);
                        }
                    }
                } else if (f.isDirectory()) {
                    moveSpecificFiles(extensions, f.getAbsolutePath(), destFolder);
                }
            }
        }
    }
}

