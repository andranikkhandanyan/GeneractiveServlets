package am.aca.generactive.servlets;

import am.aca.generactive.config.ApplicationContainer;
import am.aca.generactive.model.Item;
import am.aca.generactive.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;

import static am.aca.generactive.servlets.HttpConstants.CONTENT_TYPE_JSON;

@WebServlet(name = "ItemsSearchServlet", value = "/item/search")
public class ItemsSearchServlet extends HttpServlet {

    public static final String PARAM_NAME = "name";
    public static final String PARAM_PRICE_GT = "priceGT";
    public static final String PARAM_PRICE_LT = "priceLT";

    private final ItemService itemService = ApplicationContainer
            .context.getBean(ItemService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE_JSON);

        Predicate<Item> searchPredicate = item -> true;
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String value = req.getParameter(key);
            if (value != null && !value.isEmpty()) {
                Predicate<Item> predicate = getPredicateForParam(key, value);
                searchPredicate = searchPredicate.and(predicate);
            }
        }

        // FIXME
        List<? extends Item> rv = itemService.getAll();

        ObjectMapper objectMapper = new ObjectMapper();
        resp.getWriter().write(objectMapper.writeValueAsString(rv));
    }

    private Predicate<Item> getPredicateForParam(String key, String value) {
        switch (key) {
            case PARAM_NAME:
                return getNamePredicate(value);
            case PARAM_PRICE_GT:
                return getGreaterThanPredicate(Double.parseDouble(value));
            case PARAM_PRICE_LT:
                return getLessThanPredicate(Double.parseDouble(value));
        }

        return item -> true;
    }

    private Predicate<Item> getNamePredicate(String value) {
        return (i) -> i.getName().contains(value);
    }

    private Predicate<Item> getGreaterThanPredicate(double value) {
        return (i) -> i.getBasePrice() >= value;
    }

    private Predicate<Item> getLessThanPredicate(double value) {
        return (i) -> i.getBasePrice() <= value;
    }
}
