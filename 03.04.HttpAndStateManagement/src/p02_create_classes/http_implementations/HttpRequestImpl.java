package p02_create_classes.http_implementations;

import p02_create_classes.interfaces.Cookie;
import p02_create_classes.interfaces.HttpRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HttpRequestImpl implements HttpRequest {
    private static String HEADER_SPLITTER = ": ";
    private static String PARAM_SPLITTER = "&";
    private static String PARAM_VALUE_SPLITTER = "=";
    private static String COOKIE_PARAM_VALUE_SPLITTER = "=";
    private static String COOKIES_SPLITTER = "; ";

    private String method;
    private String url;
    private String httpVersion;
    private Map<String, String> headers;
    private Map<String, String> bodyParams;
    private List<Cookie> cookies;

    public HttpRequestImpl() {
    }

    public HttpRequestImpl(List<String> httpRequest) {
        this.init(httpRequest);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return Collections.unmodifiableMap(this.bodyParams);
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
        return this.url;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.url = requestUrl;
    }

    @Override
    public String getHttpVersion() {
        return this.httpVersion;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.put(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParams.put(parameter, value);
    }

    /**
     * Checks if the url contains a "."
     * @return true if the requested by the client url is a resource, e.g. "/some_picture.jpg"
     * or false if it is a path, e.g. "/index"
     */
    @Override
    public boolean isResource() {
        return this.url.contains(".");
    }

    @Override
    public List<Cookie> getCookies() {
        return Collections.unmodifiableList(this.cookies);
    }

    @Override
    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Method = ").append(this.method).append(", ")
                .append("Url = ").append(this.url).append(", ")
                .append("Http Version = ").append(this.httpVersion);

        sb.append(System.lineSeparator());

        sb.append("Header: ").append(System.lineSeparator());
        if (this.headers.isEmpty()) {
            sb.append("\t").append("No headers!").append(System.lineSeparator());
        } else {
            for (Map.Entry<String, String> headerValue : this.headers.entrySet()) {
                sb.append("\t").append(headerValue.getKey()).append(" -> ").append(headerValue.getValue())
                        .append(System.lineSeparator());
            }
        }

        sb.append("Body params:").append(System.lineSeparator());
        if (this.bodyParams.isEmpty()) {
            sb.append("\t").append("No body parameters!");
        } else {
            for (Map.Entry<String, String> paramValue : this.bodyParams.entrySet()) {
                sb.append("\t").append(paramValue.getKey()).append(" -> ").append(paramValue.getValue())
                        .append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    private void init(List<String> httpRequest) { //TODO cookies part
        String requestLine = this.getRequestLine(httpRequest);

        this.setMethod(this.extractMethod(requestLine));
        this.setRequestUrl(this.extractUrl(requestLine));
        this.httpVersion = this.extractHttpVersion(requestLine);

        this.headers = this.extractHeaders(httpRequest);
        if (!this.headers.containsKey("Date")) { //TODO does every http request must have a date?
            this.addHeader("Date", this.getCurrentDate());
        }

        this.cookies = this.extractCookies();

        this.bodyParams = this.extractBodyParams(httpRequest);
    }

    private String getCurrentDate() {
        String dateFormat = "dd/MM/yyyy";
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        return localDate.format(formatter);
    }

    private String getRequestLine(List<String> httpRequest) {
        return httpRequest.get(0);
    }

    private String extractMethod(String requestLine) {
        return requestLine.split("\\s+")[0];
    }

    private String extractUrl(String requestLine) {
        return requestLine.split("\\s+")[1];
    }

    private String extractHttpVersion(String requestLine) {
        return requestLine.split("\\s+")[2];
    }

    private Map<String, String> extractHeaders(List<String> httpRequest) {
        Map<String, String> result = new LinkedHashMap<>();

        int indexOfEmptyString = httpRequest.indexOf(""); //Line of CRLF
        httpRequest.subList(1, indexOfEmptyString)
                .stream()
                .forEach(l -> {
                    result.put(l.split(HEADER_SPLITTER)[0], l.split(HEADER_SPLITTER)[1]);
                });

        return result;
    }

    private List<Cookie> extractCookies() {
        List<Cookie> cookies = new ArrayList<>();

        String[] cookiesTokens = this.headers.get("Cookie").split(COOKIES_SPLITTER);

        for (String cookieToken : cookiesTokens) {
            cookies.add(
                    new CookieImpl(cookieToken.split(COOKIE_PARAM_VALUE_SPLITTER)[0],
                            cookieToken.split(COOKIE_PARAM_VALUE_SPLITTER)[1]));
        }

        return cookies;
    }

    private Map<String, String> extractBodyParams(List<String> httpRequest) {
        Map<String, String> result = new LinkedHashMap<>();

        List<String> httpBody = httpRequest.subList(httpRequest.indexOf("") + 1, httpRequest.size());

        for (String line : httpBody) {
            String[] kvps = line.split(PARAM_SPLITTER);
            for (int i = 0; i < kvps.length; i++) {
                result.put(kvps[i].split(PARAM_VALUE_SPLITTER)[0], kvps[i].split(PARAM_VALUE_SPLITTER)[1]);
            }
        }

        return result;
    }
}
