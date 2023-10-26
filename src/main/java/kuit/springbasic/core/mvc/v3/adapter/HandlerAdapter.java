package kuit.springbasic.core.mvc.v3.adapter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.core.mvc.v1.ControllerV1;
import kuit.springbasic.core.mvc.v2.ControllerV2;
import kuit.springbasic.core.model.ModelAndView;
import kuit.springbasic.core.util.UserSessionUtils;
import kuit.springbasic.domain.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public interface HandlerAdapter {

    boolean supports(Object handler);

    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException, SQLException;

    default Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

    default void setControllerFields(HttpServletRequest request, ControllerV1 controller) {
        HttpSession session = request.getSession();
        controller.setSession(session);

        boolean isLoggedIn = UserSessionUtils.isLoggedIn(session);
        controller.setIsLoggedIn(isLoggedIn);

        if (isLoggedIn) {
            User userFromSession = UserSessionUtils.getUserFromSession(session);
            controller.setUserFromSession(userFromSession);
        }
    }

    default void setControllerFields(HttpServletRequest request, ControllerV2 controller) {
        HttpSession session = request.getSession();
        controller.setSession(session);

        boolean isLoggedIn = UserSessionUtils.isLoggedIn(session);
        controller.setIsLoggedIn(isLoggedIn);

        if (isLoggedIn) {
            User userFromSession = UserSessionUtils.getUserFromSession(session);
            controller.setUserFromSession(userFromSession);
        }
    }

}
