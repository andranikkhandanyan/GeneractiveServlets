package am.aca.generactive.servlets;

import am.aca.generactive.config.ApplicationContainer;
import am.aca.generactive.model.Group;
import am.aca.generactive.repository.GroupRepository;
import am.aca.generactive.repository.ItemRepositoryImpl;
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

    private final GroupRepository groupRepository = ApplicationContainer
            .context.getBean(GroupRepository.class);

    /**
     * Get all groups
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE_JSON);
        Long groupId = URLUtils.getLastPathSegment(req, resp);
        if (groupId == null) return;

        Optional<Group> groupOpt = groupRepository.getGroup(groupId);
        if (groupOpt.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(groupOpt.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Resource not found: " + groupId);
        }
    }

}
