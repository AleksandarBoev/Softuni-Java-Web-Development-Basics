package app.utils;

import java.io.*;


public class MyFileReaderImpl implements MyFileReader {
    @Override
    public String readFileContentFromFullPath(String fullPath) throws IOException {
        StringBuilder sb = new StringBuilder();

        File file = new File(fullPath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String currentLine = "";
        while ((currentLine = reader.readLine()) != null) {
            sb.append(currentLine).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
