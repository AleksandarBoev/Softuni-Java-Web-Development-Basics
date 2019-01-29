package p02_create_classes.interfaces;

import java.util.Map;

public interface HttpResponse {
    Map<String, String> getHeaders();

    void addHeader(String header, String value);

    /**
     * @return the status code. E.g. 200 for "OK", 400 for "Unauthorized", etc.
     */
    int getStatusCode();

    void setStatusCode(int statusCode);

    /**
     * @return the body of the http response
     */
    byte[] getContent();

    void setContent(byte[] content);

    /**
     * @return the entire http response in the form of an array of bytes. To get the string
     * representation do this: "String stringForm = new String(byteArray);"
     */
    byte[] getBytes();
}
