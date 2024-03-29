package com.example.demo.util;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.demo.History;

public class SessionUtils {
    @SuppressWarnings("unchecked")
    public static Optional<List<History>> getHistories(HttpSession session) {
        Object historiesObject = session.getAttribute("histories");
        if (historiesObject instanceof List) {
            List<?> temp = (List<?>) historiesObject;
            if (!temp.isEmpty() && temp.get(0) instanceof History) {
                return Optional.of((List<History>) temp);
            }
        }
        return Optional.empty();
    }
}