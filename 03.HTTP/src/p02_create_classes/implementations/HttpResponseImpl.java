package p02_create_classes.implementations;

import p02_create_classes.interfaces.HttpRequest;
import p02_create_classes.interfaces.HttpResponse;

import java.util.*;
import java.util.stream.Collectors;

public class HttpResponseImpl implements HttpResponse {
    private static Map<Integer, String> codeMessages = new HashMap<Integer, String>() {{ //not sure if this is bad code or not
        put(404, "Not Found | The requested functionality was not found.");
        put(401, "Unauthorized | You are not authorized to access the requested functionality.");
        put(400, "Not Found | There was an error with the requested functionality due to malformed request.");
        put(200, "OK");
    }};

    private Map<String, String> headers;
    private int statusCode;
    private String httpVersion;
    private byte[] content;
    private byte[] responseMessage;
    private HttpRequest httpRequest;
    private Set<String> validUrls;
    private byte[] bytes;

    public HttpResponseImpl(HttpRequest httpRequest, Set<String> validUrls) {
        this.httpRequest = httpRequest;
        this.validUrls = validUrls;
        this.httpVersion = httpRequest.getHttpVersion();
        this.headers = new LinkedHashMap<>(httpRequest.getHeaders());
        this.headers.remove("Authorization"); //TODO possible bug if Authorization is not there

        if (!httpRequest.getHeaders().containsKey("Authorization"))
            this.setStatusCode(401);

        if ("POST".equals(httpRequest.getMethod()) && httpRequest.getBodyParameters().isEmpty())
            this.setStatusCode(400);

        if (!validUrls.contains(httpRequest.getRequestUrl()))
            this.setStatusCode(404);

        if (this.statusCode == 0) {
            this.statusCode = 200;
        }

        this.setBytes();
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContent() {
        return codeMessages.get(this.statusCode).split(" \\| ")[1].getBytes();
    }

    private void setBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.httpVersion).append(" ")
                .append(this.statusCode).append(" ")
                .append(codeMessages.get(this.statusCode).split(" \\| ")[0])
                .append(System.lineSeparator());

        this.headers.entrySet().forEach(kvp -> {
            sb.append(kvp.getKey()).append(" ").append(kvp.getValue()).append(System.lineSeparator());
        });

        sb.append(System.lineSeparator());

        if (this.statusCode / 100 != 2) {
            sb.append(codeMessages.get(this.statusCode).split(" \\| ")[1]);
            this.bytes = sb.toString().getBytes();
            return;
        }

        String authorizationLine = this.httpRequest.getHeaders().get("Authorization");
        String encodedInfo = authorizationLine.replace("Basic", "").trim();
        String decodedInfo = this.decode64Bit(encodedInfo);

        sb.append("Greetings ").append(decodedInfo).append("! ");

        sb.append(String.format("You have successfully created %s with ", this.httpRequest.getBodyParameters().get("name")));
        sb.append(String.join(", ",
                this.httpRequest.getBodyParameters().entrySet().stream()
                        .skip(1)
                        .map(kvp -> kvp.getKey() + " - " + kvp.getValue()).collect(Collectors.toList())));

        sb.append(".");

        this.bytes = sb.toString().getBytes();
    }

    @Override
    public byte[] getBytes() { //should return the whole response
        return this.bytes;
    }

    @Override
    public void setStatusCode(int statusCode) { //there shouldn't be a setter for the status code. Not a public one at least
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) { //shouldn't be public

    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }



    private String decode64Bit(String encoded) {
        byte[] decoded = Base64.getDecoder().decode(encoded);
        return new String(decoded);
    }
}
