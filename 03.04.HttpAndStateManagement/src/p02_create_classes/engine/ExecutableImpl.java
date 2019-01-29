package p02_create_classes.engine;

import p02_create_classes.http_implementations.HttpRequestImpl;
import p02_create_classes.interfaces.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExecutableImpl implements Executable {
    private Reader reader;
    private Writer writer;
    private List<HttpRequest> httpRequests; //don't know how to structure code, so that the implementation can be determined via constructor
    private List<HttpResponse> httpResponses;

    public ExecutableImpl(Reader reader, Writer writer) throws IOException {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void execute() throws IOException {
        this.init();
 //       Set<String> validUrls = readUrls(); //must be commented for the cookies input
        List<String> httpRequestAsList = this.readRequestAsList();

        this.httpRequests.add(new HttpRequestImpl(httpRequestAsList));
 //       this.writer.writeLine(this.httpRequests.get(0).toString()); //looks ok

//        this.httpResponses.add(new HttpResponseImpl(this.httpRequests.get(0), validUrls));
//        String responseBody = new String(this.httpResponses.get(0).getBytes());
//        this.writer.writeLine(responseBody);

        this.writer.writeLine(this.getCookiesFormatted(httpRequests.get(0)));
    }

    private void init() {
        this.httpRequests = new ArrayList<>();
        this.httpResponses = new ArrayList<>();
    }

    private Set<String> readUrls() {
        return Arrays.stream(this.reader.readLine().split("\\s+")).collect(Collectors.toSet());
    }

    private List<String> readRequestAsList() throws IOException {
        List<String> result = new ArrayList<>();
        String input;
        while (!("".equals(input = this.reader.readLine()))) {
            result.add(input);
        }

        result.add("");

        while (!("".equals(input = this.reader.readLine()))) {
            result.add(input);
        }

        return result;
    }

    private String getCookiesFormatted(HttpRequest httpRequest) {
        StringBuilder sb = new StringBuilder();

        for (Cookie cookie : httpRequest.getCookies()) {
            sb.append(cookie.toString()).append(System.lineSeparator());
        }

        if (sb.length() == 0) {
            return "No cookies!";
        }

        return sb.toString().trim();
    }
}
