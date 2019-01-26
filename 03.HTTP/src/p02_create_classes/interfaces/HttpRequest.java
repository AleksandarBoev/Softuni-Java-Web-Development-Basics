package p02_create_classes.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface HttpRequest {
    Map<String, String> getHeaders();

    Map<String, String> getBodyParameters();

    String getMethod();

    void setMethod(String method);

    String getRequestUrl();

    void setRequestUrl(String requestUrl);

    String getHttpVersion();

    void setHttpVersion(String httpVersion);

    void addHeader(String header, String value);

    void addBodyParameters(String parameter, String value);

    boolean isResource();
}
