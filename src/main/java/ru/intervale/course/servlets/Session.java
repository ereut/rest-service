package ru.intervale.course.servlets;

import java.util.Date;

public class Session {


    private String sessionId;
    private Date finishSessionTime;

    public Session() {
    }

    public Session(String sessionId, Date finishSessionTime) {
        this.sessionId = sessionId;
        this.finishSessionTime = finishSessionTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setFinishSessionTime(Date finishSessionTime) {
        this.finishSessionTime = finishSessionTime;
    }

    public Date getFinishSessionTime() {
        return finishSessionTime;
    }

}


