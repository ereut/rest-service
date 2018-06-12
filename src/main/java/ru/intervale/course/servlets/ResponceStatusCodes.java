package ru.intervale.course.servlets;

public enum ResponceStatusCodes {

    SUCCESS(200, "OK"), NOT_FOUND(404, "Not Found"), BAD_REQUEST(400, "Bad Request"),
    SERVER_ERROR(500, "Internal Server Error");

    private static final String PREFIX_RESPONCE_CODE_MESSAGE = "Responce code: ";

    private int codeNumber;
    private String description;

    private ResponceStatusCodes(int codeNumber, String description) {
        this.codeNumber = codeNumber;
        this.description = description;
    }

    public String toString() {
        return PREFIX_RESPONCE_CODE_MESSAGE + codeNumber + " " + description + "\n";
    }


}
