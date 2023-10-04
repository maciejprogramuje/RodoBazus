package pl.maciejprogramuje.rodobazus;

public class RowInExcel {
    private String name;
    private String path;
    private String pathWithoutFilename;
    private String fileExtension;

    public RowInExcel(String name, String fileExtension, String path, String pathWithoutFilename) {
        this.name = name;
        this.fileExtension = fileExtension;
        this.path = path;
        this.pathWithoutFilename = pathWithoutFilename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathWithoutFilename() {
        return pathWithoutFilename;
    }

    public void setPathWithoutFilename(String pathWithoutFilename) {
        this.pathWithoutFilename = pathWithoutFilename;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
