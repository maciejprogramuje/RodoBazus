package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;
import pl.maciejprogramuje.rodobazus.Controllers.MainControllerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileZipped extends FileAbstract {
    public FileZipped() {
        rows = new ArrayList<RowInExcel>();
    }

    public void readNamesOfFiles(String rootPath, String combinedFileName, final StringProperty messageStringProperty) {
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(rootPath);
            Enumeration zipEntries = zipFile.entries();
            while (zipEntries.hasMoreElements()) {
                String filePath = ((ZipEntry) zipEntries.nextElement()).getName();
                if (!filePath.endsWith("/")) {
                    final String name = filePath.substring(filePath.lastIndexOf("/") + 1);
                    String filePathWithoutFilename = filePath.substring(0, filePath.lastIndexOf("/") + 1);
                    String tFileExtension = name.substring(name.lastIndexOf("."));
                    // UtilsFile.writeToTxt(combinedFileName, filePath, name);

                    System.out.println(name + "," + tFileExtension  + "," + filePath + ", " + filePathWithoutFilename);
                    MainControllerUtils.showOnMessageLabel(name, messageStringProperty);

                    rows.add(new RowInExcel(name, tFileExtension, filePath, filePathWithoutFilename));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
