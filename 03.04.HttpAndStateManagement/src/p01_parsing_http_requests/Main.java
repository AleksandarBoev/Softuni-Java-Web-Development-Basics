package p01_parsing_http_requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        //Low performance, but more readable.
        Set<String> validUrls = Arrays.stream(consoleReader.readLine().split("\\s+")).collect(Collectors.toSet());
        List<String> httpRequest = readRequestAsList();

        String response = constructHttpResponse(httpRequest, validUrls);
        System.out.println(response);
    }

    private static List<String> readRequestAsList() throws IOException {
        List<String> result = new ArrayList<>();
        String input;
        while (!("".equals(input = consoleReader.readLine()))) {
            result.add(input);
        }

        result.add("");

        while (!("".equals(input = consoleReader.readLine()))) {
            result.add(input);
        }

        return result;
    }

    private static String getRequestLine(List<String> httpRequest) {
        return httpRequest.get(0);
    }

    private static String getRequestMethod(String requestLine) {
        return requestLine.split("\\s+")[0];
    }

    private static String getRequestUrl(String requestLine) {
        return requestLine.split("\\s+")[1];
    }

    private static String getRequestHttpVersion(String requestLine) {
        return requestLine.split("\\s+")[2];
    }

    private static Map<String, String> getHeaders(List<String> httpRequest) {
        Map<String, String> result = new LinkedHashMap<>();

        int indexOfEmptyString = httpRequest.indexOf("");
        httpRequest.subList(1, httpRequest.indexOf(""))
                .stream()
                .forEach(l -> {
                    result.put(l.split(": ")[0], l.split(": ")[1]);
                });

        return result;
    }

    private static Map<String, String> getBodyParameters(List<String> httpRequest) {
        Map<String, String> result = new LinkedHashMap<>();

        List<String> httpBody = httpRequest.subList(httpRequest.indexOf("") + 1, httpRequest.size());

        for (String line : httpBody) {
            String[] kvps = line.split("&");
            for (int i = 0; i < kvps.length; i++) {
                result.put(kvps[i].split("=")[0], kvps[i].split("=")[1]);
            }
        }

        return result;
    }

    private static String getMapAsString(Map<String, String> someMap) {
        if (someMap.isEmpty())
            return "";

        StringBuilder sb = new StringBuilder();

        someMap.entrySet().forEach(kvp -> {
            sb.append("\t").append(kvp.getKey()).append(" -> ").append(kvp.getValue()).append("\n");
        });

        return sb.toString().substring(0, sb.length() - 1);
    }


    //HTTP RESPONSE PART______________________________________________
    static int getStatusCode(List<String> httpRequest, Set<String> validUrls) {
        int statusCode = 200;

        if (!validUrls.contains(getRequestUrl(getRequestLine(httpRequest)))) { //if the path/resource (url) is not valid
            statusCode = 404;
        }

        if (!getHeaders(httpRequest).containsKey("Authorization")) {
            statusCode = 401;
        }

        if ("POST".equals(getRequestMethod(getRequestLine(httpRequest))) && getBodyParameters(httpRequest).isEmpty()) {
            statusCode = 400;
        }

        return statusCode;
    }

    enum ResponseStatus {
        STATUS_200(200, "OK", ""),
        STATUS_400(400, "Unauthorized", "There was an error with the requested functionality due to malformed request."),
        STATUS_401(401, "Unauthorized", "You are not authorized to access the requested functionality."),
        STATUS_404(404, "Not Found", "The requested functionality was not found.");

        private int code;
        private String responseMessage;
        private String bodyMessage;

        ResponseStatus(int code, String responseMessage, String bodyMessage) {
            this.code = code;
            this.responseMessage = responseMessage;
            this.bodyMessage = bodyMessage;
        }

        static ResponseStatus getStatus(int code) { //TODO is this okay to do?
            for (ResponseStatus responseStatus : ResponseStatus.values()) {
                if (responseStatus.code == code)
                    return responseStatus;
            }

            return null;
        }

        @Override
        public String toString() {
            return this.name() + " | " + this.responseMessage + " | " + this.code + " | " + this.bodyMessage;
        }
    }

    static String decode64Bit(String encoded) {
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded);
    }

    static String getDecodedName(List<String> httpRequest) {
        String encoded = getHeaders(httpRequest).get("Authorization").replace("Basic", "").trim();
        return decode64Bit(encoded);
    }

    static String constructOkBodyMessage(List<String> httpRequest) {
        StringBuilder sb = new StringBuilder();

        sb.append("Greetings ").append(getDecodedName(httpRequest)).append("! ");

        if ("GET".equals(getRequestMethod(getRequestLine(httpRequest)))) {
            return sb.toString();
        }

        //You have successfully created Yum with quantity – 50, price – 10.
        Map<String, String> bodyParams = getBodyParameters(httpRequest);
        sb.append("You have successfully created ").append(bodyParams.get("name")).append(" with ");

        List<String> bodyParamsProcessed = bodyParams.entrySet().stream()
                .map(kvp -> kvp.getKey() + " - " + kvp.getValue())
                .collect(Collectors.toList());

        sb.append(String.join(", ", bodyParamsProcessed)).append(".");

        return sb.toString();
    }

    static String constructHttpResponse(List<String> httpRequest, Set<String> validUrls) {
        //TODO get status code, construct first line, construct header, construct body

        String httpRequestLine = getRequestLine(httpRequest);

        String httpRequestVersion = getRequestHttpVersion(httpRequestLine);

        int statusCode = getStatusCode(httpRequest, validUrls);
        ResponseStatus responseStatus = ResponseStatus.getStatus(statusCode);

        Map<String, String> httpResponseHeader = getHeaders(httpRequest);
        httpResponseHeader.remove("Authorization");

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %d %s%n", httpRequestVersion, responseStatus.code, responseStatus.responseMessage));

        httpResponseHeader.entrySet().stream()
                .map(kvp -> kvp.getKey() + ": " + kvp.getValue()
                ).forEach(h -> sb.append(h).append(System.lineSeparator()));

        sb.append(System.lineSeparator());

        if (responseStatus.code == 200) {
            sb.append(constructOkBodyMessage(httpRequest));
        } else {
            sb.append(responseStatus.bodyMessage);
        }

        return sb.toString();
    }
}


