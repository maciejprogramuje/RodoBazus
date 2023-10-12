package pl.maciejprogramuje.rodobazus;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GitUtility {
    public static void doGitCommand(String command, String folder, String branchName, boolean isFileListGenerated) {
        ArrayList<String> lines = new ArrayList<>();

        try {
            String[] commands = {
                    "cmd",
                    "/c",
                    command + " " + branchName
            };

            Process process = Runtime.getRuntime().exec(commands, null, new File(folder));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 8 * 1024);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    System.out.println("GIT -->>" + line);

                    if (isFileListGenerated) {
                        lines.add(line.trim());
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
