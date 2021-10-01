package am.aca.generactive.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public static Long getLastPathSegment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lastPathSegment = getPathLastSegment(req.getRequestURI());
        long itemId;
        try {
            itemId = Long.parseLong(lastPathSegment);
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
