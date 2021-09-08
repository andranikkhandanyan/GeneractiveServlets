package am.aca.generactive.servlets;

import am.aca.generactive.model.Group;
import am.aca.generactive.repository.GroupJdbcRepository;
import am.aca.generactive.util.URLUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import static am.aca.generactive.servlets.HttpConstants.CONTENT_TYPE_JSON;

@WebServlet(name = "GroupServlet", urlPatterns = "/group/*")
public class GroupServlet extends HttpServlet {

    private final GroupJdbcRepository groupJdbcRepository = new GroupJdbcRepository();

    /**
     * Get all groups
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE_JSON);
        Integer groupId = URLUtils.getLastPathSegment(req, resp);
        if (groupId == null) return;

        Optional<Group> groupOpt = groupJdbcRepository.getGroup(groupId);
        if (groupOpt.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(groupOpt.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Resource not found: " + groupId);
        }
    }

}
