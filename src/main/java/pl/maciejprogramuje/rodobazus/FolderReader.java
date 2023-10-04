package pl.maciejprogramuje.rodobazus;

import javafx.beans.property.StringProperty;
import pl.maciejprogramuje.rodobazus.controllers.MainControllerUtils;
import pl.maciejprogramuje.rodobazus.models.FileRow;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static pl.maciejprogramuje.rodobazus.Main.EXCLUDED_EXTENSIONS;

public class FolderReader {
    List<FileRow> fileRows;

    public FolderReader() {
        fileRows = new ArrayList<>();
    }

    public void readNamesOfFiles(String rootPath, final StringProperty messageStringProperty) {
        File directory = new File(rootPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (final File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                    String patch = f.getAbsolutePath();

                    if (Arrays.stream(EXCLUDED_EXTENSIONS).noneMatch(Predicate.isEqual(extension))) {
                        fileRows.add(new FileRow(name, extension, patch));
                        int rowLastNumber = fileRows.size() - 1;
                        fileRows.get(rowLastNumber).setRowNumber(rowLastNumber);

                        MainControllerUtils.showOnMessageLabel(name, messageStringProperty);

                        System.out.println(rowLastNumber + ": " + name + ", " + extension + ", " + patch);
                    }
                } else if (f.isDirectory()) {
                    readNamesOfFiles(f.getAbsolutePath(), messageStringProperty);
                }
            }
        }
    }

    public List<FileRow> getFileRows() {
        return fileRows;
    }
}