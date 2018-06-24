package ru.intervale.course.servlets;

import com.google.common.base.Strings;

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
    public static Session getSession(Integer customerId) {
        return customerSessionMap.get(customerId);
    }

    public  static boolean isCurrentSessionValid(Integer customerId) {
        Session session = customerSessionMap.get(customerId);

        return session != null && new Date().before(customerSessionMap.get(customerId).getFinishSessionTime());

    }

    public static void invalidateSession(Integer customerId) {
        customerSessionMap.remove(customerId);
    }

    @Nullable
    public static Integer getCustomerId(String sessionId) {

        if (Strings.isNullOrEmpty(sessionId)) {
            return null;
        }

        Optional<Integer> optional= customerSessionMap.entrySet()
               .stream()
               .filter(entry -> sessionId.equals(entry.getValue().getSessionId()))
               .findFirst()
               .map(Map.Entry::getKey);
        return optional.orElse(null);
    }

    private static Date getSessionDurationTime() {
        return new Date(new Date().getTime() + SESSION_DURATION);
    }


}
