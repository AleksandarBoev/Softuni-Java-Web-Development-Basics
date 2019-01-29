package p02_create_classes.http_implementations;

import p02_create_classes.interfaces.Cookie;

public class CookieImpl implements Cookie {
    private String key;
    private String value;

    public CookieImpl(String key, String value) {
        this.setKey(key);
        this.setValue(value);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.key + " <-> " + this.value;
    }

    private void setKey(String key) {
        this.key = key;
    }

    private void setValue(String value) {
        this.value = value;
    }
}
