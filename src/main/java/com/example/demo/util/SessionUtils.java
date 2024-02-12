package com.example.demo.util;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import com.example.demo.History;

public class SessionUtils {

    @SuppressWarnings("unchecked")
    public static Optional<List<History>> getHistories(HttpSession session) {
        Object historiesObject = session.getAttribute("histories");
        if (historiesObject instanceof List<?> temp) {
            if (!temp.isEmpty() && temp.get(0) instanceof History) {
                return Optional.of((List<History>) historiesObject);
            }
        }
        return Optional.empty();
    }
}
