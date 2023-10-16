package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JarComparator {
    private final String enterBazusA;
    private final String enterBazusB;
    private List<String> jarListA;
    private List<String> jarListB;
    private StringProperty pathProperty;

    public JarComparator(String enterBazusA, String enterBazusB, StringProperty pathProperty) {
        this.enterBazusA = enterBazusA;
        this.enterBazusB = enterBazusB;
        this.pathProperty = pathProperty;
    }

    public void downloadRepos() {
        try {
            jarListA = fillJarList(enterBazusA);
            jarListB = fillJarList(enterBazusB);

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
        }

        for (String jar : jarListB) {
            extractToDirectories(jar, "B");
        }
    }

    private void extractToDirectories(String jar, String version) {
        String fileName = jar.substring(jar.lastIndexOf("/") + 1);
        String fileUrl = "C:\\RodoTemp\\BazusRodo\\_temp\\" + version + "\\" + fileName;
        String saveDir = "";
        if (fileName.contains("-")) {
            saveDir = "C:\\RodoTemp\\BazusRodo\\" + version + "\\" + fileName.substring(0, fileName.lastIndexOf("-"));
        } else {
            saveDir = "C:\\RodoTemp\\BazusRodo\\" + version + "\\" + fileName.substring(0, fileName.indexOf(".jar"));
        }

        pathProperty.setValue("Rozpakowywanie: " + fileUrl);

        ExtractorUtility.extractFile(fileUrl, saveDir);
    }

    private List<String> fillJarList(String enterBazus) throws IOException {
        List<String> readLines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(enterBazus));
        String readLine = reader.readLine();

        while (readLine != null) {
            readLines.add(readLine);
            readLine = reader.readLine();
        }

        reader.close();

        List<String> lines = new ArrayList<>();
        for (String l : readLines) {
            if (l.contains("jar href=") && l.contains("/bazus")) {
                lines.add(l.substring(l.indexOf("https"), l.lastIndexOf("jar") + 3).replace("https", "http"));
            }
        }

        return lines;
    }

    public void cleanFoldersFromExcludedFiles() {
        try {
            pathProperty.setValue("Trwa czyszczenie C:\\RodoTemp\\BazusRodo\\A z *.class itp.");
            DeleteFilesUtility.cleanFolders("C:\\RodoTemp\\BazusRodo\\A", "A");

            pathProperty.setValue("Trwa czyszczenie C:\\RodoTemp\\BazusRodo\\B z *.class itp.");
            DeleteFilesUtility.cleanFolders("C:\\RodoTemp\\BazusRodo\\B", "B");

            pathProperty.setValue("Oczyszczanie folderow zakonczone");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
