package kuit.springbasic.core.mvc.v1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.util.UserSessionUtils;
import kuit.springbasic.core.mvc.view.JsonView;
import kuit.springbasic.core.mvc.view.JspView;
import kuit.springbasic.core.mvc.view.View;
import kuit.springbasic.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name = "dispatcherV1", urlPatterns = "/v1/*", loadOnStartup = 1)
public class DispatcherServletV1 extends HttpServlet {

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSON_VIEW_PREFIX = "jsonView";

    private RequestMappingV1 requestMapping;

    @Override
    public void init() {
        requestMapping = new RequestMappingV1();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.info("--- DispatcherServletV1 ---");

        ControllerV1 controller = getController(request, response);
        if (controller == null) return;

        ModelAndView modelAndView = getModelAndView(request, controller);
        String viewName = modelAndView.getViewName();
        if (viewName == null) return;

        View view = getView(viewName);
        view.render(request, response, modelAndView.getModel());
    }

    private ControllerV1 getController(HttpServletRequest request, HttpServletResponse response) {
        ControllerV1 controller = requestMapping.getController(request);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        setControllerFields(request, controller);
        return controller;
    }

    private static void setControllerFields(HttpServletRequest request, ControllerV1 controller) {
        HttpSession session = request.getSession();
        controller.setSession(session);

        boolean isLoggedIn = UserSessionUtils.isLoggedIn(session);
        controller.setIsLoggedIn(isLoggedIn);
        log.info("isLoggedIn={}", isLoggedIn);

        if (isLoggedIn) {
            User userFromSession = UserSessionUtils.getUserFromSession(session);
            controller.setUserFromSession(userFromSession);
        }
    }

    private ModelAndView getModelAndView(HttpServletRequest request, ControllerV1 controller) {
        Map<String, String> params = createParamMap(request);
        ModelAndView modelAndView;
        try {
            modelAndView = controller.execute(params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> params.put(paramName, request.getParameter(paramName)));
        return params;
    }

    private View getView(String viewName) {
        if (viewName.equals(JSON_VIEW_PREFIX)) {
            return new JsonView();
        }
        boolean isRedirect = viewName.startsWith(REDIRECT_PREFIX);
        return jspViewResolver(viewName, isRedirect);
    }

    private JspView jspViewResolver(String viewName, boolean isRedirect) {
        if (isRedirect) {
            return new JspView(viewName.substring(REDIRECT_PREFIX.length()), true);
        }
        return new JspView("/WEB-INF/" + viewName + ".jsp", false);
    }

}
