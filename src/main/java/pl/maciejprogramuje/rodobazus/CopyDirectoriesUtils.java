package pl.maciejprogramuje.rodobazus;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class CopyDirectoriesUtils {
    public static void copyWuRepos(String source, String branch) {
        File fileASource = new File(source);
        File fileADestination = new File("C:\\RodoTemp\\WuRodo\\" + branch);

        FileFilter filterGit = FileFilterUtils.notFileFilter(FileFilterUtils.nameFileFilter(".git"));

        try {
            FileUtils.copyDirectory(fileASource, fileADestination, filterGit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
