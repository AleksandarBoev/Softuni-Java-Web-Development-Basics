package p02_create_classes.io;

import p02_create_classes.interfaces.Writer;

public class ConsoleWriter implements Writer {
    @Override
    public void write(String line) {
        System.out.print(line);
    }

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
