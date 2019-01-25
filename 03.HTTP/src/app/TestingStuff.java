package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestingStuff {
    public static void main(String[] args) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> wat = new ArrayList<>();

        String input;
        while (!(input = reader.readLine()).equals("end")) {
            wat.add(input);
        }

        if (wat.contains(System.lineSeparator()) || wat.contains("\n") || wat.contains("")) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    String extractAuthorizationLine(List<String> input) {
        String result = null; //if it returns null, then such a line has not been found

        for (String s : input) {
            if (s.contains("Authorization")) {
                result = s;
            }
        }

        return result;
    }
}
