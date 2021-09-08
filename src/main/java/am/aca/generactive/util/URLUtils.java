package am.aca.generactive.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class URLUtils {

    public static String getPathLastSegment(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        if (url.lastIndexOf("/") == -1) {
            return null;
        }
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static Integer getLastPathSegment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lastPathSegment = getPathLastSegment(req.getRequestURI());
        int itemId;
        try {
            itemId = Integer.parseInt(lastPathSegment);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Expected int: " + lastPathSegment);
            return null;
        }
        return itemId;
    }

    private URLUtils() {

    }
}
