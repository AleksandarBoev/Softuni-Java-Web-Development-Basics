package p02_create_classes.app;

import javafx.util.Pair;
import p02_create_classes.implementations.HttpRequestImpl;
import p02_create_classes.implementations.HttpResponseImpl;
import p02_create_classes.interfaces.HttpRequest;
import p02_create_classes.interfaces.HttpResponse;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String testString =
                "POST /url HTTP/1.1\n" +
                        "Date: 17/01/2019\n" +
                        "Host: localhost:8000\n" +
                        "Content-Type: application/xml\n" +
                        "Authorization: Basic UGVzaG8=\n" +
                        "\n" +
                        "name=Yum&quantity=50&price=10\n";

        HttpRequest httpRequest = new HttpRequestImpl(testString);
        System.out.println(httpRequest);

        String[] wat = new String[] {"no", "way"};

        Pair<String, String> wat2 = new Pair<>("wat", "no");

        String paths = "/url /path /register /login /products/create /admin /users /all";

        Set<String> validUrls = Arrays.stream(paths.split(" ")).collect(Collectors.toSet());
        HttpResponse httpResponse = new HttpResponseImpl(httpRequest, validUrls);

        String answer = new String(httpResponse.getBytes());
        System.out.println(answer);
    }
}
