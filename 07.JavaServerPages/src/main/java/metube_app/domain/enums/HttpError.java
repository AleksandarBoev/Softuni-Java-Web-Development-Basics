package metube_app.domain.enums;

public enum HttpError {
    //error codes and descriptions: https://www.restapitutorial.com/httpstatuscodes.html
    ERROR_400(400, "Bad request"), ERROR_404(404, "Not found");

    private int code;
    private String description;

    HttpError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public static HttpError getError(int code) {
        switch (code) {
            case 400:
                return ERROR_400;

            case 404:
                return ERROR_404;

            default:
                return ERROR_404;
        }
    }
}
