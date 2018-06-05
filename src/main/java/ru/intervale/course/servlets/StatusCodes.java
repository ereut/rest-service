package ru.intervale.course.servlets;

public enum StatusCodes {
    SUCCESS(200, "OK"), NOT_FOUND(404, "Not Found"), BAD_REQUEST(400, "Bad Request"),
    SERVER_ERROR(500, "Internal Server Error");

    private int codeNumber;
    private String description;

    private StatusCodes(int codeNumber, String description) {
        this.codeNumber = codeNumber;
        this.description = description;
    }

    public String toString() {
        return "Response code:" + codeNumber + " " + description;
    }

}
