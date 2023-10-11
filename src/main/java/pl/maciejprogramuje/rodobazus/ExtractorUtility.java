package pl.maciejprogramuje.rodobazus;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;

public class ExtractorUtility {
    public static void extractFile(String fileURL, String saveDir) throws IOException {
        String[] commands = {
                "cmd",
                "/c",
                "7z x " + fileURL + " -o" + saveDir
        };

        Process process = Runtime.getRuntime().exec(commands);
        process.destroy();
    }
}
