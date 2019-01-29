package p02_create_classes.interfaces;

import java.util.List;
import java.util.Map;

public interface HttpRequest {
    Map<String, String> getHeaders();

    Map<String, String> getBodyParameters();

    String getMethod();

    void setMethod(String method);

    String getRequestUrl();

    void setRequestUrl(String requestUrl);

    String getHttpVersion();

    void addHeader(String header, String value);

    void addBodyParameter(String parameter, String value);

    /*
    The isResource() method should check if the requestedUrl
     is a resource and not an actual route, and should return a
     boolean result.
     */
    boolean isResource();

    List<Cookie> getCookies();

    void addCookie(Cookie cookie);
}
