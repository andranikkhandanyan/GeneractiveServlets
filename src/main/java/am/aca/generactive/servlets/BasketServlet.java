//package am.aca.generactive.servlets;
//
//import am.aca.generactive.config.ApplicationContainer;
//import am.aca.generactive.model.Basket;
//import am.aca.generactive.repository.BasketRepository;
//import am.aca.generactive.repository.ItemRepositoryImpl;
//import am.aca.generactive.util.URLUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import static am.aca.generactive.servlets.HttpConstants.CONTENT_TYPE_JSON;
//
//@WebServlet(name = "BasketServlet", urlPatterns = "/basket/*")
//public class BasketServlet extends HttpServlet {
//
//    private final BasketRepository basketRepository = ApplicationContainer
//            .context.getBean(BasketRepository.class);
//
//    /**
//     * Get all baskets
//     */
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType(CONTENT_TYPE_JSON);
//        Long basketId = URLUtils.getLastPathSegment(req, resp);
//        if (basketId == null) return;
//
//        Optional<Basket> basketOpt = basketRepository.getBasket(basketId.longValue());
//        if (basketOpt.isPresent()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            resp.getWriter().write(objectMapper.writeValueAsString(basketOpt.get()));
//        } else {
//            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            resp.getWriter().write("Resource not found: " + basketId);
//        }
//    }
//
//}
