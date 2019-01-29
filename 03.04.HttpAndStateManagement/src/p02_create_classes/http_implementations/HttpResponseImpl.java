package p02_create_classes.http_implementations;

import p02_create_classes.enums.ResponseStatus;
import p02_create_classes.interfaces.HttpRequest;
import p02_create_classes.interfaces.HttpResponse;

import java.util.*;
import java.util.stream.Collectors;

public class HttpResponseImpl implements HttpResponse {
    private Map<String, String> headers;
    private byte[] content;
    private byte[] entireResponse;
    private ResponseStatus responseStatus;
    private HttpRequest httpRequest;
    private Set<String> validUrls;

    public HttpResponseImpl(HttpRequest httpRequest, Set<String> validUrls) {
        this.httpRequest = httpRequest;
        this.validUrls = validUrls;
        this.init();
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public int getStatusCode() { //TODO this is just wrong
        return this.responseStatus.getCode();
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.responseStatus = ResponseStatus.getStatus(statusCode);
    }

    @Override
    public byte[] getContent() {
        //Construct it every time, because some values might have been altered via setters
        return this.constructBodyMessage().getBytes();
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public byte[] getBytes() {
        return this.constructHttpResponse().getBytes();
    }

    private void init() {
        this.headers = new LinkedHashMap<>(this.httpRequest.getHeaders());

        this.headers.remove("Authorization");

        this.setStatusCode(this.initializeStatusCode());
        this.content = this.constructBodyMessage().getBytes();
        this.entireResponse = this.constructHttpResponse().getBytes();
    }

    private int initializeStatusCode() {
        int statusCode = 200;

        if (!this.validUrls.contains(this.httpRequest.getRequestUrl())) { //if the path/resource (url) is not valid
            statusCode = 404;
        }

        if (!this.httpRequest.getHeaders().containsKey("Authorization")) {
            statusCode = 401;
        }

        if ("POST".equals(this.httpRequest.getMethod()) && this.httpRequest.getBodyParameters().isEmpty()) {
            statusCode = 400;
        }

        return statusCode;
    }

    private String decode64Bit(String encoded) {
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded);
    }

    private String getDecodedName() {
        String encoded = this.httpRequest.getHeaders().get("Authorization").replace("Basic", "").trim();
        return decode64Bit(encoded);
    }

    private String constructOkBodyMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Greetings ").append(getDecodedName()).append("! ");

        if ("GET".equals(this.httpRequest.getMethod())) {
            return sb.toString();
        }

        //You have successfully created Yum with quantity – 50, price – 10.
        Map<String, String> bodyParams = this.httpRequest.getBodyParameters();
        sb.append("You have successfully created ").append(bodyParams.get("name")).append(" with ");

        List<String> bodyParamsProcessed = bodyParams.entrySet().stream()
                .filter(kvp -> !kvp.getKey().equals("name"))
                .map(kvp -> kvp.getKey() + " – " + kvp.getValue())
                .collect(Collectors.toList());

        sb.append(String.join(", ", bodyParamsProcessed)).append(".");

        return sb.toString();
    }

    private String constructBodyMessage() {
        if (this.responseStatus.getCode() == 200) {
            return constructOkBodyMessage();
        } else {
            return responseStatus.getBodyMessage();
        }
    }

    private String constructHttpResponse() {
        //TODO get status code, construct first line, construct header, construct body
        String httpRequestVersion = this.httpRequest.getHttpVersion();

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %d %s%n", httpRequestVersion, this.responseStatus.getCode(), this.responseStatus.getResponseMessage()));

        this.headers.entrySet().stream()
                .map(kvp -> kvp.getKey() + ": " + kvp.getValue()
                ).forEach(h -> sb.append(h).append(System.lineSeparator()));

        sb.append(System.lineSeparator());

        sb.append(this.constructBodyMessage());

        return sb.toString();
    }
}
