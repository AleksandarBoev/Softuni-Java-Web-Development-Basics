package p02_create_classes.enums;

public enum ResponseStatus {
    STATUS_200(200, "OK", ""),
    STATUS_400(400, "Bad Request", "There was an error with the requested functionality due to malformed request."),
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

    public static ResponseStatus getStatus(int code) { //TODO is this code okay?
        for (ResponseStatus responseStatus : ResponseStatus.values()) {
            if (responseStatus.code == code)
                return responseStatus;
        }

        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public String getBodyMessage() {
        return this.bodyMessage;
    }

    @Override
    public String toString() {
        return this.name() + " | " + this.responseMessage + " | " + this.code + " | " + this.bodyMessage;
    }
}
