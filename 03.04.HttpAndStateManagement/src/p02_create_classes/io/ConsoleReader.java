package p02_create_classes.io;

import p02_create_classes.interfaces.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException ioe) {
            return null;
        }
    }
}
