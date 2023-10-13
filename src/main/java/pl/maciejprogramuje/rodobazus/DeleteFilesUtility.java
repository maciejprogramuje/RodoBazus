package pl.maciejprogramuje.rodobazus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

import static pl.maciejprogramuje.rodobazus.Main.*;

public class DeleteFilesUtility {
    public static void deleteAllFilesInFolder(String rootPath) throws IOException {
        FileUtils.deleteDirectory(new File(rootPath));
        FileUtils.forceMkdir(new File(rootPath));
    }

    public static void cleanFolders(String rootPath, String branch) throws IOException {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    if (!clean(f, name, extension)) {
                        if (!move("C:\\BazusTemp\\Docs\\", branch, DOC_FILES, extension, f, name)) {
                            move("C:\\BazusTemp\\Pics\\", branch, PIC_FILES, extension, f, name);
                        }
                    }

                } else if (f.isDirectory()) {
                    cleanFolders(f.getAbsolutePath(), branch);
                }
            }
        }
    }

    private static boolean move(String x, String branch, String[] docFiles, String extension, File f, String name) throws IOException {
        String destFolder = x + branch;

        for (String docExtension : docFiles) {
            if (docExtension.equals(extension)) {

                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
                name = name.substring(0, name.lastIndexOf(".")) + "_" + timeStamp + "." + extension;

                FileUtils.moveFile(
                        f,
                        new File(destFolder + "\\" + name),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved: " + name);

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
}
