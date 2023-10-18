package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesComparator {
    private final String app;
    private final List<String> jarListA;
    private final List<String> jarListB;
    private final StringProperty pathProperty;

    public FilesComparator(String app, String enterA, String enterB, StringProperty pathProperty) {
        this.app = app;
        this.pathProperty = pathProperty;
        jarListA = new ArrayList<>();
        jarListB = new ArrayList<>();

        try {
            if (app.equals("WuRodo")) {
                readFoldersToFillJarList("C:\\RodoTemp\\WuRodo\\A", jarListA);
                readFoldersToFillJarList("C:\\RodoTemp\\WuRodo\\B", jarListB);
            } else {
                readJnlpToFillJarList(enterA, jarListA);
                readJnlpToFillJarList(enterB, jarListB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFoldersToFillJarList(String enter, List<String> jarList) {
        File directory = new File(enter);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);

                    if (name.contains("bazus") && extension.equals("jar")) {
                        jarList.add(f.getAbsolutePath());
                    }
                } else if (f.isDirectory()) {
                    readFoldersToFillJarList(f.getAbsolutePath(), jarList);
                }
            }
        }
    }

    public void downloadBazusRepos() {
        try {
            System.out.println("=================== A ==================");
            jarListA.forEach(System.out::println);
            System.out.println("=================== B ==================");
            jarListB.forEach(System.out::println);

            for (String jar : jarListA) {
                pathProperty.setValue(DownloadUtility.downloadFile(jar, "C:\\RodoTemp\\BazusRodo\\_temp\\A"));
            }

            for (String jar : jarListB) {
                pathProperty.setValue(DownloadUtility.downloadFile(jar, "C:\\RodoTemp\\BazusRodo\\_temp\\B"));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void extractRepos() {
        for (String jar : jarListA) {
            extractToDirectories(jar, "A");

            pathProperty.setValue("Rozpakowywanie z A: " + jar);
        }

        for (String jar : jarListB) {
            extractToDirectories(jar, "B");

            pathProperty.setValue("Rozpakowywanie z B: " + jar);
        }
    }

    //todo fileUrl
    private void extractToDirectories(String jar, String branch) {
        String fileName = jar.substring(jar.lastIndexOf("/") + 1);

        String fileUrl = fileName;
        String saveDir = "";

        if (app.equals("BazusRodo")) {
            fileUrl = "C:\\RodoTemp\\" + app + "\\_temp\\" + branch + "\\" + fileName;
            saveDir = "C:\\RodoTemp\\" + app + "\\" + branch + "\\";
        }

        if (fileName.contains("-")) {
            saveDir = saveDir + fileName.substring(0, fileName.lastIndexOf("-"));
        } else {
            saveDir = saveDir + fileName.substring(0, fileName.indexOf(".jar"));
        }

        ExtractorUtility.extractFile(fileUrl, saveDir);
    }

    private void readJnlpToFillJarList(String enter, List<String> jarList) throws IOException {
        List<String> readLines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(enter));
        String readLine = reader.readLine();

        while (readLine != null) {
            readLines.add(readLine);
            readLine = reader.readLine();
        }

        reader.close();

        for (String l : readLines) {
            if (l.contains("jar href=") && l.contains("/bazus")) {
                jarList.add(l.substring(l.indexOf("https"), l.lastIndexOf("jar") + 3).replace("https", "http"));
            }
        }
    }

    public void cleanFoldersFromExcludedFiles() {
        try {
            pathProperty.setValue("Trwa czyszczenie " + app);
            DeleteFilesUtility.cleanFolders("C:\\RodoTemp\\" + app + "\\A", app);

            pathProperty.setValue("Trwa czyszczenie " + app);
            DeleteFilesUtility.cleanFolders("C:\\RodoTemp\\" + app + "\\B", app);

            pathProperty.setValue("Oczyszczanie folderow zakonczone");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
