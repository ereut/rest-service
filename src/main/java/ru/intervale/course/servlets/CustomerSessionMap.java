package ru.intervale.course.servlets;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerSessionMap {

    private final static  int SESSION_DURATION = 20 * 60 * 1000;

    private static Map<Integer, Session> customerSessionMap;

    static {

        customerSessionMap = new ConcurrentHashMap<>();
    }

    @Nullable
    public static Session insertSession(Integer customerId, String sessionId) {
        return customerSessionMap.put(customerId, new Session(sessionId,
                getSessionDurationTime()));
    }

    public static void updateSession(Integer customerId) {
        customerSessionMap.get(customerId).setFinishSessionTime(getSessionDurationTime());
    }

    @Nullable
    public static String getValidSessionId(Integer customerId) {
        Session session = customerSessionMap.get(customerId);
        if (session == null) {
            return null;
        }
        if (new Date().before(session.getFinishSessionTime())) {
            return session.getSessionId();
        }
        return null;
    }

    @Nullable
    public static Session getSession(Integer customerId) {
        return customerSessionMap.get(customerId);
    }

    public  static boolean isCurrentSessionValid(Integer customerId) {
        return new Date().before(customerSessionMap.get(customerId).getFinishSessionTime());
    }

    public static void invalidateSession(Integer customerId) {
        customerSessionMap.remove(customerId);
    }

    @Nullable
    public static Integer getCustomerId(String sessionId) {
       Optional<Integer> optional= customerSessionMap.entrySet()
               .stream()
               .filter(entry -> sessionId.equals(entry.getValue().getSessionId()))
               .findFirst()
               .map(entry -> entry.getKey());
       if (optional.isPresent()) {
           return optional.get();
       }
       return null;
    }

    private static Date getSessionDurationTime() {
        return new Date(new Date().getTime() + SESSION_DURATION);
    }

    private static void printMap() {
        for (Map.Entry<Integer, Session> entry : customerSessionMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getFinishSessionTime());
        }
    }

    public static void main (String[] args) throws InterruptedException {

        insertSession(1, "aaaaaaa");
        System.out.println(getCustomerId("aaadeaaaa"));



        /*Thread.sleep(5000);
        insertSession(2, "aaaaaaa");
        printMap();
        System.out.println(isCurrentSessionValid(1));
        Thread.sleep(5000);
        updateSession(1);
        invalidateSession(1);
        printMap();
        */

    }








}
