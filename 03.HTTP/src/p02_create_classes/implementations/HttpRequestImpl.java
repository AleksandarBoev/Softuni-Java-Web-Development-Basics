package p02_create_classes.implementations;

import p02_create_classes.interfaces.HttpRequest;

import java.util.*;

public class HttpRequestImpl implements HttpRequest {
    private String method;
    private String requestUrl;
    private String httpVersion;
    private Map<String, String> headers;
    private Map<String, String> bodyParameters;

    public HttpRequestImpl(String httpRequest) {
        String[] tokens = httpRequest.split("\n");

        String[] requestLine = tokens[0].split("\\s+");
        this.setMethod(requestLine[0]);
        this.setRequestUrl(requestLine[1]);
        this.setHttpVersion(requestLine[2]);

        this.headers = new LinkedHashMap<>();
        int i;
        for (i = 1; i < tokens.length; i++) {
            if (!("".equals(tokens[i]))) {
                String[] kvp = tokens[i].split(": ");
                this.headers.put(kvp[0], kvp[1]);
            }
            else
                break;
        }

        this.bodyParameters = new LinkedHashMap<>();
        if (i != tokens.length - 1) { //if there is something after the head, then add it
            for (int j = i + 1; j < tokens.length; j++) {
                if (!("".equals(tokens[j]))) {
                    String[] kvpParams = tokens[j].split("&");

                    for (int k = 0; k < kvpParams.length; k++) {
                        this.bodyParameters.put(kvpParams[k].split("=")[0], kvpParams[k].split("=")[1]);
                    }
                }
            }
        }

    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParameters);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String getHttpVersion() {
        return this.httpVersion;
    }

    @Override
    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameters(String parameter, String value) {
        this.bodyParameters.put(parameter, value);
    }

    @Override
    public boolean isResource() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Method = ").append(this.method).append(System.lineSeparator());
        sb.append("Request url = ").append(this.requestUrl).append(System.lineSeparator());
        sb.append("HTTP version = ").append(this.httpVersion).append(System.lineSeparator());

        sb.append("Headers: ").append(System.lineSeparator());
        for (Map.Entry<String, String> stringStringEntry : this.headers.entrySet()) {
            sb.append("\t").append(stringStringEntry.getKey()).append(": ").append(stringStringEntry.getValue()).append(System.lineSeparator());
        }

        sb.append("Body params: ").append(System.lineSeparator());
        for (Map.Entry<String, String> stringStringEntry : this.bodyParameters.entrySet()) {
            sb.append("\t").append(stringStringEntry.getKey()).append(" = ").append(stringStringEntry.getValue()).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
