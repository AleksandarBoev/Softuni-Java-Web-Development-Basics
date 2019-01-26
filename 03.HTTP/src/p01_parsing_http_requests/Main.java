package p01_parsing_http_requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Set<String> validUrls = new HashSet<>(Arrays.asList(reader.readLine().split("\\s+")));

        List<String> httpRequest = new ArrayList<>();

        String input;
        while (!(input = reader.readLine()).equals("end"))
            httpRequest.add(input);

        reader.close();

        //String[] requestLineTokens = httpRequestInfo.get(0).split("\\s+");
        String[] requestLineTokens = httpRequest.get(0).split("\\s+");
        String method = requestLineTokens[0];
        String url = requestLineTokens[1];
        String httpVersion = requestLineTokens[2];

        List<String> httpHeader = new ArrayList<>();
        List<String> httpBody = new ArrayList<>();

        boolean hasEmptyLine = false;
        int i;
        for (i = 1; i < httpRequest.size(); i++) {
            String currentLine = httpRequest.get(i);

            if ("".equals(currentLine)) {
                hasEmptyLine = true;
                continue;
            }

            if (!hasEmptyLine) {
                if (lineIsImportant(currentLine))
                    httpHeader.add(currentLine);
            } else {
                httpBody.add(currentLine);
            }
        }

        StringBuilder httpResponse = new StringBuilder();

        String responseFirstLine = "HTTP/1.1 200 OK";
        String responseMessage = "";
        String extractedAuthorizationLine = extractAuthorizationLine(httpHeader);

        if (!validUrls.contains(url)) {
            responseFirstLine = httpVersion + " 404 Not Found";
            responseMessage = "The requested functionality was not found.";
        } else if (extractedAuthorizationLine == null) {
            responseFirstLine = httpVersion + " 401 Unauthorized";
            responseMessage = "You are not authorized to access the requested functionality.";
        } else if ("POST".equals(method) && (httpBody.isEmpty() || getMessage(httpBody.get(0)) == null)) {
            responseFirstLine = httpVersion + " HTTP/1.1 400 Bad Request";
            responseMessage = "There was an error with the requested functionality due to malformed request.";
        }

        httpResponse.append(responseFirstLine).append(System.lineSeparator());

        httpResponse.append(String.join(System.lineSeparator(), httpHeader)).append(System.lineSeparator());

        if ("".equals(responseMessage)) {
            String userName = extractedAuthorizationLine.replace("Authorization: Basic ", "");
            userName = decode64Bit(userName);
            responseMessage = "Greetings "+ userName + "! " + getMessage(httpBody.get(0));
            httpResponse.append(responseMessage);
        } else {
            httpResponse.append(responseMessage);
        }

        System.out.println(httpResponse.toString());
    }

    static String decode64Bit(String encoded) {
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded);
    }

    static boolean hasBody(List<String> httpRequest) {
        return httpRequest.contains("") || httpRequest.contains("\n") || httpRequest.contains(System.lineSeparator());
    }

    static String extractAuthorizationLine(List<String> httpRequest) {
        String result = null; //if it returns null, then such a line has not been found

        for (int i = 0; i < httpRequest.size(); i++) {
            if (httpRequest.get(i).contains("Authorization: ")) {
                result = httpRequest.get(i);
                httpRequest.remove(i);
                break;
            }
        }

        return result;
    }

    static boolean lineIsImportant(String line) {
        return line.contains("Date: ") || line.contains("Host: ")
                || line.contains("Content-Type: ") || line.contains("Authorization: ");
    }

    //name=Yum&quantity=50&price=10
    static String getMessage(String info) {
        String[] tokens = info.split("&");

        if (tokens.length < 2 || !tokens[0].contains("name"))
            return null;

        StringBuilder result = new StringBuilder();
        result.append("You have successfully created ")
                .append(tokens[0].replace("name=", ""))
                .append(" with ");

        for (int i = 1; i < tokens.length; i++) {
            String[] keyValue = tokens[i].split("=");
            result.append(keyValue[0]).append(" - ").append(keyValue[1]).append(", ");
        }

        return result.toString().substring(0, result.toString().length() - 2) + ".";
    }
}
