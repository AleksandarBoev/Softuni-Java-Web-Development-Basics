package app.domain.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MyFileReader {
    String readFileContentFromFullPath(String fullPath) throws IOException;
}
