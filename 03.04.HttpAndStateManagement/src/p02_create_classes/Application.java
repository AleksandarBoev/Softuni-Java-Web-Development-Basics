package p02_create_classes;

import p02_create_classes.engine.ExecutableImpl;
import p02_create_classes.interfaces.Executable;
import p02_create_classes.interfaces.Reader;
import p02_create_classes.interfaces.Writer;
import p02_create_classes.io.ConsoleReader;
import p02_create_classes.io.ConsoleWriter;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        Reader consoleReader = new ConsoleReader();
        Writer consoleWriter = new ConsoleWriter();
        Executable httpEngine = new ExecutableImpl(consoleReader, consoleWriter);

        httpEngine.execute();
    }
}
