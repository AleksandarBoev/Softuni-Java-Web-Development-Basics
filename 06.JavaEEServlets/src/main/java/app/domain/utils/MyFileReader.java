package app.domain.utils;

import java.io.IOException;

public interface MyFileReader {
    String readFileContentFromFullPath(String fullPath) throws IOException;
}
