package pl.maciejprogramuje.rodobazus;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public abstract class FileAbstract {
    protected List<RowInExcel> rows;

    public abstract void readNamesOfFiles(String rootPath, String combinedFileName, StringProperty messageStringProperty);

    public void writeToTxt(String fileName, String filePath, String name, String filePathWithoutFilename) {
        createNewFileTxt(fileName);

        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, true));
            if (name != null) {
                writer.append(name).append(",");
            }
            writer.append(filePath).append(",");
            writer.append(filePathWithoutFilename).append("\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeToXlsx(List<RowInExcel> rows, String combinedFileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(Main.bundles.getString("excel.sheet.listing"));
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue(Main.bundles.getString("excel.column.first"));
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue(Main.bundles.getString("excel.column.second"));
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue(Main.bundles.getString("excel.column.third"));
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue(Main.bundles.getString("excel.column.fourth"));

        for (int i = 0; i < rows.size(); i++) {
            System.out.println("row " + (i + 1) + ", " + rows.get(i).getName() + ", " + rows.get(i).getFileExtension() + ", " + rows.get(i).getPath()+ ", " + rows.get(i).getPathWithoutFilename());
            Row newRow = sheet.createRow(i + 1);
            Cell cell = newRow.createCell(0);
            cell.setCellValue(rows.get(i).getName());
            cell = newRow.createCell(1);
            cell.setCellValue(rows.get(i).getFileExtension());
            cell = newRow.createCell(2);
            cell.setCellValue(rows.get(i).getPath());
            cell = newRow.createCell(3);
            cell.setCellValue(rows.get(i).getPathWithoutFilename());
        }

        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(combinedFileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNewFileTxt(String name) {
        File file = new File(name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RowInExcel> getRows() {
        return rows;
    }
}
