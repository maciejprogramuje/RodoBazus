package pl.maciejprogramuje.rodobazus;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ExtractorUtility {
    public static void extractFile(String fileURL, String saveDir) {
        try {
            System.out.println("Rozpakowywanie: " + fileURL + " do: " + saveDir);
            ZipFile zipFile = new ZipFile(fileURL);
            zipFile.extractAll(saveDir);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
