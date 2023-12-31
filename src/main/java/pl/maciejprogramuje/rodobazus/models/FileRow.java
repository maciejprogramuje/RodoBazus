package pl.maciejprogramuje.rodobazus.models;

public class FileRow {
    int rowNumber;
    String rowName;
    String rowExtension;
    String rowPath;

    public FileRow(String rowName, String rowExtension, String rowPath) {
        this.rowName = rowName;
        this.rowExtension = rowExtension;
        this.rowPath = rowPath;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public String getRowExtension() {
        return rowExtension;
    }

    public void setRowExtension(String rowExtension) {
        this.rowExtension = rowExtension;
    }

    public String getRowPath() {
        return rowPath;
    }

    public void setRowPath(String rowPath) {
        this.rowPath = rowPath;
    }
}
