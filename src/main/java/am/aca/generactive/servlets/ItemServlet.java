package am.aca.generactive.servlets;

import am.aca.generactive.config.ApplicationContainer;
import am.aca.generactive.model.GenerativeItem;
import am.aca.generactive.model.Item;
import am.aca.generactive.repository.ItemRepositoryImpl;
import am.aca.generactive.service.ItemService;
import am.aca.generactive.servlets.enums.ItemType;
import am.aca.generactive.util.URLUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static am.aca.generactive.servlets.HttpConstants.CONTENT_TYPE_JSON;

@WebServlet(name = "ItemServlet", urlPatterns = "/item/*")
public class ItemServlet extends HttpServlet {

    public static final String PARAM_TYPE = "type";

    private final ItemService itemService = ApplicationContainer
            .context.getBean(ItemService.class);

    /**
     * Receive {@link Item} object in JSON format String.
     * Expected url param(s): {@code type}, must be one of {@link ItemType}.
     * Respond with updated {@link Item} and status code {@code 200}.
     * Respond with error message and status code {@code 400}, if missing required
     * param(s), wrong {@link ItemType} string representation,
     * code {@code 404} if item not found.
     *
     * Updated Item will be updated in {@link ItemRepositoryImpl}
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String typeParam = req.getParameter(PARAM_TYPE);
        resp.setContentType(CONTENT_TYPE_JSON);
        if (typeParam == null || typeParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing param: " + PARAM_TYPE);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        ItemType itemType = ItemType.valueOf(typeParam);

        String payload = req.getReader().lines().collect(Collectors.joining());
        Item item;
        switch (itemType) {
            case GENERATIVE:
                item = objectMapper.readValue(payload, GenerativeItem.class);
                break;
            case STOCK:
                // FIXME
                item = objectMapper.readValue(payload, Item.class);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Wrong type: " + itemType);
                return;
        }

        Long itemId = URLUtils.getLastPathSegment(req, resp);
        if (itemId == null) return;

        item.setId(itemId);
        if (itemService.update(item) == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Resource not found: " + itemId);
        }

        resp.getWriter().write(objectMapper.writeValueAsString(item));
    }

    /**
     * Get all items
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long itemId = URLUtils.getLastPathSegment(req, resp);
        if (itemId == null) return;

        Optional<Item> itemOpt = itemService.getItem(itemId);
        if (itemOpt.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            resp.getWriter().write(objectMapper.writeValueAsString(itemOpt.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Resource not found: " + itemId);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long itemId = URLUtils.getLastPathSegment(req, resp);
        if (itemId == null) return;

        if (!itemService.delete(itemId)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Resource not found: " + itemId);
        }
    }
}
