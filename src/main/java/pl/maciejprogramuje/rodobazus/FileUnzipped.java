package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;
import pl.maciejprogramuje.rodobazus.Controllers.MainControllerUtils;

import java.io.File;
import java.util.ArrayList;

public class FileUnzipped extends FileAbstract {
    public FileUnzipped() {
        rows = new ArrayList<RowInExcel>();
    }

    public void readNamesOfFiles(String rootPath, String combinedFileName, final StringProperty messageStringProperty) {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    MainControllerUtils.showOnMessageLabel(f.getName(), messageStringProperty);
                    String tFileExtension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                    System.out.println(f.getName() + "," + tFileExtension + "," + f.getAbsolutePath() + "," + f.getParent());
                    rows.add(new RowInExcel(f.getName(), tFileExtension, f.getAbsolutePath(), f.getParent()));
                } else if (f.isDirectory()) {
                    try {
                        readNamesOfFiles(f.getAbsolutePath(), combinedFileName, messageStringProperty);
                    } catch (Exception e) {
                        writeToTxt(Main.fileNameDenied, f.getAbsolutePath(), f.getName(), f.getParent());
                    }
                }
            }
        }
    }
}